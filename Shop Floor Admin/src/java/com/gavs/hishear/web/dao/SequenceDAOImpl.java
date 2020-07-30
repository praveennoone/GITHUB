// File:         SequenceDAOImpl.java
// Created:      Feb 23, 2011
// Author:       rahjeshd
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gavs.hishear.EventLog.LogTools;
import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.M3APIParameter;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.m3interface.dto.POTransaction;
import com.gavs.hishear.m3interface.dto.Program;
import com.gavs.hishear.web.constants.ActivityCode;
import com.gavs.hishear.web.constants.PriceTimeQuantity;
import com.gavs.hishear.web.constants.WorkCenterCapacity;
import com.gavs.hishear.web.constants.webConstants;
import com.gavs.hishear.web.domain.Activity;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.domain.FourthShiftData;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.MasterData;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.SequenceActivity;
import com.gavs.hishear.web.domain.User;
import com.gavs.hishear.web.domain.WorkCenter;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.Util;
import com.gavs.lisi.integration.allocation.AllocationEngineService;

/**
 * The Class SequenceDAOImpl.
 * 
 */
public class SequenceDAOImpl implements SequenceDAO {

	private static final String ONE = "1";

	private static final String EMPTY_STRING = "";

	private static final int MINUTES_PER_HOUR = 60;

	private static final String NO = "no";

	private static final String YES = "yes";

	/** Data Source to use. */
	private DataSource dataSource;

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The allocation engine service. */
	private AllocationEngineService allocationEngineService;

	/** The result. */
	private boolean result;

