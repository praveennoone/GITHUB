// File:         FactoryDaoImpl.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.factory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.util.HtmlUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.StandardTime;
import com.gavs.hishear.web.domain.TestMethods;
import com.gavs.hishear.web.reports.factory.domain.FactoryDto;
import com.gavs.hishear.util.Util;


/**
 * The Class FactoryDaoImpl.
 * 
 */
public class FactoryDaoImpl implements FactoryDao {

	/** Data Source to use. */
	private DataSource dataSource;

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The user context. */
	private UserContext userContext;

	/**
	 * Get the Connection.
	 * 
	 * @return Connection
	 * @throws SQLException
	 *             the sQL exception
	 */

	private static final Logger logger = Logger.getLogger(FactoryDao.class);

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
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getShiftTime(com.
	 * gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getShiftTime(UserContext objUserContext) {
		ArrayList arrShiftTime = new ArrayList();

		System.out.print("/n/n Query :"
				+ objUserContext.getQueries().get("SHOPR_045"));

		final SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yy");
		final SimpleDateFormat format2 = new SimpleDateFormat(
				"MM/dd/yy hh:mm aa");
		final String currentDateTime = format1.format(new Date());
		final Calendar from = Calendar.getInstance();
		final Calendar to = Calendar.getInstance();

		arrShiftTime = (ArrayList) this.jdbcTemplate.query(
				(String) objUserContext.getQueries().get("SHOPR_045"),
				new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						FactoryDto objFactoryDto = new FactoryDto();

						objFactoryDto.setShiftStartTime(rs
								.getString("ShiftStartTime"));
						objFactoryDto.setShiftEndTime(rs
								.getString("ShiftEndTime"));
						objFactoryDto.setShift(rs.getString("ShiftCode"));

						String fromDate = currentDateTime + " "
								+ objFactoryDto.getShiftStartTime();
						String toDate = currentDateTime + " "
								+ objFactoryDto.getShiftEndTime();
						try {
							from.setTime(format2.parse(fromDate));
							to.setTime(format2.parse(toDate));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}

						if (to.before(from)) {
							objFactoryDto.setDateShiftRequired(true);
						}

						return objFactoryDto;

					}

				});

