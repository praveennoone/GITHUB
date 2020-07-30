/**
 * 
 */
package com.gavs.hishear.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.Department;
import com.gavs.hishear.web.domain.WorkCenter;
import com.gavs.hishear.web.reports.DeptWCAssetReport;

/**
 * @author rahjeshd
 * 
 */
public class DeptWCAssetReportDAOImpl implements DeptWCAssetReportDAO {

	/** Data Source to use */
	private DataSource dataSource;

	private DataSource dataSourceFS;

	private static final Logger logger = Logger
			.getLogger(DeptWCAssetReportDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.AssetDAO#getDepartments(com.gavs.hishear.web
	 * .domain.Asset)
	 */
	@Override
	public ArrayList<Department> getDepartments(String query) {
		ArrayList<Department> departments = new ArrayList<Department>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			System.out.println(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Department department = new Department();
				department.setDeptNumber(rs.getString("deptNumber"));
				department.setDeptName(rs.getString("deptName"));
				departments.add(department);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return departments;

	}

	/**
	 * Get the Connection.
	 * 
	 * @return Connection
	 * @throws SQLException
	 */

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * Get the Connection.
	 * 
	 * @return Connection
	 * @throws SQLException
	 */

	public Connection getConnectionFS() throws SQLException {
		return dataSourceFS.getConnection();
	}

	/**
	 * Safely closes connection
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
	 * @return Returns the dataSource.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the dataSourceFS
	 */
	public DataSource getDataSourceFS() {
		return dataSourceFS;
	}

	/**
	 * @param dataSourceFS
	 *            the dataSourceFS to set
	 */
	public void setDataSourceFS(DataSource dataSourceFS) {
		this.dataSourceFS = dataSourceFS;
	}

	@Override
	public ArrayList<Asset> getAssetDetails(DeptWCAssetReport dto) {
		ArrayList<Asset> assetDetails = new ArrayList<Asset>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String query = dto.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getDeptNumber());
			System.out.println(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Asset asset = new Asset();
				asset.setAssetNumber(rs.getString("AssetNo"));
				asset.setAssetDescNumber(rs.getString("Description"));
				asset.setQuantityCaptureRequired(rs.getString("QtyMeasure"));
				assetDetails.add(asset);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return assetDetails;
	}

	/**
	 * 
	 */
	public ArrayList<WorkCenter> getWorkcenters(DeptWCAssetReport dto) {
		return getWorkcenters(dto.getDeptNumber(), dto.getQuery());
	}

	public ArrayList<WorkCenter> getWorkcenters(String departmentNumber,
			String query) {
		ArrayList<WorkCenter> workcenters = new ArrayList<WorkCenter>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, departmentNumber);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				WorkCenter workCenter = new WorkCenter();
				workCenter.setWorkcenterCode(rs.getString("WorkCenter"));
				workCenter.setWorkcenterDesc(rs.getString("ItemDescription"));
				workcenters.add(workCenter);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return workcenters;
	}

	public void insertDepartment(DeptWCAssetReport dto) {
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String query = dto.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getNewDeptNumber());
			int result = pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	public String isValidWorkCentre(String wcCode, String query) {
		String wcDesc = null;

		Connection connection = null;
		ResultSet rs = null;
		boolean valiadWC = false;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, wcCode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				wcDesc = rs.getString("ItemDescription");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return wcDesc;
	}

	public int updateQtyCaptureRequiredFlag(String deptNumber, String asset,
			String qtyCaptureRequired, String query) {
		System.out.println(qtyCaptureRequired + "-" + asset + "-" + deptNumber);
		int count = 0;
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, qtyCaptureRequired);
			pstmt.setString(2, asset);
			pstmt.setString(3, deptNumber);
			count = pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return count;
	}
}
