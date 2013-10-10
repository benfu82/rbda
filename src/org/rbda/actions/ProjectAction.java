package org.rbda.actions;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.rbda.MainFrame;
import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.Pipeline;
import org.rbda.biz.ProjectFile;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.Equation;
import org.rbda.equation.EquationCollection;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;
import org.rbda.resource.TextResourceBundle;
import org.rbda.utils.Utils;


/**
 * Create File Action
 * 
 * @author fujunwei
 * 
 */
public class ProjectAction extends AbstractAction {

	private JFileChooser fileChooser = null;
	private MainFrame parent = null;
	private JMenu openedLatelyMenu = null;

	public static final String COMMAND_CREATE = "create";
	public static final String COMMAND_OPEN = "open";
	public static final String COMMAND_SAVE = "save";
	public static final String COMMAND_LATEST = "latest";
	public static final String COMMAND_SAVE_AS = "save_as";
	public static final String COMMAND_EXIT = "exit";

	private static final String LATEST_FILE = "latest";
	private static final int MAX_LATEST_FILE_COUNT = 10;

	public ProjectAction(MainFrame parent) {
		this("", null, parent);
	}

	public ProjectAction(String name, Icon icon) {
		this(name, icon, null);
	}

	public ProjectAction(String name, Icon icon, MainFrame parent) {
		super(name, icon);
		this.parent = parent;

		createLatestMenu();

		// init file chooser dialog
		fileChooser = new JFileChooser();
		String extj[] = { ProjectFile.FILE_SUFFIX };
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				TextResourceBundle.getInstance().getString("ProjectFile")
						+ "(*" + ProjectFile.FILE_SUFFIX + ")", extj);
		fileChooser.setFileFilter(filter);
	}

	public JMenu getLatestMenu(String name) {
		openedLatelyMenu.setText(name);
		return openedLatelyMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int ret;
		String command = e.getActionCommand();
		if (COMMAND_OPEN.equals(command)) {
			String lastFile = "";
			if (openedLatelyMenu.getItemCount() != 0) {
				lastFile = openedLatelyMenu.getItem(
						openedLatelyMenu.getItemCount() - 1).getText();
			}
			fileChooser.setSelectedFile(new File(lastFile));
			ret = fileChooser.showOpenDialog(parent);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				open(file);
				parent.showAllMenu();
			}
		} else if (COMMAND_SAVE_AS.equals(command)
				|| COMMAND_CREATE.equals(command)) {
			String lastFile = "";
			if (openedLatelyMenu.getItemCount() != 0) {
				lastFile = openedLatelyMenu.getItem(
						openedLatelyMenu.getItemCount() - 1).getText();
			}
			fileChooser.setSelectedFile(new File(lastFile));

			File file = null;
			while (true) {
				ret = fileChooser.showSaveDialog(parent);
				if (ret == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					if (!file.getAbsolutePath().endsWith(
							ProjectFile.FILE_SUFFIX)) {
						file = new File(file.getAbsolutePath()
								+ ProjectFile.FILE_SUFFIX);
					}
					if (file.exists()) {
						ret = JOptionPane.showConfirmDialog(
								fileChooser,
								TextResourceBundle.getInstance().getString(
										"FileExisted"), "",
								JOptionPane.OK_CANCEL_OPTION);
						if (ret != JOptionPane.OK_OPTION)
							continue;
						file.delete();
					}
					break;
				}
				break;
			}
			if (file == null)
				return;
			if (COMMAND_SAVE_AS.equals(command)) {
				ProjectFile savedFile = new ProjectFile(file);
				save(savedFile);
			} else if (COMMAND_CREATE.equals(command)) {
				create(file);
				parent.showAllMenu();
			}
		} else if (COMMAND_SAVE.equals(command)) {
			save();
		} else if (COMMAND_LATEST.equals(command)) {
			JMenuItem menuItem = (JMenuItem) e.getSource();
			String filePath = menuItem.getText();
			open(new File(filePath));
		} else if (COMMAND_EXIT.equals(command)) {
			Object obj = SystemProperties
					.getProperty(SystemProperties.STATE_EDITING);
			if (obj != null) {
				Boolean isEditing = (Boolean) obj;
				if (isEditing) {
					ret = JOptionPane.showConfirmDialog(
							parent,
							TextResourceBundle.getInstance().getString(
									"ConfirmSaveFile"));
					if (ret == JOptionPane.OK_OPTION) {
						save();
						System.exit(0);
					} else if (ret == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}
			}
		}
	}

	private void create(File file) {
		ProjectFile projFile = new ProjectFile(file);
		projFile.create();
		parent.setProjectFile(projFile);

		PipelineTree projectTree = new PipelineTree(projFile.getName());
		parent.addLeftTree(projectTree);
		SystemProperties.setProperty(SystemProperties.STATE_EDITING,
				new Boolean(true));
	}

	private void open(File file) {
		ProjectFile projFile = new ProjectFile(file);
		projFile.open();

		PipelineTree projectTree = new PipelineTree(projFile.getName());
		Pipeline[] pipelines = projFile.getPipelines();
		if (pipelines != null) {
			for (Pipeline pipeline : pipelines) {
				projectTree.addPipeline(pipeline);
			}
		}
		parent.addLeftTree(projectTree);
		parent.setProjectFile(projFile);
		addToLatestMenu(projFile);
		
		// load equation info
		DBQueryer dbQueryer = (DBQueryer) SystemProperties
				.getProperty(SystemProperties.DB_QUERYER);
		Equation[] equationsInSys = Equation.getAll(dbQueryer);
		PipelineAction.updateLineAttributeDef(equationsInSys);
	}

	private void save() {
		save(parent.getProjectFile());
	}

	private void save(ProjectFile projFile) {
		PipelineTree projectTree = parent.getLeftTree();
		Pipeline[] pipelines = projectTree.getPipelines();
		projFile.setPipelines(pipelines);
		projFile.save();

		addToLatestMenu(projFile);
		SystemProperties.setProperty(SystemProperties.STATE_EDITING,
				new Boolean(false));
	}

	private void addToLatestMenu(ProjectFile file) {
		for (int i = 0; i < openedLatelyMenu.getItemCount(); i++) {
			if (file.getPath().equals(openedLatelyMenu.getItem(i).getText()))
				return;
		}

		if (openedLatelyMenu.getItemCount() > MAX_LATEST_FILE_COUNT) {
			openedLatelyMenu.remove(0);
		}
		JMenuItem menuItem = new JMenuItem(file.getPath());
		menuItem.setActionCommand(COMMAND_LATEST);
		menuItem.addActionListener(this);
		openedLatelyMenu.add(menuItem);
		saveLatestMenu();
	}

	private void createLatestMenu() {
		openedLatelyMenu = new JMenu("");
		try {
			String line = null;
			File file = new File(LATEST_FILE);
			if (!file.exists())
				return;
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			while ((line = fileReader.readLine()) != null) {
				JMenuItem menuItem = new JMenuItem(line);
				menuItem.setActionCommand(COMMAND_LATEST);
				menuItem.addActionListener(this);
				openedLatelyMenu.add(menuItem);
			}
			fileReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void saveLatestMenu() {
		try {
			File file = new File(LATEST_FILE);
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < openedLatelyMenu.getItemCount(); i++) {
				fileWriter.write(openedLatelyMenu.getItem(i).getText() + "\n");
			}
			fileWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}