package org.rbda.main;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.rbda.MainFrame;
import org.rbda.actions.CalculationAction;
import org.rbda.actions.EquationAction;
import org.rbda.actions.EvaluationAction;
import org.rbda.actions.PipelineAction;
import org.rbda.actions.ProjectAction;
import org.rbda.actions.ScenarioAction;
import org.rbda.actions.SegmentAction;
import org.rbda.actions.ViewMenuAction;
import org.rbda.resource.TextResourceBundle;


/**
 * Menu Bar Implementation
 * 
 * @author fujunwei
 * 
 */
public class MenuBar extends JMenuBar {
	private MainFrame parent = null;
	private PipelineTree pipelineTree = null;
	private ProjectAction projectFileAction;
	private PipelineAction pipelineAction;
	private CalculationAction calculationAction;
	
	private JMenu file;
	private JMenu edit;
	private JMenu view;
	private JMenu setting;
	private JMenu create;
	private JMenu cal;
	private JMenu evaluate;
	private JMenu help;

	public MenuBar(MainFrame parent, PipelineTree pipelineTree) {
		this.parent = parent;
		this.pipelineTree = pipelineTree;
		add(createFileMenu());
		add(createEditMenu());
		add(createViewMenu());
		add(createSettingMenu());
		add(createCreateMenu());
		add(createCalMenu());
		add(createEvaluateMenu());
		add(createHelpMenu());
		
		showAllMenu(false);
	}
	
	public void showAllMenu(boolean isVisible){
		edit.setVisible(isVisible);
		create.setVisible(isVisible);
		cal.setVisible(isVisible);
		evaluate.setVisible(isVisible);
		help.setVisible(isVisible);
	}
	
	public void setPipelineTree(PipelineTree pipelineTree){
		this.pipelineTree = pipelineTree;
		pipelineAction.setPipelineTree(pipelineTree);
		calculationAction.setPipelineTree(pipelineTree);
	}
	
	public ProjectAction getProjectFileAction(){
		return this.projectFileAction;
	}

