/*
 * 该类实现蒙特卡罗仿真
 * 主要完成以下几项功能：
 * 1、方程与管段参数对应
 * 2、检验是否已经计算过
 * 3、蒙特卡罗仿真
 * 4、
 * 其中最关键的是类中的一个全局变量calculated_equation_parameters，
 * 存放计算过的方程参数和结果，这些信息包含在ParametersGroup对象中；
 * HashMap中的值是{equation_Id, List<ParametersGroup>}
 * 而ParametersGroup本身是List<Distribution>
 * 所以HashMap中放的其实是分布List的List
 */
package org.rbda.calculate;

import java.util.*;
import org.rbda.limitstateequation.*;
import org.rbda.pipeline.Pipeseg;
import org.rbda.probability.Distribution;

/**
 * 
 * @author kewin
 */
public final class MenteCarlo {

	public static HashMap calculated_equation_parameters = new HashMap();

	/**
	 * 将极限状态方程所需要参数值从管段上取出来
	 */
	public static ParametersGroup Map_Property2Equation(int equation_Id,
			Pipeseg pipeseg) {
		List<Distribution> parameters = new ArrayList<Distribution>();
		for (int i = 0; i < EquationInformation.parameters_number[equation_Id]; i++) {
			// 从方程信息类中找到极限状态方程参数名列表，再从管段对象的HASH表中将该名参数值找到返回
			parameters.add((Distribution) pipeseg.properties
					.get(EquationInformation.parameters_names[equation_Id][i]));
		}
		ParametersGroup parametersgroup = new ParametersGroup(parameters);
		parametersgroup.setEquation_Id(equation_Id);
		return parametersgroup;
	}

	/**
	 * 检查方程是否计算过 在用MenteCarloCalculation计算之前，要先调用该函数进行检查
	 */
	public static boolean CheckCalculated(ParametersGroup parametersgroup) {
		boolean calculated = false;
		int equation_Id = parametersgroup.getEquation_Id();
		List<Distribution> parameters = parametersgroup.getParamters_group();

		if (calculated_equation_parameters.containsKey(equation_Id)) {
			ParametersGroup temp_parametersgroup;
			List<Distribution> temp_parameters = new ArrayList<Distribution>();
			// 遍历该方程下所有计算过的参数组
			Iterator iter = ((List<ParametersGroup>) calculated_equation_parameters
					.get(equation_Id)).iterator();
			GROUP: while (iter.hasNext() && (!calculated)) {
				temp_parametersgroup = (ParametersGroup) iter.next();
				temp_parameters = temp_parametersgroup.getParamters_group();
				// 现在取到了一组参数值，接下来将与给定参数值比较
				Iterator iter1 = temp_parameters.iterator();
				Iterator iter2 = parameters.iterator();
				Distribution temp_distri, in_distri;
				while (iter1.hasNext()) {
					temp_distri = (Distribution) iter1.next();
					in_distri = (Distribution) iter2.next();
					// 首先比较是否分布类型相同，如果不同分布则直接跳转进行下一组参数比较
					if (temp_distri.id != in_distri.id) {
						continue GROUP;
					} else {// 如果分布类型也相同，则比较参数值
						for (int i = 0; i < temp_distri.parameters.length; i++) {
							// 如果分布参数不同则直接跳转进行下一组参数比较
							if (temp_distri.parameters[i] != in_distri.parameters[i]) {
								continue GROUP;
							}
						}// 保证分布的参数值相同
					}// 保证分布类型相同
				}// 所有的分布相同
					// 验证了计算过该方程，且方程的参数组中存与输入参数组相同的值，则直接赋计算结果
				parametersgroup.equation_fail_probability = temp_parametersgroup.equation_fail_probability;
				calculated = true;// 一旦有相同，则修改标记，从而不再验（由while条件包含对它的判断）
			}
		}
		return calculated;
	}

	/**
	 * 蒙特卡罗计算，入口参数parametersgroup中已经包含了方程Id和参数链表
	 * 最后再将计算结果放回parametersgroup中，所以不用返回值也可以。
	 */
	public static double MenteCarloCalculation(ParametersGroup parametersgroup,
			int n) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {

		double result = 0;
		int m = 0;
		int equation_Id = parametersgroup.getEquation_Id();
		// 用parametersgroup中的方程Id取到方程名，然后用方程名字生成极限状态方程，再用来计算。
		LimitStateEquation ls = (LimitStateEquation) (Class
				.forName("org.rbda.limitstateequation."
						+ (String) EquationInformation.equations_name2fun_map
								.get(EquationInformation.equations_name[equation_Id]))
				.newInstance());
		ls.Init(equation_Id);
		Iterator iter;
		iter = parametersgroup.getParamters_group().iterator();
		List<Distribution> parameters = new ArrayList();
		Distribution dss;
		int i = 0;
		while (iter.hasNext()) {
			dss = (Distribution) iter.next();
			if (dss.id == 9) {// 为常数
				ls.parameters[i] = dss.parameters[0];
			} else {
				parameters.add(dss);
			}
			i++;
		}

		// 只将随机分布取出来抽样
		parametersgroup.setDistri_parameters_positions();
		// 开始计算，最重要的地方！！！！
		Distribution ds;
		for (int simu_times = 0; simu_times < n; simu_times++) {
			iter = parameters.iterator();
			i = 0;
			while (iter.hasNext()) {
				ds = (Distribution) iter.next();
				ls.parameters[parametersgroup.Distri_parameters_positions[i]] = ds
						.sample_one();
				i++;
			}
			if (ls.EquationValue() < 0) {
				m++;
			}
		}
		result = ((double) m) / (double) n;

		// 先保存计算结果
		parametersgroup.equation_fail_probability = result;
		// 再将ParametersGroup加入到HashMap中极限状态方程的参数组列表中
		if (calculated_equation_parameters.containsKey(equation_Id)) {
			((List<ParametersGroup>) calculated_equation_parameters
					.get(equation_Id)).add(parametersgroup);
		} else {
			List<ParametersGroup> new_parametersgroup_list = new ArrayList<ParametersGroup>();
			new_parametersgroup_list.add(parametersgroup);
			calculated_equation_parameters.put(equation_Id,
					new_parametersgroup_list);
		}
		return result;
	}

}
