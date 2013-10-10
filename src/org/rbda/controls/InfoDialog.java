package org.rbda.controls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.rbda.resource.TextResourceBundle;


/**
 * Information Dialog Window Implementation
 * 
 * @author fujunwei
 * 
 */
public class InfoDialog extends JDialog implements ActionListener{
	
	/**
	 * Constructor
	 * 
	 * @param owner
	 *            the Frame from which the dialog is displayed
	 * @param content
	 *            the content which the dialog is displayed
	 * @param title
	 *            dialog title
	 */
	public InfoDialog(Frame owner, Component content, String title) {
		this(owner, content, title, false);
	}

	/**
	 * Constructor
	 * 
	 * @param owner
	 *            the Frame from which the dialog is displayed
	 * @param content
	 *            the component which the dialog is displayed
	 * @param title
	 *            dialog title
	 * @param modal
	 *            specifies whether dialog blocks user input to other top-level
	 *            windows when shown
	 */
	public InfoDialog(Frame owner, Component content, String title, Boolean modal) {
		super(owner, title, modal);
		
		this.getContentPane().setLayout(new BorderLayout());
		if (content != null) {
			this.getContentPane().add(content, BorderLayout.CENTER);
		}
		
		JButton btnOK = new JButton(TextResourceBundle.getInstance().getString("OK"));
		btnOK.setActionCommand("OK");
		btnOK.addActionListener(this);
		
		JButton btnHelp = new JButton(TextResourceBundle.getInstance().getString("Help"));
		btnHelp.setActionCommand("help");
		btnHelp.addActionListener(this);
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.add(btnOK);
		btnPanel.add(btnHelp);
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
		
	}

	/**
	 * set the component which the dialog is displayed
	 * 
	 * @param content
	 *            the component which the dialog is displayed
	 */
	public void setContentComponent(Component content) {
		this.getContentPane().removeAll();
		if (content != null) {
			this.getContentPane().add(content, BorderLayout.CENTER);
		}
	}

	public void showDialog() {
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd  = e.getActionCommand();
		if("OK".equals(cmd)){
			this.setVisible(false);
		}else if("help".equals(cmd)){
			
		}
	}
	
	

}
