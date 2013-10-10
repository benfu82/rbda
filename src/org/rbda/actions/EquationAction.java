package org.rbda.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.controls.IOKCancel;
import org.rbda.controls.OKCancelDialog;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.Equation;
import org.rbda.equation.EquationCollection;
import org.rbda.equation.Equation;
import org.rbda.main.ControlConstants;
import org.rbda.main.SystemProperties;
import org.rbda.resource.TextResourceBundle;
import org.rbda.utils.Utils;


public class EquationAction extends AbstractAction {
	public static final String COMMAND_EDIT = "edit";

	private EquationMaintenancePanel equationEditPanel;
	private OKCancelDialog equationEditDialog;

	public EquationAction(String name) {
		this(name, null);
	}

	public EquationAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (COMMAND_EDIT.equals(cmd)) {
			if (equationEditDialog == null) {
				equationEditPanel = new EquationMaintenancePanel();
				equationEditDialog = new OKCancelDialog(null,
						equationEditPanel, "", true);
				equationEditDialog.setLocationRelativeTo(null);
				equationEditDialog.setSize(new Dimension(
						ControlConstants.DIALOG_WIDTH,
						ControlConstants.DIALOG_HEIGHT));
			}
			equationEditDialog.showDialog();
		}
	}

}

class EquationMaintenancePanel extends JPanel implements ActionListener, IOKCancel {
	private JList<Equation> equationList;
	private EquationEditPanel equationEditPanel;
	private DefaultListModel<Equation> equationListModel;
	private OKCancelDialog equationEditDialog;
	private DBQueryer dbQueryer;

	public EquationMaintenancePanel() {
		init();
	}

	private void init() {
		dbQueryer = (DBQueryer) SystemProperties
				.getProperty(SystemProperties.DB_QUERYER);
		Equation[] equationsInSys = Equation.getAll(dbQueryer);
		equationListModel = new DefaultListModel<Equation>();
		if (equationsInSys != null) {
			for (Equation equationInSys : equationsInSys) {
				equationListModel.addElement(equationInSys);
			}
		}
		equationList = new JList<Equation>(equationListModel);
		equationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnAdd = new JButton(TextResourceBundle.getInstance()
				.getString("Add"));
		btnAdd.setActionCommand("Add");
		btnAdd.addActionListener(this);

		JButton btnEdit = new JButton(TextResourceBundle.getInstance()
				.getString("Edit"));
		btnEdit.setActionCommand("Edit");
		btnEdit.addActionListener(this);

		JButton btnRemove = new JButton(TextResourceBundle.getInstance()
				.getString("Remove"));
		btnRemove.setActionCommand("Remove");
		btnRemove.addActionListener(this);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(btnEdit);
		btnPanel.add(btnRemove);
		btnPanel.add(Box.createVerticalGlue());

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(equationList), BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.EAST);

