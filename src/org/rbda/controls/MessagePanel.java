package org.rbda.controls;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Message Panel
 * 
 * @author fujunwei
 * 
 */
public class MessagePanel extends JPanel {
	public static final int MSG_TYPE_INFO = 0;
	public static final int MSG_TYPE_ERROR = 1;

	private SimpleAttributeSet errorStyleAttrs = null;
	private SimpleAttributeSet infoStyleAttrs = null;
	private JTextPane msgPane = null;

	public MessagePanel() {
		initTextStyle();
		msgPane = new JTextPane();
		msgPane.setEditable(false);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(msgPane), BorderLayout.CENTER);
	}

	public void print(String msg) {
		print(msg, MSG_TYPE_INFO);
	}

	/**
	 * Output message, if error message, display it applying error style. or
	 * else, applying general style
	 * 
	 * @param msg
	 *            message content
	 * @param msgType
	 *            message type, whether error or info type.
	 */
	public void print(String msg, int msgType) {
		SimpleAttributeSet attrSet = null;
		attrSet = msgType == MSG_TYPE_ERROR ? errorStyleAttrs : infoStyleAttrs;

		try {
			Document doc = msgPane.getDocument();
			doc.insertString(doc.getLength(), msg, attrSet);
		} catch (BadLocationException ex) {

		}
	}

	// initialize text style
	private void initTextStyle() {
		// init error text style
		errorStyleAttrs = new SimpleAttributeSet();
		StyleConstants.setForeground(errorStyleAttrs, Color.RED);
		StyleConstants.setItalic(errorStyleAttrs, true);
		StyleConstants.setFontSize(errorStyleAttrs, 24);

		// init info text style
		infoStyleAttrs = new SimpleAttributeSet();
		StyleConstants.setForeground(infoStyleAttrs, Color.BLACK);
		StyleConstants.setItalic(infoStyleAttrs, false);
		StyleConstants.setFontSize(infoStyleAttrs, 20);
	}
}
