// File:         ManagementDaoImpl.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.management.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.sql.DataSource;

import com.gavs.hishear.m3interface.M3APIClient;
import com.gavs.hishear.m3interface.M3APIException;
import com.gavs.hishear.web.domain.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gavs.hishear.constant.Constant;
import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.M3APIParameter;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.LoginDAO;
import com.gavs.hishear.web.reports.factory.domain.FactoryDto;
import com.gavs.hishear.web.reports.management.domain.CorrectionLog;
import com.gavs.hishear.web.reports.management.domain.ManagementalDto;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.Util;

/**
 * The Class ManagementDaoImpl.
 *
 */
public class ManagementDaoImpl implements ManagementDao {

	private static final String EMPTY_STRING = "";

	/** Data Source to use. */
	private DataSource dataSource;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;
    private M3APIClient m3APIClient;

	// private UserContext userContext;

	private static final Logger logger = Logger
			.getLogger(ManagementDaoImpl.class);

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
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getAssetUsageReport
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getAssetUsageReport(ManagementalDto managementalDto,
			UserContext objUserContext) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		ManagementalDto dto = null;
		final DecimalFormat decfmt = new DecimalFormat("0.00");

		try {

			LoginDAO objLoginDao;

			reports = (ArrayList) this.jdbcTemplate.query(
					(String) objUserContext.getQueries().get("SHOPR_042"),
					new Object[] { managementalDto.getDepartment(),
							managementalDto.getFromDate(),
							managementalDto.getToDate(),
							managementalDto.getAsset(), "A" }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ManagementalDto objmanagementalDto = new ManagementalDto();
							objmanagementalDto.setEmpId(rs
									.getString("EmployeeNumber"));
							objmanagementalDto.setAssetId(rs
									.getString("AssetNumber"));
							objmanagementalDto.setPartid(rs
									.getString("ItemNumber"));
							objmanagementalDto.setMoNumber(rs
									.getString("MONumber"));
							objmanagementalDto.setPartDesc(rs
									.getString("ItemDescription"));
							objmanagementalDto.setDepartment(rs
									.getString("Department"));
							objmanagementalDto.setAssetDesc(rs
									.getString("Description"));
							objmanagementalDto.setActivity(rs
									.getString("Activity"));
							objmanagementalDto.setQuantity(rs
									.getString("ActualQty"));
							float ftarpph = 0.0f;
							float factpph = 0.0f;

							if (rs.getString("TargetPPH") != null
									&& !(" ".equals(rs.getString("TargetPPH")))) {
								ftarpph = Float.valueOf(
										rs.getString("TargetPPH").trim())
										.floatValue();
							}

							if (rs.getString("ActualPPH") != null
									&& !(" ".equals(rs.getString("ActualPPH")))) {
								factpph = Float.valueOf(
										rs.getString("ActualPPH").trim())
										.floatValue();
							}
							String tarpph = (String) decfmt.format(ftarpph);
							String actpph = (String) decfmt.format(factpph);

							float fHrs = Util.getfloatActualHrs(rs
									.getString("RealHrs"));

							int TargetQty = Math.round(ftarpph * fHrs);

							int Effciency = 0;
							if (ftarpph > 1) {
								Effciency = Math
										.round((factpph / ftarpph) * 100);
							}

							objmanagementalDto.setRealHours(Util
									.getActualHrs(rs.getString("RealHrs")));
							objmanagementalDto.setTargetPPH(String.valueOf(Math
									.round(ftarpph)));
							objmanagementalDto.setActualPPH(String.valueOf(Math
									.round(factpph)));
							objmanagementalDto.setTargetQty(String
									.valueOf(TargetQty));
							objmanagementalDto.setEfficiency(String
									.valueOf(Effciency));
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

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getExpActivityReport
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getExpActivityReport(ManagementalDto managementalDto,
			UserContext objUserContext) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		ManagementalDto dto = null;
		String condition = null;

		try {

			LoginDAO objLoginDao;
			System.out.print("User Context :"
					+ objUserContext.getQueries().get("SHOPR_043"));
			reports = (ArrayList) this.jdbcTemplate.query(
					(String) objUserContext.getQueries().get("SHOPR_043"),
					new Object[] { managementalDto.getMoNumber(),
							managementalDto.getLineNumber(),
							managementalDto.getEmpId() }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ManagementalDto objmanagementalDto = new ManagementalDto();
							objmanagementalDto.setEmpName(rs
									.getString("EmployeeName"));
							objmanagementalDto.setMoNumber(rs
									.getString("MONumber"));
							objmanagementalDto.setPartid(rs
									.getString("ItemNumber"));
							objmanagementalDto.setAssetId(rs
									.getString("AssetNumber"));
							objmanagementalDto.setAssetName(rs
									.getString("Description"));
							objmanagementalDto.setActivity(rs
									.getString("Activity"));
							objmanagementalDto.setHrs(EMPTY_STRING
									+ rs.getFloat("ActMins"));
							/*
							 * START : Ramanan.M - 30th Aug : The StartTime And
							 * EndTime format was changed from 24 Hours to 12
							 * Hours AM/PM Format.
							 */
							String startTime = EMPTY_STRING;
							String endTime = EMPTY_STRING;
							if (rs.getString("StartTime") != null
									|| rs.getString("StartTime") != "null"
									|| rs.getString("StartTime") != EMPTY_STRING) {
								startTime = DateUtil.getDateFormatToAMPM(rs
										.getString("StartTime"),
										Constant.DATE_FORMAT_12);
							}
							objmanagementalDto.setStartTime(startTime);
							if (rs.getString("EndTime") != null
									|| rs.getString("EndTime") != "null"
									|| rs.getString("EndTime") != EMPTY_STRING) {
								endTime = DateUtil.getDateFormatToAMPM(rs
										.getString("EndTime"),
										Constant.DATE_FORMAT_12);
							}
							objmanagementalDto.setEndTime(endTime);
							/*
							 * END : Ramanan.M - 30th Aug : The StartTime And
							 * EndTime format was changed from 24 Hours to 12
							 * Hours AM/PM Format.
							 */

							objmanagementalDto.setSeqn(rs
									.getString("SequenceNumber"));
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

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getEmployeePerformanceReport
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getEmployeePerformanceReport(
			ManagementalDto managementalDto, UserContext objUserContext) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		ManagementalDto dto = null;
		String condition = null;
		final DecimalFormat decfmt = new DecimalFormat("0.00");

		try {

			LoginDAO objLoginDao;

			reports = (ArrayList) this.jdbcTemplate.query(
					(String) objUserContext.getQueries().get("SHOPR_042"),
					new Object[] { managementalDto.getDepartment(),
							managementalDto.getFromDate(),
							managementalDto.getToDate(),
							managementalDto.getEmpId(), "E" }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ManagementalDto objmanagementalDto = new ManagementalDto();
							objmanagementalDto.setEmpId(rs
									.getString("EmployeeNumber"));
							objmanagementalDto.setEmpName(rs
									.getString("EmployeeName"));
							objmanagementalDto.setDepartment(rs
									.getString("Department"));
							objmanagementalDto.setPartid(rs
									.getString("ItemNumber"));
							objmanagementalDto.setPartDesc(rs
									.getString("ItemDescription"));
							objmanagementalDto.setMoNumber(rs
									.getString("MONumber"));
							objmanagementalDto.setAssetId(rs
									.getString("AssetNumber"));
							objmanagementalDto.setAssetDesc(rs
									.getString("Description"));
							objmanagementalDto.setActivity(rs
									.getString("Activity"));
							objmanagementalDto.setQuantity(rs
									.getString("ActualQty"));
							float ftarpph = 0.0f;
							float factpph = 0.0f;

							if (rs.getString("TargetPPH") != null
									&& !(" ".equals(rs.getString("TargetPPH")))) {
								ftarpph = Float.valueOf(
										rs.getString("TargetPPH").trim())
										.floatValue();
							}
							if (rs.getString("ActualPPH") != null
									&& !(" ".equals(rs.getString("ActualPPH")))) {
								factpph = Float.valueOf(
										rs.getString("ActualPPH").trim())
										.floatValue();
							}
							String tarpph = (String) decfmt.format(ftarpph);
							String actpph = (String) decfmt.format(factpph);

							float fHrs = Util.getfloatActualHrs(rs
									.getString("RealHrs"));

							int TargetQty = Math.round(ftarpph * fHrs);

							int Effciency = 0;
							if (ftarpph > 1) {
								Effciency = Math
										.round((factpph / ftarpph) * 100);
							}

							objmanagementalDto.setRealHours(Util
									.getActualHrs(rs.getString("RealHrs")));
							objmanagementalDto.setTargetPPH(String.valueOf(Math
									.round(ftarpph)));
							objmanagementalDto.setActualPPH(String.valueOf(Math
									.round(factpph)));
							objmanagementalDto.setTargetQty(String
									.valueOf(TargetQty));
							objmanagementalDto.setEfficiency(String
									.valueOf(Effciency));
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

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getStandardRateReport
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getStandardRateReport(ManagementalDto managementalDto,
			UserContext objUserContext) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		final DecimalFormat decfmt = new DecimalFormat("0.00");

		try {
			// connection = getConnection();
			LoginDAO objLoginDao;

			reports = (ArrayList) this.jdbcTemplate.query(
					(String) objUserContext.getQueries().get("SHOPR_042"),
					new Object[] { managementalDto.getDepartment(),
							managementalDto.getFromDate(),
							managementalDto.getToDate(),
							managementalDto.getPartid(), "P" },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ManagementalDto objmanagementalDto = new ManagementalDto();
							objmanagementalDto.setEmpId(rs
									.getString("EmployeeNumber"));
							objmanagementalDto.setDepartment(rs
									.getString("Department"));
							objmanagementalDto.setPartid(rs
									.getString("ItemNumber"));
							objmanagementalDto.setPartDesc(rs
									.getString("ItemDescription"));
							objmanagementalDto.setMoNumber(rs
									.getString("MONumber"));
							objmanagementalDto.setAssetId(rs
									.getString("AssetNumber"));
							objmanagementalDto.setAssetDesc(rs
									.getString("Description"));
							objmanagementalDto.setActivity(rs
									.getString("Activity"));
							objmanagementalDto.setQuantity(rs
									.getString("ActualQty"));

							float ftarpph = 0.0f;
							float factpph = 0.0f;

							if (rs.getString("TargetPPH") != null
									&& !(" ".equals(rs.getString("TargetPPH")))) {
								ftarpph = Float.valueOf(
										rs.getString("TargetPPH").trim())
										.floatValue();

							}
							if (rs.getString("ActualPPH") != null
									&& !(" ".equals(rs.getString("ActualPPH")))) {
								factpph = Float.valueOf(
										rs.getString("ActualPPH").trim())
										.floatValue();
							}

							String tarpph = (String) decfmt.format(ftarpph);
							String actpph = (String) decfmt.format(factpph);

							float fHrs = Util.getfloatActualHrs(rs
									.getString("RealHrs"));

							int TargetQty = Math.round(ftarpph * fHrs);

							int Effciency = 0;
							if (ftarpph > 1) {
								Effciency = Math
										.round((factpph / ftarpph) * 100);
							}

							objmanagementalDto.setRealHours(Util
									.getActualHrs(rs.getString("RealHrs")));
							objmanagementalDto.setTargetPPH(String.valueOf(Math
									.round(ftarpph)));
							objmanagementalDto.setActualPPH(String.valueOf(Math
									.round(factpph)));
							objmanagementalDto.setTargetQty(String
									.valueOf(TargetQty));
							objmanagementalDto.setEfficiency(String
									.valueOf(Effciency));
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getProductionReport
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getProductionReport(ManagementalDto managementalDto,
			UserContext objUserContext) {
		ArrayList reports = new ArrayList();
		Connection connection = null;

		ResultSet rs = null;
		try {

			String condition = EMPTY_STRING;
			int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
			System.out.println("------ managementalDto.getWorkCenter(), "
					+ managementalDto.getWorkCenter());
			System.out.println("------ managementalDto.getFromDate(), "
					+ managementalDto.getFromDate());
			System.out.println("------ managementalDto.getToDate(), "
					+ managementalDto.getToDate());
			System.out.println("------ managementalDto.getMoNumber(), "
					+ managementalDto.getMoNumber());
			System.out.println("------ managementalDto.getEmpId(), "
					+ managementalDto.getEmpId());
			System.out.println("------ managementalDto.getPartid(), "
					+ managementalDto.getPartid());
			System.out.println("------ managementalDto.getAssetId(), "
					+ managementalDto.getAssetId());
			System.out.println("------ managementalDto.getFacility(), "
					+ managementalDto.getFacility());
			System.out.println("------ managementalDto.getCostCenter(), "
					+ managementalDto.getCostCenter());
			String costCenter = null;
			if (managementalDto.getWorkCenter() == null) {
				costCenter = managementalDto.getCostCenter();
			}
			reports = (ArrayList) this.jdbcTemplate.query(
					(String) objUserContext.getQueries().get("SHOPR_041"),
					new Object[] {
							managementalDto.getWorkCenter(),
							managementalDto.getFromDate(),
							managementalDto.getToDate(),
							managementalDto.getMoNumber(),
							managementalDto.getEmpId(),
							StringUtils.defaultString(managementalDto
									.getPartid()),
							StringUtils.defaultString(managementalDto
									.getAssetId()),
							managementalDto.getFacility(), costCenter },
					argTypes, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {

							ManagementalDto objmanagementalDto = new ManagementalDto();

							objmanagementalDto.setEmpName(rs
									.getString("EmployeeName"));
							objmanagementalDto.setWorkCenter(rs
									.getString("WorkCenter"));
							objmanagementalDto.setMoNumber(rs
									.getString("MONumber"));
							objmanagementalDto.setPartid(rs
									.getString("ItemNumber"));

							objmanagementalDto.setAsset(rs
									.getString("AssetNumber"));
							//System.out.println("pavan got the data to show : " + rs.getString("AssetNumber"));
							objmanagementalDto.setAssetDesc(rs
									.getString("Description"));

							objmanagementalDto.setSetup(Util.getActualHrs(rs
									.getString("SetupMins")));
							float setupMinutes = Float.parseFloat(rs
									.getString("SetupMins"));
							float retoolMinutes = Float.parseFloat(rs
									.getString("RetoolMins"));
							float runMinutes = Float.parseFloat(rs
									.getString("RunMins"))
									- retoolMinutes;
							if (runMinutes < 0) {
								runMinutes = 0;
							}
							objmanagementalDto.setRun(Util.getActualHrs(EMPTY_STRING
									+ runMinutes));

							objmanagementalDto.setReTool(Util.getActualHrs(rs
									.getString("RetoolMins")));
							objmanagementalDto.setEmpId(rs
									.getString("EmployeeNumber"));
							objmanagementalDto.setLineNumber(rs
									.getString("LineNumber"));
							objmanagementalDto.setExpCount(rs
									.getInt("ExcepCount"));
							objmanagementalDto.setPartDesc(rs
									.getString("ItemDescription"));
							objmanagementalDto.setCompletedQty(EMPTY_STRING
									+ rs.getInt("QtyCompleted"));

							float TotHrs = setupMinutes + runMinutes
									+ retoolMinutes;
							objmanagementalDto.setTotalHours1(Util
									.getActualHrs(String.valueOf(TotHrs)));

							return objmanagementalDto;

						}

					});

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		System.out.println("size of reports = " + reports.size());
		return reports;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getFactories
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getFactories(FactoryDto factoryDto,
			UserContext objUserContext) {
		ArrayList factArrayList = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		FactoryDto factory = new FactoryDto();
		factory.setFactory("PINS");
		factArrayList.add(factory);

		factory = new FactoryDto();
		factory.setFactory("COLLARS");
		factArrayList.add(factory);

		factory = new FactoryDto();
		factory.setFactory("NUTS");
		factArrayList.add(factory);

		return factArrayList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getShiftTime
	 * (com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getShiftTime(UserContext objUserContext) {
		ArrayList arrShiftTime = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

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
							ManagementalDto objmanagementalDto = new ManagementalDto();

							objmanagementalDto.setShiftStartTime(rs
									.getString("ShiftStartTime"));
							objmanagementalDto.setShiftEndTime(rs
									.getString("ShiftEndTime"));
							objmanagementalDto.setShift(rs
									.getString("ShiftCode"));

							String fromDate = currentDateTime + " "
									+ objmanagementalDto.getShiftStartTime();
							String toDate = currentDateTime + " "
									+ objmanagementalDto.getShiftEndTime();
							try {
								from.setTime(format2.parse(fromDate));
								to.setTime(format2.parse(toDate));
							} catch (java.text.ParseException e) {
								e.printStackTrace();
							}

							if (to.before(from)) {
								objmanagementalDto.setDateShiftRequired(true);
							}
							return objmanagementalDto;

						}

					});

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return arrShiftTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getActivityLog
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	@Override
	public ArrayList getActivityLog(ManagementalDto managementalDto,
			UserContext objUserContext) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		try {

			reports = (ArrayList) this.jdbcTemplate.query(
					(String) objUserContext.getQueries().get("SHOPR_066"),
					new Object[] { managementalDto.getDepartment(),
							managementalDto.getFromDate(),
							managementalDto.getToDate(),
							managementalDto.getFromDate(),
							managementalDto.getToDate() }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ManagementalDto objmanagementalDto = new ManagementalDto();
							objmanagementalDto.setEmpId(rs
									.getString("EmployeeNumber"));
							objmanagementalDto.setEmpName(rs
									.getString("EmployeeName"));
							objmanagementalDto.setAsset(rs
									.getString("AssetNumber"));
							objmanagementalDto.setAssetDesc(rs
									.getString("Description"));
							objmanagementalDto.setActivity(rs
									.getString("ActivityCode"));
							objmanagementalDto.setPartid(rs
									.getString("ItemNumber"));
							objmanagementalDto.setPartDesc(rs
									.getString("ItemDescription"));
							objmanagementalDto.setLogon(rs
									.getString("LogonDate"));
							objmanagementalDto.setLogoff(rs
									.getString("LogoffDate"));
							objmanagementalDto.setCompletedQty(EMPTY_STRING
									+ rs.getInt("QtyCompleted"));
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

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getActivityLogSummary
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getActivityLogSummary(ManagementalDto managementalDto,
			UserContext objUserContext) {
		Connection connection = null;
		ResultSet rs = null;
		ArrayList reports = new ArrayList();

		for (int i = 0; i < 10; i++) {

			ManagementalDto objmanagementalDto = new ManagementalDto();

			objmanagementalDto.setMoNumber("MONumber");
			objmanagementalDto.setPartid("PartNumber");
			objmanagementalDto.setSequence("sequence");
			objmanagementalDto.setAsset("AssetNumber");
			objmanagementalDto.setAssetDesc("Description");
			objmanagementalDto.setSetup("SetupMins");
			objmanagementalDto.setRun("RunMins");
			objmanagementalDto.setReTool("RetoolMins");
			objmanagementalDto.setTotalHrs("totalHrs");
			objmanagementalDto.setCompletedQty(EMPTY_STRING + "QtyCompleted");
			objmanagementalDto.setExpCount(1);
			objmanagementalDto.setDeptId("deptId");
			objmanagementalDto.setWorkCenterNo("workCenterNo");
			objmanagementalDto.setWorkCenterDesc("workCenterDesc");

			reports.add(objmanagementalDto);
		}

		return reports;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getDepartmentForFactory
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getDepartmentForFactory(FactoryDto factoryDto,
			UserContext objUserContext) {
		ArrayList cat1 = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;

		try {
			connection = getConnection();
			cat1 = (ArrayList) this.jdbcTemplate.query((String) objUserContext
					.getQueries().get("SHOPR_079"), new Object[] { factoryDto
					.getFactory() }, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					ManagementalDto objmanagementalDto = new ManagementalDto();
					objmanagementalDto.setDeptId(rs.getString("DeptNumber"));
					objmanagementalDto.setDepartment(rs.getString("DeptName"));
					objmanagementalDto.setCat(rs.getString("Category"));
					return objmanagementalDto;

				}

			});

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return cat1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getPerformanceAssetwiseReport
	 * (com.gavs.hishear.web.reports.factory.domain.FactoryDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getPerformanceAssetwiseReport(final FactoryDto factoryDto,
			UserContext userContext) {
		Connection connection = null;
		ResultSet rs = null;
		ArrayList PerformanceReport = new ArrayList();
		FactoryDto factDto = null;
		if (factoryDto.getDepartment() == null) {
			factoryDto.setDepartment(EMPTY_STRING);
		}
		final DecimalFormat decfmt = new DecimalFormat("0.00");
		try {
			connection = getConnection();

			PerformanceReport = (ArrayList) this.jdbcTemplate.query(
					(String) userContext.getQueries().get("SHOPR_078"),
					new Object[] { factoryDto.getDepartment(),
							factoryDto.getFactory(), factoryDto.getFromDate(),
							factoryDto.getToDate(), factoryDto.getFromDate(),
							factoryDto.getToDate() }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {

							FactoryDto factDto = new FactoryDto();

							factDto.setAssetNumber(rs.getString("AssetNumber"));
							factDto.setPartNumber(rs.getString("ItemNumber"));
							factDto.setShift(factoryDto.getShift());
							factDto.setEmployeeName(rs
									.getString("EmployeeName"));

							float ftarpph = 0.0f;
							float factpph = 0.0f;
							if (rs.getString("TargetPPH") != null
									&& !(" ".equals(rs.getString("TargetPPH")))) {
								ftarpph = Float.valueOf(
										rs.getString("TargetPPH").trim())
										.floatValue();
							}
							if (rs.getString("ActualPPH") != null
									&& !(" ".equals(rs.getString("ActualPPH")))) {
								factpph = Float.valueOf(
										rs.getString("ActualPPH").trim())
										.floatValue();
							}
							String tarpph = (String) decfmt.format(ftarpph);
							String actpph = (String) decfmt.format(factpph);

							float fHrs = Util.getfloatActualHrs(rs
									.getString("RealHrs"));

							int TargetQty = Math.round(ftarpph * fHrs);

							int Effciency = 0;
							if (ftarpph > 1) {
								Effciency = Math
										.round((factpph / ftarpph) * 100);
							}
							factDto.setRun("10");
							factDto.setPPH(String.valueOf(Math.round(ftarpph)));
							factDto.setPPHAct(String.valueOf(Math
									.round(factpph)));
							factDto.setQtyTarget(String.valueOf(TargetQty));
							factDto.setQtyAct(rs.getString("ActualQty"));
							factDto.setActRunHrs(Util.getActualHrs(rs
									.getString("RealHrs")));

							factDto.setDownHrs(EMPTY_STRING + (10 - fHrs));
							factDto.setEfficiency(String.valueOf(Effciency));
							return factDto;
						}
					});

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return PerformanceReport;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getDepartments
	 * (com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList getDepartments(UserContext userContext) {
		ArrayList departments = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			departments = (ArrayList) this.jdbcTemplate.query(
					(String) userContext.getQueries().get("SHOPR_037"),
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Department department = new Department();
							department
									.setDeptNumber(rs.getString("DeptNumber"));
							department.setDeptName(rs.getString("DeptName"));
							return department;
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return departments;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getCostCentersForFacility(com.gavs.hishear.m3interface.dto.MVXDTAMI)
	 */
	@Override
	public ArrayList getCostCentersForFacility(MVXDTAMI mvxdtami) {
      /*  Map<String, String> args = new HashMap<String, String>(2);
        args.put(M3Parameter.CONO.getValue(), applicationPropertyBean.getCompany());
        args.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());*/
		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("PDZ010MI");
		parameter.setInputItem("LstByCostCenterItem");
		parameter.setOutputItem("LstByCostCenterResponseItem");
		parameter.setFunctionName("LstByCostCenter");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Company", applicationPropertyBean.getCompany());
		map.put("Facility", mvxdtami.getFacility());
		//map.put(M3Parameter.COCE.getValue(), mvxdtami.getCostCenter());
		parameter.setInputData(map);
		
		
		ArrayList list = null;
		try {
			list = (ArrayList) m3APIWSClient.callM3API(parameter);
		} catch (Exception e) {
			logger.error(e.getMessage());

		}

		return list;
		

        /*try {
            List<Map<M3Parameter, String>> result = m3APIClient.execute("PDZ010MI", "LstByCostCenter",
                    args, M3Parameter.COCE, M3Parameter.TX40);
            Map<String, CostCenter> costCenters = new HashMap<String, CostCenter>();
            for(Map<M3Parameter, String> cost : result) {
                CostCenter costCenter = new CostCenter();
                costCenter.setCostCenterCode(cost.get(M3Parameter.COCE));
                costCenter.setCostCenterDesc(cost.get(M3Parameter.TX40));
                costCenters.put(costCenter.getCostCenterCode(), costCenter);
            }
            return new ArrayList(costCenters.values());
        } catch (M3APIException e) {
            return null;
        }*/
     
    }

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getWorkCentersForCostCenter(com.gavs.hishear.m3interface.dto.MVXDTAMI)
	 */
	@Override
	public ArrayList getWorkCentersForCostCenter(MVXDTAMI mvxdtami) {
		// TODO Auto-generated method stub

		M3APIParameter parameter = new M3APIParameter();

		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("LSEMPDWCTX1Item");
		parameter.setOutputItem("LSEMPDWCTX1ResponseItem");
		parameter.setFunctionName("getLSEMPDWCTX1");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), applicationPropertyBean.getCompany());
		map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
		map.put(M3Parameter.COCE.getValue(), mvxdtami.getCostCenter());

		parameter.setInputData(map);

		ArrayList list = null;
		try {
			list = (ArrayList) m3APIWSClient.callM3API(parameter);
		} catch (Exception e) {
			logger.error(e.getMessage());

		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getFacilities
	 * (java.lang.String)
	 */
	public ArrayList<Facility> getFacilities(String division) {
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
			inputDataMap.put(M3Parameter.CONO.getValue(), applicationPropertyBean.getCompany());
			parameter.setInputData(inputDataMap);
			allFacilities = (ArrayList<Facility>) m3APIWSClient
					.callM3API(parameter);// m3Dao.getFacilities();
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getCorrectionTrackingReport
	 * (com.gavs.hishear.web.reports.management.domain.ManagementalDto,
	 * com.gavs.hishear.web.context.UserContext)
	 */
	public ArrayList<CorrectionLog> getCorrectionTrackingReport(
			ManagementalDto managementalDto, UserContext objUserContext) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(managementalDto.getDtToDate());
		cal.add(Calendar.DATE, 1);
		Date toDate = cal.getTime();
		String startDate = DateUtil.uiDateFormat(managementalDto
				.getDtFromDate(), "yyyy-MM-dd");
		String endDate = DateUtil.uiDateFormat(toDate, "yyyy-MM-dd");

		String query = (String) objUserContext.getQueries().get("SHOPR_110");
		return (ArrayList<CorrectionLog>) jdbcTemplate.query(query,
				new Object[] { startDate, endDate }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						CorrectionLog correctionLog = new CorrectionLog();
						correctionLog.setOprName(rs.getString("Name"));
						// correctionLog.setOprLastName(rs.getString("LastName"));
						correctionLog.setMoNumber(rs.getString("MONumber"));
						correctionLog.setSequenceNumber(rs
								.getString("SequenceNumber"));
						correctionLog.setCorrectedBy(rs
								.getString("CorrectedBy"));
						correctionLog.setCorrectedOn(rs
								.getTimestamp("CorrectedOn"));
						correctionLog.setStampNumber(rs.getString("StampNo"));
						correctionLog.setEmployeeNumber(rs
								.getString("EmployeeNumber"));
						correctionLog.setOldEmployeeNumber(rs
								.getString("OldEmployeeNumber"));
						correctionLog.setActivityCode(rs
								.getString("ActivityCode"));
						correctionLog.setOldActivityCode(rs
								.getString("OldActivityCode"));
						correctionLog
								.setLogonDate(rs.getTimestamp("LogonDate"));
						correctionLog.setOldLogonDate(rs
								.getTimestamp("OldLogonDate"));
						correctionLog.setLogOffDate(rs
								.getTimestamp("LogoffDate"));
						correctionLog.setOldLogOffDate(rs
								.getTimestamp("OldLogoffDate"));
						correctionLog.setQtyCompleted(rs
								.getString("QtyCompleted"));
						correctionLog.setOldQtyCompleted(rs
								.getString("oldQtyCompleted"));
						correctionLog.setAssetNumber(rs
								.getString("AssetNumber"));
						correctionLog.setOldAssetNumber(rs
								.getString("OldAssetNumber"));
						correctionLog.setCompleteStatusFlag(rs
								.getString("CompleteStatusFlag"));
						correctionLog.setOldCompleteStatusFlag(rs
								.getString("OldCompleteStatusFlag"));
						// correctionLog.setMoNumber(rs.getString("RouterDate"));
						correctionLog.setNewFlag(rs.getString("newFlag"));
						correctionLog.setMultipleCorrectionFlag(rs
								.getString("MultipleCorrectionFlag"));
						return correctionLog;
					}
				});
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getM3RequestLogs
	 * (java.lang.String)
	 */
	@Override
	public ArrayList<ErrorLog> getM3RequestLogs(String dateProcessed,
			String query) throws Exception {
		ArrayList<ErrorLog> requestLogs = new ArrayList<ErrorLog>();
		Connection connection = null;
		try {
			connection = getConnection();
			requestLogs = (ArrayList<ErrorLog>) this.jdbcTemplate.query(query,
					new Object[] { dateProcessed }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ErrorLog errorLog = new ErrorLog();
							errorLog.setWebServiceName(rs.getString(
									"WebServiceName").replace("Write", EMPTY_STRING));
							errorLog.setFunctionName(rs
									.getString("FunctionName"));
							errorLog.setColumnHeaders(rs
									.getString("ColumnHeaders"));
							errorLog.setColumnValues(rs
									.getString("ColumnValues"));
							return errorLog;
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return requestLogs;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.gavs.hishear.web.reports.management.dao.ManagementDao#getM3TransByType
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<ErrorLog> getM3TransByType(String transType,
			String wareHouse, String dateProcessed) throws Exception {
		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MWS070MIRead");
		parameter.setInputItem("ListTransByTypeItem");
		parameter.setOutputItem("ListTransByTypeResponseItem");
		parameter.setFunctionName("lstTransByType");
		parameter.setReadOrWrite("read");

		HashMap<String, String> inputDataMap = new HashMap<String, String>();
		inputDataMap.put(M3Parameter.TTYP.getValue(), transType);
		inputDataMap.put(M3Parameter.TRDT.getValue(), dateProcessed);
		inputDataMap.put(M3Parameter.WHLO.getValue(), wareHouse);
		parameter.setInputData(inputDataMap);
		return (ArrayList<ErrorLog>) m3APIWSClient.callM3API(parameter);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.gavs.hishear.web.reports.management.dao.ManagementDao#
	 * getFacilitiesForDivision(java.lang.String)
	 */
	@Override
	public ArrayList<String> getFacilitiesForDivision(String division,
			String query) {
		ArrayList<String> facilities = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = getConnection();
			facilities = (ArrayList<String>) this.jdbcTemplate.query(query,
					new Object[] { division }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							return rs.getString("Facility");
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return facilities;
	}
	

	

    public void setM3APIClient(M3APIClient m3APIClient) {
        this.m3APIClient = m3APIClient;
    }
}