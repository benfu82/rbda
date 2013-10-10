package org.rbda.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.Pipeline;


import org.rbda.controls.IOKCancel;
import org.rbda.controls.OKCancelDialog;
import org.rbda.dao.DBException;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.EquationCollection;
import org.rbda.equation.Equation;

import org.rbda.main.ControlConstants;
import org.rbda.main.PipelineAttributePanel;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;
import org.rbda.resource.TextResourceBundle;
import org.rbda.utils.Utils;

/**
 * Action which is related to Pipeline maintaince, such as create.
 * 
 * @author fujunwei
 * 
 */
public class PipelineAction extends AbstractAction {
	public static final String CMD_CREATE = "create";
	public static final String CMD_IMPORT = "import";
	public static final String CMD_EDIT_ATTRS = "edit_attrs";

	private PipelineTree pipelineTree;
	private Frame parent;

	public PipelineAction(String name, PipelineTree pipelineTree) {
		this(name, null, pipelineTree);
	}

	public PipelineAction(String name, Icon icon, PipelineTree pipelineTree) {
		super(name, icon);
		this.pipelineTree = pipelineTree;
	}

	public void setParent(Frame parent) {
		this.parent = parent;
	}

	public void setPipelineTree(PipelineTree pipelineTree) {
		this.pipelineTree = pipelineTree;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (CMD_CREATE.equals(cmd)) {
			// create pipeline
			createPipeline();
		} else if (CMD_IMPORT.equals(cmd)) {
			// import pipeline
			importFromFile();
		} else if (CMD_EDIT_ATTRS.equals(cmd)) {
			openPipelineProperty();
		}

	}

	public static void updateLineAttributeDef(Equation[] equations) {
		String[] paramNames = Utils.getEquationParamNames(equations);
		LineAttributeDefCollection lineAttrDefs = new LineAttributeDefCollection();
		for (int i = 0; i < paramNames.length; i++) {
			lineAttrDefs.addLineAttributeDef(new LineAttributeDef(i + 1,
					paramNames[i]));
		}
		SystemProperties.setProperty(
				SystemProperties.LINE_ATTRIBUTE_COLLECTION, lineAttrDefs);
	}

	public static void updateLineAttributeDef(Equation equation){
		if(equation==null) throw new IllegalArgumentException("param equation should not be null");
		LineAttributeDefCollection lineAttrDefs = (LineAttributeDefCollection)SystemProperties.getProperty(
				SystemProperties.LINE_ATTRIBUTE_COLLECTION);
		if(lineAttrDefs==null)lineAttrDefs = new LineAttributeDefCollection();
		
		int equationParamCount = equation.getParameterCount();
		for (int i = 0; i < equationParamCount; i++) {
			if (!lineAttrDefs.isExisted(equation.getParameterNameAt(i))) {
				lineAttrDefs.addLineAttributeDef(new LineAttributeDef(lineAttrDefs.getCount()+1, equation.getParameterNameAt(i)));
			}
		}
		SystemProperties.setProperty(
				SystemProperties.LINE_ATTRIBUTE_COLLECTION, lineAttrDefs);
	}

	// 创建管道
	private void createPipeline() {
		PipelinePropertyPanel pipelinePropPane = new PipelinePropertyPanel();
		OKCancelDialog dialog = new OKCancelDialog(parent, pipelinePropPane,
				TextResourceBundle.getInstance().getString("CreatePipeline"),
				true);
		dialog.setValidChecker(pipelinePropPane);
		dialog.setSize(new Dimension(ControlConstants.DIALOG_WIDTH_SMALL,
				ControlConstants.DIALOG_HEIGHT_SMALL));
		dialog.setLocationRelativeTo(null);
		dialog.showDialog();
		if (dialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
			Pipeline pipeline = pipelinePropPane.getPipeline();
			pipelineTree.addPipeline(pipeline);
			SystemProperties.setProperty(SystemProperties.STATE_EDITING,
					new Boolean(true));
		}
	}

	// 从外部文件导入管道
	private void importFromFile() {
		SystemProperties.setProperty(SystemProperties.STATE_EDITING,
				new Boolean(true));
	}

	// 打开管道属性
	private void openPipelineProperty() {
		EquationCollection equations = null;
		LineAttributeDefCollection lineAttrDefs = null;
		if (SystemProperties.containsKey(SystemProperties.EQUATION_COLLECTION)) {
			equations = (EquationCollection) SystemProperties
					.getProperty(SystemProperties.EQUATION_COLLECTION);
		}
		if (SystemProperties
				.containsKey(SystemProperties.LINE_ATTRIBUTE_COLLECTION)) {
			lineAttrDefs = (LineAttributeDefCollection) SystemProperties
					.getProperty(SystemProperties.LINE_ATTRIBUTE_COLLECTION);
		}

		PipelineAttributePanel panel = new PipelineAttributePanel(
				pipelineTree.getSelectedPipeline(), lineAttrDefs, equations);
		OKCancelDialog dialog = new OKCancelDialog(
				(Frame) null,
				panel,
				TextResourceBundle.getInstance().getString("PipelineAttribute"),
				true);
		dialog.setLocationRelativeTo(pipelineTree);
		dialog.setSize(ControlConstants.DIALOG_WIDTH_LARGE,
				ControlConstants.DIALOG_HEIGHT_LARGE);
		dialog.showDialog();
		if (dialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
			panel.save();

			Pipeline selectedPipeline = pipelineTree.getSelectedPipeline();
			for (int i = 0; i < selectedPipeline.getSegmentCount(); i++) {
				pipelineTree.addSegment(selectedPipeline.getSegmentAt(i));
			}
			SystemProperties.setProperty(SystemProperties.STATE_EDITING,
					new Boolean(true));
		}
	}

}

class PipelinePropertyPanel extends JPanel implements IOKCancel {
	private JTextField txtName;
	private JTextField txtLength;

	public PipelinePropertyPanel() {

		JLabel lblName = new JLabel(TextResourceBundle.getInstance().getString(
				"Name"));
		txtName = new JTextField(25);

		JLabel lblLength = new JLabel(TextResourceBundle.getInstance()
				.getString("Length"));
		txtLength = new JTextField(25);

		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
		leftPane.add(lblName);
		leftPane.add(Box.createVerticalStrut(ControlConstants.MARGIN * 2));
		leftPane.add(lblLength);

		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
		rightPane.add(txtName);
		rightPane.add(txtLength);

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(Box.createHorizontalStrut(ControlConstants.MARGIN));
		add(leftPane);
		add(rightPane);
		add(Box.createHorizontalStrut(ControlConstants.MARGIN));

	}

	public Pipeline getPipeline() {
		String name = txtName.getText();
		float length = Float.parseFloat(txtLength.getText());
		return new Pipeline(name, 0, length);
	}

	@Override
	public boolean checkInput() {
		String name = txtName.getText();
		String length = txtLength.getText();

		if (name == null || name.isEmpty() || length == null
				|| length.isEmpty()) {
			return false;
		}

		try {
			Float.parseFloat(length);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	@Override
	public void ok() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}
