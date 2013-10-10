package org.rbda.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;

import org.rbda.MainFrame;


/**
 * View Menu Action Listener Implementation
 * @author fujunwei
 *
 */
public class ViewMenuAction extends AbstractAction {
	
	public static final String COMMAND_TOOLBAR = "toolbar";
	public static final String COMMAND_MSGPANE = "msgpane";
	public static final String COMMAND_WORKSPACE = "workspace";
	
	private MainFrame parent;
	
	public ViewMenuAction(String name, MainFrame parent) {
		this(name, null, parent);

	}

	public ViewMenuAction(String name, Icon icon, MainFrame parent) {
		super(name, icon);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)e.getSource();
		String cmd = e.getActionCommand();
		if(COMMAND_TOOLBAR.equals(cmd)){
			parent.setToolBarVisible(menuItem.isSelected());
		}else if(COMMAND_MSGPANE.equals(cmd)){
			parent.setMessagePaneVisible(menuItem.isSelected());
		}else if(COMMAND_WORKSPACE.equals(cmd)){
			parent.setWorkspaceVisible(menuItem.isSelected());
		}
	}

}
