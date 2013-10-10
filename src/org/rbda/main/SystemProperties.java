package org.rbda.main;

import java.util.HashMap;

public class SystemProperties {
	private static HashMap<String, Object> props = new HashMap<String, Object>();
	
	public static final String EQUATION_COLLECTION = "equations";
	public static final String LINE_ATTRIBUTE_COLLECTION = "lineattributes";
	public static final String STATE_EDITING = "state_editing";
	public static final String DB_QUERYER = "db_queryer";
	public static final String DISTRIBUTION_COLLECTION = "distributions";
	public static final String DISTRIBUTION_ID_COLLECTION = "distributionIDs";
	
	public static void setProperty(String key, Object value){
		props.put(key, value);
	}
	
	public static Object getProperty(String key){
		return props.get(key);
	}
	
	public static boolean containsKey(String key){
		return props.containsKey(key);
	}
}
