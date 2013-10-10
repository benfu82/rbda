package org.rbda.controls;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.rbda.biz.Scenario;
import org.rbda.biz.Segment;
import org.rbda.equation.Equation;

public class EvaluationChartPanel extends JPanel implements ActionListener{
	private static final String CMD_SAVE = "save";
	
	private JComboBox<Object> comboScenario;
	private JComboBox<Object> comboEquation;
	private JComboBox comboReliability;
	private JComboBox<Integer> comboYear;
	
	private JFreeChart lineChart;
	
	public EvaluationChartPanel(){
		
	}
	
	private void init(){
		setLayout(new BorderLayout());
	}
	
	private void initToolButton(){
		JPanel pnlTool = new JPanel();
		pnlTool.setLayout(new BoxLayout(pnlTool, BoxLayout.LINE_AXIS));
		
		JButton btnSave = new JButton(new ImageIcon("icons/save.png"));
		btnSave.setActionCommand(CMD_SAVE);
		btnSave.addActionListener(this);
		
		comboScenario = new JComboBox<Object>();
		comboEquation = new JComboBox<Object>();
		comboReliability = new JComboBox();
		comboYear = new JComboBox<Integer>();
		
		pnlTool.add(btnSave);
		pnlTool.add(comboScenario);
		pnlTool.add(comboEquation);
		pnlTool.add(comboReliability);
		pnlTool.add(comboYear);
		
		add(pnlTool, BorderLayout.NORTH);
	}
	
	private void initChartPanel(){
		lineChart = ChartFactory.createLineChart("", "", "", null);
	}

	public void setScenarios(Scenario[] scenarios){
		if(scenarios == null) return;
		comboScenario.removeAllItems();
		for(Scenario scenario: scenarios){
			comboScenario.addItem(scenario);
		}
	}
	
	public void setSegments(Segment[] segments){
		if(segments == null) return;
		comboScenario.removeAllItems();
		for(Segment segment: segments){
			comboScenario.addItem(segment);
		}
	}
	
	public void setEquations(Equation[] equations){
		if(equations == null) return;
		comboEquation.removeAllItems();
		for(Equation equation: equations){
			comboEquation.addItem(equation);
		}
	}
	
	public void setData(XYDataset dataSet){
		XYPlot xyPlot = (XYPlot)lineChart.getPlot();
		xyPlot.setDataset(dataSet);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
