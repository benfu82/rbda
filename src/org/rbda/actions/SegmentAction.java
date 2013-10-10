package org.rbda.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.rbda.MainFrame;
import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.Pipeline;
import org.rbda.biz.Scenario;
import org.rbda.controls.CheckBoxItem;
import org.rbda.controls.CheckListRenderer;
import org.rbda.controls.IOKCancel;
import org.rbda.controls.OKCancelDialog;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.EquationCollection;

import org.rbda.main.ControlConstants;
import org.rbda.main.PipelineAttributePanel;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;

import org.rbda.resource.TextResourceBundle;
import org.rbda.utils.Utils;

public class SegmentAction extends AbstractAction {
	public static final String CMD_CREATE = "create";

	private MainFrame parent;
	private OKCancelDialog segmentDialog;
	private boolean isOK = false;

	public SegmentAction(String name) {
		this(name, null);
	}

	public SegmentAction(String name, Icon icon) {
		super(name, icon);
	}

	public void setParent(MainFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (CMD_CREATE.equals(cmd)) {
			create();
		}
	}

	private void create() {
		if (segmentDialog == null) {
			SegmentationPanel segPanel = new SegmentationPanel(parent);
			segmentDialog = new OKCancelDialog(parent, segPanel,
					TextResourceBundle.getInstance().getString(
							"AutoSegmentation"), true);
			segmentDialog.setValidChecker(segPanel);
			segmentDialog.setSize(new Dimension(ControlConstants.DIALOG_WIDTH,
					ControlConstants.DIALOG_HEIGHT));
			segmentDialog.setLocationRelativeTo(null);
			segmentDialog.setResizable(false);
		}
		segmentDialog.showDialog();
	}
}

class SegmentationPanel extends JPanel implements ActionListener, IOKCancel {
	private static final String CMD_SELECT_ALL = "select_all";
	private static final String CMD_DISELECT_ALL = "diselect_all";
	private static final String CMD_DISTANCE_SEG = "distance-segmentation";
	private static final String CMD_ATTRIBUTE_SEG = "attribute-segmentation";

	private JList<CheckBoxItem> lineAttrList;
	private DefaultListModel<CheckBoxItem> lineAttrModel;
	private JTextField txtDistance;
	private JPanel btnSelectPanel;
	private JPanel pnlDistanceInput;
	private JRadioButton btnDistanceSeg;
	private JRadioButton btnAttrSeg;

	public SegmentationPanel(MainFrame parent) {
		setLayout(new BorderLayout());

		// init distance segmentation radiobox
		btnDistanceSeg = new JRadioButton();
		btnDistanceSeg.setActionCommand(CMD_DISTANCE_SEG);
		btnDistanceSeg.addActionListener(this);
		JLabel lblDistance = new JLabel(TextResourceBundle.getInstance()
				.getString("DistanceSegPrefix"));
		txtDistance = new JTextField(10);
		JLabel lblDistance2 = new JLabel(TextResourceBundle.getInstance()
				.getString("DistanceSegSuffix"));
		pnlDistanceInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,
				0));
		pnlDistanceInput.add(lblDistance);
		pnlDistanceInput.add(txtDistance);
		pnlDistanceInput.add(lblDistance2);

		JPanel pnlDistanceSeg = new JPanel(
				new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnlDistanceSeg.add(btnDistanceSeg);
		pnlDistanceSeg.add(pnlDistanceInput);

		// init line attribute list
		btnAttrSeg = new JRadioButton(TextResourceBundle.getInstance()
				.getString("AttributeSeg"), true);
		btnAttrSeg.setActionCommand(CMD_ATTRIBUTE_SEG);
		btnAttrSeg.addActionListener(this);
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(btnDistanceSeg);
		btnGroup.add(btnAttrSeg);

		lineAttrModel = new DefaultListModel<CheckBoxItem>();
		LineAttributeDefCollection lineAttrDefs = (LineAttributeDefCollection) SystemProperties
				.getProperty(SystemProperties.LINE_ATTRIBUTE_COLLECTION);
		if (lineAttrDefs != null) {
			for (LineAttributeDef lineAttrDef : lineAttrDefs.getAll()) {
				lineAttrModel.addElement(new CheckBoxItem(lineAttrDef, false));
			}
		}
		lineAttrList = new JList<CheckBoxItem>(lineAttrModel);
		lineAttrList.setCellRenderer(new CheckListRenderer());

		// init buttons
		JButton btnAdd = new JButton(TextResourceBundle.getInstance()
				.getString("SelectAll"));
		btnAdd.setActionCommand(CMD_SELECT_ALL);
		btnAdd.addActionListener(this);
		JButton btnEdit = new JButton(TextResourceBundle.getInstance()
				.getString("DiselectAll"));
		btnEdit.setActionCommand(CMD_DISELECT_ALL);
		btnEdit.addActionListener(this);

		btnSelectPanel = new JPanel();
		btnSelectPanel.setLayout(new BoxLayout(btnSelectPanel,
				BoxLayout.PAGE_AXIS));
		btnSelectPanel.add(btnAdd);
		btnSelectPanel.add(btnEdit);

		JPanel pnlSeg = new JPanel(new BorderLayout());
		pnlSeg.add(btnAttrSeg, BorderLayout.NORTH);
		pnlSeg.add(lineAttrList, BorderLayout.CENTER);
		pnlSeg.add(btnSelectPanel, BorderLayout.EAST);

		//
		JCheckBox chkReSeg = new JCheckBox(TextResourceBundle.getInstance()
				.getString("ReSegmentation"));

		this.setLayout(new BorderLayout());
		this.add(pnlDistanceSeg, BorderLayout.NORTH);
		this.add(pnlSeg, BorderLayout.CENTER);
		this.add(chkReSeg, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (CMD_SELECT_ALL.equals(cmd)) {
			for (int i = 0; i < lineAttrModel.getSize(); i++) {
				CheckBoxItem item = lineAttrModel.getElementAt(i);
				item.setChecked(true);
			}
		} else if (CMD_DISELECT_ALL.equals(cmd)) {
			for (int i = 0; i < lineAttrModel.getSize(); i++) {
				CheckBoxItem item = lineAttrModel.getElementAt(i);
				item.setChecked(false);
			}
		} else if (CMD_DISTANCE_SEG.equals(cmd)) {
			lineAttrList.setEnabled(false);
			btnSelectPanel.setEnabled(false);
			pnlDistanceInput.setEnabled(true);
		} else if (CMD_ATTRIBUTE_SEG.equals(cmd)) {
			lineAttrList.setEnabled(true);
			btnSelectPanel.setEnabled(true);
			pnlDistanceInput.setEnabled(false);
		}
	}

	@Override
	public boolean checkInput() {
		if (btnDistanceSeg.isSelected()) {
			if (!Utils.isNumber(txtDistance.getText())) {
				return false;
			}
		} else if (btnAttrSeg.isSelected()) {
			boolean isChecked = false;
			for (int i = 0; i < lineAttrModel.getSize(); i++) {
				CheckBoxItem item = lineAttrModel.getElementAt(i);
				if (item.isChecked()) {
					isChecked = true;
					break;
				}
			}
			if (!isChecked)
				return false;
		}

		return true;

	}

	@Override
	public void ok() {

	}

	@Override
	public void cancel() {

	}

}
