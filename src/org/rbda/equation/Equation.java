package org.rbda.equation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import org.rbda.dao.DBQueryer;
import org.rbda.logging.Logger;

public class Equation {
	public static final String TABLE_NAME = "equation";
	public static final String SQL_EQUATION = "create table equation ("
			+ "id integer,"
			+ "name varchar(256), param_name varchar(256), param_value float );";
	
	private int id;
	private String name;
	private Hashtable<String, Double> params;
	private ArrayList<String> paramNames;

	public Equation(int id, String name) {
		this.id = id;
		this.name = name;
		this.params = new Hashtable<String, Double>();
		this.paramNames = new ArrayList<String>();
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getParameterCount() {
		return (paramNames == null) ? null : paramNames.size();
	}

	public String getParameterNameAt(int index) {
		return (index < 0 || paramNames == null || index > paramNames.size()) ? null
				: paramNames.get(index);
	}

	public String getParameterDescAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParameterNames(String[] names) {
		this.params.clear();
		this.paramNames.clear();
		if (names == null)
			return;

		for (String name : names) {
			this.params.put(name, Double.MIN_VALUE);
			this.paramNames.add(name);
		}
	}

	public void addParameter(String name, double value) {
		this.paramNames.add(name);
		this.params.put(name, value);
	}

	public static Equation[] getAll(DBQueryer queryer) {
		String sql = String.format("select * from %s order by id", TABLE_NAME);
		ResultSet rs = queryer.query(sql);
		if (rs == null)
			return null;
		Collection<Equation> list = new ArrayList<Equation>();
		try {
			int lastid = Integer.MIN_VALUE;
			Equation lastEquation = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String param_name = rs.getString("param_name");
				float param_value = rs.getFloat("param_value");
				if (lastid != id) {
					Equation equation = new Equation(id, name);
					list.add(equation);
					lastEquation = equation;
					lastid = id;
				}
				if (lastEquation != null)
					lastEquation.addParameter(param_name, param_value);
			}
		} catch (SQLException ex) {
			Logger.getInstance().warn("Get Equations from DB failure");
		} finally {
			try {
				rs.getStatement().close();
			} catch (SQLException ex) {

			}
		}
		return list.toArray(new Equation[0]);
	}

	/**
	 * add this object into storage
	 * 
	 * @param queryer
	 */
	public void add(DBQueryer queryer) {
		if (this.params == null || this.params.size() == 0)
			return;
		Enumeration<String> paramNames = this.params.keys();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			double paramValue = this.params.get(paramName);
			String sql = String.format(
					"insert into %s values(%d, '%s', '%s', %f)\n", TABLE_NAME,
					id, name, paramName, paramValue);
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
				.format("delete from %s where id=%d", TABLE_NAME, id);
		queryer.executeUpdate(sql);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Equation cloneEquation() {
		Equation cloned = new Equation(this.id, this.name);
		String[] paramNames = this.params.keySet().toArray(new String[0]);
		for (int i = 0; i < paramNames.length; i++) {
			cloned.addParameter(paramNames[i], this.params.get(paramNames[i]));
		}
		cloned.setParameterNames(paramNames);

		return cloned;
	}

}
