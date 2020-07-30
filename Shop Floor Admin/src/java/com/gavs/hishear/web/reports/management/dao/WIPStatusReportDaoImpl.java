// File:         WIPStatusReportDaoImpl.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gavs.hishear.web.reports.management.domain.WIPStatusDto;

/**
 * The Class WIPStatusReportDaoImpl.
 */
public class WIPStatusReportDaoImpl implements WIPStatusReportDao {

	/** Data Source to use. */
	private DataSource dataSource;

	/** JDBC Template to use. */
	private JdbcTemplate jdbcTemplate;

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
	 * @seecom.gavs.hishear.web.reports.management.dao.WIPStatusReportDao#
	 * getWipStatusReport
	 * (com.gavs.hishear.web.reports.management.domain.WIPStatusDto)
	 */
	public List getWipStatusReport(WIPStatusDto dto) throws Exception {
		ArrayList results = new ArrayList();
		try {
			results = (ArrayList) this.jdbcTemplate.query(dto.getQuery(),
					new Object[] {
							StringUtils.defaultString(dto.getPartNumber()),
							StringUtils.defaultString(dto.getPartNumber()),
							StringUtils.defaultString(dto.getFactory()) },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							WIPStatusDto wIPStatusDto = new WIPStatusDto();
							wIPStatusDto.setMoNumber(rs.getString("MONumber"));
							wIPStatusDto.setLineNumber(rs
									.getString("LineNumber"));
							wIPStatusDto.setCompletedSequence(rs
									.getString("CompletedSequence"));
							wIPStatusDto.setLatestPickQty(rs
									.getString("LatestPickQty"));
							wIPStatusDto.setProcessingSequence(rs
									.getString("ProcessingSequence"));
							wIPStatusDto.setProcessingQty(rs
									.getString("ProcessingQty"));
							wIPStatusDto.setDepartment(rs
									.getString("Department"));
							wIPStatusDto.setLocation(rs.getString("Location"));
							wIPStatusDto.setPriority(rs.getInt("Priority"));
							wIPStatusDto.setItemNumber(rs
									.getString("ItemNumber"));
							wIPStatusDto.setItemDescription(rs
									.getString("ItemDescription"));
							return wIPStatusDto;
						}
					});
		} catch (EmptyResultDataAccessException empty) {
			empty.printStackTrace();
		} catch (Exception e) {
			throw e;
		}

		return results;
	}
}
