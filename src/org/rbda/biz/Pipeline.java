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
 * Pipeline Entity
 * 
 * @author fujunwei
 * 
 */
public class Pipeline {
	public static final String TABLE_NAME = "pipeline";
	public static final String SQL_PIPELINE = "create table "
			+ Pipeline.TABLE_NAME
			+ " ("
			+ "id integer primary key autoincrement, "
			+ "name varchar(256), start float, end float, owner_id int, owner_type varchar(128);";
	private int id;
	private String name;
	private float start;
	private float end;
	private int ownerID = -1;
	private String ownerType;
	private HashMap<String, int[]> attrFilters;
	private List<Section> segments;
	private boolean isNew = true; // 标示是新创建的还是从已存在的库中加载生成的

	public Pipeline(String name, float start, float end) {
		this(-1, name, start, end, true);
	}

	private Pipeline(int id, String name, float start, float end, boolean isNew) {
		this(id, name, start, end, -1, null, true);
	}

	private Pipeline(int id, String name, float start, float end, int ownerID,
			String ownerType, boolean isNew) {
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
		this.isNew = isNew;
		this.ownerID = ownerID;
		this.ownerType = ownerType;
		this.segments = new ArrayList<Section>();
	}

	public void addAttributeFilter(String filterName, int[] attrDefIDs) {
		if (attrFilters == null)
			attrFilters = new HashMap<String, int[]>();

		attrFilters.put(filterName, attrDefIDs);
	}

	public void removeAttributeFilter(String filterName) {
		if (attrFilters == null)
			return;

		attrFilters.remove(filterName);
	}

	public void addSegment(Section segment) {
		if (segment == null)
			return;

		segments.add(segment);
	}

	public void setSegments(List<Section> segments) {
		this.segments = segments;
	}

	public int getID() {
		return this.id;
	}

	/**
	 * Get pipeline name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set pipeline name
	 * 
	 * @param name
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get pipeline length
	 * 
	 * @return
	 */
	public float getLength() {
		return end - start;
	}

	/**
	 * Get pipeline start position
	 * 
	 * @return
	 */
	public float getStart() {
		return this.start;
	}

	/**
	 * Set pipeline start position
	 * 
	 * @param start
	 */
	public void setStart(float start) {
		this.start = start;
	}

	/**
	 * Get pipeline end position
	 * 
	 * @return
	 */
	public float getEnd() {
		return this.end;
	}

	/**
	 * Set pipeline end position
	 * 
	 * @param end
	 */
	public void setEnd(float end) {
		this.end = end;
	}

	public void setOwnerID(int id) {
		this.ownerID = id;
	}

	public int getOwnerID() {
		return this.ownerID;
	}

	public void setOwnerType(String type) {
		this.ownerType = type;
	}

	public String getOwnerType() {
		return this.ownerType;
	}

	/**
	 * Get the line attribute filter name list
	 * 
	 * @return
	 */
	public String[] getAttributeFilterNames() {
		return (attrFilters != null) ? attrFilters.keySet().toArray(
				new String[0]) : null;
	}

	/**
	 * Get all the line Attribute identifiers related to the filter name
	 * 
	 * @param filterName
	 *            filter name
	 * @return
	 */
	public int[] getAttributeIDs(String filterName) {
		if (filterName == null || filterName.isEmpty() || attrFilters == null)
			return null;
		return attrFilters.get(filterName);
	}

	public int getSegmentCount() {
		return segments == null ? 0 : segments.size();
	}

	/**
	 * Get Segment indexed at @index
	 * 
	 * @param index
	 *            segment index, which is started from 0
	 * @return
	 */
	public Section getSegmentAt(int index) {
		if (index < 0 || segments == null || index >= segments.size()) {
			return null;
		}

		return segments.get(index);
	}

