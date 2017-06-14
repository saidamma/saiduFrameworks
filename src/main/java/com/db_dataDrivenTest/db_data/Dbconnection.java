package com.db_dataDrivenTest.db_data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * This class contains all DB connection methods
 * 
 * @author Saidamma
 * 
 */
public class Dbconnection {
	/*
	 * DB Details 
	 * jdbc.url=
	 */

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "******************";
	private static final String DB_USER = "************";
	private static final String DB_PASSWORD = "******************";

	public static Map<String, List<Object>> getDataFromDb(String sql,
			String[] fieldName) throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;
		ResultSet rs = null;

		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		String selectTableSQL = sql;

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			for (int i = 0; i < fieldName.length; i++) {

				List<Object> l = new ArrayList<Object>();

				// execute select SQL stetement
				rs = statement.executeQuery(selectTableSQL);
				while (rs.next()) {

					l.add(rs.getString(fieldName[i]));
				}

				resultMap.put(fieldName[i], l);
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return resultMap;

	}

	static ArrayList<Map<String, Object>> getRows(String query)
			throws SQLException {

		Connection dbConnection = null;
		dbConnection = getDBConnection();

		Statement stmt = dbConnection.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rset.getMetaData();
		int columncount = rsmd.getColumnCount();
		ArrayList<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		while (rset.next()) {
			Map<String, Object> row = new HashMap<String, Object>();
			for (int i = 1; i <= columncount; i++) {
				row.put(rsmd.getColumnName(i), rset.getObject(i));
			}
			queryResult.add(row);
		}
		return queryResult;
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
}
