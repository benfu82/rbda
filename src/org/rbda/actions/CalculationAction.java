package org.rbda.actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.rbda.biz.LineAttribute;
import org.rbda.biz.Pipeline;
import org.rbda.biz.Section;
import org.rbda.calculate.MenteCarlo;
import org.rbda.calculate.ParametersGroup;
import org.rbda.controls.InfoDialog;
import org.rbda.controls.MessagePanel;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.Equation;
import org.rbda.logging.Logger;
import org.rbda.main.ControlConstants;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;
import org.rbda.pipeline.Pipeseg;
import org.rbda.resource.TextResourceBundle;
import org.rbda.utils.Utils;


public class CalculationAction extends AbstractAction {

	public static final String COMMAND_RELIABLITY = "reliablity_cal";
	protected static final String COMMAND_CALCULATE = "cal";
	protected static final String COMMAND_RECALCULATE = "recal";

	private PipelineReliablityCalPanel pipeReliablityCalPanel;
	private InfoDialog calDialog;
	private JTextField txtCalCount;
	private MessagePanel msgPanel;

	public CalculationAction(String name, PipelineTree pipelineTree,
			MessagePanel msgPanel) {
		this(name, null, pipelineTree, msgPanel);
	}

	public CalculationAction(String name, Icon icon, PipelineTree pipelineTree,
			MessagePanel msgPanel) {
		super(name, icon);

		pipeReliablityCalPanel = new PipelineReliablityCalPanel(pipelineTree,
				this);
		calDialog = new InfoDialog(null, pipeReliablityCalPanel,
				TextResourceBundle.getInstance().getString("Calculate"), true);
		calDialog.setSize(new Dimension(ControlConstants.DIALOG_WIDTH,
				ControlConstants.DIALOG_HEIGHT));
		calDialog.setLocationRelativeTo(null);
		this.msgPanel = msgPanel;
	}

	public void setPipelineTree(PipelineTree pipelineTree) {
		pipeReliablityCalPanel.setPipelineTree(pipelineTree);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (COMMAND_RELIABLITY.equals(cmd)) {
			calReliablity();
		} else if (COMMAND_CALCULATE.equals(cmd)
				|| COMMAND_RECALCULATE.equals(cmd)) {
			calculate();
		}
	}

	private void calReliablity() {
		calDialog.showDialog();
	}

	private void calculate() {
		DBQueryer dbQueryer = (DBQueryer) SystemProperties
				.getProperty(SystemProperties.DB_QUERYER);
		Equation[] equationsInSys = Equation.getAll(dbQueryer);
		Section[] segments = pipeReliablityCalPanel.getSelectedSegments();
		int calCount = pipeReliablityCalPanel.getCalculateCount();
		try {
			for (Section segment : segments) {
				Pipeseg pipeSeg = new Pipeseg();
				pipeSeg.Id = segment.getID();
				pipeSeg.name = segment.getName();
				Collection<LineAttribute> lineAttrs = segment.getAttributes();
				for (Equation equation : equationsInSys) {
					ParametersGroup paramGroup = MenteCarlo
							.Map_Property2Equation(equation.getID(), pipeSeg);
					double ret = MenteCarlo.MenteCarloCalculation(paramGroup,
							calCount);
					String msg = TextResourceBundle.getInstance().getString(
							"CalSuccessed")
							+ ret;
					this.msgPanel.print(msg);
				}
			}
		} catch (Exception ex) {
			this.msgPanel.print(
					TextResourceBundle.getInstance().getString("CalFailed"),
					MessagePanel.MSG_TYPE_ERROR);
			this.msgPanel.print(ex.getMessage());
			Logger.getInstance().error("Calculate Error");
			Logger.getInstance().error(ex.getMessage());
		}
	}

}

class PipelineReliablityCalPanel extends JPanel {
	private PipelineTree pipelineTree;
	private CalculationAction action;
	private JTextField txtCalCount;

	public PipelineReliablityCalPanel(PipelineTree pipelineTree,
			CalculationAction action) {
		this.pipelineTree = Utils.clonePipelineTree(pipelineTree);
		this.action = action;

		init();
	}

	public void setPipelineTree(PipelineTree pipelineTree) {
		this.pipelineTree = Utils.clonePipelineTree(pipelineTree);
		this.add(new JScrollPane(this.pipelineTree), BorderLayout.CENTER);
	}

	private void init() {
		JLabel lblCalCount = new JLabel(TextResourceBundle.getInstance()
				.getString("CalCount"));
		txtCalCount = new JTextField();
		JButton btnCal = new JButton(TextResourceBundle.getInstance()
				.getString("Calculate"));
		btnCal.setActionCommand(CalculationAction.COMMAND_CALCULATE);
		btnCal.addActionListener(this.action);

		JButton btnReCal = new JButton(TextResourceBundle.getInstance()
				.getString("ReCalculate"));
		btnReCal.setActionCommand(CalculationAction.COMMAND_RECALCULATE);
		btnReCal.addActionListener(this.action);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
		btnPanel.add(lblCalCount);
		btnPanel.add(txtCalCount);
		btnPanel.add(btnCal);
		btnPanel.add(btnReCal);
		btnPanel.add(Box.createVerticalGlue());

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(pipelineTree), BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.EAST);
	}

	public Section[] getSelectedSegments() {
		return pipelineTree.getSelectedSections();
	}

	public int getCalculateCount() {
		return Integer.parseInt(txtCalCount.getText());
	}

}
