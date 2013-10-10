package org.rbda.equation;

import java.util.Hashtable;

public class Distribution {
	private int id;
	private String name;
	private Hashtable<String, Double> params;
	
	public Distribution(int id, String name){
		this.id = id;
		this.name = name;
		this.params = new Hashtable<String, Double>();
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void addParam(String paramName, Double paramValue){
		params.put(paramName, paramValue);
	}
}
