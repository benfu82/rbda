package org.rbda.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.rbda.logging.Logger;

/**
 * 数据库查询接口
 * 
 * @author fujunwei
 * 
 */
public class DBQueryer {
	private Logger logger = Logger.getInstance();
	private Connection conn = null;
	private String dbName;

	public DBQueryer(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Connect to DB
	 * 
	 * @throws DBException
	 */
	public void connect() throws DBException {
		connect(true);
	}

	/**
	 * Connect to DB
	 * 
	 * @param autoCommit
	 *            set db auto-commit mode
	 * @throws DBException
	 */
	public void connect(boolean autoCommit) throws DBException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"
					+ dbName);
			conn.setAutoCommit(autoCommit);
			this.conn = conn;
		} catch (ClassNotFoundException ex) {
			String errMsg = "JDBC Driver Class is not Found";
			logger.error(errMsg);
			throw new DBException(errMsg, ex);
		} catch (SQLException ex) {
			String errMsg = String.format("Connect to DB %s failure",
					"sqlite:pipeline.db");
			logger.error(errMsg);
			throw new DBException(errMsg, ex);
		}
	}

	/**
	 * close DB connection
	 */
	public void close() {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException ex) {
			logger.warn(String.format("close db happened exception %s",
					ex.getMessage()));
		}
	}

	/**
	 * do query
	 * 
	 * @param sql
	 *            querying sql
	 */
	public ResultSet query(String sql) {
		if (conn == null)
			return null;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
		return rs;
	}

	/**
	 * execute update sql, includes insert, remove, update operation.
	 * 
	 * @param sql
	 * @return if execute sql successfully, return true, or else return false.
	 */
	public boolean executeUpdate(String sql) {
		if (conn == null)
			return false;

		Statement stmt = null;
		boolean isSuccess = true;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			isSuccess = false;
			logger.error(String.format("execute sql failure: %s", sql));
			logger.error(ex.getMessage());
		}
		return isSuccess;
	}

	/**
	 * commit the transaction
	 */
	public void commit() throws DBException {
		try {
			conn.commit();
		} catch (SQLException ex) {
			String errMsg = "commit DB transaction failure";
			logger.error(errMsg);
			throw new DBException(errMsg, ex);
		}
	}

	/**
	 * callback the transaction
	 */
	public void callback() throws DBException {
		try {
			conn.commit();
		} catch (SQLException ex) {
			String errMsg = "commit DB transaction failure";
			logger.error(errMsg);
			throw new DBException(errMsg, ex);
		}
	}

	public int getLastInsertRowID() {
		if (conn == null)
			return -1;
		String sql = "select last_insert_rowid()";
		Statement stmt = null;
		ResultSet rs = null;
		int lastInsertRowID = -1;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				lastInsertRowID = rs.getInt("last_insert_rowid()");
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {

			}
		}
		return lastInsertRowID;
	}

}