	/**
	 * Get Segment whose line attribute is equal to @attrDef
	 * 
	 * @param attrDef
	 *            line attribute defined object
	 * @return
	 */
	public Section[] getSegmentByAttr(LineAttributeDef attrDef) {
		if (segments == null || attrDef == null) {
			return null;
		}

		ArrayList<Section> matchedSegments = new ArrayList<Section>();
		for (Section segment : segments) {
			Collection<LineAttribute> attributes = segment.getAttributes();
			if (attributes == null)
				continue;
			for (LineAttribute attribute : attributes) {
				if (attribute.getID() == attrDef.getId()) {
					matchedSegments.add(segment);
					break;
				}
			}
		}

		return matchedSegments.toArray(new Section[0]);
	}

	public static Pipeline[] getAll(DBQueryer queryer) {
		String sql = String.format("select * from %s", TABLE_NAME);
		ResultSet rs = queryer.query(sql);
		if (rs == null)
			return null;
		Collection<Pipeline> list = new ArrayList<Pipeline>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float start = rs.getFloat("start");
				float end = rs.getFloat("end");
				list.add(new Pipeline(id, name, start, end, false));
			}

			for (Pipeline pipeline : list) {
				Section[] segments = Section.getAll(queryer, pipeline.getID());
				if (segments == null)
					continue;
				for (Section segment : segments) {
					pipeline.addSegment(segment);
				}
			}
		} catch (SQLException ex) {
			Logger.getInstance().warn("Get All Pipelines from DB failed");
		} finally {
			try {
				rs.close();
			} catch (SQLException ex) {

			}
		}
		return list.toArray(new Pipeline[0]);
	}

	public static Pipeline[] getAll(DBQueryer queryer, int ownerID,
			String ownerType) {
		String sql = String.format(
				"select * from %s where ownerID=%d and ownerType='%s'",
				TABLE_NAME, ownerID, ownerType);
		ResultSet rs = queryer.query(sql);
		if (rs == null)
			return null;
		Collection<Pipeline> list = new ArrayList<Pipeline>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float start = rs.getFloat("start");
				float end = rs.getFloat("end");
				list.add(new Pipeline(id, name, start, end, ownerID, ownerType,
						false));
			}
		} catch (SQLException ex) {
			Logger.getInstance().warn("Get All Pipelines from DB failed");
		} finally {
			try {
				rs.getStatement().close();
			} catch (SQLException ex) {

			}
		}
		return list.toArray(new Pipeline[0]);
	}

	/**
	 * add this object into storage
	 * 
	 * @param queryer
	 */
	public void add(DBQueryer queryer) {
		String sql = String.format("insert into %s values(%s, '%s', %f, %f)\n",
				TABLE_NAME, "NULL", name, start, end);
		queryer.executeUpdate(sql);
		this.id = queryer.getLastInsertRowID();
		if (this.segments != null) {
			for (Section segment : this.segments) {
				segment.setPipeID(id);
				segment.add(queryer);
			}
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

		if (this.segments != null && this.segments.size() != 0) {
			Section.removeAll(id, queryer);
			this.segments.clear();
			this.segments = null;
		}
	}

	public void update(DBQueryer queryer) {
		Section.removeAll(id, queryer);
		if (this.segments != null && this.segments.size() != 0) {
			for (Section segment : this.segments) {
				segment.add(queryer);
			}
		}
	}

	public void save(DBQueryer queryer) {
		if (isNew) {
			add(queryer);
			isNew = false;
		} else {
			update(queryer);
		}
	}

	public static void removeAll(int scenarioID, DBQueryer queryer) {
		String sql = String.format("delete from %s where scenario_id=%d",
				TABLE_NAME, scenarioID);
		queryer.executeUpdate(sql);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Pipeline clone() {
		Pipeline cloned = new Pipeline(this.id, this.name, this.start,
				this.end, this.ownerID, this.ownerType, this.isNew);
		for(Section segment: segments){
			cloned.addSegment(segment.clone());
		}
		
		return cloned;
	}

}