	private static final Logger logger = Logger
			.getLogger(SequenceDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getCompletedSequences(com.gavs.hishear
	 * .web.domain.Sequence)
	 */
	public ArrayList<Sequence> getCompletedSequences(Sequence sequence) {
		ArrayList<Sequence> sequences = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Sequence dto = new Sequence();
				dto.setSequence(rs.getString("SequenceNumber"));
				dto.setSetupStatus(rs.getString("Setup"));
				dto.setRunStatus(rs.getString("Run"));
				dto.setQtyCompleted(rs.getDouble("QtyCompleted"));
				sequences.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getMoLine(com.gavs.hishear.web.domain
	 * .Sequence)
	 */
	public ArrayList<Sequence> getMoLine(Sequence sequence) {
		ArrayList<Sequence> sequences = new ArrayList<Sequence>();

		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			String query = sequence.getQuery();

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getPartNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence dto = new Sequence();
				dto.setMoNumber(rs.getString("MONumber"));
				dto.setLineNumber(rs.getString("LineNumber"));
				sequences.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequences;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#setInputFlag(com.gavs.hishear.web
	 * .domain.Sequence)
	 */
	// @Override
	public void setInputFlag(Sequence sequence) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = null;
			String query = sequence.getQuery();

			// if(sequence.isInputRequired()) {
			// query = "INSERT INTO SFS_InputFlag VALUES(?,?,?)";
			// } else {
			// query = "DELETE FROM SFS_InputFlag WHERE MONumber = ? AND
			// LineNumber = ? AND SequenceNumber = ? ";
			// }

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			pstmt.setString(3, sequence.getSequence());

			System.out.println(EMPTY_STRING + query);
			pstmt.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#checkInputFlag(com.gavs.hishear.
	 * web.domain.Sequence)
	 */
	public void checkInputFlag(Sequence sequence) {
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			pstmt.setString(3, sequence.getSequence());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sequence.setInputRequired(true);
			} else {
				sequence.setInputRequired(false);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getSequenceDetails(com.gavs.hishear
	 * .web.domain.Sequence)
	 */
	public ArrayList getSequenceDetails(Sequence sequence) {
		ArrayList<Sequence> sequenceDetails = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			pstmt.setString(3, sequence.getSequence());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence dto = new Sequence();
				dto.setEmployeeName(rs.getString("Name"));
				dto.setDepartment(rs.getString("DeptName"));
				dto.setAssetNumber(rs.getString("AssetNumber"));
				dto.setAssetDesc(rs.getString("AssetDescription"));
				dto.setActivity(rs.getString("Activity"));
				dto.setQtyCompleted(rs.getDouble("QtyCompleted"));
				dto.setLogonDate(rs.getTimestamp("LogonDate"));
				dto.setLogoffDate(rs.getTimestamp("LogoffDate"));
				dto.setEmployeeID(rs.getString("EmployeeNumber"));
				try {
					dto.setJobActivityNumber(rs.getString("JobActivityNumber"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setJobNumber(rs.getString("JobNumber"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setActivityCode(rs.getString("ActivityCode"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setActivityStatus(rs.getString("ActivityStatus"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setComments(rs.getString("Comments"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setApproveDate(rs.getDate("ApproveDate"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setMoNumber(rs.getString("MONumber"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setLineNumber(rs.getString("LineNumber"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				try {
					dto.setSequence(rs.getString("SequenceNumber"));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				sequenceDetails.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return sequenceDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getSequenceDetailsForPick(com.gavs
	 * .hishear.web.domain.Sequence)
	 */
	public ArrayList getSequenceDetailsForPick(Sequence sequence) {
		ArrayList<Sequence> sequenceDetails = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			System.out.println(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			pstmt.setString(3, sequence.getSequence());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence dto = new Sequence();
				dto.setEmployeeName(rs.getString("EmployeeName"));
				dto.setAssetNumber(rs.getString("AssetNumber"));
				dto.setAssetDesc(rs.getString("AssetDescription"));
				dto.setActivity(rs.getString("Activity"));
				dto.setQtyCompleted(rs.getDouble("QtyComplete"));
				dto.setStatus(rs.getString("Status"));
				sequence.setJobNumber(rs.getString("JobNumber"));
				sequenceDetails.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequenceDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#completeSequence(java.lang.String,
	 * java.lang.String)
	 */
	public void completeSequence(String jobNumber, String query) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			System.out.println(query);
			pstmt.setString(1, jobNumber);
			pstmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#completeSequenceActivities(java.
	 * lang.String, java.lang.String)
	 */
	public void completeSequenceActivities(String jobNumber, String query) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			System.out.println(query);
			pstmt.setString(1, jobNumber);
			pstmt.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getPendingOrders(com.gavs.hishear
	 * .web.domain.Sequence)
	 */
	public List getPendingOrders(Sequence sequence) {
		List workcenters = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getWorkCenterCode());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WorkCenter workCenter = new WorkCenter();
				workCenter.setWorkcenterCode(sequence.getWorkCenterCode());
				workCenter.setItemDesc(rs.getString("ItemDescription"));
				workCenter.setMoNumber(rs.getString("MONumber"));
				workCenter.setLineNumber(rs.getString("LineNumber"));
				workCenter.setSequencenumber(rs.getString("SequenceNumber"));
				workCenter.setRequiredDate(rs.getTimestamp("RequiredDate"));
				workCenter.setSetup(rs.getString("Setup"));
				workCenter.setSetup(rs.getString("Setup"));
				workCenter.setRun(rs.getString("Run"));
				workCenter.setItemNumber(rs.getString("ItemNumber"));
				workCenter.setRequiredQuantity(rs.getInt("RequiredQuantity"));

				workcenters.add(workCenter);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return workcenters;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#createAndCompleteSequence(com.gavs
	 * .hishear.web.domain.Sequence, java.lang.String)
	 */
	public void createAndCompleteSequence(Sequence dto, String username) {
		Connection connection = null;
		try {
			connection = getConnection();
			String query = dto.getQuery();

			String isReportedToM3 = EMPTY_STRING;
			if (dto.isReportedToM3()) {
				isReportedToM3 = "Y";
			}

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getMoNumber());
			pstmt.setString(2, dto.getLineNumber());
			pstmt.setString(3, dto.getSequence());
			pstmt.setString(4, dto.getEmployeeID());
			pstmt.setString(5, dto.getAssetNumber());
			pstmt.setDouble(6, dto.getQtyCompleted());
			pstmt.setInt(7, dto.getReasonCode());
			pstmt.setString(8, dto.getComments());
			pstmt.setString(9, dto.getPartNumber());
			pstmt.setDouble(10, (int) dto.getOrderQty());
			pstmt.setString(11, dto.getDepartment());
			pstmt.setString(12, dto.getWorkCenterCode());
			pstmt.setString(13, "M"); // QtyFlag, added by Ashraf.
			pstmt.setString(14, dto.getNextSequenceNumber());
			pstmt.setString(15, isReportedToM3);
			pstmt.setString(16, username);
			pstmt.setString(17, dto.getFacility());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getBOMActivitiesForMOLine(com.gavs
	 * .hishear.web.domain.Sequence)
	 */
	public List getBOMActivitiesForMOLine(Sequence sequence) {
		ArrayList<Sequence> bomActivities = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;

		String query = sequence.getQuery();// (String)

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setInt(2, Integer.parseInt(sequence.getLineNumber()));
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Sequence seq = new Sequence();
				seq.setSequence(rs.getString("SequenceNumber"));
				seq.setActivityCode(rs.getString("ActivityCode"));
				seq.setDepartment(rs.getString("Department"));

				bomActivities.add(seq);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return bomActivities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getPreviousCompletedQty(com.gavs
	 * .hishear.web.domain.Sequence, java.lang.String)
	 */
	public double getPreviousCompletedQty(Sequence dto, String query) {
		double qty = 0;
		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setString(1, dto.getMoNumber());
			stmt.setString(2, dto.getLineNumber());
			stmt.setString(3, dto.getSequence());
			rs = stmt.executeQuery();
			if (rs.next()) {
				qty = rs.getDouble("QtyCompleted");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return qty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getSequences(com.gavs.hishear.web
	 * .domain.Sequence)
	 */
	@Override
	public ArrayList<Sequence> getSequences(Sequence sequence) {
		ArrayList<Sequence> sequences = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {

				sequence.setPartDesc(rs.getString("ItemDescription"));
				sequence.setPartNumber(rs.getString("ItemNumber"));
				sequence.setOrderQty(rs.getDouble("OrderQty"));

				Sequence dto = new Sequence();
				dto.setSequence(rs.getString("SequenceNumber"));
				dto.setPartNumber(rs.getString("ItemNumber"));
				dto.setPartDesc(rs.getString("ItemDescription"));
				dto.setRequiredDate(rs.getDate("RequiredDate"));
				dto.setWorkCenterCode(rs.getString("WCCode"));
				dto.setWorkCenterDesc(rs.getString("WCDescription"));
				dto.setOrderQty(rs.getDouble("OrderQty"));
				dto.setSetupStatus(rs.getString("Setup"));
				dto.setRunStatus(rs.getString("Run"));
				dto.setQtyCompleted(rs.getDouble("QtyCompleted"));
				sequences.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getLocalActivitiesForMOLineSeq(com
	 * .gavs.hishear.web.domain.Sequence, java.lang.String)
	 */
	public List getLocalActivitiesForMOLineSeq(Sequence currentSequence,
			String query) {
		ArrayList<Sequence> sequences = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, currentSequence.getMoNumber());
			pstmt.setString(2, currentSequence.getLineNumber());
			pstmt.setString(3, currentSequence.getSequence());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence sequence = new Sequence();
				sequence.setSequence(rs.getString("SequenceNumber"));
				sequence.setActivityCode(rs.getString("ActivityCode"));
				sequence.setQtyCompleted(rs.getDouble("QtyCompleted"));
				sequences.add(sequence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getUser(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User getUser(String userId, String query) {
		Connection connection = null;
		ResultSet rs = null;
		User user = null;
		try {
			connection = getConnection();

			PreparedStatement pstmt = connection.prepareStatement(query);
			System.out.println(query);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#logSequencePick(com.gavs.hishear
	 * .web.domain.Sequence, java.lang.String, java.lang.String, boolean)
	 */
	public void logSequencePick(Sequence dto, String username, String query,
			boolean freshSequence) {

		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getMoNumber());
			pstmt.setString(2, dto.getLineNumber());
			pstmt.setString(3, dto.getSequence());
			pstmt.setString(4, username);
			pstmt.setDouble(5, dto.getQtyCompleted());
			if (!freshSequence) {
				pstmt.setDouble(6, dto.getQtyCompleted() - dto.getPickQty());
			} else {
				pstmt.setDouble(6, dto.getQtyCompleted());
			}
			pstmt.setString(7, dto.getEmployeeID());
			pstmt.setString(8, dto.getAssetNumber());
			pstmt.setTimestamp(9, new Timestamp(new Date().getTime()));
			pstmt.setString(10, dto.getComments());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getFourthShiftData(java.lang.String,
	 * java.lang.String, java.lang.String, double, java.lang.String)
	 */
	public List<FourthShiftData> getFourthShiftData(String moNumber,
			String lineNumber, String sequenceNumber, double qtyCompleted,
			String query) {
		ArrayList<FourthShiftData> fourthShiftDataList = new ArrayList<FourthShiftData>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, moNumber);
			pstmt.setString(2, lineNumber);
			pstmt.setString(3, sequenceNumber);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FourthShiftData fourthShiftData = new FourthShiftData();
				fourthShiftData.setMoNumber(rs.getString("MONumber"));
				fourthShiftData.setLineNumber(rs.getString("LineNumber"));
				fourthShiftData.setSequenceNumber(rs
						.getString("SequenceNumber"));
				fourthShiftData.setComponentNumber(rs
						.getString("ComponentNumber"));
				fourthShiftData.setParentNumber(rs.getString("ParentNumber"));
				String bomActivityFlag = rs.getString("BOMActivityFlag");
				if ("S".equals(bomActivityFlag) || "R".equals(bomActivityFlag)) {
					fourthShiftData.setRequiredQty(roundDBL(rs
							.getDouble("RequiredQuantity")));
				} else {
					fourthShiftData.setRequiredQty(qtyCompleted);
				}
				fourthShiftData.setQtyType(rs.getString("QuantityType"));
				fourthShiftData.setPickTime(new Date());
				fourthShiftDataList.add(fourthShiftData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return fourthShiftDataList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getFourthShiftDataForPickReversal
	 * (java.lang.String, java.lang.String, java.lang.String, double,
	 * java.lang.String)
	 */
	public List<FourthShiftData> getFourthShiftDataForPickReversal(
			String moNumber, String lineNumber, String sequenceNumber,
			double qtyCompleted, String query) {
		ArrayList<FourthShiftData> fourthShiftDataList = new ArrayList<FourthShiftData>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, moNumber);
			pstmt.setString(2, lineNumber);
			pstmt.setString(3, sequenceNumber);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FourthShiftData fourthShiftData = new FourthShiftData();
				fourthShiftData.setMoNumber(rs.getString("MONumber"));
				fourthShiftData.setLineNumber(rs.getString("LineNumber"));
				fourthShiftData.setSequenceNumber(rs
						.getString("SequenceNumber"));
				fourthShiftData.setComponentNumber(rs
						.getString("ComponentNumber"));
				fourthShiftData.setParentNumber(rs.getString("ParentNumber"));
				String bomActivityFlag = rs.getString("BOMActivityFlag");
				if ("S".equals(bomActivityFlag) || "R".equals(bomActivityFlag)) {
					fourthShiftData.setRequiredQty(roundDBL(rs
							.getDouble("IssuedQuantity")));

				} else {
					fourthShiftData.setRequiredQty(qtyCompleted);

				}
				fourthShiftData.setQtyType(rs.getString("QuantityType"));
				fourthShiftData.setPickTime(new Date());
				fourthShiftDataList.add(fourthShiftData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return fourthShiftDataList;
	}

	/**
	 * Round dbl.
	 * 
	 * @param targetDBL
	 *            the target dbl
	 * @return the double
	 */
	private double roundDBL(double targetDBL) {
		int decimalPlace = 2;
		BigDecimal bd = new BigDecimal(targetDBL);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_EVEN);
		return (bd.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertFourthShiftData(com.gavs.hishear
	 * .web.domain.FourthShiftData, java.lang.String)
	 */
	public void insertFourthShiftData(FourthShiftData fourthShiftData,
			String query) {

		Connection connection = null;
		try {
			connection = getConnection();

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, fourthShiftData.getMoNumber());
			pstmt.setString(2, fourthShiftData.getLineNumber());
			pstmt.setString(3, fourthShiftData.getSequenceNumber());
			pstmt.setString(4, fourthShiftData.getComponentNumber());
			pstmt.setString(5, new SimpleDateFormat("yyyy MM dd HH:mm:ss")
					.format(fourthShiftData.getPickTime()));
			pstmt.setString(6, fourthShiftData.getParentNumber());
			pstmt.setDouble(7, fourthShiftData.getRequiredQty());
			pstmt.setString(8, fourthShiftData.getQtyType());

			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#checkDeptExistence(java.lang.String,
	 * java.lang.String)
	 */
	public boolean checkDeptExistence(String deptNumber, String query) {
		Connection connection = null;
		ResultSet rs = null;
		boolean deptExists = false;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, deptNumber);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				deptExists = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return deptExists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getAssets(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List getAssets(String query, String workCenter, String facility) {

		Connection connection = null;
		ResultSet rs = null;
		List assets = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, workCenter);
			pstmt.setString(2, facility);
			rs = pstmt.executeQuery();
			assets = new ArrayList();
			while (rs.next()) {
				// assets= new ArrayList();
				Asset asset = new Asset();
				asset.setAssetNumber(rs.getString("AssetNumber"));
				asset.setAssetDescNumber(asset.getAssetNumber() + "-"
						+ rs.getString("Description"));
				asset.setStatus(rs.getString("AssetStatus"));
				asset.setDepartmentName(rs.getString("WorkCenter"));
				asset.setFactory(rs.getString("Facility"));
				assets.add(asset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return assets;
	}

	// Added Date : 10th March 2009
	// Method to get reasons from ReasonMaster
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getReasons(java.lang.String)
	 */
	public ArrayList<Sequence> getReasons(String query) {
		Connection connection = null;
		ResultSet rs = null;
		ArrayList<Sequence> reasons = new ArrayList<Sequence>();
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "Pick");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence sequence = new Sequence();
				sequence.setReasonCode(rs.getInt("ReasonCode"));
				sequence.setReasonDesc(rs.getString("ReasonDesc"));
				reasons.add(sequence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return reasons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getSequenceDetailsForModification
	 * (com.gavs.hishear.web.domain.Sequence)
	 */
	public ArrayList getSequenceDetailsForModification(Sequence sequence) {
		ArrayList<Sequence> sequenceDetails = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			String query = sequence.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);
			System.out.println(query);
			pstmt.setString(1, sequence.getMoNumber());
			pstmt.setString(2, sequence.getLineNumber());
			pstmt.setString(3, sequence.getSequence());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence dto = new Sequence();
				dto.setEmployeeID(rs.getString("EmployeeNumber"));
				dto.setEmployeeName(rs.getString("EmployeeName"));
				dto.setAssetNumber(rs.getString("AssetNumber"));
				dto.setAssetDesc(rs.getString("AssetDescription"));
				dto.setQtyCompleted(rs.getDouble("QtyComplete"));
				dto.setLogonDate(rs.getTimestamp("LogonDate"));
				dto.setLogoffDate(rs.getTimestamp("LogoffDate"));
				dto.setJobActivityNumber(rs.getString("JobActivityNumber"));
				sequenceDetails.add(dto);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequenceDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateAssetNumber(java.lang.String,
	 * com.gavs.hishear.web.domain.Sequence, java.lang.String)
	 */
	public void updateAssetNumber(String userName, Sequence dto, String query) {
		Connection connection = null;
		try {
			connection = getConnection();

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getAssetNumber());
			pstmt.setString(2, dto.getJobActivityNumber());

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateReportedQty(java.lang.String,
	 * com.gavs.hishear.web.domain.Sequence, java.lang.String)
	 */
	public void updateReportedQty(String userName, Sequence dto, String query) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MM-dd-yyyy HH:mm:ss.SSS");

		Connection connection = null;
		try {
			connection = getConnection();

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setDouble(1, dto.getQtyCompleted());
			pstmt.setString(2, dto.getJobActivityNumber());
			pstmt.setTimestamp(3, new Timestamp(dto.getLogonDate().getTime()));
			pstmt.setTimestamp(4, new Timestamp(dto.getLogoffDate().getTime()));
			pstmt.setDouble(5, dto.getOriginalQtyCompleted());

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#logOriginalData(java.lang.String,
	 * com.gavs.hishear.web.domain.Sequence, java.lang.String, java.lang.String,
	 * int, java.lang.String)
	 */
	public void logOriginalData(String userName, Sequence dto, String query,
			String changeFlag, int reasonCode, String comments) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setString(3, dto.getMoNumber());
			pstmt.setString(4, dto.getLineNumber());
			pstmt.setString(5, dto.getSequence());
			pstmt.setString(6, dto.getOriginalAssetNumber());
			pstmt.setDouble(7, dto.getOriginalQtyCompleted());
			pstmt.setString(8, dto.getEmployeeID());
			pstmt.setString(9, dto.getJobActivityNumber());
			pstmt.setString(10, changeFlag);
			pstmt.setInt(11, reasonCode);
			pstmt.setString(12, comments);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#createAuditLog(com.gavs.hishear.
	 * web.domain.Sequence, java.lang.String)
	 */
	@Override
	public boolean createAuditLog(Sequence dto, String query) {
		// TODO Auto-generated method stub
		boolean status = false;
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getCompany());
			pstmt.setString(2, dto.getFacility());
			pstmt.setInt(3, (int) dto.getQtyCompleted());
			pstmt.setString(4, dto.getUOM());
			pstmt.setString(5, dto.getMoNumber());
			pstmt.setString(6, dto.getSequence());
			pstmt.setString(7, EMPTY_STRING);
			pstmt.setString(8, dto.getPartNumber());
			pstmt.setString(9, dto.getCompletionFlag());
			pstmt.setInt(10, 0);
			pstmt.setInt(11, 0);
			pstmt.setInt(12, 0);
			pstmt.setInt(13, 0);
			pstmt.executeUpdate();
			pstmt.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getErrorActivities(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Activity> getErrorActivities(String moNumber, String query) {
		ArrayList<Activity> errorActivities = new ArrayList<Activity>();
		errorActivities = (ArrayList<Activity>) jdbcTemplate.query(query,
				new Object[] { moNumber }, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Activity activity = new Activity();
						activity.setAssetNumber(rs.getString("AssetNumber"));
						activity.setActivityCode(rs.getString("ActivityCode"));
						activity.setCompletedQuantity(rs.getInt("QtyCompleted"));
						activity.setEmployeeNumber(rs
								.getString("EmployeeNumber"));
						activity.setJobActivityNumber(rs
								.getString("JobActivityNumber"));
						activity.setOperationNumber(rs
								.getString("SequenceNumber"));
						activity.setLogonDate(rs.getTimestamp("LogonDate"));
						activity.setLogoffDate(rs.getTimestamp("LogoffDate"));
						activity.setMinutesProcessed(Util.roundDBL(
								rs.getDouble("MinutesProcessed"), 2));
						activity.setActivityLogNumber(rs
								.getString("ActivityLogNumber"));
						activity.setMoNumber(rs.getString("MONumber"));
						activity.setOperationDescription(rs
								.getString("OperationDescription"));
						// START - WO 25251 - PPH Review
						activity.setJobNumber(rs.getString("JobNumber"));
						// END - WO 25251 - PPH Review

						activity.setRunHours(EMPTY_STRING
								+ Util.roundDBL(activity.getMinutesProcessed()
										/ MINUTES_PER_HOUR, 2));
						activity.setPph(EMPTY_STRING
								+ Util.roundDBL(
										activity.getCompletedQuantity()
												/ (activity
														.getMinutesProcessed() / MINUTES_PER_HOUR),
										2));

						return activity;
					}
				});
		return errorActivities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getMODetailsFromM3(java.lang.String)
	 */
	public MVXDTAMI getMODetailsFromM3(String moNumber) throws Exception {

		M3APIParameter parameter = new M3APIParameter();

		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMWOHEDX2Item");
		parameter.setOutputItem("GETMWOHEDX2ResponseItem");
		parameter.setFunctionName("getDetailsForMONumber");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		map.put(M3Parameter.MFNO.getValue(), moNumber);
		parameter.setInputData(map);

		MVXDTAMI mvxdtami = null;
		try {
			mvxdtami = (MVXDTAMI) m3APIWSClient.callM3API(parameter);

		} catch (Exception e) {
			System.out.println("e.getMessage() = " + e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}

		return mvxdtami;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#PMZ070WriteSetRptOperation(java.
	 * lang.String, int, double, java.lang.String, java.lang.String,
	 * java.lang.String, boolean, int, double, java.lang.String, double,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void PMZ070WriteSetRptOperation(String moNumber,
			int completedQuantity, double timeToReport, String sequenceNumber,
			String employeeNumber, String manualCompletionFlag,
			boolean isNegativeReporting, int workCenterCapacity,
			double priceTimeQuantity, String activityCode,
			double weightToReport, String errorLogQuery, String userName,
			String query) throws Exception {
		MVXDTAMI mvxdtami = getMODetailsFromM3(moNumber);
		M3APIParameter parameter = new M3APIParameter();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
		System.out.println("PMS70WriteSetRptOperation..................");
		/*
		 * START. Sharepoint item 1441. Reporting of Setup and Run Times
		 * Incorrect
		 */
		try {
			if (isNegativeReporting) {
				map.put(M3Parameter.MAQA.getValue(),
						String.valueOf(-completedQuantity));
				// map.put("UMAS",
				// String.valueOf(decimalFormat.format(-labourSetupTime )));
				//
				// map.put("UMAT",
				// String.valueOf(decimalFormat.format(-labourRunTime )));
				//
				// map.put("UPIT",
				// String.valueOf(decimalFormat.format(-machineRunTime )));
				//
				// map.put("USET",
				// String.valueOf(decimalFormat.format(-machineSetupTime )));

			} else {
				map.put(M3Parameter.MAQA.getValue(),
						String.valueOf(completedQuantity));
				// map.put("UMAS",
				// String.valueOf(decimalFormat.format(labourSetupTime )));
				//
				// map.put("UMAT",
				// String.valueOf(decimalFormat.format(labourRunTime )));
				//
				// map.put("UPIT",
				// String.valueOf(decimalFormat.format(machineRunTime)));
				//
				// map.put("USET",
				// String.valueOf(decimalFormat.format(machineSetupTime)));

			}
			setReportingValues(workCenterCapacity, priceTimeQuantity,
					activityCode, timeToReport, weightToReport,
					isNegativeReporting, map);
			/*
			 * END. Sharepoint item 1441. Reporting of Setup and Run Times
			 * Incorrect
			 */
			map.put(M3Parameter.MAUN.getValue(), "PCE");
			map.put(M3Parameter.MFNO.getValue(), moNumber);
			map.put(M3Parameter.OPNO.getValue(), sequenceNumber);
			map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());
			map.put(M3Parameter.REND.getValue(), manualCompletionFlag);

			// Added to Suppress the warning - Sundar
			map.put(M3Parameter.DSP1.getValue(), ONE);
			map.put(M3Parameter.DSP2.getValue(), ONE);
			map.put(M3Parameter.EMNO.getValue(), employeeNumber);
			map.put(M3Parameter.UOM.getValue(), "PCE");

			parameter.setInputData(map);
			// parameter.setWebServiceName("PMS070MIWrite");
			// parameter.setInputItem("RptOperationItem");
			// parameter.setOutputItem("RptOperationResponseItem");
			// parameter.setFunctionName("rptOperation");

			parameter.setWebServiceName("PMZ070MIWrite");
			parameter.setInputItem("RptOperationItem");
			parameter.setOutputItem("RptOperationResponseItem");
			parameter.setFunctionName("rptOperation");
			// Begin - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011

			/* START. Capture M3 Update Requests */
			insertM3Request(parameter, userName, query);
			/* END. Capture M3 Update Requests */
			m3APIWSClient.callM3API(parameter);

			// End - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
		} catch (Exception e) {
			e.printStackTrace();
			LogTools.windowsEventLogError("PMZ070MIWrite");
			if (!isNegativeReporting) {
				ErrorLog errorLog = new ErrorLog();
				errorLog.setWebServiceName("PMZ070MIWrite");
				String columnHeaders = "CONO,FACI,MAQA,MAUN,MFNO,OPNO,PRNO,REND,UMAS,UMAT,UPIT,USET,DSP1,DSP2,EMNO";
				errorLog.setColumnHeaders(columnHeaders);
				String columnValues = applicationPropertyBean.getCompany()
						+ "," + mvxdtami.getFacility() + ","
						+ completedQuantity + "," + "PCE" + "," + moNumber
						+ "," + sequenceNumber + ","
						+ mvxdtami.getProductNumber() + ","
						+ manualCompletionFlag + ","
						+ map.get(M3Parameter.UMAS.getValue()) + ","
						+ map.get(M3Parameter.UMAT.getValue()) + ","
						+ map.get(M3Parameter.UPIT.getValue()) + ","
						+ map.get(M3Parameter.USET.getValue()) + "," + ONE
						+ "," + ONE + "," + employeeNumber;
				errorLog.setColumnValues(columnValues);
				errorLog.setFunctionName("rptOperation");
				errorLog.setInputItem("RptOperationItem");
				errorLog.setOutputItem("RptOperationResponseItem");
				errorLog.setDisplayProgram(false);
				errorLog.setNoOfTry(0);
				errorLog.setPriority(Integer.parseInt(applicationPropertyBean
						.getPriorityForPMS070Write()));
				errorLog.setErrorMessage(e.getMessage());
				errorLog.setLoginUserID(employeeNumber);

				insertErrorLogForM3WriteFailure(errorLog, errorLogQuery);
			}
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#PMZ70WriteSetRptOperation(java.lang
	 * .String, int, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, double, int, java.lang.String)
	 */
	@Override
	public void PMZ70WriteSetRptOperation(String moNumber,
			int completedQuantity, String sequenceNumber,
			String employeeNumber, String manualCompletionFlag,
			String usedLabourRunTime, double priceTimeQuantity,
			int workCenterCapacity, String activityCode, String m3Query,
			String errorQuery, String userName) throws Exception {

		MVXDTAMI mvxdtami = getMODetailsFromM3(moNumber);
		M3APIParameter parameter = new M3APIParameter();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());

		java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
				"0.##");

		try {
			String UMAS = EMPTY_STRING;
			String UMAT = EMPTY_STRING;
			String UPIT = EMPTY_STRING;
			String USET = EMPTY_STRING;

			// PCAP = 1
			if (WorkCenterCapacity.MACHINE == workCenterCapacity) {

				// CTCD = 1000 or 1
				if (priceTimeQuantity == PriceTimeQuantity.HOURS
						|| priceTimeQuantity == PriceTimeQuantity.PIECE) {
					if (ActivityCode.RUN.equals(activityCode)) {
						// UPIT and UMAT
						UMAT = decimalFormat.format(-Double
								.parseDouble(usedLabourRunTime));
						UPIT = decimalFormat.format(-Double
								.parseDouble(usedLabourRunTime));
					} else if (ActivityCode.SETUP.equals(activityCode)) {
						USET = decimalFormat.format(-Double
								.parseDouble(usedLabourRunTime));
					}

				}

				// CTCD = 100 // Report Weight
				else if (priceTimeQuantity == PriceTimeQuantity.WEIGHT) {
					if (ActivityCode.RUN.equals(activityCode)) {
						// UPIT and UMAT = 0
						UMAT = "0.0";
						UPIT = decimalFormat.format(-Double
								.parseDouble(usedLabourRunTime));
					}
				}
			}

			// PCAP = 2
			else if (WorkCenterCapacity.LABOR == workCenterCapacity) {
				if (ActivityCode.RUN.equals(activityCode)) {
					// UMAT
					UMAT = decimalFormat.format(-Double
							.parseDouble(usedLabourRunTime));

				} else if (ActivityCode.SETUP.equals(activityCode)) {
					// UMAS
					UMAS = decimalFormat.format(-Double
							.parseDouble(usedLabourRunTime));
				}
			}

			map.put(M3Parameter.MAQA.getValue(),
					String.valueOf(-completedQuantity));

			if (!EMPTY_STRING.equals(UMAS)) {
				map.put(M3Parameter.UMAS.getValue(), UMAS);
			}
			if (!EMPTY_STRING.equals(UMAT)) {
				map.put(M3Parameter.UMAT.getValue(), UMAT);
			}
			if (!EMPTY_STRING.equals(UPIT)) {
				map.put(M3Parameter.UPIT.getValue(), UPIT);
			}
			if (!EMPTY_STRING.equals(USET)) {
				map.put(M3Parameter.USET.getValue(), USET);
			}

			if (manualCompletionFlag != null) {
				map.put(M3Parameter.REND.getValue(), manualCompletionFlag);
			}

			// map.put("UMAT",
			// String.valueOf(decimalFormat.format(-Double.parseDouble(usedLabourRunTime))));
			// /* START - Fix for Sharepoint Item 1293 - 8 - Operation rejection
			// and reversal in M3 to be improved */
			// map.put("UPIT",
			// String.valueOf(decimalFormat.format(-Double.parseDouble(usedLabourRunTime))));
			// /* END - Fix for Sharepoint Item 1293 - 8 - Operation rejection
			// and reversal in M3 to be improved */

			map.put(M3Parameter.MFNO.getValue(), moNumber);
			map.put(M3Parameter.OPNO.getValue(), sequenceNumber);
			map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());

			// Added to Suppress the warning - Sundar
			map.put(M3Parameter.DSP1.getValue(), ONE);
			map.put(M3Parameter.DSP2.getValue(), ONE);
			map.put(M3Parameter.DSP3.getValue(), ONE);
			map.put(M3Parameter.DSP4.getValue(), ONE);

			parameter.setInputData(map);
			parameter.setWebServiceName("PMZ070MIWrite");
			parameter.setInputItem("RptOperationItem");
			parameter.setOutputItem("RptOperationResponseItem");
			parameter.setFunctionName("rptOperation");
			// Begin - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
			/* START. Capture M3 Update Requests */
			insertM3Request(parameter, userName, m3Query);
			m3APIWSClient.callM3API(parameter);
			// End - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
		} catch (Exception e) {
			e.printStackTrace();
			// Begin - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
			ErrorLog errorLog = new ErrorLog();
			errorLog.setWebServiceName("PMZ070MIWrite");
			String columnHeaders = "CONO,FACI,MAQA,MAUN,MFNO,OPNO,PRNO,REND,UMAS,UMAT,UPIT,USET,DSP1,DSP2,EMNO";
			errorLog.setColumnHeaders(columnHeaders);
			String columnValues = applicationPropertyBean.getCompany() + ","
					+ mvxdtami.getFacility() + "," + completedQuantity + ","
					+ "PCE" + "," + moNumber + "," + sequenceNumber + ","
					+ mvxdtami.getProductNumber() + "," + manualCompletionFlag
					+ "," + map.get(M3Parameter.UMAS.getValue()) + ","
					+ map.get(M3Parameter.UMAT.getValue()) + ","
					+ map.get(M3Parameter.UPIT.getValue()) + ","
					+ map.get(M3Parameter.USET.getValue()) + "," + ONE + ","
					+ ONE + "," + employeeNumber;
			errorLog.setColumnValues(columnValues);
			errorLog.setFunctionName("rptOperation");
			errorLog.setInputItem("RptOperationItem");
			errorLog.setOutputItem("RptOperationResponseItem");
			errorLog.setDisplayProgram(false);
			errorLog.setNoOfTry(0);
			errorLog.setPriority(Integer.parseInt(applicationPropertyBean
					.getPriorityForPMS070Write()));
			errorLog.setErrorMessage(e.getMessage());
			errorLog.setLoginUserID(employeeNumber);

			insertErrorLogForM3WriteFailure(errorLog, errorQuery);
			// End - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#PMS50WriteSetRptReceipt(com.gavs
	 * .hishear.web.domain.Sequence, java.lang.String, java.lang.String)
	 */
	@Override
	public void PMS50WriteSetRptReceipt(Sequence sequence,
			String errorLogQuery, String userName, String query) {
		System.out.println("PMS50WriteSetRptReceipt called ..................");
		MVXDTAMI mvxdtami = null;
		try {
			mvxdtami = getMODetailsFromM3(sequence.getMoNumber());
			sequence.setProductNumber(mvxdtami.getProductNumber());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		M3APIParameter parameter = new M3APIParameter();
		HashMap<String, String> map = new HashMap<String, String>();

		map.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
		map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());
		map.put(M3Parameter.MFNO.getValue(), sequence.getMoNumber());
		map.put(M3Parameter.RPQA.getValue(),
				String.valueOf(sequence.getMorvQuantity()));
		map.put(M3Parameter.MAUN.getValue(), "PCE");
		map.put(M3Parameter.REND.getValue(), ONE);
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		map.put(M3Parameter.WHSL.getValue(), EMPTY_STRING);
		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
		// map.put("WHSL", applicationPropertyBean.getMorvLocation());
		map.put(M3Parameter.BANO.getValue(), sequence.getMoNumber());
		// Added to Suppress the warning - Sundar
		map.put(M3Parameter.DSP1.getValue(), ONE);
		map.put(M3Parameter.DSP2.getValue(), ONE);
		map.put(M3Parameter.DSP3.getValue(), ONE);
		map.put(M3Parameter.DSP4.getValue(), ONE);
		map.put(M3Parameter.DSP5.getValue(), ONE);
		map.put(M3Parameter.DSP6.getValue(), ONE);
		map.put(M3Parameter.DSP7.getValue(), ONE);
		map.put(M3Parameter.DSP8.getValue(), ONE);

		parameter.setInputData(map);
		parameter.setWebServiceName("PMS050MIWrite");
		parameter.setInputItem("RptReceiptItem");
		parameter.setOutputItem("RptReceiptResponseItem");
		parameter.setFunctionName("rptReceipt");

		try {
			// Begin - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
			/* START. Capture M3 Update Requests */
			insertM3Request(parameter, userName, query);
			/* END. Capture M3 Update Requests */
			m3APIWSClient.callM3API(parameter);
			// End - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLog errorLog = new ErrorLog();
			errorLog.setWebServiceName("PMS050MIWrite");
			String columnHeaders = "CONO,FACI,PRNO,MFNO,RPQA,MAUN,REND,WHSL,BANO,DSP1,DSP2,DSP3,DSP4,DSP5,DSP6,DSP7,DSP8";
			errorLog.setColumnHeaders(columnHeaders);
			String columnValues = applicationPropertyBean.getCompany() + ","
					+ mvxdtami.getFacility() + ","
					+ mvxdtami.getProductNumber() + ","
					+ sequence.getMoNumber()
					+ ","
					+ String.valueOf(sequence.getMorvQuantity())
					+ ","
					+ "PCE"
					+ ","
					+ ONE
					+ ","
					// Begin WO# 27639 - Moving Static links to dynamic - Pinky
					// - Infosys - 23 June 2011
					+ EMPTY_STRING
					+ ","
					// End WO# 27639 - Moving Static links to dynamic - Pinky -
					// Infosys - 23 June 2011
					+ sequence.getMoNumber() + "," + ONE + "," + ONE + ","
					+ ONE + "," + ONE + "," + ONE + "," + ONE + "," + ONE + ","
					+ ONE;
			errorLog.setColumnValues(columnValues);
			errorLog.setFunctionName("rptReceipt");
			errorLog.setInputItem("RptReceiptItem");
			errorLog.setOutputItem("RptReceiptResponseItem");
			errorLog.setDisplayProgram(false);
			errorLog.setNoOfTry(0);
			errorLog.setPriority(Integer.parseInt(applicationPropertyBean
					.getPriorityForPMS050MIWrite()));
			errorLog.setErrorMessage(e.getMessage());
			errorLog.setLoginUserID(sequence.getEmployeeNumber());

			insertErrorLogForM3WriteFailure(errorLog, errorLogQuery);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#approveInspectionResultUsingLIS200
	 * (com.gavs.hishear.web.domain.Sequence, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void approveInspectionResultUsingLIS200(Sequence sequence,
			String errorLogQuery, String userName, String m3RequestQuery) {
		System.out
				.println("approveInspectionResultUsingLIS200 start ..................");
		MVXDTAMI mvxdtami = null;
		try {
			mvxdtami = getMODetailsFromM3(sequence.getMoNumber());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Program mainProgram = new Program();
		HashMap<String, String> map = new HashMap<String, String>();

		map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
		sequence.setFacility(mvxdtami.getFacility());
		map.put(M3Parameter.ITNO.getValue(), mvxdtami.getProductNumber());
		map.put(M3Parameter.BANO.getValue(), sequence.getMoNumber());
		map.put(M3Parameter.ANNO.getValue(), "01");
		map.put(M3Parameter.ANRS.getValue(), "OK");
		map.put(M3Parameter.SPAN.getValue(), "KBAHADUR");

		mainProgram.setInputData(map);
		mainProgram.setWebServiceName("LIS200Write");
		mainProgram.setM3Function("LIS200");
		mainProgram.setFunctionName("approveInspectionResultLIS200");

		Program subProgram = new Program();
		subProgram.setM3Function("PMS130");
		mainProgram.getSubPrograms().add(subProgram);

		try {
			// Begin - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011

			/* START. Capture M3 Update Requests */
			insertM3Request(mainProgram, userName, m3RequestQuery);
			/* END. Capture M3 Update Requests */
			m3APIWSClient.callService(mainProgram);

			// End - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLog errorLog = new ErrorLog();
			errorLog.setWebServiceName("LIS200Write");
			String columnHeaders = "FACI,ITNO,BANO,ANNO,ANRS,SPAN";
			errorLog.setColumnHeaders(columnHeaders);
			String columnValues = mvxdtami.getFacility() + ","
					+ mvxdtami.getProductNumber() + ","
					+ sequence.getMoNumber() + "," + "01" + "," + "OK" + ","
					+ "KBAHADUR";
			errorLog.setColumnValues(columnValues);
			errorLog.setM3Function("LIS200");
			errorLog.setFunctionName("approveInspectionResultLIS200");
			errorLog.setDisplayProgram(true);
			errorLog.setSubFunctionName("PMS130");
			errorLog.setNoOfTry(0);
			errorLog.setPriority(Integer.parseInt(applicationPropertyBean
					.getPriorityForLIS200Write()));
			errorLog.setErrorMessage(e.getMessage());
			errorLog.setLoginUserID(sequence.getEmployeeNumber());

			insertErrorLogForM3WriteFailure(errorLog, errorLogQuery);
		}
		System.out
				.println("approveInspectionResultUsingLIS200 end ..................");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#inspectedItemPutawayUsingPMS130(
	 * com.gavs.hishear.web.domain.Sequence, java.lang.String, java.lang.String)
	 */
	@Override
	public void inspectedItemPutawayUsingPMS130(Sequence sequence,
			String errorLogQuery, String userName, String m3RequestQuery)
			throws Exception {
		System.out
				.println("inspectedItemPutawayUsingPMS130 start ..................");
		MVXDTAMI mvxdtami = getMODetailsFromM3(sequence.getMoNumber());

		Program mainProgram = new Program();
		HashMap<String, String> map = new HashMap<String, String>();

		map.put(M3Parameter.BANO.getValue(), sequence.getMoNumber());
		map.put(M3Parameter.ITNO.getValue(), mvxdtami.getProductNumber());
		sequence.setProductNumber(mvxdtami.getProductNumber());
		map.put(M3Parameter.STAS.getValue(), "2");
		mainProgram.setInputData(map);
		// Changed - Sundar
		mainProgram.setWebServiceName("PMS130Write");
		mainProgram.setM3Function("PMS130");
		mainProgram.setFunctionName("inspectedItemPutawayPMS130");

		try {
			// Begin - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
			/* START. Capture M3 Update Requests */
			insertM3Request(mainProgram, userName, m3RequestQuery);
			/* END. Capture M3 Update Requests */
			m3APIWSClient.callService(mainProgram);
			// End - M3 Request to be logged before making an call - VP -
			// Infosys - May 11, 2011
		} catch (Exception e) {
			e.printStackTrace();
			ErrorLog errorLog = new ErrorLog();
			errorLog.setWebServiceName("PMS130Write");
			String columnHeaders = "BANO,ITNO,STAS";
			errorLog.setColumnHeaders(columnHeaders);
			String columnValues = sequence.getMoNumber() + ","
					+ mvxdtami.getProductNumber() + "," + "2";
			errorLog.setColumnValues(columnValues);
			errorLog.setM3Function("PMS130");
			errorLog.setFunctionName("inspectedItemPutawayPMS130");
			errorLog.setDisplayProgram(true);
			errorLog.setSubFunctionName("PMS130");
			errorLog.setNoOfTry(0);
			errorLog.setPriority(Integer.parseInt(applicationPropertyBean
					.getPriorityForPMS130Write()));
			errorLog.setErrorMessage(e.getMessage());
			errorLog.setLoginUserID(sequence.getEmployeeNumber());

			insertErrorLogForM3WriteFailure(errorLog, errorLogQuery);
			throw new Exception(e);
		}
		System.out
				.println("inspectedItemPutawayUsingPMS130 End ..................");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateErrorLogCompletion(double,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateErrorLogCompletion(String flag, String user,
			String jobNumber, String query) throws Exception {

		System.out.println("updateErrorLogCompletion.................");
		System.out.println("query = " + query);
		System.out.println("user = " + user);
		System.out.println("jobNumber = " + jobNumber);
		int noOfRowsAffected = 0;
		try {
			noOfRowsAffected = jdbcTemplate.update(query, new Object[] { flag,
					user, jobNumber });
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(
					"Error while updating the status. Please try again later.");
		}
		return noOfRowsAffected > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getMODetails(java.lang.String)
	 */
	@Override
	public ArrayList<Sequence> getMODetails(String query) {
		ArrayList<Sequence> sequenceDetails = new ArrayList<Sequence>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Sequence dto = new Sequence();
				dto.setMoNumber(rs.getString("MONumber"));
				dto.setPartNumber(rs.getString("ProductNo"));
				sequenceDetails.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return sequenceDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateActivityLog(com.gavs.hishear
	 * .web.domain.Sequence, com.gavs.hishear.web.domain.Sequence,
	 * java.lang.String)
	 */
	public void updateActivityLog(Sequence modifiedSequence,
			Sequence selectedSequence, String query) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setDouble(1, modifiedSequence.getQtyCompleted());
			pstmt.setTimestamp(2,
					DateUtil.getTimestamp(modifiedSequence.getLogonDate()));
			pstmt.setTimestamp(3,
					DateUtil.getTimestamp(modifiedSequence.getLogoffDate()));
			pstmt.setString(4, modifiedSequence.getJobActivityNumber());
			pstmt.setTimestamp(5,
					DateUtil.getTimestamp(selectedSequence.getLogonDate()));
			pstmt.setTimestamp(6,
					DateUtil.getTimestamp(selectedSequence.getLogoffDate()));

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	private Timestamp getTimestamp(Date date) {
		Timestamp timestamp;
		if (date == null) {
			return null;
		}
		return new Timestamp(DateUtil.timeStampValidation(date));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertRejectedActivity(com.gavs.
	 * hishear.web.domain.Sequence, java.lang.String)
	 */
	public void insertRejectedActivity(Sequence selectedSequence, String query) {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, selectedSequence.getJobActivityNumber());
			pstmt.setString(2, selectedSequence.getJobNumber());
			pstmt.setString(3, selectedSequence.getActivityCode());
			pstmt.setString(4, selectedSequence.getEmployeeID());
			pstmt.setString(5, selectedSequence.getAssetNumber());
			pstmt.setString(6, selectedSequence.getActivityStatus());
			pstmt.setString(7, selectedSequence.getComments());
			pstmt.setTimestamp(8,
					DateUtil.getTimestamp(selectedSequence.getApproveDate()));
			pstmt.setTimestamp(9,
					DateUtil.getTimestamp(selectedSequence.getLogonDate()));
			pstmt.setTimestamp(10,
					DateUtil.getTimestamp(selectedSequence.getLogoffDate()));
			pstmt.setDouble(11, selectedSequence.getQtyCompleted());
			pstmt.setString(12, selectedSequence.getMoNumber());
			pstmt.setString(13, selectedSequence.getLineNumber());
			pstmt.setString(14, selectedSequence.getSequence());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#doPhysicalAllocation(java.lang.String
	 * , java.lang.String)
	 */
	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	// public void doPhysicalAllocation(String itemNumber, String facility) {
	//
	// try {
	//
	// String considerCustomerPreferences = null;
	// if ("PIN".equals(facility) || "COL".equals(facility)
	// || "NUT".equals(facility) || "TOR".equals(facility)) {
	// considerCustomerPreferences = applicationPropertyBean
	// .isConsiderCustomerPreferencesForHSC();
	// } else if ("COI".equals(facility)) {
	// considerCustomerPreferences = applicationPropertyBean
	// .isConsiderCustomerPreferencesForMDK();
	// }
	//
	// allocationEngineService
	// .doPhysicalAllocation(itemNumber, facility,
	// Integer.parseInt(applicationPropertyBean
	// .getShippingDays()),
	// Integer.parseInt(applicationPropertyBean
	// .getMinimumStockAllocationPercentage()),
	// Integer.parseInt(applicationPropertyBean
	// .getIgnorableShippedQuantityPercentage()),
	// Integer.parseInt(applicationPropertyBean
	// .getThresholdPrice()),
	// applicationPropertyBean.getIgnoredLocations(),
	// applicationPropertyBean
	// .getLeastAllocationPriorityLocation(),
	// considerCustomerPreferences);
	// } catch (NumberFormatException e) {
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * Insert error log for m3 write failure.
	 * 
	 * @param errorLog
	 *            the error log
	 * @param errorLogQuery
	 *            the error log query
	 */
	public void insertErrorLogForM3WriteFailure(ErrorLog errorLog,
			String errorLogQuery) {

		String displayProgram;
		if (errorLog.isDisplayProgram()) {
			displayProgram = YES;
		} else {
			displayProgram = NO;
		}

		try {
			jdbcTemplate
					.update(errorLogQuery,
							new Object[] {
									errorLog.getWebServiceName(),
									errorLog.getColumnHeaders(),
									errorLog.getColumnValues(),
									StringUtils.defaultString(errorLog
											.getInputItem()),
									StringUtils.defaultString(errorLog
											.getOutputItem()),
									StringUtils.defaultString(errorLog
											.getM3Function()),
									StringUtils.defaultString(errorLog
											.getSubFunctionName()),
									errorLog.getFunctionName(),
									displayProgram,
									errorLog.getNoOfTry(),
									errorLog.getPriority(),
									new java.sql.Date(new Date().getTime()),
									StringUtils.trimToEmpty(errorLog
											.getErrorMessage()),
									StringUtils.defaultString(errorLog
											.getLoginUserID()), "N" });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Trim message.
	 * 
	 * @param message
	 *            the message
	 * @return the string
	 */
	public String trimMessage(String message) {
		String[] messageArray = message.split(" ");
		String afterRemovingSpace = EMPTY_STRING;
		if (message != null && !message.equalsIgnoreCase(EMPTY_STRING)) {
			for (int i = 0; i < messageArray.length; i++) {
				if (!messageArray[i].trim().equals(EMPTY_STRING)) {
					afterRemovingSpace = afterRemovingSpace + " "
							+ messageArray[i].trim();
				}
			}
			afterRemovingSpace = afterRemovingSpace.substring(
					afterRemovingSpace.indexOf("-") + 1,
					afterRemovingSpace.lastIndexOf(" "));
		}
		return afterRemovingSpace.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#closeAllOpenedActivities(com.gavs
	 * .hishear.web.domain.Sequence, java.lang.String)
	 */
	public void closeAllOpenedActivities(Sequence sequence, String query) {

		this.jdbcTemplate.update(query, new Object[] { sequence.getMoNumber(),
				sequence.getLineNumber() });

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateMOStatusAsCompleted(com.gavs
	 * .hishear.web.domain.Sequence, java.lang.String)
	 */
	public void updateMOStatusAsCompleted(Sequence sequence, String query) {
		// Removed hard coded value in the query to update the status
		// dynamically.
		// Changes done by Ashraf. 26th August 2010.
		String moStatus = "C";
		this.jdbcTemplate.update(
				query,
				new Object[] { moStatus, sequence.getMoNumber(),
						sequence.getLineNumber() });

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

	/**
	 * Gets the m3 apiws client.
	 * 
	 * @return the m3APIWSClient
	 */
	public M3APIWSClient getM3APIWSClient() {
		return m3APIWSClient;
	}

	/**
	 * Sets the m3 apiws client.
	 * 
	 * @param client
	 *            the m3APIWSClient to set
	 */
	public void setM3APIWSClient(M3APIWSClient client) {
		m3APIWSClient = client;
	}

	/**
	 * Gets the allocation engine service.
	 * 
	 * @return the allocationEngineService
	 */
	public AllocationEngineService getAllocationEngineService() {
		return allocationEngineService;
	}

	/**
	 * Sets the allocation engine service.
	 * 
	 * @param allocationEngineService
	 *            the allocationEngineService to set
	 */
	public void setAllocationEngineService(
			AllocationEngineService allocationEngineService) {
		this.allocationEngineService = allocationEngineService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getErrorMoNumbers(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Sequence> getErrorMoNumbers(String query, String facility) {
		// TODO Auto-generated method stub
		ArrayList<Sequence> errorMONumbers = new ArrayList<Sequence>();
		errorMONumbers = (ArrayList<Sequence>) jdbcTemplate.query(query,
				new Object[] { facility }, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Sequence sequence = new Sequence();
						sequence.setMoNumber(rs.getString("MONumber"));
						return sequence;
					}
				});

		return errorMONumbers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getFacilities(java.lang.String)
	 */
	@Override
	public ArrayList<Facility> getFacilities(String division) throws Exception {
		ArrayList<Facility> divisionFacilities = new ArrayList<Facility>();
		ArrayList<Facility> allFacilities = null;
		try {
			M3APIParameter parameter = new M3APIParameter();
			parameter.setWebServiceName("CRS008MIRead");
			parameter.setInputItem("ListFacilityItem");
			parameter.setOutputItem("ListFacilityResponseItem");
			parameter.setFunctionName("listFacility");
			parameter.setReadOrWrite("read");

			HashMap<String, String> inputDataMap = new HashMap<String, String>();
			inputDataMap.put(M3Parameter.CONO.getValue(),
					applicationPropertyBean.getCompany());
			parameter.setInputData(inputDataMap);

			allFacilities = (ArrayList<Facility>) m3APIWSClient
					.callM3API(parameter);// m3Dao.getFacilities();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could NOT communicate to M3");
		}
		if (allFacilities != null) {
			for (Facility facility : allFacilities) {
				if (facility.getDivision().equals(division)) {
					divisionFacilities.add(facility);
				}
			}
		}

		return divisionFacilities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#fetchMOSeqActDetails(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	public void fetchMOSeqActDetails(ManufacturingOrder dto, String qry)
			throws Exception {

		HashMap<String, Sequence> sequences = null;
		ArrayList<SequenceActivity> actList = null;

		// HashMap<String,String> employeeMap = null;
		HashMap<String, String> assetMap = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			System.out.println("qry:****" + qry + "****");
			pstmt = connection.prepareStatement(qry);
			pstmt.setString(1, dto.getFacility());
			pstmt.setString(2, dto.getMoNumber());
			pstmt.setString(3, dto.getFacility());
			pstmt.setString(4, dto.getMoNumber());
			pstmt.setString(5, dto.getFacility());
			pstmt.setString(6, dto.getMoNumber());

			rs = pstmt.executeQuery();
			System.out.println("after executing" + rs);

			SequenceActivity seqAct = null;
			Sequence seq = null;
			String prevSeq = null;
			String currSeq = null;
			double totQtyCompleted = 0;
			String actStatus = null;
			boolean actStatFlag = true;
			actList = new ArrayList();
			sequences = new HashMap<String, Sequence>();
			assetMap = new HashMap<String, String>();
			// employeeMap = new HashMap<String,String>();
			String value = null;
			String empNum = null;
			String assetNum = null;
			ArrayList<String> activityList = new ArrayList<String>();
			while (rs.next()) {
				currSeq = rs.getString("SequenceNumber");
				if (prevSeq == null
						|| (prevSeq != null && !prevSeq
								.equalsIgnoreCase(currSeq))) {
					if (prevSeq != null) {

						// if(actStatFlag &&
						// activityList.contains(webConstants.SETUP)) {
						// seq.setSetupStatus(webConstants.STATUS_COMPLETE);
						// }
						activityList = new ArrayList<String>();
						seq.setQtyCompleted(totQtyCompleted);
						totQtyCompleted = 0;
						// seq.setEmployeeMap(employeeMap);
						seq.setAssetMap(assetMap);
						seq.setSeqActivityDetails(actList);
						sequences.put(prevSeq, seq);
						assetMap = new HashMap<String, String>();
						// employeeMap = new HashMap<String,String>();
						actList = new ArrayList();
					}

					seq = new Sequence();
					seq.setSequence(currSeq);
					prevSeq = currSeq;
					seq.setOldSequenceStatus(rs.getString("SequenceStatus"));
					seq.setSequenceStatus(rs.getString("SequenceStatus"));
					seq.setWorkCenterCode(rs.getString("WorkCenter"));
					seq.setPartNumber(rs.getString("ItemNumber"));
					seq.setJobNumber(rs.getInt("JobNumber"));

					if (seq.getSequenceStatus() != null
							&& seq.getSequenceStatus().equalsIgnoreCase(
									webConstants.STATUS_COMPLETE)) {
						seq.setCompletedDate(rs.getDate("LastUpdatedDate"));
						// seq.setRunStatus(webConstants.STATUS_COMPLETE);
					}
					// else {
					// seq.setRunStatus(webConstants.STATUS_INPROGRESS);
					// }

				}

				seqAct = new SequenceActivity();
				seqAct.setActivity(rs.getString("ActivityCode")); // chk for X
				// code
				seqAct.setOldActivity(rs.getString("ActivityCode"));
				empNum = rs.getString("EmployeeNumber");
				seqAct.setEmployeeNumber(empNum);
				seqAct.setOldEmployeeNumber(empNum);
				/*
				 * if (empNum != null) { employeeMap.put(empNum, value); }
				 */
				seqAct.setActivityLogNumber(rs.getInt("ActivityLogNumber"));
				seqAct.setJobActivityNumber(rs.getInt("JobActivityNumber"));
				// seq.setJobActivityNumber(String.valueOf(rs.getInt("JobActivityNumber")));
				assetNum = rs.getString("AssetNumber");
				seqAct.setAssetNumber(assetNum);
				seqAct.setOldAssetNumber(assetNum);
				if (assetNum != null) {
					assetMap.put(assetNum, value);
					// need to get asset desc
				}
				totQtyCompleted += rs.getInt("QtyCompleted");
				seqAct.setQtyCompleted(rs.getInt("QtyCompleted"));
				seqAct.setOldQtyCompleted(rs.getInt("QtyCompleted"));
				Calendar cal = Calendar.getInstance();
				Date logonDate = rs.getTimestamp("LogonDate");
				if (logonDate != null) {
					cal.setTime(logonDate);
					cal.set(Calendar.MILLISECOND, 0);
					logonDate = cal.getTime();
				}
				seqAct.setLogonDate(logonDate);
				seqAct.setOldLogonDate(logonDate);

				Date logoffDate = rs.getTimestamp("LogoffDate");
				if (logoffDate != null) {
					cal.setTime(logoffDate);
					cal.set(Calendar.MILLISECOND, 0);
					logoffDate = cal.getTime();
				}

				seqAct.setLogoffDate(logoffDate);
				seqAct.setOldLogoffDate(logoffDate);

				actStatus = rs.getString("ActivityStatus");
				seqAct.setActivityStatus(actStatus);
				activityList.add(seqAct.getActivity());
				if (seqAct.getActivity() != null
						&& seqAct.getActivity().equalsIgnoreCase(
								webConstants.SETUP)
						&& actStatus != null
						&& actStatus
								.equalsIgnoreCase(webConstants.STATUS_INPROGRESS)) {
					actStatFlag = false;
				}

				// if (seqAct.getActivity() != null
				// && seqAct.getActivity().equalsIgnoreCase(
				// webConstants.SETUP)) {
				// if (actStatus != null
				// && actStatus
				// .equalsIgnoreCase(webConstants.STATUS_INPROGRESS)) {
				// seq.setSetupStatus(webConstants.STATUS_INPROGRESS);
				// } else if (actStatus != null
				// && actStatus
				// .equalsIgnoreCase(webConstants.STATUS_COMPLETE)) {
				// seq.setSetupStatus(webConstants.STATUS_COMPLETE);
				// }
				// } else if (seqAct.getActivity() != null
				// && seqAct.getActivity().equalsIgnoreCase(
				// webConstants.RUN)) {
				// if (actStatus != null
				// && actStatus
				// .equalsIgnoreCase(webConstants.STATUS_INPROGRESS)) {
				// seq.setRunStatus(webConstants.STATUS_INPROGRESS);
				// } else if (actStatus != null
				// && actStatus
				// .equalsIgnoreCase(webConstants.STATUS_COMPLETE)) {
				// seq.setRunStatus(webConstants.STATUS_COMPLETE);
				// }
				//

				seqAct.setType(rs.getString("Type"));
				if (rs.getString("Type") != null
						&& rs.getString("Type").equalsIgnoreCase("2-break")) {
					seqAct.setActivity(webConstants.PAUSE);
					seqAct.setOldActivity(webConstants.PAUSE);
					seqAct.setLogonDate(rs.getTimestamp("BreakStart"));
					seqAct.setOldLogonDate(rs.getTimestamp("BreakStart"));
					seqAct.setLogoffDate(rs.getTimestamp("BreakEnd"));
					seqAct.setOldLogoffDate(rs.getTimestamp("BreakEnd"));

					// Begin WO# 32731 - Error while pick operation - Ambrish -
					// Infosys - 18
					// July 2011
					seqAct.setActivityBreakNumber(rs.getInt("ActivityBreakId"));
					// End WO# 32731 - Error while pick operation - Ambrish -
					// Infosys - 18
					// July 2011

				} else if (rs.getString("Type") != null
						&& rs.getString("Type").equalsIgnoreCase("3-retool")) {
					seqAct.setActivity(webConstants.RETOOL);
					seqAct.setOldActivity(webConstants.RETOOL);
					seqAct.setLogonDate(rs.getTimestamp("StartDate"));
					seqAct.setOldLogonDate(rs.getTimestamp("StartDate"));
					seqAct.setLogoffDate(rs.getTimestamp("EndDate"));
					seqAct.setOldLogoffDate(rs.getTimestamp("EndDate"));
					// seqAct.setActivityRetoolNumber(rs.getInt("ActivityRetoolId"));
				}
				actList.add(seqAct);

			}
			// for the last rec
			if (seq != null) {
				// if(actStatFlag && activityList.contains(webConstants.SETUP))
				// {
				// seq.setSetupStatus(webConstants.STATUS_COMPLETE);
				// }
				seq.setQtyCompleted(totQtyCompleted);
				// seq.setEmployeeMap(employeeMap);
				seq.setAssetMap(assetMap);
				seq.setSeqActivityDetails(actList);
				sequences.put(prevSeq, seq);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Some SQL Exception occured" + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Some Exception occured" + e.getMessage());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			closeConnection(connection);
			dto.setSequences(sequences);
			// chking the values

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#fetchAssetDetails(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	@Override
	public void fetchAssetDetails(ManufacturingOrder dto, String qry)
			throws Exception {
		// get from dto asset map and iterate to get asset desc from db

		Sequence sequence = dto.getSelectedSequeuce();
		HashMap assetMap = sequence.getAssetMap();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();

			pstmt = connection.prepareStatement(qry);

			// iterate thro hashmap to get all assets and run the qry
			if (assetMap != null) {

				Set keySet = assetMap.keySet();
				Iterator iterator = null;

				for (iterator = keySet.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					if (assetMap.get(key) != null) {
						break;
					}
					pstmt.setString(1, dto.getFacility());
					pstmt.setString(2, key);
					pstmt.setString(3, sequence.getWorkCenterCode());
					rs = pstmt.executeQuery();

					while (rs.next()) {
						assetMap.put(key, rs.getString("AssetName"));

					}
					System.out.println("asset map:" + assetMap.size());
				}
			}

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#fetchEmployeeDetails(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	@Override
	public void fetchEmployeeDetails(ManufacturingOrder dto, String qry)
			throws Exception {
		// get from dto employee map and iterate to get asset desc from db
		Sequence seq = dto.getSelectedSequeuce();
		HashMap<String, String> employeeMap = new HashMap<String, String>();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry);

			// iterate thro hashmap to get all assets and run the qry

			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					employeeMap.put(rs.getString("EmployeeNumber"),
							rs.getString("Name"));
				}
				dto.setEmployeeMap(employeeMap);
			}

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getEmployeeLoginDetails(com.gavs
	 * .hishear.web.domain.ManufacturingOrder, java.lang.String)
	 */
	public void getEmployeeLoginDetails(ManufacturingOrder dto, String qry)
			throws Exception {
		// get from dto for which employee login details needs to be obtained

		Connection connection = null;
		ResultSet rs = null;

		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			pstmt.setString(1, seqAct.getEmployeeNumber());
			// need to set the date for which we need the kronos punch in & out
			pstmt.setString(2, EMPTY_STRING);
			rs = pstmt.executeQuery();

			if (rs != null) {
				if (rs.next()) {
					seqAct.setKronosPunchIn(rs.getDate(2));
					seqAct.setKronosPunchOut(rs.getDate(3));
				}
			}

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#validateEmployee(java.lang.String,
	 * java.lang.String)
	 */
	public String validateEmployee(String qry, String newEmployeeNumber)
			throws Exception {
		// get from dto employee map and iterate to get asset desc from db
		Connection connection = null;
		ResultSet rs = null;
		String name = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry);

			pstmt.setString(1, newEmployeeNumber);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				name = rs.getString("Name");
				if (name == null) {
					name = " ";
				}

			} else {
				name = null;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			closeConnection(connection);
		}

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getPauseReasons(java.lang.String)
	 */
	public ArrayList<MasterData> getPauseReasons(String qry) throws Exception {

		Connection connection = null;
		ResultSet rs = null;
		ArrayList al = new ArrayList();
		MasterData ms = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ms = new MasterData();
				ms.setCode(rs.getString("ExActivityCode"));
				ms.setDesc(rs.getString("Description"));
				al.add(ms);
			}
		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

		return al;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertActivityLog(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void insertActivityLog(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			pstmt = connection.prepareStatement(qry,
					Statement.RETURN_GENERATED_KEYS);

			// pstmt.setInt(1, seqAct.getActivityLogNumber()); // to do check
			// for activitylog number
			pstmt.setInt(1, seqAct.getJobActivityNumber());
			pstmt.setInt(2, seqAct.getQtyCompleted());
			pstmt.setTimestamp(3,
					new Timestamp(seqAct.getLogonDate().getTime()));
			pstmt.setTimestamp(4, new Timestamp(seqAct.getLogoffDate()
					.getTime()));

			System.out.println("Job activty no:"
					+ seqAct.getJobActivityNumber());
			pstmt.executeUpdate();

			// get the identity column and set it to seqact

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				seqAct.setActivityLogNumber(rs.getInt(1));
			}
			System.out.println("activty log no:"
					+ seqAct.getActivityLogNumber());

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateActivityLog(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void updateActivityLog(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			pstmt.setInt(1, seqAct.getJobActivityNumber());
			pstmt.setTimestamp(2,
					new Timestamp(seqAct.getLogonDate().getTime()));
			pstmt.setTimestamp(3, new Timestamp(seqAct.getLogoffDate()
					.getTime()));
			pstmt.setInt(4, seqAct.getQtyCompleted());
			pstmt.setInt(5, seqAct.getActivityLogNumber());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#deleteActivityLog(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void deleteActivityLog(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			pstmt.setInt(1, seqAct.getActivityLogNumber());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertActivityBreak(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void insertActivityBreak(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, seqAct.getJobActivityNumber());
			if (seqAct.getLogonDate() != null) {
				pstmt.setTimestamp(2, new Timestamp(seqAct.getLogonDate()
						.getTime()));
			} else {
				pstmt.setTimestamp(2, null);
			}

			if (seqAct.getLogoffDate() != null) {
				pstmt.setTimestamp(3, new Timestamp(seqAct.getLogoffDate()
						.getTime()));
			} else {
				pstmt.setTimestamp(3, null);
			}

			pstmt.setString(4, seqAct.getBreakActivityCode());
			pstmt.executeUpdate();

			// get the identity column and set it to seqact
			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				seqAct.setActivityBreakNumber(rs.getInt(1));
			}

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateActivityBreak(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void updateActivityBreak(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			// pstmt.setString(2, seqAct.getBreakActivityCode());
			if (seqAct.getLogonDate() != null) {
				pstmt.setTimestamp(1, new Timestamp(seqAct.getLogonDate()
						.getTime()));
			} else {
				pstmt.setTimestamp(1, null);
			}
			if (seqAct.getLogoffDate() != null) {
				pstmt.setTimestamp(2, new Timestamp(seqAct.getLogoffDate()
						.getTime()));
			} else {
				pstmt.setTimestamp(2, null);
			}
			pstmt.setString(3, seqAct.getBreakActivityCode());

			// Begin WO# 32731 - Error while pick operation - Ambrish - Infosys
			// - 18
			// July 2011

			// pstmt.setInt(4, seqAct.getJobActivityNumber());
			pstmt.setInt(4, seqAct.getActivityBreakNumber());

			// End WO# 32731 - Error while pick operation - Ambrish - Infosys -
			// 18
			// July 2011

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#deleteActivityBreak(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void deleteActivityBreak(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry);

			// pstmt.setInt(1, seqAct.getJobActivityNumber());
			pstmt.setInt(1, seqAct.getActivityBreakNumber());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertActivityRetool(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void insertActivityRetool(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry,
					Statement.RETURN_GENERATED_KEYS);
			System.out.println("job act no" + seqAct.getJobActivityNumber());
			pstmt.setInt(1, seqAct.getJobActivityNumber());
			pstmt.setTimestamp(2,
					new Timestamp(seqAct.getLogonDate().getTime()));
			pstmt.setTimestamp(3, new Timestamp(seqAct.getLogoffDate()
					.getTime()));

			pstmt.executeUpdate();
			System.out.println("activity retool update done");

			// get the identity column and set it to seqact
			rs = pstmt.getGeneratedKeys();

			System.out.println("getting generated keys");
			if (rs.next()) {
				seqAct.setActivityRetoolNumber(rs.getInt(1));
			}
			System.out.println("activity retool"
					+ seqAct.getActivityRetoolNumber());

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateActivityRetool(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void updateActivityRetool(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			if (seqAct.getLogonDate() != null) {
				pstmt.setTimestamp(1, new Timestamp(seqAct.getLogonDate()
						.getTime()));
			} else {
				pstmt.setTimestamp(1, null);
			}
			if (seqAct.getLogoffDate() != null) {
				pstmt.setTimestamp(2, new Timestamp(seqAct.getLogoffDate()
						.getTime()));
			} else {
				pstmt.setTimestamp(2, null);
			}

			pstmt.setInt(3, seqAct.getJobActivityNumber());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#deleteActivityRetool(com.gavs.hishear
	 * .web.domain.SequenceActivity, java.lang.String)
	 */
	public void deleteActivityRetool(SequenceActivity seqAct, String qry)
			throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			pstmt.setInt(1, seqAct.getActivityRetoolNumber());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateOrderDetail(com.gavs.hishear
	 * .web.domain.Sequence, java.lang.String)
	 */
	public void updateOrderDetail(Sequence seq, String qry) throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);
			SequenceActivity seqAct = seq.getSelectedSeqActivity();
			String status = null;
			if (seqAct.getCompleteSequenceFlag().equalsIgnoreCase(
					webConstants.SEQ_COMPLETED)) {
				status = webConstants.STATUS_COMPLETE;
			} else {
				status = webConstants.STATUS_INPROGRESS;
			}
			pstmt.setString(1, status);
			pstmt.setInt(2, seq.getJobNum());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getJobActivityNumber(com.gavs.hishear
	 * .web.domain.Sequence, java.lang.String)
	 */
	public boolean getJobActivityNumber(Sequence seq, String qry)
			throws Exception {
		boolean flag = false;
		System.out.println("getJobActivityNumber.......................qry = "
				+ qry);
		System.out.println("seq.getJobNum() = " + seq.getJobNum());

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SequenceActivity seqAct = seq.getSelectedSeqActivity();
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry);
			int index = 1;
			pstmt.setInt(index++, seq.getJobNum());

			System.out.println("seqAct.getEmployeeNumber() = "
					+ seqAct.getEmployeeNumber());
			if (seqAct.getActivity() != null
					&& (seqAct.getActivity().equalsIgnoreCase(
							webConstants.RETOOL) || seqAct.getActivity()
							.equalsIgnoreCase(webConstants.PAUSE))) {
				System.out.println("code = " + webConstants.RUN);
				pstmt.setString(index++, webConstants.RUN);
			} else {
				pstmt.setString(index++, seqAct.getActivity());
				System.out.println("code = " + seqAct.getActivity());
			}
			pstmt.setString(index++, seqAct.getEmployeeNumber());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seqAct.setJobActivityNumber(rs.getInt("JobActivityNumber"));
				seqAct.setActivityStatus(rs.getString("ActivityStatus"));
				System.out.println("seqAct.getJobActivityNumber() = "
						+ seqAct.getJobActivityNumber());
				flag = true;

			} else {
				flag = false;
			}

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			closeConnection(connection);
		}
		System.out
				.println("flag                                                                        = "
						+ flag);
		return flag;
	}

	// START Correction Interface Issue fix
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateSequenceActivity(com.gavs.
	 * hishear.web.domain.SequenceActivity, java.lang.String, java.lang.String)
	 */
	public void updateSequenceActivity(SequenceActivity seqAct, String qry,
			String status) throws Exception {
		// END Correction Interface Issue fix
		Connection connection = null;
		System.out
				.println("updateSequenceActivity.................................. qry "
						+ qry);
		System.out.println("seqAct.getAssetNumber() = "
				+ seqAct.getAssetNumber());
		System.out.println("seqAct.getActivityStatus() = "
				+ seqAct.getActivityStatus());
		System.out.println("seqAct.getJobActivityNumber() = "
				+ seqAct.getJobActivityNumber());

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			int index = 1;
			/*
			 * if (seqAct.isActChangeFlag() || seqAct.isNewFlag()) {
			 * pstmt.setString(index++, seqAct.getActivity()); }
			 */
			pstmt.setString(index++, seqAct.getAssetNumber());
			// START Correction Interface Issue fix
			pstmt.setString(index++, status); // to do understand what needs to
			// be put here
			// END Correction Interface Issue fix
			pstmt.setInt(index++, seqAct.getJobActivityNumber());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertOrderMaster(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	public void insertOrderMaster(ManufacturingOrder dto, String qry)
			throws Exception {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(qry);

			pstmt.setString(1, dto.getMoNumber());
			pstmt.setString(2, webConstants.LINE_NUMBER);
			pstmt.setString(3, dto.getFacility());
			pstmt.setString(4, webConstants.STATUS_INPROGRESS);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertOrderDetail(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	public void insertOrderDetail(ManufacturingOrder dto, String qry)
			throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry,
					Statement.RETURN_GENERATED_KEYS);

			Sequence seq = dto.getSelectedSequeuce();
			SequenceActivity seqAct = seq.getSelectedSeqActivity();

			pstmt.setString(1, dto.getMoNumber());
			pstmt.setString(2, webConstants.LINE_NUMBER);
			pstmt.setString(3, seq.getSequence());
			pstmt.setString(4, seq.getPartNumber());
			// pstmt.setDouble(6, seq.getOrderQty());
			if (seqAct.getCompleteSequenceFlag() != null
					&& seqAct.getCompleteSequenceFlag().equalsIgnoreCase(
							webConstants.SEQ_COMPLETED)) {
				pstmt.setString(5, webConstants.STATUS_COMPLETE);
			} else {
				pstmt.setString(5, webConstants.STATUS_INPROGRESS);
			}
			pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
			pstmt.setString(7, seq.getWorkCenterCode());
			pstmt.executeUpdate();

			// getting the job number - need to check it

			// get the identity column and set it to seqact
			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				seq.setJobNumber(rs.getInt(1));
			}
			// System.out.println("activity retool" + seqAct.get)

		} catch (SQLException se) {
			se.printStackTrace();
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();

			}
			closeConnection(connection);
		}

	}

	// START Correction Interface Issue fix
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#insertSequenceActivity(com.gavs.
	 * hishear.web.domain.Sequence, java.lang.String, java.lang.String)
	 */
	public void insertSequenceActivity(Sequence seq, String qry, String status)
			throws Exception {
		// END Correction Interface Issue fix
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(qry,
					Statement.RETURN_GENERATED_KEYS);

			SequenceActivity seqAct = seq.getSelectedSeqActivity();

			pstmt.setInt(1, seq.getJobNum()); // need to understand where to get
			// it
			if (seqAct.getActivity() != null
					&& (seqAct.getActivity().equalsIgnoreCase(
							webConstants.RETOOL) || seqAct.getActivity()
							.equalsIgnoreCase(webConstants.PAUSE))) {
				pstmt.setString(2, webConstants.RUN);
			} else {
				pstmt.setString(2, seqAct.getActivity());
			}
			pstmt.setString(3, seqAct.getEmployeeNumber());
			pstmt.setString(4, seqAct.getAssetNumber());
			// START Correction Interface Issue fix
			pstmt.setString(5, status);
			// END Correction Interface Issue fix
			pstmt.executeUpdate();

			// get the identity column and set it to seqact

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				seqAct.setJobActivityNumber(rs.getInt(1));
			}
		} catch (SQLException se) {
			throw new Exception(se.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#createCorrectionLog(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	public void createCorrectionLog(ManufacturingOrder dto, String query)
			throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, dto.getMoNumber());
			pstmt.setString(index++, seq.getSequence());
			pstmt.setString(index++, dto.getUserName());
			pstmt.setTimestamp(index++, new Timestamp(new Date().getTime()));
			pstmt.setString(index++, seqAct.getStampNo());
			if (!seqAct.isNewFlag()
					&& (seqAct.getEmployeeNumber() != null
							&& seqAct.getOldEmployeeNumber() != null && !seqAct
							.getEmployeeNumber().equalsIgnoreCase(
									seqAct.getOldEmployeeNumber()))) {
				pstmt.setString(index++, seqAct.getEmployeeNumber());
				pstmt.setString(index++, seqAct.getOldEmployeeNumber());
			} else if (seqAct.isNewFlag()) {
				pstmt.setString(index++, seqAct.getEmployeeNumber());
				pstmt.setString(index++, EMPTY_STRING);
			} else {
				pstmt.setString(index++, EMPTY_STRING);
				pstmt.setString(index++, EMPTY_STRING);
			}
			if (!seqAct.isNewFlag()
					&& (seqAct.getActivity() != null
							&& seqAct.getOldActivity() != null && !seqAct
							.getActivity().equalsIgnoreCase(
									seqAct.getOldActivity()))) {
				pstmt.setString(index++, seqAct.getActivity());
				pstmt.setString(index++, seqAct.getOldActivity());
			} else if (seqAct.isNewFlag()) {
				pstmt.setString(index++, seqAct.getActivity());
				pstmt.setString(index++, EMPTY_STRING);
			} else {
				pstmt.setString(index++, EMPTY_STRING);
				pstmt.setString(index++, EMPTY_STRING);
			}
			if (!seqAct.isNewFlag()
					&& (seqAct.getLogonDate() != null
							&& seqAct.getOldLogonDate() != null && !seqAct
							.getLogonDate().equals(seqAct.getOldLogonDate()))) {
				pstmt.setTimestamp(index++, new Timestamp(seqAct.getLogonDate()
						.getTime()));
				pstmt.setTimestamp(index++, new Timestamp(seqAct
						.getOldLogonDate().getTime()));
			} else if (seqAct.isNewFlag() && seqAct.getLogonDate() != null) {
				pstmt.setTimestamp(index++, new Timestamp(seqAct.getLogonDate()
						.getTime()));
				pstmt.setTimestamp(index++, null);
			} else {
				pstmt.setTimestamp(index++, null);
				pstmt.setTimestamp(index++, null);
			}

			if (!seqAct.isNewFlag()
					&& (seqAct.getLogoffDate() != null
							&& seqAct.getOldLogoffDate() != null && !seqAct
							.getLogoffDate().equals(seqAct.getOldLogoffDate()))) {
				pstmt.setTimestamp(index++, new Timestamp(seqAct
						.getLogoffDate().getTime()));
				pstmt.setTimestamp(index++, new Timestamp(seqAct
						.getOldLogoffDate().getTime()));
			} else if (seqAct.isNewFlag() && seqAct.getLogoffDate() != null) {
				pstmt.setTimestamp(index++, new Timestamp(seqAct
						.getLogoffDate().getTime()));
				pstmt.setTimestamp(index++, null);
			} else {
				pstmt.setTimestamp(index++, null);
				pstmt.setTimestamp(index++, null);
			}

			if (!seqAct.isNewFlag()
					&& (seqAct.getQtyCompleted() != seqAct.getOldQtyCompleted())) {
				pstmt.setInt(index++, seqAct.getQtyCompleted());
				pstmt.setInt(index++, seqAct.getOldQtyCompleted());
			} else if (seqAct.isNewFlag()) {
				pstmt.setInt(index++, seqAct.getQtyCompleted());
				pstmt.setInt(index++, 0);
			} else {
				pstmt.setInt(index++, 0);
				pstmt.setInt(index++, 0);
			}

			if (!seqAct.isNewFlag()
					&& (seqAct.getAssetNumber() != null
							&& seqAct.getOldAssetNumber() != null && !seqAct
							.getAssetNumber().equalsIgnoreCase(
									seqAct.getOldAssetNumber()))) {
				pstmt.setString(index++, seqAct.getAssetNumber());
				pstmt.setString(index++, seqAct.getOldAssetNumber());

			} else if (seqAct.isNewFlag()) {
				pstmt.setString(index++, seqAct.getAssetNumber());
				pstmt.setString(index++, EMPTY_STRING);
			} else {
				pstmt.setString(index++, EMPTY_STRING);
				pstmt.setString(index++, EMPTY_STRING);
			}

			pstmt.setString(index++, seqAct.getCompleteSequenceFlag());

			if (seqAct.getRouterDate() != null) {
				pstmt.setTimestamp(index++, new Timestamp(seqAct
						.getRouterDate().getTime()));
			} else {
				pstmt.setTimestamp(index++, null);
			}

			if (seqAct.isNewFlag()) {
				pstmt.setString(index++, webConstants.ADDED_RECORD);
			} else {
				pstmt.setString(index++, null);
			}

			pstmt.setString(index++, seqAct.getMultipleCorrectionFlag());

			System.out.println("seq.getOldSequenceStatus() = "
					+ seq.getOldSequenceStatus());
			String oldSeqStatus = "0";
			if (seq.getOldSequenceStatus() != null) {
				if ("C".equalsIgnoreCase(seq.getOldSequenceStatus())) {
					oldSeqStatus = ONE;
				} else {
					oldSeqStatus = "0";
				}
			}
			pstmt.setString(index++, oldSeqStatus);

			System.out.println("index:" + index);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			closeConnection(connection);
		}

	}

	/**
	 * Fetch the Department Based on the Workcenter and Facility.
	 * 
	 * @param facility
	 *            the facility
	 * @param workCenter
	 *            the work center
	 * @return the dept by work center
	 * @throws Exception
	 *             the exception
	 */
	public WorkCenter getDeptByWorkCenter(String facility, String workCenter)
			throws Exception {

		M3APIParameter parameter = new M3APIParameter();

		parameter.setWebServiceName("PMZ085MIRead");
		parameter.setInputItem("GetDEPTByPLGRItem");
		parameter.setOutputItem("GetDEPTByPLGRResponseItem");
		parameter.setFunctionName("getDEPTByPLGR");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		map.put(M3Parameter.FACI.getValue(), facility);
		map.put(M3Parameter.PLGR.getValue(), workCenter);
		parameter.setInputData(map);

		WorkCenter department = null;
		try {
			department = (WorkCenter) m3APIWSClient.callM3API(parameter);

		} catch (Exception e) {
			System.out.println("e.getMessage() = " + e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}

		return department;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getEmployeeKronosLogonByLogoutDate
	 * (com.gavs.hishear.web.domain.ManufacturingOrder, java.lang.String)
	 */
	public Date[] getEmployeeKronosLogonByLogoutDate(ManufacturingOrder dto,
			String query) {
		try {
			Sequence seq = dto.getSelectedSequeuce();
			SequenceActivity seqAct = seq.getSelectedSeqActivity();
			String logoffDate = DateUtil.uiDateFormat(seqAct.getLogoffDate(),
					"MM/dd/yyyy");
			return (Date[]) jdbcTemplate.queryForObject(query, new Object[] {
					seqAct.getEmployeeNumber(), logoffDate }, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					Date[] kronosPunchInPunchOut = new Date[2];
					Date punchInRounded = rs.getTimestamp("PunchInRounded");
					Date punchOutRounded = rs.getTimestamp("PunchOutRounded");
					kronosPunchInPunchOut[0] = punchInRounded;
					kronosPunchInPunchOut[1] = punchOutRounded;
					return kronosPunchInPunchOut;
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getEmployeeKronosLogonByLoginDate
	 * (com.gavs.hishear.web.domain.ManufacturingOrder, java.lang.String)
	 */
	@Override
	public Date[] getEmployeeKronosLogonByLoginDate(ManufacturingOrder dto,
			String query) {
		try {
			Sequence seq = dto.getSelectedSequeuce();
			SequenceActivity seqAct = seq.getSelectedSeqActivity();
			System.out.println("getEmployeeKronosLogonByLoginDate = ");
			System.out.println("seqAct.getEmployeeNumber() = "
					+ seqAct.getEmployeeNumber());
			System.out.println("seqAct.getLogonDate() = "
					+ seqAct.getLogonDate());
			String logonDate = DateUtil.uiDateFormat(seqAct.getLogonDate(),
					"MM/dd/yyyy");
			System.out.println("logoffDate = " + logonDate);

			return (Date[]) jdbcTemplate.queryForObject(query, new Object[] {
					seqAct.getEmployeeNumber(), logonDate }, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					Date[] kronosPunchInPunchOut = new Date[2];
					Date punchInRounded = rs.getTimestamp("PunchInRounded");
					Date punchOutRounded = rs.getTimestamp("PunchOutRounded");
					kronosPunchInPunchOut[0] = punchInRounded;
					kronosPunchInPunchOut[1] = punchOutRounded;
					return kronosPunchInPunchOut;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getEmployeeKronosLogonLatest(com
	 * .gavs.hishear.web.domain.ManufacturingOrder, java.lang.String)
	 */
	@Override
	public Date[] getEmployeeKronosLogonLatest(ManufacturingOrder dto,
			String query) {
		try {
			Sequence seq = dto.getSelectedSequeuce();
			SequenceActivity seqAct = seq.getSelectedSeqActivity();
			System.out.println("getEmployeeKronosLogonLatest = ");
			System.out.println("seqAct.getEmployeeNumber() = "
					+ seqAct.getEmployeeNumber());

			return (Date[]) jdbcTemplate.queryForObject(query,
					new Object[] { seqAct.getEmployeeNumber() },
					new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Date[] kronosPunchInPunchOut = new Date[2];
							Date punchInRounded = rs
									.getTimestamp("PunchInRounded");
							Date punchOutRounded = rs
									.getTimestamp("PunchOutRounded");
							kronosPunchInPunchOut[0] = punchInRounded;
							kronosPunchInPunchOut[1] = punchOutRounded;
							return kronosPunchInPunchOut;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#updateActivityCompleteStatus(java
	 * .lang.String, int)
	 */
	@Override
	public void updateActivityCompleteStatus(String query, int jobNumber) {
		try {
			jdbcTemplate.update(query, new Object[] { jobNumber });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#isPPHErrorExist(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean isPPHErrorExist(String query, String moNumber) {
		System.out.println("isPPHErrorExist............................");
		System.out.println("moNumber = " + moNumber);
		System.out.println("query = " + query);
		int pphErrorCount = 0;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, moNumber);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pphErrorCount = rs.getInt("PPHError");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		System.out.println("pphErrorCount = " + pphErrorCount);
		if (pphErrorCount > 0) {
			return true;
		}
		return false;
	}

	/* START. Sharepoint item 1441. Reporting of Setup and Run Times Incorrect */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getWorkCenterCapacity(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public int getWorkCenterCapacity(String workCenterNumber, String moNumber)
			throws Exception {
		System.out.println("getWorkCenterCapacity................");
		System.out.println("workCenterNumber = " + workCenterNumber);
		System.out.println("moNumber = " + moNumber);
		int workCenterCapacity = 0;
		if (workCenterNumber != null) {
			MVXDTAMI mvxdtami = null;
			try {
				mvxdtami = getMODetailsFromM3(moNumber);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Unable to get Facility for this operation");
			}
			if (mvxdtami != null && mvxdtami.getFacility() != null) {
				try {
					WorkCenter workCenter = getDeptByWorkCenter(
							mvxdtami.getFacility(), workCenterNumber);
					workCenterCapacity = workCenter.getCapacity();
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(
							"Unable to get WOrkcenter Capacity for this Workcenter : "
									+ workCenterNumber);
				}
			} else {
				throw new Exception("Unable to get Facility for this operation");
			}
		} else {
			throw new Exception(
					"Unable to get Workcenter Number for this operation");
		}
		System.out.println("workCenterCapacity = " + workCenterCapacity);
		return workCenterCapacity;
	}

	/**
	 * Sets the reporting values.
	 * 
	 * @param workCenterCapacity
	 *            the work center capacity
	 * @param priceTimeQuantity
	 *            the price time quantity
	 * @param activityCode
	 *            the activity code
	 * @param timeToReport
	 *            the time to report
	 * @param weightToReport
	 *            the weight to report
	 * @param isReversal
	 *            the is reversal
	 * @param map
	 *            the map
	 */
	private void setReportingValues(int workCenterCapacity,
			double priceTimeQuantity, String activityCode, double timeToReport,
			double weightToReport, boolean isReversal, HashMap map) {
		System.out.println("---------------------------------");
		System.out.println("workCenterCapacity = " + workCenterCapacity);
		System.out.println("priceTimeQuantity = " + priceTimeQuantity);
		System.out.println("activityCode = " + activityCode);
		System.out.println("timeToReport = " + timeToReport);
		System.out.println("weightToReport = " + weightToReport);
		System.out.println("isReversal = " + isReversal);
		System.out.println("---------------------------------");

		String UMAS = EMPTY_STRING;
		String UMAT = EMPTY_STRING;
		String UPIT = EMPTY_STRING;
		String USET = EMPTY_STRING;

		if (WorkCenterCapacity.MACHINE == workCenterCapacity) { // PCAP = 1
			if (priceTimeQuantity == PriceTimeQuantity.HOURS
					|| priceTimeQuantity == PriceTimeQuantity.PIECE) { // CTCD
				// =//
				// 1000//
				// or 1
				if (ActivityCode.RUN.equals(activityCode)) { // UPIT and UMAT
					UMAT = setValueByReversalFlag(timeToReport, isReversal);
					UPIT = setValueByReversalFlag(timeToReport, isReversal);
				} else if (ActivityCode.SETUP.equals(activityCode)) {
					USET = setValueByReversalFlag(timeToReport, isReversal);
				}
			} else if (priceTimeQuantity == PriceTimeQuantity.WEIGHT) { // CTCD//
				// =
				// 100//
				// ////
				// Report//
				// Weight
				if (ActivityCode.RUN.equals(activityCode)) { // UPIT and UMAT
					// =// 0
					UMAT = "0.0";
					UPIT = setValueByReversalFlag(weightToReport, isReversal);
				}
			}
		} else if (WorkCenterCapacity.LABOR == workCenterCapacity) {// PCAP = 2
			if (ActivityCode.RUN.equals(activityCode)) {
				UMAT = setValueByReversalFlag(timeToReport, isReversal);
			} else if (ActivityCode.SETUP.equals(activityCode)) {
				UMAS = setValueByReversalFlag(timeToReport, isReversal);
			}
		}

		System.out.println("UMAS = " + UMAS);
		System.out.println("UMAT = " + UMAT);
		System.out.println("UPIT = " + UPIT);
		System.out.println("USET = " + USET);

		if (!EMPTY_STRING.equals(UMAS)) {
			map.put(M3Parameter.UMAS.getValue(), UMAS);
		}
		if (!EMPTY_STRING.equals(UMAT)) {
			map.put(M3Parameter.UMAT.getValue(), UMAT);
		}
		if (!EMPTY_STRING.equals(UPIT)) {
			map.put(M3Parameter.UPIT.getValue(), UPIT);
		}
		if (!EMPTY_STRING.equals(USET)) {
			map.put(M3Parameter.USET.getValue(), USET);
		}

	}

	/**
	 * Sets the value by reversal flag.
	 * 
	 * @param dblValue
	 *            the dbl value
	 * @param isReversal
	 *            the is reversal
	 * @return the string
	 */
	private String setValueByReversalFlag(double dblValue, boolean isReversal) {
		java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
				"0.##");
		if (isReversal) {
			return decimalFormat.format(-dblValue);
		} else {
			return decimalFormat.format(dblValue);
		}
	}

	/* END. Sharepoint item 1441. Reporting of Setup and Run Times Incorrect */

	/* START. Capture M3 Update Requests */
	/**
	 * Insert m3 request.
	 * 
	 * @param m3APIParameter
	 *            the m3 api parameter
	 * @param userName
	 *            the user name
	 */
	public void insertM3Request(M3APIParameter m3APIParameter, String userName,
			String query) {
		try {
			String[] headersAndValues = parseM3RequestData(m3APIParameter
					.getInputData());
			if (m3APIParameter != null) {
				this.jdbcTemplate.update(
						query,
						new Object[] { m3APIParameter.getWebServiceName(),
								headersAndValues[0], headersAndValues[1],
								m3APIParameter.getInputItem(),
								m3APIParameter.getOutputItem(),
								m3APIParameter.getFunctionName(), EMPTY_STRING,
								EMPTY_STRING,
								new java.sql.Timestamp(new Date().getTime()),
								userName });
			}
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert m3 request.
	 * 
	 * @param m3Program
	 *            the m3 program
	 * @param userName
	 *            the user name
	 */
	public void insertM3Request(Program m3Program, String userName,
			String m3RequestQuery) {
		/*
		 * String query =
		 * "insert into dbo.SFS_M3RequestLog (WebServiceName,ColumnHeaders,ColumnValues,InputItem,OutputItem,"
		 * +
		 * " FunctionName,M3FunctionName,SubFunctionName,DateProcessed,LoginUserID) "
		 * + " VALUES (?,?,?,?,?,?,?,?,?,?)";
		 */
		String query = m3RequestQuery;
		try {
			String[] headersAndValues = parseM3RequestData(m3Program
					.getInputData());
			if (m3Program != null) {

				String subFunctionName = EMPTY_STRING;
				if (m3Program.getSubPrograms() != null
						&& m3Program.getSubPrograms().size() > 0) {
					Program subProgram = (Program) m3Program.getSubPrograms()
							.get(0);
					if (subProgram != null
							&& subProgram.getM3Function() != null) {
						subFunctionName = subProgram.getM3Function();
					}
				}
				m3Program.getSubPrograms();
				this.jdbcTemplate.update(
						query,
						new Object[] { m3Program.getWebServiceName(),
								headersAndValues[0], headersAndValues[1],
								EMPTY_STRING, EMPTY_STRING,
								m3Program.getFunctionName(),
								m3Program.getM3Function(), subFunctionName,
								new java.sql.Timestamp(new Date().getTime()),
								userName, "SFA" });
			}
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the m3 request data.
	 * 
	 * @param inputData
	 *            the input data
	 * @return the string[]
	 */
	private String[] parseM3RequestData(HashMap inputData) {
		String[] headersAndValues = new String[2];
		if (inputData != null) {
			Set keys = inputData.keySet();
			ArrayList<String> columnHeaders = new ArrayList<String>();
			ArrayList<String> columnValues = new ArrayList<String>();
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String value = EMPTY_STRING;
				try {
					value = (String) inputData.get(key);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				columnHeaders.add(key);
				columnValues.add(value);
			}
			headersAndValues[0] = org.springframework.util.StringUtils
					.collectionToCommaDelimitedString(columnHeaders);
			headersAndValues[1] = org.springframework.util.StringUtils
					.collectionToCommaDelimitedString(columnValues);
		}
		return headersAndValues;
	}

	/* END. Capture M3 Update Requests */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getBreakTime(java.lang.String,
	 * java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public float getBreakTime(String query, String actNo, Date logonDate,
			Date logoffDate) {
		return ((Float) this.jdbcTemplate.queryForObject(query, new Object[] {
				actNo, logonDate, logoffDate }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getFloat("TotalBreakSeconds");
			}
		})).floatValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getRetoolTime(java.lang.String,
	 * java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public float getRetoolTime(String query, String actNo, Date logonDate,
			Date logoffDate) {
		return ((Float) this.jdbcTemplate.queryForObject(query, new Object[] {
				actNo, logonDate, logoffDate }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getFloat("TotalRetoolSeconds");
			}
		})).floatValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getOperationDetails(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	public List<Sequence> getOperationDetails(String MONumber,
			String operationNumber, String query) {
		List sequences = null;
		try {
			sequences = this.jdbcTemplate.query(query, new Object[] { MONumber,
					operationNumber }, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					Sequence seq = new Sequence();
					seq.setSequenceStatus(rs.getString("SequenceStatus"));
					seq.setQtyCompleted(rs.getDouble("QtyCompleted"));
					seq.setWorkCenterCode(rs.getString("WorkCenter"));
					seq.setCostCenter(rs.getString("CostCenter"));
					seq.setEmployeeName(rs.getString("EmployeeName"));
					return seq;
				}
			});

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sequences;
	}

	/**
	 * * Method to get the PO Number and PO Line Number for the given MO,
	 * Operation.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param operationNumber
	 *            the operation number
	 * @return the purchase order details using mvxdtami
	 * @throws Exception
	 *             the exception
	 */
	public MVXDTAMI getPurchaseOrderDetailsUsingMVXDTAMI(String moNumber,
			String operationNumber) throws Exception {
		M3APIParameter parameter = new M3APIParameter();

		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMPLINEX3Item");
		parameter.setOutputItem("GETMPLINEX3ResponseItem");
		parameter.setFunctionName("getMPLinex3");

		HashMap<String, String> map = new HashMap<String, String>();

		map.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		map.put(M3Parameter.RORC.getValue(), ONE);
		map.put(M3Parameter.RORL.getValue(), operationNumber);
		map.put(M3Parameter.RORN.getValue(), moNumber);

		parameter.setInputData(map);
		MVXDTAMI mvxdtami;
		try {
			mvxdtami = (MVXDTAMI) m3APIWSClient.callM3API(parameter);
		} catch (Exception e) {
			throw new Exception(
					"Purchase order not created for this Manufacturing Order");
		}
		return mvxdtami;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getPOTransactions(java.lang.String)
	 */
	public List<POTransaction> getPOTransactions(String poNumber)
			throws Exception {
		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setFunctionName("getLSEMPLINDX1");
		parameter.setInputItem("LSEMPLINDX1Item");
		parameter.setOutputItem("LSEMPLINDX1ResponseItem");

		HashMap<String, String> inputDataMap = new HashMap<String, String>();
		inputDataMap.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		inputDataMap.put(M3Parameter.PUNO.getValue(), poNumber);

		parameter.setInputData(inputDataMap);
		try {
			return (List<POTransaction>) m3APIWSClient.callM3API(parameter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#reversePOLineTransaction(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void reversePOLineTransaction(String warehouse, String poNumber,
			String poLineNumber, String poSubLineNumber, String poStatus,
			String poLineSuffix) throws Exception {
		M3APIParameter parameter = new M3APIParameter();
		HashMap<String, String> inputDataMap = new HashMap<String, String>();

		inputDataMap.put(M3Parameter.CONO.getValue(),
				applicationPropertyBean.getCompany());
		inputDataMap.put(M3Parameter.WHLO.getValue(), warehouse);
		inputDataMap.put(M3Parameter.PUNO.getValue(), poNumber);
		inputDataMap.put(M3Parameter.PNLI.getValue(), poLineNumber);
		inputDataMap.put(M3Parameter.PNLS.getValue(), poSubLineNumber);
		inputDataMap.put(M3Parameter.PUOS.getValue(), String.valueOf(poStatus));
		inputDataMap.put(M3Parameter.PNLX.getValue(), poLineSuffix);

		parameter.setWebServiceName("PPZ330MIWrite");
		parameter.setFunctionName("delPOLineTrans");
		parameter.setInputItem("DelPOLineTransItem");
		parameter.setOutputItem("DelPOLineTransResponseItem");
		parameter.setInputData(inputDataMap);

		try {
			m3APIWSClient.callM3API(parameter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#deleteSFSSequence(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void deleteSFSSequence(String moNumber, String sequenceNumber,
			String query) {
		ArrayList param = new ArrayList();
		param.add(moNumber);
		param.add(sequenceNumber);
		this.jdbcTemplate.update(query, param.toArray());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.SequenceDAO#getBreakTime(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public float getBreakTime(String username, String moNumber,
			String sequenceNumber, String query) {
		return ((Float) this.jdbcTemplate.queryForObject(query, new Object[] {
				moNumber, sequenceNumber, username }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getFloat("TotalBreakSeconds");
			}
		})).floatValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.SequenceDAO#getOrderDetails(com.gavs.hishear
	 * .web.domain.ManufacturingOrder, java.lang.String)
	 */
	@Override
	public ManufacturingOrder getOrderDetails(ManufacturingOrder dto, String qry) {
		Sequence seq = dto.getSelectedSequeuce();
		ManufacturingOrder manufacturingOrder = null;
		try {
			manufacturingOrder = (ManufacturingOrder) this.jdbcTemplate
					.queryForObject(qry, new Object[] { dto.getMoNumber(),
							webConstants.LINE_NUMBER, seq.getSequence() },
							new RowMapper() {
								public Object mapRow(ResultSet rs, int arg1)
										throws SQLException {
									ManufacturingOrder manufacturingOrder = new ManufacturingOrder();
									manufacturingOrder.setMoNumber(rs
											.getString("MONumber"));
									manufacturingOrder.setJobNumber(rs
											.getInt("JobNumber"));
									return manufacturingOrder;
								}
							});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return manufacturingOrder;
	}

}