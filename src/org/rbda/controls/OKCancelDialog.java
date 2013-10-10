package org.rbda.controls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.rbda.main.ErrorMsgPanel;
import org.rbda.resource.TextResourceBundle;


/**
 * Dialog Window Implementation
 * 
 * @author fujunwei
 * 
 */
public class OKCancelDialog extends JDialog implements ActionListener, Runnable {
	public static final int DIALOG_OK = 0;
	public static final int DIALOG_CANCEL = 1;

	private int optionResult = DIALOG_OK;
	private IOKCancel checker;

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
	public OKCancelDialog(Frame owner, Component content, String title) {
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
	public OKCancelDialog(Frame owner, Component content, String title,
			Boolean modal) {
		super(owner, title, modal);

		JButton btnOk = new JButton(TextResourceBundle.getInstance().getString(
				"OK"));
		btnOk.setActionCommand("ok");
		btnOk.addActionListener(this);
		JButton btnCancel = new JButton(TextResourceBundle.getInstance()
				.getString("Cancel"));
		btnCancel.setActionCommand("cancel");
		btnCancel.addActionListener(this);
		JPanel btnPane = new JPanel();
		btnPane.add(btnOk);
		btnPane.add(btnCancel);
		if (content != null) {
			this.getContentPane().add(content, BorderLayout.CENTER);
		}
		this.getContentPane().add(btnPane, BorderLayout.PAGE_END);

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
			this.getContentPane().add(content);
		}
	}

	public void setValidChecker(IOKCancel checker) {
		this.checker = checker;
	}

	public int getOptionResult() {
		return this.optionResult;
	}

	public void showDialog() {
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("ok".equals(cmd)) {
			if (checker != null && !checker.checkInput()) {
				shake();
				return;
			}
			checker.ok();
			optionResult = DIALOG_OK;
		} else if ("cancel".equals(cmd)) {
			checker.cancel();
			optionResult = DIALOG_CANCEL;
		}
		setVisible(false);
	}

	@Override
	public void run() {
		int moveDistance = 5;
		Point curLoc = this.getLocation();
		this.setLocation(curLoc.x, curLoc.y);
		try {
			Thread.sleep(30);
			this.setLocation(curLoc.x + moveDistance, curLoc.y + moveDistance);
			Thread.sleep(30);
			this.setLocation(curLoc.x, curLoc.y);
			Thread.sleep(30);
			this.setLocation(curLoc.x + moveDistance, curLoc.y);
			Thread.sleep(30);
			this.setLocation(curLoc.x, curLoc.y + moveDistance);
			Thread.sleep(30);
			this.setLocation(curLoc.x, curLoc.y);
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}
	}

	private void shake() {
		new Thread(this).start();
	}

}
