/**
 * WorkCenterDAOImpl.java
 */
package com.gavs.hishear.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.AssetConfig;
import com.gavs.hishear.web.domain.ConfigDivision;
import com.gavs.hishear.web.domain.ReasonCode;
import com.gavs.hishear.web.domain.ReasonType;
import com.gavs.hishear.web.domain.WorkCenterConfig;

/**
 * @author ambrishv
 * 
 */
// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011
public class WorkCenterDAOImpl implements WorkCenterDAO {

	/** Data Source to use. */
	private DataSource dataSource;

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	private static final Logger logger = Logger
			.getLogger(WorkCenterDAOImpl.class);

	/**
	 * Method to get the Work Center Configuration Details
	 * 
	 * @param workCenter
	 * @param query
	 * @return
	 */
	public ArrayList<WorkCenterConfig> getWorkCenterConfigDetails(
			WorkCenterConfig workCenter, String query) {

		ArrayList<WorkCenterConfig> workCenterConfig = new ArrayList<WorkCenterConfig>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);

			String facility = "";
			if (workCenter.getFacility() != null) {
				facility = workCenter.getFacility();
			}
			pstmt.setString(1, facility);

			String workCenterCode = "";
			if (workCenter.getWorkCenterCode() != null) {
				workCenterCode = workCenter.getWorkCenterCode();
			}

			pstmt.setString(2, workCenterCode);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				WorkCenterConfig work = new WorkCenterConfig();
				work.setFacility(rs.getString("Facility"));
				work.setWorkCenterCode(rs.getString("WorkCenter"));
				work.setDescription(rs.getString("WorkCenterName"));
				work.setPphThresholdPercentage(rs.getInt("PPHThreshold"));
				work.setScaleRequired(rs.getString("ScaleRequired"));
				work.setActiveFlag(rs.getString("Active"));
				/*below setScrapCount is added by saikiran tk.no=414799*/
				work.setScrapCount(rs.getString("ScrapCount"));
				
