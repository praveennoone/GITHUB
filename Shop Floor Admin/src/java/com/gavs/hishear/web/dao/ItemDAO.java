// File:         ItemDAO.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.dao;

import java.util.List;

import com.gavs.hishear.web.domain.Item;
import com.gavs.hishear.web.domain.Sequence;

/**
 * The Interface ItemDAO.
 */
public interface ItemDAO {

	/**
	 * Validate item number.
	 * 
	 * @param itemNumber
	 *            the item number
	 * @param query
	 *            the query
	 * @return the item
	 * @throws Exception
	 *             the exception
	 */
	public Item validateItemNumber(String itemNumber, String query)
			throws Exception;

	/**
	 * Reset item weight.
	 * 
	 * @param item
	 *            the item
	 * @throws Exception
	 *             the exception
	 */
	public void resetItemWeight(Item item) throws Exception;

	/**
	 * Gets the item details.
	 * 
	 * @param sequence
	 *            the sequence
	 * @return the item details
	 * @throws Exception
	 *             the exception
	 */
	public Item getItemDetails(Sequence sequence) throws Exception;

	/**
	 * Gets the item details.
	 * 
	 * @param item
	 *            the item
	 * @param query
	 *            the query
	 * @return the item details
	 */
	public void getItemDetails(Item item, String query);

	/**
	 * Check item weight.
	 * 
	 * @param sequence
	 *            the sequence
	 * @return the item
	 * @throws Exception
	 *             the exception
	 */
	public Item checkItemWeight(Sequence sequence) throws Exception;

	/**
	 * Insert itemweight.
	 * 
	 * @param itemWeight
	 *            the item weight
	 * @param sequence
	 *            the sequence
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean insertItemweight(float itemWeight, Sequence sequence)
			throws Exception;

	/**
	 * Update itemweight.
	 * 
	 * @param itemWeight
	 *            the item weight
	 * @param sequence
	 *            the sequence
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean updateItemweight(float itemWeight, Sequence sequence)
			throws Exception;

	/**
	 * Gets the parent and component details.
	 * 
	 * @param partNumber
	 *            the part number
	 * @param query
	 *            the query
	 * @return the parent and component details
	 */
	public List getParentAndComponentDetails(String partNumber, String query);

	/**
	 * Gets the inventory quantity.
	 * 
	 * @param partNumber
	 *            the part number
	 * @param query
	 *            the query
	 * @return the inventory quantity
	 */
	public List getInventoryQuantity(String partNumber, String query);

	// Begin - WO#25255 - Report weight processed for manually entered quantity
	// - VP - Infosys - 05-May-2011
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
			String query) throws Exception;
	// End - WO#25255 - Report weight processed for manually entered quantity -
	// VP - Infosys - 05-May-2011
}
