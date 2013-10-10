package org.rbda.biz;

import java.util.ArrayList;
import java.util.Collection;

import org.rbda.main.SystemProperties;

public class LineAttributeDef {
	private int id;
	private String name;
	private Collection<Integer> optionalDistributes; // 可选的分布类型标识集合

	public LineAttributeDef(int id, String name) {
		this.id = id;
		this.name = name;

		optionalDistributes = new ArrayList<Integer>();
		optionalDistributes.addAll((Collection<Integer>) SystemProperties
				.getProperty(SystemProperties.DISTRIBUTION_ID_COLLECTION));
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isSelectable() {
		return (optionalDistributes == null || optionalDistributes.size() == 0) ? false
				: true;
	}

	public Integer[] getOptionalDistributes() {
		return (optionalDistributes != null) ? optionalDistributes
				.toArray(new Integer[0]) : null;
	}

	public String toString() {
		return name;
	}

}
