package org.rbda.equation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rbda.biz.LineAttributeDef;
import org.rbda.limitstateequation.EquationInformation;
import org.rbda.logging.Logger;

/**
 * Equation Collection
 * 
 * @author fujunwei
 * 
 */
public class EquationCollection {
	private List<Equation> allEquations;
	private List<Equation> selectedEquations;

	public EquationCollection() {
		allEquations = new ArrayList<Equation>();
		selectedEquations = new ArrayList<Equation>();
	}

	public void addEquation(Equation equation) {
		if (equation == null)
			return;
		allEquations.add(equation);
	}

	/**
	 * Get the all equation
	 * 
	 * @return
	 */
	public Equation[] getAll() {
		return allEquations.size() == 0 ? null : allEquations
				.toArray(new Equation[0]);
	}

	/**
	 * Get equation count
	 * 
	 * @return
	 */
	public int getCount() {
		return allEquations.size();
	}

	/**
	 * Get equation indexed at @index
	 * 
	 * @param index
	 * @return
	 */
	public Equation getEquationAt(int index) {
		if (index < 0 || index >= allEquations.size())
			return null;

		return allEquations.get(index);
	}

	public Equation getEquationByID(int id) {
		Equation found = null;
		for (Equation equation : allEquations) {
			if (equation.getID() == id) {
				found = equation;
				break;
			}
		}
		return found;
	}

	/**
	 * Load the all equation
	 */
	public void loadAllEquation() {
		EquationInformation.EquationInformationInit(); // 读极限状态方程基础信息
		String[] equationNames = EquationInformation.equations_name; // 方程的名字数组
		int[] parameterCounts = EquationInformation.parameters_number; // 方程所需参数个数
		String[][] parameterNames = EquationInformation.parameters_names; // 方程中参数名字
		if (equationNames == null || equationNames.length == 0) {
			Logger.getInstance().error("the system don't load any equation");
		}
		for (int i = 0; i < equationNames.length; i++) {
			Equation equation = new Equation(i + 1,
					equationNames[i]);
			equation.setParameterNames(parameterNames[i]);
			allEquations.add(equation);
		}
	}
}
