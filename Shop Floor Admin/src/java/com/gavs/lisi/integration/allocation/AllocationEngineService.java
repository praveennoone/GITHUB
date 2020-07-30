// File:         AllocationEngineService.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.lisi.integration.allocation;

/**
 * The Interface AllocationEngineService.
 * 
 */
public interface AllocationEngineService {

	/**
	 * Do physical allocation.
	 * 
	 * @param itemNumber
	 *            the item number
	 * @param facility
	 *            the facility
	 * @param allocationTimeFence
	 *            the allocation time fence
	 * @param minimumStockAllocationPercentage
	 *            the minimum stock allocation percentage
	 * @param ignorableShippedQuantityPercentage
	 *            the ignorable shipped quantity percentage
	 * @param thresholdPrice
	 *            the threshold price
	 * @param ignoredLocations
	 *            the ignored locations
	 * @param leastAllocationPriorityLocation
	 *            the least allocation priority location
	 * @param considerCustomerPreferences
	 *            the consider customer preferences
	 * @throws Exception
	 *             the exception
	 */
	public void doPhysicalAllocation(String itemNumber, String facility,
			int allocationTimeFence, int minimumStockAllocationPercentage,
			int ignorableShippedQuantityPercentage, int thresholdPrice,
			String ignoredLocations, String leastAllocationPriorityLocation,
			String considerCustomerPreferences) throws Exception;
}
