// File:         LoginDAOImpl.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gavs.hishear.web.domain.ApplicationPropertyBean;

/**
 * The Class LoginDAOImpl.
 * 
 */
public class LoginDAOImpl implements LoginDAO {

	/** The data source. */
	private DataSource dataSource;

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/** The DataSource */
	private DataSource ds1;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	private static final Logger logger = Logger.getLogger(LoginDAOImpl.class);

	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.LoginDAO#getQueries(java.lang.String)
	 */
	public HashMap getQueries(String code) {
		// TODO Auto-generated method stub
		HashMap queriesMap = new HashMap();
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection != null) {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT SQL_Code, SQL_Query, SQL_Type FROM dbo.M3Apps_Query");
				String queryCode = null;
				String query = null;
				while (resultSet.next()) {
					queryCode = resultSet.getString("SQL_Code");
					query = resultSet.getString("SQL_Query");
					queriesMap.put(queryCode, query);
				}
				resultSet.close();
				resultSet = null;

			} else {
				System.out.println("Error: No active Connection");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return queriesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.LoginDAO#getReportQueries(java.lang.String)
	 */
	public HashMap getReportQueries(String code) {
		// TODO Auto-generated method stub
		HashMap queriesMap = new HashMap();
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection != null) {
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT SQL_Code, SQL_Query, SQL_Type FROM dbo.M3Apps_Query WHERE SQL_Code LIKE '%"
								+ code + "%'");
				String queryCode = null;
				String query = null;
				while (resultSet.next()) {
					queryCode = resultSet.getString("SQL_Code");
					query = resultSet.getString("SQL_Query");
					queriesMap.put(queryCode, query);
				}
				resultSet.close();
				resultSet = null;

			} else {
				System.out.println("Error: No active Connection");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return queriesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.LoginDAO#getDivisions(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getDivisions(String query) {
		System.out.println("--------------------query--->" + query);
		ArrayList<String> divisions = new ArrayList<String>();
		divisions = (ArrayList<String>) this.jdbcTemplate.query(query,
				new Object[] {}, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("Company");

					}

				});
		System.out.println("--------------------divisions--->" + divisions);
		return divisions;
	}

	/**
	 * Gets the data source.
	 * 
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Sets the data source.
	 * 
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Safely closes connection.
	 * 
	 * @param connection
	 *            Connection to close. Can be null.
	 */
	public void closeConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * Get the Connection.
	 * 
	 * @return Connection
	 * @throws SQLException
	 *             the sQL exception
	 */

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

	public ArrayList<String> getDistinctADGroups() throws Exception {
		ArrayList<String> DistinctADGroups = new ArrayList<String>();
		Connection connection = null;
		String activeDirectoryGroup = null;
		try {
			connection = getDefaultConnection();
			if (connection != null) {
				Statement statement = connection.createStatement();

				String query = "select distinct ActiveDirectoryGroup from Application_ActiveDirectoryGroup where ApplicationName = 'Shop Floor Admin'";

				ResultSet resultSet = statement.executeQuery(query);

				while (resultSet.next()) {
					activeDirectoryGroup = resultSet
							.getString("ActiveDirectoryGroup");
					DistinctADGroups.add(activeDirectoryGroup);
				}
				resultSet.close();
				resultSet = null;

			} else {
				System.out.println("Error: No active Connection");
			}

		} catch (Exception e) {
			System.out.println("Exception in fetching app properties");
			e.printStackTrace();

		} finally {
			closeConnection(connection);
		}
		return DistinctADGroups;
	}

	public ApplicationPropertyBean getActiveDirectoryGroup(
			ApplicationPropertyBean applPropMgr) {

		HashMap<String, ArrayList<String>> activeDirectoryMap = new HashMap<String, ArrayList<String>>();
		Connection connection = null;
		String code = null;
		String value = null;

		try {
			// Get the Data source Connection
			connection = getDefaultConnection();
			if (connection != null) {

				// Fetch user roles having access to URL
				Statement statement = connection.createStatement();
				String runQuery = "SELECT distinct URL, ActiveDirectoryGroup FROM Application_ActiveDirectoryGroup where ApplicationName = 'Shop Floor Admin' order by URL";
				ResultSet resultSet = statement.executeQuery(runQuery);
				String prevCode = "";
				int iCount = 0;
				ArrayList<String> adgList = new ArrayList<String>();
				while (resultSet.next()) {
					code = resultSet.getString("URL");
					value = resultSet.getString("ActiveDirectoryGroup");

					if (iCount == 0) {
						iCount++;
						adgList.add(value);
						prevCode = code;

					} else {
						if (!prevCode.equalsIgnoreCase(code)) {
							prevCode = code;
							adgList = new ArrayList<String>();
							adgList.add(value);
						} else {
							adgList.add(value);
						}
						activeDirectoryMap.put(prevCode, adgList);
					}

				}
				applPropMgr.setActiveDirectoryMap(activeDirectoryMap);
				resultSet.close();
				resultSet = null;

				// Fetch user roles having access to menus
				String name = null;
				String adg = null;
				HashMap<String, ArrayList<String>> activeDirectoryMenuMap = new HashMap<String, ArrayList<String>>();
				String query = "SELECT distinct ActiveDirectoryGroup, Menu FROM Application_ActiveDirectoryGroup where ApplicationName = 'Shop Floor Admin' and Menu != 'NULL' order by Menu";
				ResultSet result = statement.executeQuery(query);
				String prevName = "";
				int count = 0;
				ArrayList<String> adgroupList = new ArrayList<String>();

				while (result.next()) {
					name = result.getString("Menu");
					adg = result.getString("ActiveDirectoryGroup");

					if (count == 0) {
						count++;
						adgroupList.add(adg);
						prevName = name;

					} else {
						if (!prevName.equalsIgnoreCase(name)) {
							prevName = name;
							adgroupList = new ArrayList<String>();
							adgroupList.add(adg);
						} else {
							adgroupList.add(adg);
						}
						activeDirectoryMenuMap.put(prevName, adgroupList);
					}

				}
				applPropMgr.setActiveMenuDirectoryMap(activeDirectoryMenuMap);
				result.close();
				result = null;
			} else {
				System.out.println("Error: No active Connection");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return applPropMgr;
	}

	/**
	 * @return the ds1
	 */
	public DataSource getDs1() {
		return ds1;
	}

	/**
	 * @param ds1
	 *            the ds1 to set
	 */
	public void setDs1(DataSource ds1) {
		this.ds1 = ds1;
	}

	/**
	 * Get the Connection.
	 * 
	 * @return Connection
	 * @throws SQLException
	 */

	public Connection getDefaultConnection() throws SQLException {
		return ds1.getConnection();
	}

	/**
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

}