package org.rbda.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import org.rbda.biz.Segment;
import org.rbda.controls.CheckBoxItem;
import org.rbda.controls.CheckListRenderer;
import org.rbda.controls.EvaluationChartPanel;
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

public class EvaluationAction extends AbstractAction {
	public static final String CMD_POINT_ANALYSE = "point_analyse";
	public static final String CMD_SEGMENT_ANALYSE = "segment_analyse";
	public static final String CMD_PIPELINE_ANALYSE = "pipeline_analyse";

	private MainFrame parent;
	private OKCancelDialog pointAnalyseDialog;
	private OKCancelDialog segmentAnalyseDialog;
	private OKCancelDialog pipelineAnalyseDialog;
	private EvaluationChartPanel pnlEvaluationChart;
	private SegmentSelectionPanel segSelectionPanel;
	private PipelineSelectionPanel pipeSelectionPanel;

	private boolean isOK = false;

	public EvaluationAction(String name) {
		this(name, null);
	}

	public EvaluationAction(String name, Icon icon) {
		super(name, icon);
		
		pnlEvaluationChart = new EvaluationChartPanel();
	}

	public void setParent(MainFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (CMD_POINT_ANALYSE.equals(cmd)) {
			pointAnalyse();
		} else if (CMD_SEGMENT_ANALYSE.equals(cmd)) {
			segmentAnalyse();
		} else if (CMD_PIPELINE_ANALYSE.equals(cmd)) {
			pipelineAnalyse();
		}
	}

	private void pointAnalyse() {
		if (pointAnalyseDialog == null) {
			JPanel segPanel = new JPanel();
			pointAnalyseDialog = new OKCancelDialog(parent, segPanel,
					TextResourceBundle.getInstance().getString(
							"AutoSegmentation"), true);
			// pointAnalyseDialog.setValidChecker(segPanel);
			pointAnalyseDialog.setSize(new Dimension(
					ControlConstants.DIALOG_WIDTH,
					ControlConstants.DIALOG_HEIGHT));
			pointAnalyseDialog.setLocationRelativeTo(null);
			pointAnalyseDialog.setResizable(false);
		}
		pointAnalyseDialog.showDialog();
		if(pointAnalyseDialog.getOptionResult() == OKCancelDialog.DIALOG_OK){
			if(parent.getWorkspaceComponent()==null){
				parent.setWorkspaceComponent(pnlEvaluationChart);
			}
			
		}
	}

	private void segmentAnalyse() {
		if (segmentAnalyseDialog == null) {
			segSelectionPanel = new SegmentSelectionPanel(parent);
			segmentAnalyseDialog = new OKCancelDialog(parent, segSelectionPanel,
					TextResourceBundle.getInstance()
							.getString("SegmentAnalyse"), true);
			segmentAnalyseDialog.setValidChecker(segSelectionPanel);
			segmentAnalyseDialog.setSize(new Dimension(
					ControlConstants.DIALOG_WIDTH,
					ControlConstants.DIALOG_HEIGHT));
			segmentAnalyseDialog.setLocationRelativeTo(null);
			segmentAnalyseDialog.setResizable(false);
		}
		segmentAnalyseDialog.showDialog();
		if(pointAnalyseDialog.getOptionResult() == OKCancelDialog.DIALOG_OK){
			if(parent.getWorkspaceComponent()==null){
				parent.setWorkspaceComponent(pnlEvaluationChart);
			}
			Segment[] segments = segSelectionPanel.getSelectedSegments();
			pnlEvaluationChart.setSegments(segments);
		}
	}

	private void pipelineAnalyse() {
		if (pipelineAnalyseDialog == null) {
			pipeSelectionPanel = new PipelineSelectionPanel(parent);
			pipelineAnalyseDialog = new OKCancelDialog(parent, pipeSelectionPanel,
					TextResourceBundle.getInstance().getString(
							"PipelineAnalyse"), true);
			pipelineAnalyseDialog.setValidChecker(pipeSelectionPanel);
			pipelineAnalyseDialog.setSize(new Dimension(
					ControlConstants.DIALOG_WIDTH,
					ControlConstants.DIALOG_HEIGHT));
			pipelineAnalyseDialog.setLocationRelativeTo(null);
			pipelineAnalyseDialog.setResizable(false);
		}
		pipelineAnalyseDialog.showDialog();
		if(pointAnalyseDialog.getOptionResult() == OKCancelDialog.DIALOG_OK){
			if(parent.getWorkspaceComponent()==null){
				parent.setWorkspaceComponent(pnlEvaluationChart);
			}
			
		}
	}
}

