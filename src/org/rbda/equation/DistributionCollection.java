package org.rbda.equation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rbda.biz.LineAttributeDef;
import org.rbda.logging.Logger;
import org.rbda.probability.DistributionInformation;

/**
 * Distribution Collection
 * 
 * @author fujunwei
 * 
 */
public class DistributionCollection {
	private List<Distribution> allDistributions;

	public DistributionCollection() {
		allDistributions = new ArrayList<Distribution>();
	}

	public void addDistribution(Distribution Distribution) {
		if (Distribution == null)
			return;
		allDistributions.add(Distribution);
	}

	/**
	 * Get the all Distribution
	 * 
	 * @return
	 */
	public Distribution[] getAll() {
		return allDistributions.size() == 0 ? null : allDistributions
				.toArray(new Distribution[0]);
	}

	/**
	 * Get Distribution count
	 * 
	 * @return
	 */
	public int getCount() {
		return allDistributions.size();
	}

	/**
	 * Get Distribution indexed at @index
	 * 
	 * @param index
	 * @return
	 */
	public Distribution getDistributionAt(int index) {
		if (index < 0 || index >= allDistributions.size())
			return null;

		return allDistributions.get(index);
	}

	public Distribution getDistributionByID(int id) {
		Distribution found = null;
		for (Distribution distribution : allDistributions) {
			if (distribution.getID() == id) {
				found = distribution;
				break;
			}
		}
		return found;
	}
	
	public Collection<Integer> getAllDistributionIDs(){
		Collection<Integer> distributionIDs = new ArrayList<Integer>();
		for (Distribution distribution : allDistributions) {
			distributionIDs.add(distribution.getID());
		}
		return distributionIDs;
	}

	/**
	 * Load the all Distribution
	 */
	public void loadAllDistribution() {
		DistributionInformation.DistributionInformationInit(); // 读分布式函数基础信息
		String[] distributionNames = DistributionInformation.distributions_name; // 分布式函数名字数组
		int[] parameterCounts = DistributionInformation.parameters_number; // 分布式函数所需参数个数
		String[][] parameterNames = DistributionInformation.parameters_names; // 分布式函数参数名字
		if (distributionNames == null || distributionNames.length == 0) {
			Logger.getInstance()
					.error("the system don't load any Distribution");
		}
		for (int i = 0; i < distributionNames.length; i++) {
			Distribution distribution = new Distribution(i + 1,
					distributionNames[i]);
			for (int j = 0; j < parameterNames[i].length; j++) {
				distribution.addParam(parameterNames[i][j], 0.0);
			}
			allDistributions.add(distribution);
		}
	}
}
