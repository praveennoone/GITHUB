package com.gavs.hishear.web.reports.exceptional.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gavs.hishear.constant.Constant;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.Util;

/**
 * @author sundarrajanr
 * 
 */
public class ExceptionalDaoImpl implements ExceptionalDao {

	/** Data Source to use */
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private UserContext userContext;

	private static final Logger logger = Logger
			.getLogger(ExceptionalDaoImpl.class);

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
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public ArrayList getLogonLogoffReport(ExceptionalDto exceptionalDto) {

		ArrayList reports = new ArrayList();
		try {
			reports = (ArrayList) this.jdbcTemplate.query(exceptionalDto
					.getQuery(), new Object[] { exceptionalDto.getEmpId(),
					exceptionalDto.getDepartment(),
					exceptionalDto.getFromDate(), exceptionalDto.getToDate(),
					exceptionalDto.getFromDate(), exceptionalDto.getToDate() },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ExceptionalDto exceptionalDto = new ExceptionalDto();
							exceptionalDto.setEmpId(rs.getString("EmployeeID"));
							exceptionalDto.setEmpName(rs
									.getString("EmployeeName"));
							exceptionalDto.setDepartment(rs
									.getString("Department"));
							exceptionalDto
									.setMoNumber(rs.getString("MONumber"));
							exceptionalDto.setLineNumber(rs
									.getString("LineNumber"));
							exceptionalDto.setSequence(rs
									.getString("SequenceNumber"));
							exceptionalDto.setActivity(rs
									.getString("ActivityCode"));

							exceptionalDto.setPunchIn(rs
									.getString("PunchInTime"));
							exceptionalDto.setPunchOut(rs
									.getString("PunchOutTime"));

							exceptionalDto.setShiftTime(rs
									.getString("ShiftCode"));

							exceptionalDto.setLogon(""
									+ rs.getTimestamp("LogonTime"));
							exceptionalDto.setLogoff(""
									+ rs.getTimestamp("LogoffTime"));
							return exceptionalDto;

						}

					});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return reports;
	}

	/**
	 * @return Returns the dataSource.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	public ArrayList getEmployeeDetailsReport(ExceptionalDto exceptionalDto) {

		ArrayList reports = new ArrayList();

		try {
			reports = (ArrayList) this.jdbcTemplate.query(exceptionalDto
					.getQuery(), new Object[] { exceptionalDto.getEmpId(),
					exceptionalDto.getDeptId() }, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					ExceptionalDto exceptionalDto = new ExceptionalDto();
					exceptionalDto.setEmpId(rs.getString("EmployeeID"));
					exceptionalDto.setEmpName(rs.getString("EmployeeName"));
					exceptionalDto.setDepartment(rs.getString("Department"));
					exceptionalDto.setPunchIn(rs.getString("PunchInTime"));
					exceptionalDto.setPunchOut(rs.getString("PunchOutTime"));
					exceptionalDto.setShiftTime(rs.getString("ShiftCode"));
					return exceptionalDto;

				}

			});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return reports;
	}

	public ArrayList getNonProductivityReport(ExceptionalDto exceptionalDto) {

		ArrayList reports = new ArrayList();

		try {
			reports = (ArrayList) this.jdbcTemplate.query(exceptionalDto
					.getQuery(), new Object[] { exceptionalDto.getFromDate(),
					exceptionalDto.getToDate(), exceptionalDto.getFromDate(),
					exceptionalDto.getToDate(), exceptionalDto.getDepartment(),
					exceptionalDto.getEmpId(), exceptionalDto.getFromDate(),
					exceptionalDto.getToDate(), exceptionalDto.getFromDate(),
					exceptionalDto.getToDate() }, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					ExceptionalDto exceptionalDto = new ExceptionalDto();
					exceptionalDto.setEmpId(rs.getString("EmployeeID"));
					exceptionalDto.setEmpName(rs.getString("EmployeeName"));
					exceptionalDto.setDepartment(rs.getString("Department"));

					exceptionalDto.setPunchIn(rs.getString("PunchInTime"));
					exceptionalDto.setPunchOut(rs.getString("PunchOutTime"));

					float workingHrs = rs.getFloat("WorkingHrs");
					float productivityHrs = rs.getFloat("ProdHrs");
					exceptionalDto.setWorkingHrs(Util.getActualHrs(""
							+ workingHrs));
					exceptionalDto.setProductivityHrs(Util.getActualHrs(""
							+ productivityHrs));

					exceptionalDto.setNps(Util.getActualHrs(""
							+ (workingHrs - productivityHrs)));
					return exceptionalDto;

				}

			});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return reports;

	}

	public ArrayList getActivityRejectedReport(ExceptionalDto exceptionalDto) {
		ArrayList reports = new ArrayList();

		try {
			reports = (ArrayList) this.jdbcTemplate.query(exceptionalDto
					.getQuery(),
					new Object[] { exceptionalDto.getMoNumber(),
							exceptionalDto.getEmpId(),
							exceptionalDto.getDepartment() }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ExceptionalDto exceptionalDto = new ExceptionalDto();
							exceptionalDto.setEmpName(rs
									.getString("EmployeeName"));
							exceptionalDto.setDepartment(rs
									.getString("Department"));
							exceptionalDto
									.setMoNumber(rs.getString("MONumber"));
							exceptionalDto.setLineNumber(rs
									.getString("LineNumber"));
							exceptionalDto.setSequence(rs
									.getString("SequenceNumber"));
							exceptionalDto.setActivity(rs
									.getString("ActivityCode"));

							exceptionalDto.setShiftTime(rs
									.getString("ShiftCode"));

							if (rs.getDate("RejectedDate") != null
									&& !rs.getDate("RejectedDate").equals(
											"null")
									&& !rs.getDate("RejectedDate").equals("")) {
								exceptionalDto.setRejectedDate(""
										+ rs.getDate("RejectedDate"));
							} else {
								exceptionalDto.setRejectedDate("");
							}
							exceptionalDto.setRejectedBy(rs
									.getString("RejectedBy"));
							exceptionalDto.setRejectedComments(rs
									.getString("RejectComments"));
							return exceptionalDto;

						}

					});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return reports;
	}

	public ArrayList getDepartments(String query) {
		ArrayList departments = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		ExceptionalDto dto = null;
		try {
			connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(query);

			rs = statement.executeQuery();
			while (rs.next()) {
				dto = new ExceptionalDto();
				dto.setDeptId(rs.getString("DeptNumber"));
				dto.setDepartment(rs.getString("DeptName"));
				dto.setCat(rs.getString("Category"));
				departments.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return departments;
	}

	public ArrayList getActivityRejection(ExceptionalDto filterDto) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		ExceptionalDto dto = null;
		try {
			connection = getConnection();

			String query = filterDto.getQuery();

			PreparedStatement pstmt = connection.prepareStatement(query);

			pstmt.setString(1, filterDto.getMoNumber());
			pstmt.setString(2, "000");
			pstmt.setString(3, filterDto.getSequence());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new ExceptionalDto();

				dto.setJobStatus(rs.getString("ActivityStatus"));
				dto.setRejectedComments(rs.getString("Comments"));
				dto.setActivity(rs.getString("Activity"));
				/*
				 * START Ramanan.M - 30th Aug : Changed the Date format LogOn
				 * and LogOff date from 24 Hours to 12 Hours AM/PM format.
				 */
				if (rs.getTimestamp("LogonDate") != null) {
					dto.setLogon(DateUtil
							.uiDateFormat(rs.getTimestamp("LogonDate"),
									Constant.DATE_FORMAT_12));
				}
				dto.setCompletedQty(rs.getString("QtyComplete"));
				if (rs.getTimestamp("LogoffDate") != null) {
					dto.setLogoff(DateUtil.uiDateFormat(rs
							.getTimestamp("LogoffDate"),
							Constant.DATE_FORMAT_12));
				}
				/*
				 * END Ramanan.M - 30th Aug : Changed the Date format LogOn and
				 * LogOff date from 24 Hours to 12 Hours AM/PM format.
				 */
				dto.setActNo(rs.getString("JobActivityNumber"));
				dto.setEmpName(rs.getString("EmployeeName"));
				dto.setRejectedDate(rs.getString("RejectDate"));
				dto.setJobNumber(rs.getInt("JobNumber"));
				dto.setAssetNumber(rs.getString("AssetNumber"));
				dto.setEmployeeNumber(rs.getString("EmployeeNumber"));
				dto.setActivityLogNumber(rs.getString("ActivityLogNumber"));
				dto.setWorkCenterNumber(rs.getString("WorkCenter"));
				dto.setLogonDate(rs.getTimestamp("LogonDate"));
				dto.setLogoffDate(rs.getTimestamp("LogoffDate"));

				reports.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return reports;
	}

	public ArrayList getActivityAdjustment(String moNumber, String lineNo,
			String seqn) {
		ArrayList reports = new ArrayList();
		Connection connection = null;
		ResultSet rs = null;
		ExceptionalDto dto = null;
		try {
			connection = getConnection();

			String query = "SELECT G.EmployeeNumber AS Employee_Id, "
					+ "(LTRIM(RTRIM(FirstName))+','+LTRIM(RTRIM(MI))+' '+LTRIM(RTRIM(LastName))) AS Employee_Name, "
					+ "A.MONumber,PunchInActual AS 'PunchIN', PunchOutActual AS 'PunchOUT', "
					+ "LogonDate AS LOGIN_TIME, LogoffDate AS LOGOFF_TIME, F.BreakStart AS PAUSE, F.BreakEnd AS RESUME "
					+ "FROM SFS_EmployeeMaster G, SFS_EmployeeLogon H, SFS_Department D, "
					+ "SFS_BOMActivityList E, SFS_OrderDetail A, SFS_ActivityLog B, "
					+ "SFS_SequenceActivity C, SFS_ActivityBreak F "
					+ "WHERE A.JobNumber  = C. JobNumber "
					+ "AND B.JobActivityNumber  = C.JobActivityNumber "
					+ "AND B.JobActivityNumber  = F. JobActivityNumber "
					+ "AND C. JobActivityNumber  = F. JobActivityNumber "
					+ "AND D.DeptNumber = H.Department "
					+ "AND G.EmployeeNumber=H.EmployeeNumber "
					+ "AND G.EmployeeNumber = C.EmployeeNumber "
					+ "AND E.BOMActivityCode = C.ActivityCode "
					+ "AND A.MONumber='" + moNumber + "' "
					+ "AND A.LineNumber='" + lineNo + "' "
					+ "AND A.SequenceNumber= '" + seqn + "'";
			PreparedStatement statement = connection.prepareStatement(query);

			rs = statement.executeQuery();
			while (rs.next()) {
				dto = new ExceptionalDto();
				dto.setEmpName(rs.getString("Employee_Name"));
				dto.setEmpId(rs.getString("Employee_Id"));
				dto.setPunchIn(rs.getString("PunchIN"));
				dto.setPunchOut(rs.getString("PunchOUT"));
				dto.setLogon(rs.getTimestamp("LOGIN_TIME").toString());
				dto.setLogoff(rs.getTimestamp("LOGOFF_TIME").toString());
				dto.setBrkStart(rs.getTimestamp("PAUSE").toString());
				dto.setBrkEnd(rs.getTimestamp("RESUME").toString());
				dto.setActNo(rs.getString("rt_act_no"));
				reports.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return reports;
	}

	public void updateRejectedRecord(ExceptionalDto dto) {
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();

			String query = dto.getQuery();
			PreparedStatement pstmt = connection.prepareStatement(query);

			pstmt.setString(1, dto.getRejectedComments());
			pstmt.setString(2, dto.getRejectedBy());
			pstmt.setString(3, dto.getRejectedDate());
			pstmt.setString(4, dto.getActNo());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	public void updatePauseTime(ExceptionalDto dto) {
		Connection connection = null;
		try {
			connection = getConnection();

			PreparedStatement pstmt = connection.prepareStatement(dto
					.getQuery());
			pstmt.setString(1, dto.getBrkStart());
			pstmt.setString(2, dto.getActNo());
			pstmt.executeUpdate();
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	public ArrayList getLogonLogoffDetails(ExceptionalDto exceptionalDto) {
		ArrayList reports = new ArrayList();

		try {
			reports = (ArrayList) this.jdbcTemplate.query(exceptionalDto
					.getQuery(), new Object[] { exceptionalDto.getDeptId(),
					exceptionalDto.getPunchInDate(),
					exceptionalDto.getPunchOutDate(),
					StringUtils.defaultString(exceptionalDto.getEmpId()),
					StringUtils.defaultString(exceptionalDto.getAsset()),
					StringUtils.defaultString(exceptionalDto.getItemNumber()),
					StringUtils.defaultString(exceptionalDto.getMoNumber()) },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							ExceptionalDto exceptionalDto = new ExceptionalDto();
							exceptionalDto.setEmpId(rs
									.getString("EmployeeNumber"));
							exceptionalDto.setEmpName(rs
									.getString("EmployeeName"));
							exceptionalDto
									.setAsset(rs.getString("AssetNumber"));
							exceptionalDto.setAssetDescription(rs
									.getString("AssetDescription"));
							exceptionalDto
									.setMoNumber(rs.getString("MONumber"));
							// exceptionalDto.setLineNumber(rs.getString("LineNumber"));
							exceptionalDto.setSequence(rs
									.getString("SequenceNumber"));
							exceptionalDto
									.setActivity(rs.getString("Activity"));
							exceptionalDto.setItemNumber(rs
									.getString("ItemNumber"));

							exceptionalDto.setQtyComleted(rs
									.getInt("QtyCompleted"));
							exceptionalDto
									.setLogon(DateUtil
											.getLogOnDetailsDateFormat(
													String
															.valueOf(rs
																	.getTimestamp("LogonDate")),
													Constant.DATE_FORMAT_UPTOSCEONDS));
							exceptionalDto
									.setLogoff(DateUtil
											.getLogOnDetailsDateFormat(
													String
															.valueOf(rs
																	.getTimestamp("LogoffDate")),
													Constant.DATE_FORMAT_UPTOSCEONDS));
							return exceptionalDto;

						}

					});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return reports;
	}

	public void rejectActivity(ExceptionalDto dto, String rejectedBy) {
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			String query = dto.getQuery();
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, dto.getActivityLogNumber());
			stmt.setString(2, rejectedBy);
			stmt.setInt(3, dto.getReasonCode());
			stmt.setString(4, dto.getRejectedComments());
			stmt.executeUpdate();
			stmt.close();

			connection.commit();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

	}

	// Added Date : 13th March 2009
	// Method to get reasons from ReasonMaster
	public ArrayList<Sequence> getReasons(String query) {
		Connection connection = null;
		ResultSet rs = null;
		ArrayList<Sequence> reasons = new ArrayList<Sequence>();
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "Reject");
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

}