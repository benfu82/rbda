package org.rbda;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import org.rbda.actions.ProjectAction;
import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.ProjectFile;
import org.rbda.controls.MessagePanel;
import org.rbda.equation.DistributionCollection;
import org.rbda.equation.EquationCollection;
import org.rbda.equation.IEquation;
import org.rbda.main.ControlConstants;
import org.rbda.main.MenuBar;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;
import org.rbda.resource.TextResourceBundle;

public class MainFrame extends JFrame {

	private MessagePanel msgPanel;
	private JFileChooser fileChooser;
	private JScrollPane treePanel;
	private MenuBar menuBar;
	private PipelineTree pipelineTree;
	private ProjectFile projectFile;
	private JToolBar toolBar;
	private JPanel workspacePanel;
	private Component workspaceComp;

	public MainFrame() {
		init();
	}

	/**
	 * Display Main Window
	 */
	public void showWin() {
		setMinimumSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void addLeftTree(PipelineTree tree) {
		treePanel.setViewportView(tree);
		menuBar.setPipelineTree(tree);
		this.pipelineTree = tree;
	}

	public void saveLeftTree() {
		menuBar.setPipelineTree(pipelineTree);
	}

	public PipelineTree getLeftTree() {
		return this.pipelineTree;
	}

	public void setProjectFile(ProjectFile projFile) {
		this.projectFile = projFile;
		this.setTitle(projFile.getName());
	}

	public String getProjectName() {
		return this.projectFile.getName();
	}

	public ProjectFile getProjectFile() {
		return this.projectFile;
	}

	public void setToolBarVisible(boolean isVisible) {
		toolBar.setVisible(isVisible);
	}

	public void setWorkspaceVisible(boolean isVisible) {
		workspacePanel.setVisible(isVisible);
	}

	public void setMessagePaneVisible(boolean isVisible) {
		msgPanel.setVisible(isVisible);
	}

	public void showAllMenu() {
		menuBar.showAllMenu(true);
	}

	public MessagePanel getMessagePanel() {
		return msgPanel;
	}

	public void setWorkspaceComponent(Component comp) {
		workspacePanel.add(comp, BorderLayout.CENTER);
		workspaceComp = comp;
	}
	
	public Component getWorkspaceComponent(){
		return workspaceComp;
	}

	/**
	 * initialize main window
	 */
	private void init() {
		this.setTitle(TextResourceBundle.getInstance().getString("Title"));

		// init menu bar
		menuBar = new MenuBar(this, null);
		this.setJMenuBar(menuBar);

		// init tool bar
		toolBar = new JToolBar();
		JButton btnCreate = new JButton(new ImageIcon("icons/create_file.png"));
		btnCreate.setBorder(BorderFactory.createEmptyBorder());
		btnCreate.setActionCommand(ProjectAction.COMMAND_CREATE);
		btnCreate.setToolTipText(TextResourceBundle.getInstance().getString(
				"CreateFile"));
		btnCreate.addActionListener(menuBar.getProjectFileAction());

		JButton btnOpen = new JButton(new ImageIcon("icons/open_file.png"));
		btnOpen.setBorder(BorderFactory.createEmptyBorder());
		btnOpen.setActionCommand(ProjectAction.COMMAND_OPEN);
		btnOpen.setToolTipText(TextResourceBundle.getInstance().getString(
				"OpenFile"));
		btnOpen.addActionListener(menuBar.getProjectFileAction());

		JButton btnSave = new JButton(new ImageIcon("icons/save.png"));
		btnSave.setBorder(BorderFactory.createEmptyBorder());
		btnSave.setActionCommand(ProjectAction.COMMAND_SAVE);
		btnSave.setToolTipText(TextResourceBundle.getInstance().getString(
				"SaveFile"));
		btnSave.addActionListener(menuBar.getProjectFileAction());

		JButton btnSaveAs = new JButton(new ImageIcon("icons/saveAs.png"));
		btnSaveAs.setActionCommand(ProjectAction.COMMAND_SAVE_AS);
		btnSaveAs.setToolTipText(TextResourceBundle.getInstance().getString(
				"SaveAsFile"));
		btnSaveAs.addActionListener(menuBar.getProjectFileAction());
		btnSaveAs.setBorder(BorderFactory.createEmptyBorder());

		toolBar.add(btnCreate);
		toolBar.add(Box.createHorizontalStrut(ControlConstants.MARGIN));
		toolBar.add(btnOpen);
		toolBar.add(Box.createHorizontalStrut(ControlConstants.MARGIN));
		toolBar.add(btnSave);
		toolBar.add(Box.createHorizontalStrut(ControlConstants.MARGIN));
		toolBar.add(btnSaveAs);
		toolBar.add(Box.createHorizontalStrut(ControlConstants.MARGIN));
		toolBar.addSeparator();
		this.add(toolBar, BorderLayout.NORTH);

		// init center area
		treePanel = new JScrollPane();
		treePanel.setMinimumSize(new Dimension(200, 300));
		workspacePanel = new JPanel(new BorderLayout());
		JSplitPane leftRightPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, treePanel, workspacePanel);
		leftRightPanel.setOneTouchExpandable(true);
		leftRightPanel.resetToPreferredSizes();

		msgPanel = new MessagePanel();
		JSplitPane mainPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				leftRightPanel, msgPanel);
		mainPanel.setOneTouchExpandable(true);
		mainPanel.resetToPreferredSizes();
		this.add(mainPanel);

		// init status bar
		JToolBar statusBar = new JToolBar();
		statusBar.setFloatable(false);
		JLabel label = new JLabel("正常");
		statusBar.add(label);
		this.add(statusBar, BorderLayout.SOUTH);

		// load all equations
		EquationCollection equations = new EquationCollection();
		equations.loadAllEquation();
		SystemProperties.setProperty(SystemProperties.EQUATION_COLLECTION,
				equations);

		DistributionCollection distributions = new DistributionCollection();
		distributions.loadAllDistribution();
		SystemProperties.setProperty(SystemProperties.DISTRIBUTION_COLLECTION,
				distributions);
		SystemProperties.setProperty(
				SystemProperties.DISTRIBUTION_ID_COLLECTION,
				distributions.getAllDistributionIDs());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mainFrame = new MainFrame();
		mainFrame.showWin();
	}

}

class ExtensionFileFilter extends FileFilter {

	private String description = "";
	private ArrayList<String> extensions = new ArrayList<String>();

	public void addExtension(String extension) {
		if (!extension.startsWith(".")) {
			extension = "." + extension;
			extensions.add(extension.toLowerCase());
		}
	}

	public void setDescription(String aDescription) {
		description = aDescription;
	}

	//
	public String getDescription() {
		return description;
	}

	//
	public boolean accept(File f) {
		//
		if (f.isDirectory())
			return true;
		//
		String name = f.getName().toLowerCase();
		//
		for (String extension : extensions) {
			if (name.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}
