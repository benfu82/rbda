package org.rbda.controls;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.rbda.resource.TextResourceBundle;


/**
 * CheckBoxTree, whose node cell render is CheckBox.
 * 
 * @author fujunwei
 * 
 */
public class CheckBoxTree extends JTree {

	public CheckBoxTree() {
		this("root");
	}
	
	
	public CheckBoxTree(String name) {
		this(new CheckBoxTreeNode(name));
	}
	
	
	public CheckBoxTree(CheckBoxTreeNode root){
		super(root);
		this.addMouseListener(new CheckBoxTreeNodeSelectionListener()); 
		this.setCellRenderer(new CheckBoxTreeCellRender());
	}

	public void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();

		if (node.getChildCount() > 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);

			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

}


class CheckBoxTreeCellRender extends JPanel implements TreeCellRenderer {
	
	private Icon openIcon, closedIcon, leafIcon;
	protected JCheckBox checkBox;
	private JLabel lblTxt, lblIcon;

	public CheckBoxTreeCellRender() {
		openIcon = UIManager.getIcon("Tree.openIcon");
		closedIcon = UIManager.getIcon("Tree.closedIcon");
		leafIcon = UIManager.getIcon("Tree.leafIcon");

		checkBox = new JCheckBox();
		checkBox.addMouseListener(new CheckBoxTreeNodeSelectionListener());
		lblTxt = new JLabel();
		lblIcon = new JLabel();
		
		this.setBackground(UIManager.getColor("Tree.textBackground"));
		checkBox.setBackground(UIManager.getColor("Tree.textBackground"));
		lblTxt.setForeground(UIManager.getColor("Tree.textForeground"));
		lblIcon.setForeground(UIManager.getColor("Tree.textForeground"));
	
		setLayout(null);
		add(lblIcon);
		add(checkBox);
		add(lblTxt);
	}

	public CheckBoxTreeCellRender(Icon open, Icon closed, Icon leaf) {
		this.openIcon = open;
		this.closedIcon = closed;
		this.leafIcon = leaf;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// TODO Auto-generated method stub
		this.setEnabled(tree.isEnabled());
		lblTxt.setText(value.toString());
		if(value instanceof CheckBoxTreeNode){
			CheckBoxTreeNode node = (CheckBoxTreeNode)value;
			checkBox.setSelected(node.isSelected());
		}
		
		if(hasFocus)  
            setBackground(UIManager.getColor("Tree.selectionBackground"));  
        else 
        	setBackground(UIManager.getColor("Tree.textBackground")); 
		
		if (leaf) {
			lblIcon.setIcon(leafIcon);
		} else if(expanded) {
			lblIcon.setIcon(openIcon);
		}else {
			lblIcon.setIcon(closedIcon);
		}

		return this;
	}
	
	
	@Override 
    public Dimension getPreferredSize()  
    {  
		Dimension dLblIcon = lblIcon.getPreferredSize();
        Dimension dCheck = checkBox.getPreferredSize();  
        Dimension dLblTxt = lblTxt.getPreferredSize();  
        return new Dimension(dCheck.width + dLblIcon.width+dLblTxt.width, dCheck.height < dLblTxt.height ? dLblTxt.height: dCheck.height);  
    }  
      
    @Override 
    public void doLayout()  
    {  
        Dimension dCheck = checkBox.getPreferredSize();  
        Dimension dLblIcon = lblIcon.getPreferredSize();  
        Dimension dLblTxt = lblTxt.getPreferredSize();  
        int yCheck = 0;  
        int yLabel = 0;  
        if(dCheck.height < dLblIcon.height)  
            yCheck = (dLblIcon.height - dCheck.height) / 2;  
        else 
            yLabel = (dCheck.height - dLblIcon.height) / 2;  
        lblIcon.setLocation(0, yCheck);
        lblIcon.setBounds(0,yCheck, dLblIcon.width, dLblIcon.height);
        checkBox.setLocation(dLblIcon.width, yCheck);  
        checkBox.setBounds(dLblIcon.width, yCheck, dCheck.width, dCheck.height);  
        lblTxt.setLocation(dLblIcon.width+dCheck.width, yLabel);  
        lblTxt.setBounds(dLblIcon.width+dCheck.width, yLabel, dLblTxt.width, dLblTxt.height);  
    }  
}


class CheckBoxTreeNodeSelectionListener extends MouseAdapter  
{  
    @Override 
    public void mouseClicked(MouseEvent event)  
    {  
        JTree tree = (JTree)event.getSource();  
        int x = event.getX();  
        int y = event.getY();  
        int row = tree.getRowForLocation(x, y);  
        TreePath path = tree.getPathForRow(row);  
        if(path != null)  
        {  
            CheckBoxTreeNode node = (CheckBoxTreeNode)path.getLastPathComponent();  
            if(node != null)  
            {  
                node.setSelected(!node.isSelected()); 
                ((DefaultTreeModel)tree.getModel()).nodeStructureChanged(node);  
            }  
        }  
    }  
} 
