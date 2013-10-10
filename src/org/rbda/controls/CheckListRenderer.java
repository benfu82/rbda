package org.rbda.controls;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class CheckListRenderer extends JPanel implements ListCellRenderer {
	private JLabel label;
	private JCheckBox checkBox;

	public CheckListRenderer() {
		//checkBox.setBackground(UIManager.getColor("List.textBackground"));
		//label.setForeground(UIManager.getColor("List.textForeground"));
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		setEnabled(list.isEnabled());
		checkBox.setSelected(((CheckBoxItem) value).isChecked());
		label.setFont(list.getFont());
		label.setText(value.toString());
		return this;
	}
}
