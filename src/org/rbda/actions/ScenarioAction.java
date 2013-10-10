package org.rbda.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.rbda.MainFrame;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.Pipeline;
import org.rbda.biz.Scenario;
import org.rbda.controls.IOKCancel;
import org.rbda.controls.OKCancelDialog;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.EquationCollection;

import org.rbda.main.ControlConstants;
import org.rbda.main.PipelineAttributePanel;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;
import org.rbda.resource.TextResourceBundle;


public class ScenarioAction extends AbstractAction implements IOKCancel {
	public static final String CMD_CREATE = "create";
	public static final String CMD_ADD = "add";
	public static final String CMD_EDIT = "edit";
	public static final String CMD_REMOVE = "remove";
	public static final String CMD_COPY = "copy";

	private MainFrame parent;
	private DefaultListModel scenarioModel;
	private OKCancelDialog scenarioDialog;
	private boolean isOK = false;

	public ScenarioAction(String name) {
		this(name, null);
	}

	public ScenarioAction(String name, Icon icon) {
		super(name, icon);
	}

	public void setParent(MainFrame parent) {
		this.parent = parent;
	}

	public void setScenarioModel(DefaultListModel scenarioModel) {
		this.scenarioModel = scenarioModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (CMD_CREATE.equals(cmd)) {
			create();
		}
		if (CMD_ADD.equals(cmd)) {
			add();
		}
		if (CMD_EDIT.equals(cmd)) {
			edit();
		}
		if (CMD_COPY.equals(cmd)) {
			copy();
		}
		if (CMD_REMOVE.equals(cmd)) {
			create();
		}
	}

	private void create() {
		if (scenarioDialog == null) {
			scenarioDialog = new OKCancelDialog(parent, new ScenarioListPanel(
					parent), "create pipeline", true);
			scenarioDialog.setValidChecker(this);
			scenarioDialog.setSize(new Dimension(
					ControlConstants.DIALOG_HEIGHT,
					ControlConstants.DIALOG_WIDTH));
			scenarioDialog.setLocationRelativeTo(null);
			scenarioDialog.setResizable(false);
		}
		scenarioDialog.showDialog();
		if (scenarioDialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
			save();
		}
	}

	private void add() {

	}

	private void edit() {

	}

	private void copy() {

	}

	private void remove() {

	}

	private void save() {

	}

