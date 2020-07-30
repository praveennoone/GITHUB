// File:         ItemDAOImpl.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gavs.hishear.web.domain.Item;
import com.gavs.hishear.web.domain.Sequence;

/**
 * The Class ItemDAOImpl.
 */
public class ItemDAOImpl implements ItemDAO {

	/** Data Source to use. */
	private DataSource dataSource;

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(ItemDAOImpl.class);

	/**
	 * Sets the data source.
	 * 
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#resetItemWeight(com.gavs.hishear.web
	 * .domain.Item)
	 */
	public void resetItemWeight(Item item) throws Exception {
		// UPDATE dbo.SFS_ItemWeight SET ItemWeight = ?, MONumber = ?,
		// LineNumber = ? WHERE ItemNumber = ? AND SequenceNumber = ?
		String query = item.getQuery();
		int updatedRecordCount = 0;
		try {
			updatedRecordCount = this.jdbcTemplate.update(query, new Object[] {
					new Float(0.0f), item.getMoNumber(), "000",
					item.getItemNumber(), item.getSequenceNumber() });
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while updating weight");
		}
		if (updatedRecordCount == 0) {
			throw new Exception(
					"Item Number / MO Number/ Sequence Number Is Not Available.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#validateItemNumber(java.lang.String,
	 * java.lang.String)
	 */
	public Item validateItemNumber(String itemNumber, String query)
			throws Exception {

		Item part = null;
		try {
			part = (Item) this.jdbcTemplate.queryForObject(query,
					new Object[] { itemNumber }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Item part = new Item();
							part.setItemNumber(rs.getString("ItemNumber"));
							part.setItemDescription(rs
									.getString("ItemDescription"));
							return part;
						}
					});

		} catch (EmptyResultDataAccessException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return part;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#checkItemWeight(com.gavs.hishear.web
	 * .domain.Sequence)
	 */
	public Item checkItemWeight(Sequence sequence) throws Exception {
		System.out
				.println("checkItemWeight: " + sequence.getPartNumber() + "\t"
						+ sequence.getMoNumber() + "\t"
						+ sequence.getSequence());

		Item part = null;

		try {
			part = (Item) this.jdbcTemplate.queryForObject(sequence.getQuery(),
					new Object[] { sequence.getPartNumber(),
							sequence.getMoNumber(), sequence.getSequence() },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Item part = new Item();
							part.setAverageWeight(rs.getFloat("ItemWeight"));

							return part;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return part;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#getItemDetails(com.gavs.hishear.web.
	 * domain.Sequence)
	 */
	public Item getItemDetails(Sequence sequence) throws Exception {

		Item part = null;

		try {
			part = (Item) this.jdbcTemplate.queryForObject(sequence.getQuery(),
					new Object[] { sequence.getMoNumber(), "000",
							sequence.getSequence() }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Item part = new Item();
							part.setItemNumber(rs.getString("ItemNumber"));
							part.setItemDescription(rs
									.getString("ItemDescription"));
							return part;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return part;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#getItemDetails(com.gavs.hishear.web.
	 * domain.Item, java.lang.String)
	 */
	public void getItemDetails(final Item item, String query) {
		try {

			this.jdbcTemplate.query(query, new Object[] { item.getMoNumber(),
					"000" }, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					item.setItemNumber(rs.getString("ItemNumber"));
					item.setItemDescription(rs.getString("ItemDescription"));
					item.setItemOrderedQuantity(rs
							.getInt("ItemOrderedQuantity"));
					return item;
				}
			});
		} catch (EmptyResultDataAccessException empty) {
			empty.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#getParentAndComponentDetails(java.lang
	 * .String, java.lang.String)
	 */
	public List getParentAndComponentDetails(String partNumber, String query) {
		List parentAndComponentDetails = new LinkedList();
		try {

			parentAndComponentDetails = (List) this.jdbcTemplate.query(query,
					new Object[] { partNumber }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Item item = new Item();
							item.setItemNumber(rs.getString("ItemNumber"));
							item.setItemType(rs.getString("ItemType"));
							item.setItemDescription(rs
									.getString("ItemDescription"));
							return item;
						}
					});
		} catch (EmptyResultDataAccessException empty) {
			empty.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parentAndComponentDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gavs.hishear.web.dao.ItemDAO#getInventoryQuantity(java.lang.String,
	 * java.lang.String)
	 */
	public List getInventoryQuantity(String partNumber, String query) {
		List inventoryDetails = null;
		try {
			inventoryDetails = this.jdbcTemplate.query(query,
					new Object[] { partNumber }, new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Item item = new Item();
							item.setQuantity(rs.getInt("InventoryQuantity"));
							item.setItemDescription(rs
									.getString("ItemDescription"));
							item.setLotNumber(rs.getString("LotNumber"));
							item.setLocation(rs.getString("Location"));
							return item;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return inventoryDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.ItemDAO#insertItemweight(float,
	 * com.gavs.hishear.web.domain.Sequence)
	 */
	public boolean insertItemweight(float itemWeight, Sequence sequence)
			throws Exception {
		int count = 0;
		Connection connection = null;
		boolean updated = false;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(sequence
					.getQuery());
			pstmt.setString(1, sequence.getPartNumber());
			pstmt.setString(2, sequence.getSequence());
			pstmt.setFloat(3, itemWeight);
			pstmt.setString(4, sequence.getMoNumber());
			pstmt.setString(5, "000");
			count = pstmt.executeUpdate();

			if (count > 0) {
				updated = true;
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return updated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.dao.ItemDAO#updateItemweight(float,
	 * com.gavs.hishear.web.domain.Sequence)
	 */
	public boolean updateItemweight(float itemWeight, Sequence sequence)
			throws Exception {
		int count = 0;
		Connection connection = null;
		boolean updated = false;
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(sequence
					.getQuery());
			pstmt.setFloat(1, itemWeight);
			pstmt.setString(2, sequence.getMoNumber());
			pstmt.setString(3, "000");
			pstmt.setString(4, sequence.getPartNumber());
			pstmt.setString(5, sequence.getSequence());
			count = pstmt.executeUpdate();

			if (count > 0) {
				updated = true;
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return updated;
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

	// Begin - WO#25255 - Report weight processed for manually entered quantity
	// - VP - Infosys - 05-May-2011
	// TODO CHange the query to retrieve the most recent average weight
	/**
	 * Gets the recent average weight.
	 * 
	 * @param partNumber
	 *            the part number
	 * @param sequenceNumber
	 *            the sequence number
	 * @param moNumber
	 *            the mo number
	 * @param lineNumber
	 *            the line number
	 * @return the recent average weight
	 * @throws exception
	 */
	public float getRecentAverageWeight(String partNumber,
			String sequenceNumber, String moNumber, String lineNumber,
			String query) throws Exception {
		float averageWeight = 0.0f;
		try {
			averageWeight = ((Float) this.jdbcTemplate.queryForObject(query,
					new Object[] { moNumber, lineNumber, sequenceNumber },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							return rs.getFloat("ItemWeight");
						}
					})).floatValue();

		} catch (EmptyResultDataAccessException e1) {
			e1.getStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			// throw new ShopFloorSystemException(e.getMessage());
		}

		return averageWeight;
	}
	// End - WO#25255 - Report weight processed for manually entered quantity -
	// VP - Infosys - 05-May-2011
}
