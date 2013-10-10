package org.rbda.auxclasses;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.rbda.gui.MainFrame;
import org.rbda.gui.NewDistributionFrame;
import org.rbda.pipeline.Pipeseg;
import org.rbda.probability.Distribution;




public class ProjectTreeSelectionListener extends MouseAdapter {

	//主界面的frame
	private MainFrame mainFrame;
	//将主界面的frame(EditorFrame)作为构造参数传入监听器
	public ProjectTreeSelectionListener( MainFrame mainFrame ) {
		this.mainFrame = mainFrame;

	}
        
	public void mousePressed(MouseEvent e) {
		//得到当前所选择的节点
                TreePath treepath = mainFrame.newprojecttree_panel.get_jTree().getSelectionPath();
                //mainFrame.pipeline.pipesegs.
                //System.out.println(((DefaultMutableTreeNode )treepath.getLastPathComponent()).toString());
                
                for (Pipeseg p: mainFrame.pipeline.pipesegs){
                    if (p.name.equals(((DefaultMutableTreeNode )treepath.getLastPathComponent()).toString())){
                        mainFrame.selectedPipeseg = p;
                        /**
                        String name = JOptionPane.showInputDialog(null, "管线参数名字", "输入", JOptionPane.QUESTION_MESSAGE);
                        Distribution distribution = new Distribution();
                        NewDistributionFrame newdistributionFrame = new NewDistributionFrame(distribution);
                        newdistributionFrame.setLocationRelativeTo(null);
                        newdistributionFrame.setSize(400, 400);
                        newdistributionFrame.setVisible(true);
                        selected.properties.put(name, distribution);
                        */
                        break;
                    }
                }    
                
               

	}

}
