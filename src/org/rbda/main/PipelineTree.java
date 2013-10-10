package org.rbda.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.rbda.actions.PipelineAction;
import org.rbda.biz.Pipeline;
import org.rbda.biz.Scenario;
import org.rbda.biz.Section;
import org.rbda.biz.Segment;

import org.rbda.controls.CheckBoxTree;
import org.rbda.controls.CheckBoxTreeNode;
import org.rbda.resource.TextResourceBundle;

public class PipelineTree extends CheckBoxTree implements MouseListener,
		ActionListener {
	private CheckBoxTreeNode systemNode = null;
	private CheckBoxTreeNode scenarioNode = null;
	private JPopupMenu systemPopupMenu;
	private JPopupMenu pipePopupMenu;
	private Pipeline selectedPipeline;
	private ArrayList<Pipeline> pipelineList;
	private ArrayList<Scenario> scenarioList;
	private String name;
	
	private CheckBoxTreeNode selectedNode;

	public PipelineTree(String name) {
		super(name);
	
		pipelineList = new ArrayList<Pipeline>();
		scenarioList = new ArrayList<Scenario>();
		CheckBoxTreeNode root = (CheckBoxTreeNode) this.getModel().getRoot();
		systemNode = new CheckBoxTreeNode(TextResourceBundle.getInstance()
				.getString("System"));
		scenarioNode = new CheckBoxTreeNode(TextResourceBundle.getInstance()
				.getString("Scenario"));

		root.add(systemNode);
		root.add(scenarioNode);

		createPopupMenu();

		addMouseListener(this);
		expandAll(this, new TreePath(root), true);
	}
	
	public String getTreeName(){
		return this.name;
	}

	public void addPipeline(Pipeline pipeline) {
		CheckBoxTreeNode pipeNode = new CheckBoxTreeNode(pipeline);
		pipeNode.add(new CheckBoxTreeNode(TextResourceBundle.getInstance()
				.getString("Property")));
		pipeNode.add(new CheckBoxTreeNode(TextResourceBundle.getInstance()
				.getString("SegmentSet")));
		systemNode.add(pipeNode);
		pipelineList.add(pipeline);
	}

	public Pipeline getSelectedPipeline() {
		return selectedPipeline;
	}
	
	public Pipeline[] getPipelines(){
		return pipelineList.toArray(new Pipeline[0]);
	}
	
	public Scenario[] getScenarios(){
		return scenarioList.toArray(new Scenario[0]);
	}
	
	public void addSegment(Section segment){
		CheckBoxTreeNode segSetNode = (CheckBoxTreeNode)selectedNode.getChildAt(1);
		segSetNode.add(new CheckBoxTreeNode(segment));
	}

	public Section[] getSelectedSections(){
		ArrayList<Section> selectedSections = new ArrayList<Section>();
		
		for(int i=0;i<systemNode.getChildCount();i++){
			CheckBoxTreeNode pipeNode = (CheckBoxTreeNode)systemNode.getChildAt(i);
			CheckBoxTreeNode segSetsNode = (CheckBoxTreeNode)pipeNode.getChildAt(1);
			for(int j=0;j<segSetsNode.getChildCount();j++){
				CheckBoxTreeNode segNode = (CheckBoxTreeNode)segSetsNode.getChildAt(i);
				if(segNode.isSelected()){
					selectedSections.add((Section)segNode.getUserObject());
				}
			}
		}
		return selectedSections.toArray(new Section[0]);
	}
	
	public Segment[] getSelectedSegments(){
		ArrayList<Segment> selectedSegments = new ArrayList<Segment>();
		
		for(int i=0;i<systemNode.getChildCount();i++){
			CheckBoxTreeNode pipeNode = (CheckBoxTreeNode)systemNode.getChildAt(i);
			CheckBoxTreeNode segSetsNode = (CheckBoxTreeNode)pipeNode.getChildAt(1);
			for(int j=0;j<segSetsNode.getChildCount();j++){
				CheckBoxTreeNode segNode = (CheckBoxTreeNode)segSetsNode.getChildAt(i);
				if(segNode.isSelected()){
					selectedSegments.add((Segment)segNode.getUserObject());
				}
			}
		}
		return selectedSegments.toArray(new Segment[0]);
	}
	
	private void createPopupMenu() {
		// system node popup menu
		PipelineAction pipeAction = new PipelineAction("", this);
		systemPopupMenu = new JPopupMenu();
		JMenuItem createPipeItem = new JMenuItem(TextResourceBundle
				.getInstance().getString("CreatePipeline"));
		createPipeItem.setActionCommand(PipelineAction.CMD_CREATE);
		createPipeItem.addActionListener(pipeAction);
		systemPopupMenu.add(createPipeItem);

		// pipeline node popup menu
		pipePopupMenu = new JPopupMenu();
		JMenuItem pipePropItem = new JMenuItem(TextResourceBundle.getInstance()
				.getString("Property"));
		pipePropItem.setActionCommand(PipelineAction.CMD_EDIT_ATTRS);
		pipePropItem.addActionListener(pipeAction);
		pipePopupMenu.add(pipePropItem);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("CreatePipeline".equals(cmd)) {

		} else if ("PipelineProp".equals(cmd)) {

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 3) {
			PipelineTree tree = (PipelineTree) e.getComponent();
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path == null)
				return;
			selectedNode = (CheckBoxTreeNode) path
					.getLastPathComponent();
			if (selectedNode == systemNode) {
				systemPopupMenu.show(tree, e.getX(), e.getY());
			} else {
				Object obj = selectedNode.getUserObject();
				if (obj instanceof Pipeline) {
					selectedPipeline = (Pipeline) obj;
					pipePopupMenu.show(tree, e.getX(), e.getY());
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
