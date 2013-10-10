package org.rbda.biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.rbda.dao.DBQueryer;
import org.rbda.logging.Logger;

public class LineAttribute {
	public static final String TABLE_NAME = "seg_attribute";
	public static final String SQL_LINE_ATTRIBUTE = "create table "
			+ LineAttribute.TABLE_NAME + "("
			+ "seg_id int, attr_name varchar(256), distribute_id int,"
			+ "param_name varchar(64), param_value double );";

	private int id;
	private int segID;
	private String name;
	private int distributeID;
	private Hashtable<String, Double> params;

	public LineAttribute(int id, int seg_id, String name) {
		this(id, -1, name, -1);
	}

	public LineAttribute(int id, int segID, String name, int distributeID) {
		this.id = id;
		this.segID = segID;
		this.name = name;
		this.distributeID = distributeID;
		this.params = new Hashtable<String, Double>();
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setDistributeID(int distributeID) {
		this.distributeID = distributeID;
	}

	public int getDistributeID() {
		return this.distributeID;
	}

	public void addParam(String paramName, Double paramValue) {
		params.put(paramName, paramValue);
	}

	public String[] getParamNames() {
		return params.keySet().toArray(new String[0]);
	}

	public Double getParamValue(String paramName) {
		return params.get(paramName);
	}

	public static LineAttribute[] getAll(DBQueryer queryer, int segID) {
		String sql = String.format(
				"select * from %s where seg_id=%d order by attr_name, distribute_id",
				TABLE_NAME, segID);
		ResultSet rs = queryer.query(sql);
		if (rs == null)
			return null;
		Collection<LineAttribute> list = new ArrayList<LineAttribute>();
		try {
			String lastAttrName = null;
			LineAttribute lastLineAttr = null;
			while (rs.next()) {
				String attrName = rs.getString("attr_name");
				int distributeID = rs.getInt("distribute_id");
				String paramName = rs.getString("param_name");
				double paramValue = rs.getDouble("param_value");
				if (lastAttrName == null || !lastAttrName.equals(attrName)) {
					lastLineAttr = new LineAttribute(-1, segID, attrName, distributeID);
					list.add(lastLineAttr);
				}
				lastLineAttr.addParam(paramName, paramValue);
			}
		} catch (SQLException ex) {
			Logger.getInstance().warn("Get All Pipelines from DB failed");
		} finally {
			try {
				rs.getStatement().close();
			} catch (SQLException ex) {

			}
		}
		return list.toArray(new LineAttribute[0]);
	}

	/**
	 * add this object into storage
	 * 
	 * @param queryer
	 */
	public void add(DBQueryer queryer) {
		for(String paramName: params.keySet()){
			double paramValue = params.get(paramName);
			String sql = String.format(
					"insert into %s values(%d, '%s', %d, '%s', %f)\n", TABLE_NAME,
					segID, name, distributeID, paramName, paramValue);
			queryer.executeUpdate(sql);
		}
		
	}

	/**
	 * remove this object from the storage
	 * 
	 * @param queryer
	 */
	public void remove(DBQueryer queryer) {
		String sql = String
				.format("delete from %s where seg_id=%d and attr_name='%s'", TABLE_NAME, segID, name);
		queryer.executeUpdate(sql);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static void removeAll(int segID, DBQueryer queryer) {
		String sql = String.format("delete from %s where seg_id=%d",
				TABLE_NAME, segID);
		queryer.executeUpdate(sql);
	}
}