	private JMenu createFileMenu() {
		file = new JMenu(TextResourceBundle.getInstance().getString(
				"File"));
		projectFileAction = new ProjectAction(parent);

		JMenuItem createFileItem = new JMenuItem(TextResourceBundle
				.getInstance().getString("CreateFile"), new ImageIcon(
				"icons/create_file.png"));
		createFileItem.setActionCommand(ProjectAction.COMMAND_CREATE);
		createFileItem.addActionListener(projectFileAction);
		JMenuItem openFileItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("OpenFile"), new ImageIcon("icons/open_file.png"));
		openFileItem.setActionCommand(ProjectAction.COMMAND_OPEN);
		openFileItem.addActionListener(projectFileAction);
		JMenuItem saveFileItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("SaveFile"), new ImageIcon("icons/save_file.png"));
		saveFileItem.setActionCommand(ProjectAction.COMMAND_SAVE);
		saveFileItem.addActionListener(projectFileAction);
		JMenuItem saveAsFileItem = new JMenuItem(TextResourceBundle
				.getInstance().getString("SaveAsFile"), new ImageIcon(
				"icons/save_as_file.png"));
		saveAsFileItem.setActionCommand(ProjectAction.COMMAND_SAVE_AS);
		saveAsFileItem.addActionListener(projectFileAction);
		JMenu latestFileMenu = new JMenu(TextResourceBundle.getInstance()
				.getString("LatestFile"));
		latestFileMenu.addActionListener(projectFileAction);
		// latestFileMenu.addMouseListener(openSaveFileAction);
		latestFileMenu = projectFileAction.getLatestMenu(TextResourceBundle
				.getInstance().getString("LatestFile"));
		JMenuItem exitItem = new JMenuItem(TextResourceBundle.getInstance().getString(
				"Exit"), new ImageIcon(
				"icons/exit.png"));
		exitItem.setActionCommand(ProjectAction.COMMAND_EXIT);
		exitItem.addActionListener(projectFileAction);
		file.add(createFileItem);
		file.add(openFileItem);
		file.add(saveFileItem);
		file.add(saveAsFileItem);
		file.add(latestFileMenu);
		file.add(exitItem);
		return file;
	}

	private JMenu createEditMenu() {
		edit = new JMenu(TextResourceBundle.getInstance().getString(
				"Edit"));
		return edit;
	}

	private JMenu createViewMenu() {
		view = new JMenu(TextResourceBundle.getInstance().getString(
				"View"));
		ViewMenuAction viewAction = new ViewMenuAction("View", parent);
		JCheckBoxMenuItem toolBar = new JCheckBoxMenuItem(TextResourceBundle.getInstance().getString(
				"ToolBar"), true);
		toolBar.setActionCommand(ViewMenuAction.COMMAND_TOOLBAR);
		toolBar.addActionListener(viewAction);
		view.add(toolBar);
		
		JCheckBoxMenuItem msgPane = new JCheckBoxMenuItem(TextResourceBundle.getInstance().getString(
				"MessagePane"), true);
		msgPane.setActionCommand(ViewMenuAction.COMMAND_MSGPANE);
		msgPane.addActionListener(viewAction);
		view.add(msgPane);
		
		JCheckBoxMenuItem workspace = new JCheckBoxMenuItem(TextResourceBundle.getInstance().getString(
				"Workspace"), true);
		workspace.setActionCommand(ViewMenuAction.COMMAND_WORKSPACE);
		workspace.addActionListener(viewAction);
		view.add(workspace);
		
		return view;
	}

	// Setting Menu
	private JMenu createSettingMenu() {
		setting = new JMenu(TextResourceBundle.getInstance().getString(
				"Setting"));
		
		// equation setting
		EquationAction equationAction = new EquationAction("");
		JMenuItem equationItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("Equation"), new ImageIcon("icons/equation_edit.png"));
		equationItem.setActionCommand(EquationAction.COMMAND_EDIT);
		equationItem.addActionListener(equationAction);
		setting.add(equationItem);
		
		// segment setting
		SegmentAction segmentAction = new SegmentAction("");
		JMenuItem segmentItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("Segmentation"), new ImageIcon("icons/create_segment.png"));
		segmentItem.setActionCommand(SegmentAction.CMD_CREATE);
		segmentItem.addActionListener(segmentAction);
		setting.add(segmentItem);
		
		return setting;
	}

	// Create Menu
	private JMenu createCreateMenu() {
		create = new JMenu(TextResourceBundle.getInstance().getString(
				"Create"));
		pipelineAction = new PipelineAction(TextResourceBundle.getInstance()
				.getString("CreatePipeline"), this.pipelineTree);
		pipelineAction.setParent(parent);
		JMenuItem createPipelineItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("CreatePipeline"), new ImageIcon("icons/create_pipeline.png"));
		createPipelineItem.setActionCommand(PipelineAction.CMD_CREATE);
		createPipelineItem.addActionListener(pipelineAction);
		
		JMenuItem importPipelineItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("ImportPipeline"), new ImageIcon("icons/import_pipeline.png"));
		importPipelineItem.setActionCommand(PipelineAction.CMD_IMPORT);
		importPipelineItem.addActionListener(pipelineAction);
		
		JMenuItem createScenarioItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("CreateScenario"), new ImageIcon("icons/create_scenario.png"));
		createScenarioItem.setActionCommand(ScenarioAction.CMD_CREATE);
		createScenarioItem.addActionListener(new ScenarioAction(TextResourceBundle.getInstance()
				.getString("CreateScenario")));
		
		create.add(createPipelineItem);
		create.add(importPipelineItem);
		create.add(createScenarioItem);
		return create;
	}

	private JMenu createCalMenu() {
		cal = new JMenu(TextResourceBundle.getInstance().getString(
				"Calculation"));
		calculationAction = new CalculationAction("", this.pipelineTree, parent.getMessagePanel());
		JMenuItem reliablityItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("ReliablityCal"), new ImageIcon("icons/reliablity_cal.png"));
		reliablityItem.setActionCommand(CalculationAction.COMMAND_RELIABLITY);
		reliablityItem.addActionListener(calculationAction);
		
		cal.add(reliablityItem);
		return cal;
	}

	private JMenu createEvaluateMenu() {
		evaluate = new JMenu(TextResourceBundle.getInstance().getString(
				"Evaluate"));
		
		EvaluationAction evaluationAction = new EvaluationAction("");
		evaluationAction.setParent(parent);
		JMenuItem pointAnalyseItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("PointAnalyse"), new ImageIcon("icons/pointAnalyse.png"));
		pointAnalyseItem.setActionCommand(EvaluationAction.CMD_POINT_ANALYSE);
		pointAnalyseItem.addActionListener(evaluationAction);
		evaluate.add(pointAnalyseItem);
		
		JMenuItem segmentAnalyseItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("SegmentAnalyse"), new ImageIcon("icons/segmentAnalyse.png"));
		segmentAnalyseItem.setActionCommand(EvaluationAction.CMD_SEGMENT_ANALYSE);
		segmentAnalyseItem.addActionListener(evaluationAction);
		evaluate.add(segmentAnalyseItem);
		
		JMenuItem pipelineAnalyseItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("PipelineAnalyse"), new ImageIcon("icons/pipelineAnalyse.png"));
		pipelineAnalyseItem.setActionCommand(EvaluationAction.CMD_PIPELINE_ANALYSE);
		pipelineAnalyseItem.addActionListener(evaluationAction);
		evaluate.add(pipelineAnalyseItem);
		
		JMenuItem reportItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("PipelineAnalyse"), new ImageIcon("icons/pipelineAnalyse.png"));
		reportItem.setActionCommand(EvaluationAction.CMD_PIPELINE_ANALYSE);
		reportItem.addActionListener(evaluationAction);
		evaluate.add(reportItem);
		
		return evaluate;
	}

	private JMenu createHelpMenu() {
		help = new JMenu(TextResourceBundle.getInstance().getString(
				"Help"));
		return help;
	}
}
