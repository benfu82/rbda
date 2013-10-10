package org.rbda.utils;

import java.util.ArrayList;
import java.util.List;

import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.Pipeline;
import org.rbda.equation.Equation;
import org.rbda.main.PipelineTree;
import org.rbda.main.SystemProperties;

public class Utils {
	public static PipelineTree clonePipelineTree(PipelineTree clonedTree) {
		if (clonedTree == null)
			return null;
		PipelineTree pipelineTree = new PipelineTree(clonedTree.getTreeName());
		Pipeline[] pipelines = clonedTree.getPipelines();
		if (pipelines != null) {
			for (Pipeline pipeline : pipelines) {
				pipelineTree.addPipeline(pipeline);
			}
		}
		return pipelineTree;
	}

	public static String[] getEquationParamNames(Equation[] equations) {
		ArrayList<String> paramNames = new ArrayList<String>();
		for (Equation equation : equations) {
			int equationParamCount = equation.getParameterCount();
			for (int i = 0; i < equationParamCount; i++) {
				if (!paramNames.contains(equation.getParameterNameAt(i))) {
					paramNames.add(equation.getParameterNameAt(i));
				}
			}
		}
		return paramNames.toArray(new String[0]);
	}

	public static boolean isNumber(String text) {
		boolean isNumber = true;
		try {
			Double.parseDouble(text);
		} catch (Exception ex) {
			isNumber = false;
		}
		return isNumber;
	}
}