		equationEditPanel = new EquationEditPanel();
		equationEditDialog = new OKCancelDialog(null, equationEditPanel,
				TextResourceBundle.getInstance().getString(
						"FailureCauseProperties"), true);
		equationEditDialog.setLocationRelativeTo(null);
		equationEditDialog.setResizable(false);
		equationEditDialog.setSize(new Dimension(
				ControlConstants.DIALOG_WIDTH_SMALL, 150));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Add".equals(cmd)) {
			equationEditDialog.showDialog();
			if (equationEditDialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
				Equation equation = equationEditPanel.getEquationModule();
				if (equation != null) {
					equationListModel.addElement(equation);
					equation.add(dbQueryer);
					PipelineAction.updateLineAttributeDef(equation);
				}
			}
		} else if ("Edit".equals(cmd)) {
			equationEditPanel
					.setEquationModule(equationList.getSelectedValue());
			equationEditDialog.showDialog();
			if (equationEditDialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
				Equation equation = equationEditPanel.getEquationModule();
				if (equation != null) {
					equation.remove(dbQueryer);
					equation.add(dbQueryer);
					PipelineAction
							.updateLineAttributeDef((Equation[]) equationListModel
									.toArray());
				}
			}
		} else if ("Remove".equals(cmd)) {
			int selectedIndex = equationList.getSelectedIndex();
			Equation equation = equationListModel.getElementAt(selectedIndex);
			equation.remove(dbQueryer);
			equationListModel.remove(selectedIndex);
			PipelineAction
					.updateLineAttributeDef((Equation[]) equationListModel
							.toArray());
		}

	}

	@Override
	public boolean checkInput() {
		return equationEditPanel.getEquationModule() == null ? false : true;
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

class EquationEditPanel extends JPanel implements ActionListener {
	private JTextField txtName;
	private JTextField txtModuleName;
	private OKCancelDialog equationSelectDialog;
	private OKCancelDialog propEditDialog;
	private EquationModulePanel equationModulePanel;
	private EquationCollection allRegisteredEquations;

	public EquationEditPanel() {
		init();
	}

	private void init() {
		allRegisteredEquations = (EquationCollection) SystemProperties
				.getProperty(SystemProperties.EQUATION_COLLECTION);
		JLabel lblName = new JLabel(TextResourceBundle.getInstance().getString(
				"Name"));
		txtName = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(lblName);
		panel.add(txtName);

		txtModuleName = new JTextField();
		txtModuleName.setEditable(false);
		txtModuleName.setBorder(BorderFactory
				.createTitledBorder(TextResourceBundle.getInstance().getString(
						"ProbabilityModule")));
		JButton btnAdd = new JButton(new ImageIcon("icons/equation_add.png"));
		btnAdd.setActionCommand("Add");
		btnAdd.addActionListener(this);

		JButton btnEdit = new JButton(new ImageIcon(
				"icons/equation_props_edit.png"));
		btnEdit.setActionCommand("Edit");
		btnEdit.addActionListener(this);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.add(txtModuleName);
		panel2.add(btnAdd);
		panel2.add(btnEdit);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(panel);
		add(panel2);
		add(Box.createVerticalGlue());

		equationModulePanel = new EquationModulePanel();
		equationSelectDialog = new OKCancelDialog(null, equationModulePanel,
				TextResourceBundle.getInstance().getString("SelectModule"),
				true);
		equationSelectDialog.setLocationRelativeTo(null);
		equationSelectDialog.setResizable(false);
		equationSelectDialog.setSize(new Dimension(
				ControlConstants.DIALOG_WIDTH, ControlConstants.DIALOG_HEIGHT));
	}

	public void setEquationModule(Equation equation) {
		txtName.setText(equation.getName());
		Equation registeredEquation = allRegisteredEquations
				.getEquationByID(equation.getID());
		txtModuleName.setText(registeredEquation.getName());
	}

	public Equation getEquationModule() {
		Equation equation = equationModulePanel.getSelectedEquation();
		if (equation == null)
			return null;

		equation = equation.cloneEquation();
		equation.setName(txtName.getText());
		return equation;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Add".equals(cmd)) {
			equationSelectDialog.showDialog();
			if (equationSelectDialog.getOptionResult() == OKCancelDialog.DIALOG_OK) {
				Equation selectedEquation = equationModulePanel
						.getSelectedEquation();
				if (selectedEquation == null)
					return;
				txtName.setText(selectedEquation.getName());
				txtModuleName.setText(selectedEquation.getName());
			}
		} else if ("Edit".equals(cmd)) {

		}
	}

}

class EquationModulePanel extends JPanel implements IOKCancel {
	private JList<Equation> moduleList;

	public EquationModulePanel() {
		init();
	}

	public Equation getSelectedEquation() {
		return moduleList.getSelectedValue();
	}

	private void init() {
		DefaultListModel<Equation> listModel = new DefaultListModel<Equation>();
		EquationCollection equationModules = (EquationCollection) SystemProperties
				.getProperty(SystemProperties.EQUATION_COLLECTION);
		if (equationModules != null) {
			for (int i = 0; i < equationModules.getCount(); i++) {
				listModel.addElement(equationModules.getEquationAt(i));
			}
		}
		moduleList = new JList<Equation>(listModel);
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		setLayout(new BorderLayout());
		add(new JLabel(TextResourceBundle.getInstance().getString(
				"SelectRegisteredModule")), BorderLayout.NORTH);
		add(new JScrollPane(moduleList), BorderLayout.CENTER);
	}

	@Override
	public boolean checkInput() {
		return (moduleList.getSelectedIndex() == -1) ? false : true;
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
