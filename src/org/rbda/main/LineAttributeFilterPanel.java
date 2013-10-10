package org.rbda.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.rbda.controls.IOKCancel;
import org.rbda.resource.TextResourceBundle;


import org.rbda.biz.LineAttributeDef;

public class LineAttributeFilterPanel extends JPanel implements ActionListener,
		IOKCancel {

	private JList<LineAttributeDef> selectedAttrListBox;
	private JList<LineAttributeDef> allAttrListBox;
	private JTextField txtFilter;

	public LineAttributeFilterPanel(LineAttributeDef[] allAttrDefs) {
		JLabel lblFilter = new JLabel(TextResourceBundle.getInstance()
				.getString("Filter Name"));
		txtFilter = new JTextField(20);
		JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEADING,
				ControlConstants.MARGIN, ControlConstants.MARGIN));
		pnlFilter.add(lblFilter);
		pnlFilter.add(txtFilter);

		JPanel selectedAttrDefPanel = this.creatSelectedLineAttrPanel();
		JPanel allAttrDefPanel = this.createAllLineAttrPanel(allAttrDefs);
		JPanel btnPanel = this.createBtnPanel();
		JPanel downPanel = new JPanel();
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.LINE_AXIS));
		downPanel.add(Box.createHorizontalStrut(ControlConstants.MARGIN));
		downPanel.add(selectedAttrDefPanel);
		downPanel.add(btnPanel);
		downPanel.add(allAttrDefPanel);
		downPanel.add(Box.createHorizontalStrut(ControlConstants.MARGIN));

		setLayout(new BorderLayout());
		add(pnlFilter, BorderLayout.NORTH);
		add(downPanel, BorderLayout.CENTER);
	}

	public void setSelectedLineAttrDefs(String filterName,
			LineAttributeDef[] attrDefArray) {
		if (filterName == null || filterName.isEmpty())
			txtFilter.setEditable(true);
		else
			txtFilter.setEditable(false);
		txtFilter.setText(filterName);
		DefaultListModel<LineAttributeDef> listModel = (DefaultListModel<LineAttributeDef>) selectedAttrListBox
				.getModel();
		listModel.removeAllElements();
		if (attrDefArray != null) {
			for (LineAttributeDef attr : attrDefArray) {
				listModel.addElement(attr);
			}
		}
	}

	public String getFilterName() {
		return txtFilter.getText();
	}

	public LineAttributeDef[] getSelectedLineAttrDef() {
		DefaultListModel<LineAttributeDef> listModel = (DefaultListModel<LineAttributeDef>) selectedAttrListBox
				.getModel();
		if (listModel.size() == 0)
			return null;
		LineAttributeDef[] selectedAttrs = new LineAttributeDef[listModel
				.size()];
		for (int i = 0; i < listModel.size(); i++) {
			selectedAttrs[i] = listModel.elementAt(i);
		}
		return selectedAttrs;
	}

	private JPanel creatSelectedLineAttrPanel() {
		selectedAttrListBox = new JList<LineAttributeDef>(
				new DefaultListModel<LineAttributeDef>());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(
				new JLabel(TextResourceBundle.getInstance().getString(
						"Line Attributes To Display")), BorderLayout.NORTH);
		panel.add(new JScrollPane(selectedAttrListBox), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createAllLineAttrPanel(LineAttributeDef[] allAttrDefs) {
		allAttrListBox = new JList<LineAttributeDef>();
		allAttrListBox.setListData(allAttrDefs);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(
				new JLabel(TextResourceBundle.getInstance().getString(
						"Available Line Attributes")), BorderLayout.NORTH);
		panel.add(new JScrollPane(allAttrListBox), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createBtnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JButton btnAdd = new JButton("<");
		btnAdd.setActionCommand("Add");
		btnAdd.addActionListener(this);
		JButton btnAddAll = new JButton("<<");
		btnAddAll.setActionCommand("AddAll");
		btnAddAll.addActionListener(this);
		JButton btnRemove = new JButton(">");
		btnRemove.setActionCommand("Remove");
		btnRemove.addActionListener(this);
		JButton btnClear = new JButton(">>");
		btnClear.setActionCommand("Clear");
		btnClear.addActionListener(this);

		panel.add(Box.createVerticalGlue());
		panel.add(btnAdd);
		panel.add(btnAddAll);
		panel.add(Box.createVerticalStrut(10));
		panel.add(btnRemove);
		panel.add(btnClear);
		panel.add(Box.createVerticalGlue());
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Add".equals(cmd)) {
			List<LineAttributeDef> selectedAttrs = this.allAttrListBox
					.getSelectedValuesList();
			if (selectedAttrs.isEmpty()) {
				ErrorMsgPanel.showAlarmDialog();
				return;
			}
			DefaultListModel<LineAttributeDef> listModel = (DefaultListModel<LineAttributeDef>) this.selectedAttrListBox
					.getModel();
			for (LineAttributeDef attr : selectedAttrs) {
				listModel.addElement(attr);
			}
		} else if ("AddAll".equals(cmd)) {
			ListModel<LineAttributeDef> allListModel = this.allAttrListBox
					.getModel();
			DefaultListModel<LineAttributeDef> selectedListModel = (DefaultListModel<LineAttributeDef>) this.selectedAttrListBox
					.getModel();
			for (int i = 0; i < allListModel.getSize(); i++) {
				selectedListModel.addElement(allListModel.getElementAt(i));
			}
		} else if ("Remove".equals(cmd)) {
			int[] selectedIndices = this.selectedAttrListBox
					.getSelectedIndices();
			if (selectedIndices.length == 0) {
				ErrorMsgPanel.showAlarmDialog();
				return;
			}
			DefaultListModel<LineAttributeDef> listModel = (DefaultListModel<LineAttributeDef>) this.selectedAttrListBox
					.getModel();
			for (int i = 0; i < selectedIndices.length; i++) {
				listModel.remove(selectedIndices[i]);
			}
		} else if ("Clear".equals(cmd)) {
			DefaultListModel<LineAttributeDef> listModel = (DefaultListModel<LineAttributeDef>) this.selectedAttrListBox
					.getModel();
			listModel.removeAllElements();
		}

	}

	@Override
	public boolean checkInput() {
		String filterName = txtFilter.getText();
		int[] selectedIndices = this.selectedAttrListBox.getSelectedIndices();
		return (filterName == null || filterName.isEmpty() || selectedIndices.length == 0) ? false
				: true;
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