	@Override
	public boolean checkInput() {
		return isOK;
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

class ScenarioListPanel extends JPanel implements ActionListener, IOKCancel {
	private static final String CMD_ADD = "add";
	private static final String CMD_EDIT = "edit";
	private static final String CMD_REMOVE = "remove";
	private static final String CMD_COPY = "copy";

	private JList<Scenario> scenarioList;
	private DefaultListModel<Scenario> scenarioModel;
	private ScenarioEditPanel scenarioEditPanel;
	private OKCancelDialog scenarioEditDialog;

	public ScenarioListPanel(MainFrame parent) {
		setLayout(new BorderLayout());

		// init scenario list
		scenarioModel = new DefaultListModel<Scenario>();
		DBQueryer dbQueryer = (DBQueryer) SystemProperties
				.getProperty(SystemProperties.DB_QUERYER);
		Scenario[] scenarios = Scenario.getAll(dbQueryer);
		if (scenarios != null) {
			for (Scenario scenario : scenarios) {
				scenarioModel.addElement(scenario);
			}
		}

		scenarioList = new JList<Scenario>(scenarioModel);
		add(scenarioList, BorderLayout.CENTER);

		// init buttons
		JButton btnAdd = new JButton(TextResourceBundle.getInstance()
				.getString("Add"));
		btnAdd.setActionCommand(CMD_ADD);
		btnAdd.addActionListener(this);
		JButton btnEdit = new JButton(TextResourceBundle.getInstance()
				.getString("Edit"));
		btnEdit.setActionCommand(CMD_EDIT);
		btnEdit.addActionListener(this);
		JButton btnCopy = new JButton(TextResourceBundle.getInstance()
				.getString("Copy"));
		btnCopy.setActionCommand(CMD_COPY);
		btnCopy.addActionListener(this);
		JButton btnRemove = new JButton(TextResourceBundle.getInstance()
				.getString("Remove"));
		btnRemove.setActionCommand(CMD_REMOVE);
		btnRemove.addActionListener(this);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(btnEdit);
		btnPanel.add(btnCopy);
		btnPanel.add(btnRemove);
		add(btnPanel, BorderLayout.EAST);

		// init Scenario Edit Dialog
		scenarioEditPanel = new ScenarioEditPanel();
		scenarioEditDialog = new OKCancelDialog(parent, new ScenarioListPanel(
				parent), "create pipeline", true);
		scenarioEditDialog.setValidChecker(this);
		scenarioEditDialog.setSize(new Dimension(
				ControlConstants.DIALOG_HEIGHT, ControlConstants.DIALOG_WIDTH));
		scenarioEditDialog.setLocationRelativeTo(null);
		scenarioEditDialog.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (CMD_ADD.equals(cmd)) {
			scenarioEditPanel.setScenario(null);
			scenarioEditDialog.showDialog();
			if (scenarioEditDialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
				Scenario scenario = scenarioEditPanel.getScenario();
				scenarioModel.addElement(scenario);
			}
		} else if (CMD_EDIT.equals(cmd)) {
			Scenario selectedScenario = scenarioList.getSelectedValue();
			scenarioEditPanel.setScenario(selectedScenario);
			scenarioEditDialog.showDialog();
		} else if (CMD_COPY.equals(cmd)) {
			Scenario selectedScenario = scenarioList.getSelectedValue();
			scenarioModel.addElement(selectedScenario.clone());
		} else if (CMD_REMOVE.equals(cmd)) {
			scenarioModel.remove(scenarioList.getSelectedIndex());
		}
	}

	@Override
	public boolean checkInput() {
		return true;
	}

	@Override
	public void ok() {
		Scenario scenario = null;
		DBQueryer dbQueryer = (DBQueryer) SystemProperties
				.getProperty(SystemProperties.DB_QUERYER);
		for (int i = 0; i < scenarioModel.getSize(); i++) {
			scenario = scenarioModel.getElementAt(i);
			scenario.save(dbQueryer);
		}
	}

	@Override
	public void cancel() {

	}

	private void add() {

	}

	private void copy() {

	}

	private void edit() {

	}

	private void remove() {

	}
}

class ScenarioEditPanel extends JPanel implements ActionListener {
	private JTextField txtName;
	private JList<Scenario> scenarioList;
	private DefaultListModel<Scenario> scenarioModel;
	private JTextArea txtComment;

	public ScenarioEditPanel() {
		init();
	}

	private void init() {
		// scenario name panel
		JLabel lblName = new JLabel(TextResourceBundle.getInstance().getString(
				"Name"));
		JTextField txtName = new JTextField();
		JPanel pnlName = new JPanel();
		pnlName.setLayout(new BoxLayout(pnlName, BoxLayout.LINE_AXIS));
		pnlName.add(lblName);
		pnlName.add(txtName);

		// scenario list panel
		scenarioModel = new DefaultListModel<Scenario>();
		scenarioList = new JList<Scenario>(scenarioModel);
		JPopupMenu btnAdd = new JPopupMenu(TextResourceBundle.getInstance()
				.getString("Add"));
		JMenuItem attrItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("ScenarioAttribute"));
		JMenuItem inspectionToolItem = new JMenuItem(TextResourceBundle
				.getInstance().getString("InspectionTool"));
		attrItem.setActionCommand("AddAttribute");
		attrItem.addActionListener(this);
		btnAdd.add(attrItem);
		btnAdd.add(inspectionToolItem);

		JPopupMenu btnEdit = new JPopupMenu(TextResourceBundle.getInstance()
				.getString("Edit"));
		JMenuItem attrItem2 = new JMenuItem(TextResourceBundle.getInstance()
				.getString("ScenarioAttribute"));
		JMenuItem inspectionToolItem2 = new JMenuItem(TextResourceBundle
				.getInstance().getString("InspectionTool"));
		attrItem2.setActionCommand("EditAttribute");
		attrItem2.addActionListener(this);
		btnEdit.add(attrItem2);
		btnEdit.add(inspectionToolItem2);

		JPopupMenu btnCopy = new JPopupMenu(TextResourceBundle.getInstance()
				.getString("Copy"));
		JMenuItem attrItem3 = new JMenuItem(TextResourceBundle.getInstance()
				.getString("ScenarioAttribute"));
		JMenuItem inspectionToolItem3 = new JMenuItem(TextResourceBundle
				.getInstance().getString("InspectionTool"));
		attrItem3.setActionCommand("CopyAttribute");
		attrItem3.addActionListener(this);
		btnCopy.add(attrItem3);
		btnCopy.add(inspectionToolItem3);

		JPopupMenu btnRemove = new JPopupMenu(TextResourceBundle.getInstance()
				.getString("Remove"));
		JMenuItem attrItem4 = new JMenuItem(TextResourceBundle.getInstance()
				.getString("ScenarioAttribute"));
		JMenuItem inspectionToolItem4 = new JMenuItem(TextResourceBundle
				.getInstance().getString("InspectionTool"));
		attrItem4.setActionCommand("RemoveAttribute");
		attrItem4.addActionListener(this);
		btnRemove.add(attrItem4);
		btnRemove.add(inspectionToolItem4);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(btnEdit);
		btnPanel.add(btnCopy);
		btnPanel.add(btnRemove);

		JPanel pnlScenarioList = new JPanel(new BorderLayout());
		pnlScenarioList.add(new JScrollPane(scenarioList), BorderLayout.CENTER);
		pnlScenarioList.add(btnPanel, BorderLayout.EAST);
		pnlScenarioList.setBorder(BorderFactory
				.createTitledBorder(TextResourceBundle.getInstance().getString(
						"Action")));

		// comment panel
		txtComment = new JTextArea();
		JPanel pnlComment = new JPanel(new BorderLayout());
		pnlComment.add(new JScrollPane(txtComment), BorderLayout.CENTER);
		pnlComment.setBorder(BorderFactory
				.createTitledBorder(TextResourceBundle.getInstance().getString(
						"Comment")));

		// add all kinds of panel into container
		setLayout(new BorderLayout());
		add(pnlName, BorderLayout.NORTH);
		add(pnlScenarioList, BorderLayout.CENTER);
		add(pnlComment, BorderLayout.SOUTH);
	}

	public void setScenario(Scenario scenario) {

	}

	public Scenario getScenario() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("AddAttribute".equals(cmd)) {
			addAttribute();
		} else if ("EditAttribute".equals(cmd)) {

		} else if ("CopyAttribute".equals(cmd)) {

		} else if ("RemoveAttribute".equals(cmd)) {

		}
	}

	private void addAttribute() {

	}

}

class ScenarioAttributePanel extends JPanel {
	private JTextField txtName;
	private JScrollPane pipeTreePane;

	public ScenarioAttributePanel() {
		init();
	}

	private void init(){
		JLabel lblName = new JLabel(TextResourceBundle.getInstance().getString("Name"));
		txtName = new JTextField();
		JPanel pnlName =  new JPanel();
		pnlName.setLayout(new BoxLayout(pnlName, BoxLayout.LINE_AXIS));
		pnlName.add(lblName);
		pnlName.add(txtName);
		
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

		/*PipelineAttributePanel panel = new PipelineAttributePanel(
				pipelineTree.getSelectedPipeline(), lineAttrDefs, equations);
		
		JPanel panel =  new JPanel(new BorderLayout());
		panel.add(pipeTreePane, BorderLayout.WEST);
		panel.add()*/
	}
}