class SegmentSelectionPanel extends JPanel implements IOKCancel {
	private MainFrame mainFrame;
	private PipelineTree pipelineTree;

	public SegmentSelectionPanel(MainFrame mainFrame) {
		setLayout(new BorderLayout());
		this.mainFrame = mainFrame;
		this.pipelineTree = Utils.clonePipelineTree(mainFrame.getLeftTree());
		add(new JScrollPane(this.pipelineTree), BorderLayout.CENTER);
	}

	public Segment[] getSelectedSegments(){
		return this.pipelineTree.getSelectedSegments();
	}
	
	@Override
	public boolean checkInput() {
		boolean isValid = true;
		Segment[] selectedSegments = this.pipelineTree.getSelectedSegments();
		if (selectedSegments == null || selectedSegments.length == 0) {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public void ok() {

	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

}

class PipelineSelectionPanel extends JPanel implements ActionListener,
		IOKCancel {
	private static final String CMD_SELECT_ALL = "select_all";
	private static final String CMD_DESELECT_ALL = "deselect_all";

	private MainFrame mainFrame;
	private JList<CheckBoxItem> scenarioList;
	private DefaultListModel<CheckBoxItem> scenarioListModel;

	public PipelineSelectionPanel(MainFrame parent) {
		setLayout(new BorderLayout());

		//
		JLabel lblPipeChk = new JLabel(TextResourceBundle.getInstance()
				.getString("SelectPipeline"));
		JComboBox<String> comboBoxPipe = new JComboBox<String>();
		comboBoxPipe.addItem(parent.getLeftTree().getName());
		JPanel pipeChkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pipeChkPanel.add(lblPipeChk);
		pipeChkPanel.add(comboBoxPipe);

		// init scenario list
		scenarioListModel = new DefaultListModel<CheckBoxItem>();
		Scenario[] scenarios = mainFrame.getLeftTree().getScenarios();
		if (scenarios != null) {
			for (Scenario scenario : scenarios) {
				scenarioListModel.addElement(new CheckBoxItem(scenario, false));
			}
		}
		scenarioList = new JList<CheckBoxItem>(scenarioListModel);
		scenarioList.setCellRenderer(new CheckListRenderer());

		// init buttons
		JButton btnAdd = new JButton(TextResourceBundle.getInstance()
				.getString("SelectAll"));
		btnAdd.setActionCommand(CMD_SELECT_ALL);
		btnAdd.addActionListener(this);
		JButton btnEdit = new JButton(TextResourceBundle.getInstance()
				.getString("DiselectAll"));
		btnEdit.setActionCommand(CMD_DESELECT_ALL);
		btnEdit.addActionListener(this);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(btnEdit);

		JPanel scenarioPanel = new JPanel(new BorderLayout());
		scenarioPanel.setBorder(BorderFactory
				.createTitledBorder(TextResourceBundle.getInstance().getString(
						"SelectScenarios")));
		scenarioPanel.add(scenarioList, BorderLayout.CENTER);
		scenarioPanel.add(btnPanel, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		this.add(pipeChkPanel, BorderLayout.NORTH);
		this.add(scenarioPanel, BorderLayout.CENTER);
	}
	
	public Scenario[] getSelectedScenarios(){
		ArrayList<Scenario> selectedScenarios = new ArrayList<Scenario>();
		for (int i = 0; i < scenarioListModel.getSize(); i++) {
			CheckBoxItem item = scenarioListModel.getElementAt(i);
			if (item.isChecked()) {
				selectedScenarios.add((Scenario)item.getUserObject());
			}
		}
		return selectedScenarios.toArray(new Scenario[0]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (CMD_SELECT_ALL.equals(cmd)) {
			for (int i = 0; i < scenarioListModel.getSize(); i++) {
				CheckBoxItem item = scenarioListModel.getElementAt(i);
				item.setChecked(true);
			}
		} else if (CMD_DESELECT_ALL.equals(cmd)) {
			for (int i = 0; i < scenarioListModel.getSize(); i++) {
				CheckBoxItem item = scenarioListModel.getElementAt(i);
				item.setChecked(false);
			}
		}
	}

	@Override
	public boolean checkInput() {
		boolean isValid = false;
		for (int i = 0; i < scenarioListModel.getSize(); i++) {
			CheckBoxItem item = scenarioListModel.getElementAt(i);
			if (item.isChecked()) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}

	@Override
	public void ok() {

	}

	@Override
	public void cancel() {

	}

}
