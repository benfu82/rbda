package org.rbda.biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.rbda.dao.DBQueryer;
import org.rbda.logging.Logger;

public class Section {
	public static final String TABLE_NAME = "section";
	public static final String SQL_SEGMENT = "create table "
			+ Section.TABLE_NAME + "("
			+ "id integer primary key autoincrement,"
			+ "pipe_id int, name varchar(256), start double, end double );";
	
	private int id;
	private int pipeID;
	private String name;
	private float start;
	private float end;
	private Collection<LineAttribute> attrs;
	private boolean isNew = true;

	public Section(String name, float start, float end, int pipeID) {
		this(-1, pipeID, name, start, end, true);
	}

	private Section(int id, int pipeID, String name, float start, float end,
			boolean isNew) {
		this.id = id;
		this.pipeID = pipeID;
		this.name = name;
		this.start = start;
		this.end = end;
		this.isNew = isNew;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getStart() {
		return this.start;
	}

	public float getEnd() {
		return this.end;
	}

	public void addAttribute(LineAttribute attr) {
		if (attr == null)
			return;
		if (attrs == null) {
			attrs = new ArrayList<LineAttribute>();
		}
		attrs.add(attr);
	}

	public Collection<LineAttribute> getAttributes() {
		return attrs;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public void setEnd(float end) {
		this.end = end;
	}
	
	public void setPipeID(int pipeID){
		this.pipeID = pipeID;
	}

	public static Section[] getAll(DBQueryer queryer, int pipeID) {
		String sql = String.format("select * from %s where pipe_id=%d",
				TABLE_NAME, pipeID);
		ResultSet rs = queryer.query(sql);
		if (rs == null)
			return null;
		Collection<Section> list = new ArrayList<Section>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float start = rs.getFloat("start");
				float end = rs.getFloat("end");
				list.add(new Section(id, pipeID, name, start, end, false));
			}
		} catch (SQLException ex) {
			Logger.getInstance().warn("Get All Pipelines from DB failed");
		} finally {
			try {
				rs.getStatement().close();
			} catch (SQLException ex) {

			}
		}
		return list.toArray(new Section[0]);
	}

	/**
	 * add this object into storage
	 * 
	 * @param queryer
	 */
	public void add(DBQueryer queryer) {
		String sql = String.format(
				"insert into %s values(%s, %d, '%s', %f, %f)\n", TABLE_NAME,
				"NULL", pipeID, name, start, end);
		queryer.executeUpdate(sql);
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

	public Section clone() {
		return new Section(this.id, this.pipeID, this.name, this.start,
				this.end, this.isNew);
	}

	public static void removeAll(int pipeID, DBQueryer queryer) {
		String sql = String.format("delete from %s where pipe_id=%d",
				TABLE_NAME, pipeID);
		queryer.executeUpdate(sql);
	}
}
