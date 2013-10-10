package org.rbda.biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.rbda.logging.Logger;

import org.rbda.dao.DBQueryer;

/**
 * Scenario Entity
 * 
 * @author
 * 
 */
public class Scenario {
	public static final String TABLE_NAME = "scenario";
	public static final String SQL_SCENARIO = "create table "
			+ Scenario.TABLE_NAME + " ("
			+ "id integer primary key autoincrement, "
			+ "name varchar(256), comment varchar(256) );";

	private int id;
	private String name;
	private String comment;
	private boolean isNew = true;
	private ArrayList<Pipeline> pipelines;

	public Scenario(String name) {
		this(-1, name, "", true);
	}

	private Scenario(int id, String name, String comment, boolean isNew) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.pipelines = new ArrayList<Pipeline>();
		this.isNew = isNew;
	}

	public int getID() {
		return this.id;
	}

	/**
	 * Get name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set name
	 * 
	 * @param name
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public void addPipeline(Pipeline pipeline) {
		this.pipelines.add(pipeline);
	}

	public int getPipelineCount() {
		return this.pipelines.size();
	}

	public Pipeline getPipelineAt(int index) {
		return this.pipelines.get(index);
	}

	public void removePipelineAt(int index) {
		this.pipelines.remove(index);
	}

	public void removePipeline(Pipeline pipeline) {
		this.pipelines.remove(pipeline);
	}

	public Pipeline[] getPipelines() {
		return this.pipelines.toArray(new Pipeline[0]);
	}

	public static Scenario[] getAll(DBQueryer queryer) {
		String sql = String.format("select * from %s", TABLE_NAME);
		ResultSet rs = queryer.query(sql);
		if (rs == null)
			return null;
		Collection<Scenario> list = new ArrayList<Scenario>();
		try {
			Scenario item = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String comment = rs.getString("comment");
				item = new Scenario(id, name, comment, false);
				list.add(item);
			}

			for (Scenario scenario : list) {
				Pipeline[] pipelines = Pipeline.getAll(queryer,
						scenario.getID(), TABLE_NAME);
				if (pipelines == null)
					continue;
				for (Pipeline pipeline : pipelines) {
					scenario.addPipeline(pipeline);
				}
			}
		} catch (SQLException ex) {
			Logger.getInstance().warn("Get All Scenarios from DB failed");
		} finally {
			try {
				rs.close();
			} catch (SQLException ex) {

			}
		}
		return list.toArray(new Scenario[0]);
	}

	/**
	 * add this object into storage
	 * 
	 * @param queryer
	 */
	public void add(DBQueryer queryer) {
		String sql = String.format("insert into %s values(%s, '%s', '%s')\n",
				TABLE_NAME, "NULL", name, comment);
		queryer.executeUpdate(sql);
		this.id = queryer.getLastInsertRowID();
		if (this.pipelines != null) {
			for (Pipeline pipeline : this.pipelines) {
				pipeline.setOwnerID(this.id);
				pipeline.setOwnerType(TABLE_NAME);
				pipeline.add(queryer);
			}
		}
		this.isNew = false;
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

		if (this.pipelines != null && this.pipelines.size() != 0) {
			Pipeline.removeAll(id, queryer);
			this.pipelines.clear();
			this.pipelines = null;
		}
	}

	public void update(DBQueryer queryer) {
		Pipeline.removeAll(id, queryer);
		if (this.pipelines != null && this.pipelines.size() != 0) {
			for (Pipeline pipeline : this.pipelines) {
				pipeline.add(queryer);
			}
		}
	}

	public void save(DBQueryer queryer) {
		if (isNew) {
			add(queryer);
		} else {
			update(queryer);
		}
	}
	
	public Scenario clone(){
		Scenario cloned = new Scenario(this.id, this.name, this.comment, this.isNew);
		for(Pipeline pipeline: this.pipelines){
			cloned.addPipeline(pipeline.clone());
		}
		return cloned;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