				workCenterConfig.add(work);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return workCenterConfig;
	}

	/**
	 * Method to Update the Work Center Configuration Details
	 * 
	 * @param workCenter
	 * @param query
	 * @return
	 */
	public void updateWorkCenterConfigDetails(WorkCenterConfig workCenter,
			String query) {
		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workCenter.getupdatedPPHThresholdPercentage());
			pstmt.setString(2, workCenter.getUpdatedScaleRequired());
			pstmt.setString(3, workCenter.getUpdatedActiveFlag());
			/*pstmt.setString(4, workCenter.getUpdatedDescription());
			pstmt.setString(5, workCenter.getFacility());
			pstmt.setString(6, workCenter.getWorkCenterCode());*/
			
			
			/*below setString add by saikiran tk.no=414799*/
			pstmt.setString(4, workCenter.getScrapCount());
			pstmt.setString(5, workCenter.getUpdatedDescription());
			pstmt.setString(6, workCenter.getFacility());
			pstmt.setString(7, workCenter.getWorkCenterCode());
			/*end tk.no=414799*/
			
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	

	/**
	 * Method to update the Asset Configuration Details
	 * 
	 * @param asset
	 * @param query
	 * @return
	 */
	public void updateAssetConfigDetails(AssetConfig asset, String query) {

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, asset.getUpdatedDescription());
			pstmt.setString(2, asset.getUpdatedStatus());
			pstmt.setString(3, asset.getFacility());
			pstmt.setString(4, asset.getWorkCenterCode());
			pstmt.setString(5, asset.getAssetNumber());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Method to Insert the Work Center Configuration Details
	 * 
	 * @param workCenter
	 * @param query
	 * @return
	 */
	public void insertNewWorkCenterDetails(WorkCenterConfig workCenter,
			String query) {

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, workCenter.getFacility());
			pstmt.setString(2, workCenter.getWorkCenterCode());
			pstmt.setString(3, workCenter.getDescription());
			pstmt.setInt(4, workCenter.getupdatedPPHThresholdPercentage());
			pstmt.setString(5, workCenter.getUpdatedScaleRequired());
			pstmt.setString(6, workCenter.getUpdatedActiveFlag());
			/*this setter is add by the saikiran tk.no=414799*/
			pstmt.setString(7, workCenter.getScrapCount());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/**
	 * Method to Insert the Asset Configuration Details
	 * 
	 * @param asset
	 * @param query
	 * @return
	 */
	public void insertNewAssetDetails(AssetConfig asset, String query) {

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, asset.getFacility());
			pstmt.setString(2, asset.getWorkCenterCode());
			pstmt.setString(3, asset.getUpdatedAssetNumber());
			pstmt.setString(4, asset.getUpdatedDescription());
			pstmt.setString(5, asset.getUpdatedStatus());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/**
	 * Method to insert the Work Center Configuration Log Details
	 * 
	 * @param workCenter
	 * @param userContext
	 * @param query
	 * @return
	 */
	public void insertWorkCenterLogDetails(WorkCenterConfig workCenter,
			UserContext userContext, String query) {

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userContext.getUserName());
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setString(3, workCenter.getFacility());
			pstmt.setString(4, workCenter.getWorkCenterCode());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/**
	 * Method to insert the Asset Configuration Log Details
	 * 
	 * @param asset
	 * @param userContext
	 * @param query
	 * @return
	 */
	public void insertAssetLogDetails(AssetConfig asset,
			UserContext userContext, String query) {

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userContext.getUserName());
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setString(3, asset.getFacility());
			pstmt.setString(4, asset.getWorkCenterCode());
			pstmt.setString(5, asset.getAssetNumber());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/**
	 * Method to insert the Variance Log Details
	 * 
	 * @param user
	 * @param variance
	 * @param operation
	 * @param type
	 * @param division
	 * @param query
	 * @return
	 */
	public void insertVarianceLogDetails(UserContext user, String variance,
			String operation, String type, String division, String query) {

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, user.getUserName());
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setString(3, operation);
			pstmt.setString(4, type);
			pstmt.setString(5, division);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/**
	 * Method to get the Asset Configuration Details
	 * 
	 * @param asset
	 * @param query
	 * @return
	 */
	public ArrayList<AssetConfig> getAssetConfigDetails(AssetConfig asset,
			String query) {

		ArrayList<AssetConfig> assetConfig = new ArrayList<AssetConfig>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);

			String workCenterCode = "";
			if (asset.getWorkCenterCode() != null) {
				workCenterCode = asset.getWorkCenterCode();
			}

			pstmt.setString(1, workCenterCode);

			String facility = "";
			if (asset.getFacility() != null) {
				facility = asset.getFacility();
			}
			pstmt.setString(2, facility);

			String assetNumber = "";
			if (asset.getAssetNumber() != null) {
				assetNumber = asset.getAssetNumber();
			}

			pstmt.setString(3, assetNumber);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssetConfig assetConfigDetail = new AssetConfig();
				assetConfigDetail.setFacility(rs.getString("Facility"));
				assetConfigDetail.setWorkCenterCode(rs.getString("WorkCenter"));
				assetConfigDetail.setAssetNumber(rs.getString("AssetNumber"));
				assetConfigDetail.setDescription(rs.getString("Description"));
				assetConfigDetail.setStatus(rs.getString("AssetStatus"));
				assetConfig.add(assetConfigDetail);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return assetConfig;
	}

	/**
	 * Method to get the Variance Details
	 * 
	 * @param configDivision
	 * @param query
	 * @return
	 */
	public ConfigDivision getVarianceDetails(ConfigDivision configDivsion,
			String query) {

		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, configDivsion.getDivision());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String operation = rs.getString("Operation");
				String type = rs.getString("Type");
				if (("First").equalsIgnoreCase(operation)) {
					if (("Positive").equalsIgnoreCase(type)) {
						configDivsion.setPositiveFirstOp(rs
								.getString("Percentage"));
						configDivsion.setOldPositiveFirstOp(rs
								.getString("Percentage"));
					} else {
						configDivsion.setNegativeFirstOp(rs
								.getString("Percentage"));
						configDivsion.setOldNegativeFirstOp(rs
								.getString("Percentage"));
					}
				} else if (("Last").equalsIgnoreCase(operation)) {
					if (("Positive").equalsIgnoreCase(type)) {
						configDivsion.setPositiveLastOp(rs
								.getString("Percentage"));
						configDivsion.setOldPositiveLastOp(rs
								.getString("Percentage"));
					} else {
						configDivsion.setNegativeLastOp(rs
								.getString("Percentage"));
						configDivsion.setOldNegativeLastOp(rs
								.getString("Percentage"));
					}
				} else if (("Other").equalsIgnoreCase(operation)) {
					if (("Positive").equalsIgnoreCase(type)) {
						configDivsion.setPositiveOtherOp(rs
								.getString("Percentage"));
						configDivsion.setOldPositiveOtherOp(rs
								.getString("Percentage"));
					} else {
						configDivsion.setNegativeOtherOp(rs
								.getString("Percentage"));
						configDivsion.setOldNegativeOtherOp(rs
								.getString("Percentage"));
					}
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return configDivsion;
	}

	/**
	 * Method to update the Variance Details
	 * 
	 * @param variance
	 * @param operation
	 * @param type
	 * @param division
	 * @param query
	 * @return
	 */
	public void updateVarianceDetails(String variance, String operation,
			String type, String division, String query) {

		Connection connection = null;

		try {

			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, variance);
			pstmt.setString(2, division);
			pstmt.setString(3, operation);
			pstmt.setString(4, type);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/**
	 * Method to get the Reason Master Details
	 * 
	 * @param dto
	 * @param query
	 * @return
	 */
	public ArrayList<ReasonType> getReasonDetails(ReasonCode dto, String query) {

		ArrayList<ReasonType> reason = new ArrayList<ReasonType>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReasonType reasonType = new ReasonType();
				reasonType.setReasonType(rs.getString("ReasonType"));
				reasonType.setReasonDesc(rs.getString("ReasonDesc"));
				reason.add(reasonType);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return reason;
	}

	/**
	 * Method to get the Reason Master Details
	 * 
	 * @param dto
	 * @param query
	 * @return
	 */
	public ArrayList<ReasonType> getReasonFilterDetails(ReasonCode dto,
			String query) {

		ArrayList<ReasonType> reason = new ArrayList<ReasonType>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getSelectedReasonType());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReasonType reasonType = new ReasonType();
				reasonType.setReasonType(rs.getString("ReasonType"));
				reasonType.setReasonDesc(rs.getString("ReasonDesc"));
				reason.add(reasonType);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return reason;
	}

	/**
	 * Method to insert the Reason Master Details
	 * 
	 * @param reasonType
	 * @param reasonDesc
	 * @param query
	 * @return
	 */
	public void insertNewReason(String reasonType, String reasonDesc,
			String query) {

		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, reasonDesc);
			pstmt.setString(2, reasonType);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
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
	 * Gets the data source.
	 * 
	 * @return Returns the dataSource.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Sets the data source.
	 * 
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Gets the application property bean.
	 * 
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * Sets the application property bean.
	 * 
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}
	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011
}
