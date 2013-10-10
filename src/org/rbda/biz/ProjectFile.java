package org.rbda.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.rbda.dao.DBException;
import org.rbda.dao.DBQueryer;
import org.rbda.equation.Equation;
import org.rbda.logging.Logger;
import org.rbda.main.SystemProperties;

public class ProjectFile {
	public static final String FILE_SUFFIX = ".rbda";

	private static final String SQL_FILE = "create.sql";

	private static final String SQL_PIPELINE = Pipeline.SQL_PIPELINE;

	private static final String SQL_SEGMENT = Section.SQL_SEGMENT;

	private static final String SQL_SEGMENT_ATTRIBUTE = LineAttribute.SQL_LINE_ATTRIBUTE;

	private static final String SQL_EQUATION = Equation.SQL_EQUATION;

	private static String[] init_sqls = { SQL_PIPELINE, SQL_SEGMENT,
			SQL_EQUATION, SQL_SEGMENT_ATTRIBUTE };

	private String name;
	private String path;
	private ArrayList<Pipeline> pipelines;
	private DBQueryer dbQueryer;

	public ProjectFile(File file) {
		this.name = file.getName().replace(FILE_SUFFIX, "");
		this.path = file.getAbsolutePath();
		this.pipelines = new ArrayList<Pipeline>();

		try {
			// init db
			DBQueryer dbQueryer = new DBQueryer(this.path);
			dbQueryer.connect();
			this.dbQueryer = dbQueryer;
			SystemProperties
					.setProperty(SystemProperties.DB_QUERYER, dbQueryer);
		} catch (DBException ex) {
			Logger.getInstance().error(ex.getMessage());
		}
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
	 * Get path
	 * 
	 * @return
	 */
	public String getPath() {
		return this.path;
	}

	public void create() {
		for (String sql : init_sqls) {
			dbQueryer.executeUpdate(sql);
		}
	}

	public void open() {
		Pipeline[] pipes = Pipeline.getAll(dbQueryer);
		if (pipes != null) {
			for (Pipeline pipeline : pipes) {
				pipelines.add(pipeline);
			}
		}
	}

	public void save() {
		for (Pipeline pipeline : pipelines) {
			pipeline.save(dbQueryer);
		}
	}

	public Pipeline[] getPipelines() {
		return pipelines.toArray(new Pipeline[0]);
	}

	public void setPipelines(Pipeline[] pipes) {
		pipelines.clear();
		if (pipes != null) {
			for (Pipeline pipeline : pipes) {
				pipelines.add(pipeline);
			}
		}
	}

}
