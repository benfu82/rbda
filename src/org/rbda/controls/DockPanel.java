package org.rbda.controls;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author fujunwei
 *
 */
public class DockPanel extends JPanel {
	private JScrollPane eastPane = null;
	private JScrollPane westPane = null;
	private JScrollPane southPane = null;
	
	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	
	
	public DockPanel(){
		eastPane = new JScrollPane();
		eastPane.setMinimumSize(new Dimension(200,300));
		westPane = new JScrollPane();
		westPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    JSplitPane leftRightPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true ,   
	    		eastPane, westPane); 
	    leftRightPane.setOneTouchExpandable(true);
	    
	    southPane = new JScrollPane();
	    JSplitPane contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true ,   
	    		leftRightPane, southPane); 
	    contentPane.setOneTouchExpandable(true);
	    contentPane.resetToPreferredSizes();  
	    add(contentPane);
	}
	
	
	/**
	 * add component into panel
	 * @param comp  added component
	 * @param position layout position, which is EAST or WEST or SOUTH, see constants
	 */
	public void add(JComponent comp, int position){
		if(position == EAST){
			eastPane.add(comp);
		}else if(position == WEST){
			westPane.add(comp);
		}else if(position == SOUTH){
			southPane.add(comp);
		}
	}
}