		return arrShiftTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getAssetReport(com
	 * .gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getAssetReport(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}
		System.out.println("factory--->" + factory);
		String query = report.getQuery();

		ArrayList reports = new ArrayList();

		reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
				report.getFromDate(), report.getToDate(), report.getFromDate(),
				report.getToDate(), report.getDepartmentId(),
				report.getDepartmentId(), factory }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				FactoryDto objFactoryDto = new FactoryDto();
				objFactoryDto.setAssetNumber(rs.getString("AssetNumber"));
				String assetDescription = rs.getString("Description");
				assetDescription = assetDescription.replaceAll("&", "&amp;");
				objFactoryDto.setAssetDesc(assetDescription);
				return objFactoryDto;

			}
		});
		return reports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getFactorySummery
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto,
	 * com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getFactorySummery(FactoryDto managementalDto,
			FactoryDto ObjFactoryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getDepartmentDetails
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getDepartmentDetails(FactoryDto ObjFactoryDto) {

		String query = ObjFactoryDto.getQuery();

		ArrayList reports = new ArrayList();

		reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
				ObjFactoryDto.getFactory(), ObjFactoryDto.getFactory() },
				new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						FactoryDto objFactoryDto = new FactoryDto();
						objFactoryDto.setDepartmentId(rs
								.getString("Department"));
						objFactoryDto.setDepartment(HtmlUtils.htmlEscape(rs
								.getString("DeptName")));
						return objFactoryDto;

					}
				});
		return reports;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getFactoryDetails
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getFactoryDetails(FactoryDto ObjFactoryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getAssetCountForDept
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public String getAssetCountForDept(FactoryDto report) {

		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		String query = report.getQuery();

		String assetCount = null;

		assetCount = (String) this.jdbcTemplate.queryForObject(query,
				new Object[] { report.getFromDate(), report.getToDate(),
						report.getFromDate(), report.getToDate(),
						report.getDepartmentId(), report.getDepartmentId(),
						factory }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("AssetCount");
					}
				});
		System.out.println("Assetcount for Dept " + report.getDepartmentId()
				+ ": " + assetCount);
		return assetCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getOperatorsSummary
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getOperatorsSummary(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}
		String query = report.getQuery();

		ArrayList reports = new ArrayList();

		reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
				report.getFromDate(), report.getToDate(), report.getFromDate(),
				report.getToDate(), report.getDepartmentId(),
				report.getDepartmentId(), factory }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				FactoryDto objFactoryDto = new FactoryDto();
				objFactoryDto.setUserName(rs.getString("EmployeeNumber"));
				objFactoryDto.setDisplayName(rs.getString("EmployeeName"));
				objFactoryDto.setMoNumber(rs.getString("MONumber"));
				objFactoryDto.setAssetNumber(rs.getString("AssetNumber"));
				return objFactoryDto;

			}
		});
		return reports;
	}

	// Data
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gavs.hishear.web.reports.factory.dao.FactoryDao#
	 * getJobsCurrentlyProcessingReport
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getJobsCurrentlyProcessingReport(FactoryDto report) {

		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		String query = report.getQuery();

		ArrayList reports = new ArrayList();

		reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
				report.getFromDate(), report.getToDate(), report.getFromDate(),
				report.getToDate(), report.getDepartmentId(),
				report.getDepartmentId(), factory }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				FactoryDto objFactoryDto = new FactoryDto();
				objFactoryDto.setMoNumber(HtmlUtils.htmlEscape(rs
						.getString("MONumber")));
				objFactoryDto.setPartNumber(HtmlUtils.htmlEscape(rs
						.getString("ItemNumber")));
				objFactoryDto.setPartDescription(HtmlUtils.htmlEscape(rs
						.getString("ItemDescription")));
				objFactoryDto.setQuantity(HtmlUtils.htmlEscape(rs
						.getString("ItemOrderedQuantity")));
				objFactoryDto.setQtyProcessed(HtmlUtils.htmlEscape(rs
						.getString("QtyProcessed")));
				objFactoryDto.setQtyPending(HtmlUtils.htmlEscape(rs
						.getString("QtyPending")));
				objFactoryDto.setQtyRejected(HtmlUtils.htmlEscape(rs
						.getString("QtyRejected")));
				objFactoryDto.setYield(HtmlUtils.htmlEscape(rs
						.getString("Yield")));
				return objFactoryDto;

			}
		});
		return reports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getPartsProcessedSummary
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getPartsProcessedSummary(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}
		String query = report.getQuery();
		ArrayList reports = new ArrayList();
		reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
				report.getDepartmentId(), report.getDepartmentId(),
				report.getFromDate(), report.getToDate(), report.getFromDate(),
				report.getToDate(), report.getDepartmentId(),
				report.getDepartmentId(), factory }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				FactoryDto objFactoryDto = new FactoryDto();
				objFactoryDto.setUserName(rs.getString("EmployeeNumber"));
				objFactoryDto.setDisplayName(rs.getString("EmployeeName"));
				objFactoryDto.setMoNumber(rs.getString("MONumber"));
				objFactoryDto.setAssetNumber(rs.getString("AssetNumber"));
				objFactoryDto.setQtyProcessed(rs.getString("QtyProcessed"));
				return objFactoryDto;
			}
		});
		return reports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getPartsRejectedSummary
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getPartsRejectedSummary(FactoryDto report) {
		ArrayList reports = new ArrayList();
		FactoryDto objFactoryDto = null;

		for (int i = 0; i < 20; i++) {
			objFactoryDto = new FactoryDto();
			objFactoryDto.setUserName("008704");
			objFactoryDto.setDisplayName("Cynthia Jiminez");
			objFactoryDto.setMoNumber("HS180796");
			objFactoryDto.setAssetNumber("5037");
			objFactoryDto.setQtyRejected("5000");
			reports.add(objFactoryDto);
		}
		return reports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getYieldSummary(com
	 * .gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getYieldSummary(FactoryDto report) {
		String query = "{call dbo.SP_GetProductionReportData (?,?,?,?,?,?,?)}";
		ArrayList reports = new ArrayList();
		Connection connection = null;

		try {
			reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
					report.getDepartmentId(), report.getFromDate(),
					report.getToDate(), "", "", "", "" }, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {

					FactoryDto objmanagementalDto = new FactoryDto();

					objmanagementalDto.setDisplayName(rs
							.getString("EmployeeName"));

					objmanagementalDto.setMoNumber(rs.getString("MONumber"));
					objmanagementalDto
							.setPartNumber(rs.getString("ItemNumber"));

					objmanagementalDto.setAssetNumber(rs
							.getString("AssetNumber"));
					String assetDesc = HtmlUtils.htmlEscape(rs
							.getString("Description"));
					objmanagementalDto.setAssetDesc(StringUtils
							.defaultString(assetDesc));

					objmanagementalDto.setSetup(Util.getActualHrs(rs
							.getString("SetupMins")));
					float setupMinutes = Float.parseFloat(rs
							.getString("SetupMins"));
					float retoolMinutes = Float.parseFloat(rs
							.getString("RetoolMins"));
					float runMinutes = Float
							.parseFloat(rs.getString("RunMins"))
							- retoolMinutes;
					if (runMinutes < 0) {
						runMinutes = 0;
					}
					objmanagementalDto.setRun(Util
							.getActualHrs("" + runMinutes));

					objmanagementalDto.setRetool(Util.getActualHrs(rs
							.getString("RetoolMins")));
					objmanagementalDto.setUserName(rs
							.getString("EmployeeNumber"));
					objmanagementalDto
							.setLineNumber(rs.getString("LineNumber"));
					objmanagementalDto.setPartDescription(HtmlUtils
							.htmlEscape(rs.getString("ItemDescription")));
					objmanagementalDto.setQuantity(""
							+ rs.getInt("QtyCompleted"));
					float TotHrs = setupMinutes + runMinutes + retoolMinutes;
					objmanagementalDto.setTotalHours(Util.getActualHrs(String
							.valueOf(TotHrs)));
					return objmanagementalDto;
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return reports;
	}

	// Data
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gavs.hishear.web.reports.factory.dao.FactoryDao#
	 * getJobsCurrentlyPendingSummary
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public ArrayList getJobsCurrentlyPendingSummary(FactoryDto report) {

		String query = report.getQuery();
		ArrayList reports = new ArrayList();
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		reports = (ArrayList) this.jdbcTemplate.query(query, new Object[] {
				report.getDepartmentId(), report.getDepartmentId(),
				report.getFromDate(), report.getToDate(), report.getFromDate(),
				report.getToDate(), report.getDepartmentId(),
				report.getDepartmentId(), factory }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				FactoryDto objFactoryDto = new FactoryDto();
				objFactoryDto.setMoNumber(rs.getString("MONumber"));
				objFactoryDto.setPartNumber(rs.getString("ItemNumber"));
				objFactoryDto.setPartDescription(HtmlUtils.htmlEscape(rs
						.getString("ItemDescription")));
				objFactoryDto.setQuantity(rs.getString("ItemOrderedQuantity"));
				objFactoryDto.setDateQueued(rs.getString("DateQueued"));
				objFactoryDto.setDuration(rs.getString("Duration"));
				return objFactoryDto;
			}
		});
		return reports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getOperatorCountForDept
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public String getOperatorCountForDept(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		String query = report.getQuery();
		String operatorCount = null;

		operatorCount = (String) this.jdbcTemplate.queryForObject(query,
				new Object[] { report.getFromDate(), report.getToDate(),
						report.getFromDate(), report.getToDate(),
						report.getDepartmentId(), report.getDepartmentId(),
						factory }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("OperatorCount");
					}
				});

		return operatorCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gavs.hishear.web.reports.factory.dao.FactoryDao#
	 * getJobCurrentlyProcessingForDept
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public String getJobCurrentlyProcessingForDept(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		String query = report.getQuery();
		String currentlyProcessingJobsCount = null;

		currentlyProcessingJobsCount = (String) this.jdbcTemplate
				.queryForObject(query, new Object[] { report.getFromDate(),
						report.getToDate(), report.getFromDate(),
						report.getToDate(), report.getDepartmentId(),
						report.getDepartmentId(), factory }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("CurrentlyProcessingJobsCount");
					}
				});

		return currentlyProcessingJobsCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gavs.hishear.web.reports.factory.dao.FactoryDao#
	 * getJobsCurrentlyPendingForDept
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public String getJobsCurrentlyPendingForDept(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		String query = report.getQuery();
		String currentlyPendingJobsCount = null;

		currentlyPendingJobsCount = (String) this.jdbcTemplate.queryForObject(
				query, new Object[] { report.getDepartmentId(),
						report.getDepartmentId(), report.getFromDate(),
						report.getToDate(), report.getFromDate(),
						report.getToDate(), factory }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("PendingJobs");
					}
				});

		return currentlyPendingJobsCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.reports.factory.dao.FactoryDao#getPartsProcessedForDept
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto)
	 */
	public String getPartsProcessedForDept(FactoryDto report) {
		String factory = report.getFactory();
		if (factory == null || "ALL".equals(factory)) {
			factory = "";
		} else if ("PINS".equals(factory)) {
			factory = "P";
		} else if ("NUTS".equals(factory)) {
			factory = "N";
		} else if ("COLLARS".equals(factory)) {
			factory = "C";
		}

		String query = report.getQuery();

		String partsProcessed = null;

		partsProcessed = (String) this.jdbcTemplate.queryForObject(query,
				new Object[] { report.getDepartmentId(),
						report.getDepartmentId(), report.getFromDate(),
						report.getToDate(), report.getFromDate(),
						report.getToDate(), factory }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("PartsProcessed");
					}
				});

		return partsProcessed;
	}

	public ArrayList<String> getDistinctTestType(StandardTime dto, String query) {

		ArrayList<String> testType = new ArrayList<String>();

		testType = (ArrayList<String>) this.jdbcTemplate.query(query,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int arsg1)
							throws SQLException {
						return rs.getString("TestType");
					}
				});
		System.out.println("test type size in dao" + testType.size());
		dto.setTestTypes(testType);
		return dto.getTestTypes();
	}

	public ArrayList getTestTypeDetails(StandardTime dto, String query) {

		ArrayList<TestMethods> testMethod = new ArrayList<TestMethods>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getSelectedTestType());
			System.out.println("query" + query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TestMethods testMethods = new TestMethods();
				testMethods.setTestMethodName(rs.getString("TestMethodName"));
				Integer temp = rs.getInt("StandardTime");

				if (temp.toString() != null) {
					testMethods.setStandardTime(rs.getInt("StandardTime"));
					testMethod.add(testMethods);
				} else {
					testMethods.setStandardTime(0);
					testMethod.add(testMethods);
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		System.out.println("testMethod.size in dao" + testMethod.size());
		return testMethod;
	}

	public void updateStandardTime(StandardTime dto, String query) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, dto.getSelectedStandardTime());
			pstmt.setString(2, dto.getSelectedTestType());
			pstmt.setString(3, dto.getSelectedTestMethodName());
			System.out.println("query" + query);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("update failed");
		} finally {
			System.out.println("updated successfully");
			closeConnection(connection);
		}
	}

	public void deleteStandardTime(StandardTime dto, String query) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getSelectedTestMethodName());
			System.out.println("query" + query);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("delete failed");
		} finally {
			System.out.println("deleted successfully");
			closeConnection(connection);
		}
	}

	public void addStandardTime(StandardTime dto, String query) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getSelectedTestMethodName());
			pstmt.setString(2, dto.getSelectedTestType());
			pstmt.setInt(3, dto.getSelectedStandardTime());
			System.out.println("query" + query);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("add failed");
		} finally {
			System.out.println("added successfully");
			closeConnection(connection);
		}
	}

	public void updateStandardTimeLog(StandardTime dto, String query,
			UserContext objUserContext) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getSelectedTestMethodName());
			pstmt.setString(2, dto.getSelectedTestType());
			pstmt.setInt(3, dto.getOldStandardTime());
			pstmt.setString(4, objUserContext.getUserName());
			pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
			System.out.println("query" + query);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("updated log failed");
		} finally {
			System.out.println("Updated log successfully");
			closeConnection(connection);
		}
	}
}