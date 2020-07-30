// File:         AllocationEngineServiceImpl.java
// Created:      Feb 23, 2011
// Author:       mohammeda
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.lisi.integration.allocation;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.lisi.allocationEngine.services.AllocationEngineService;

/**
 * The Class AllocationEngineServiceImpl.
 * 
 */
public class AllocationEngineServiceImpl implements
		com.gavs.lisi.integration.allocation.AllocationEngineService {

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.gavs.lisi.integration.allocation.AllocationEngineService#
	 * doPhysicalAllocation(java.lang.String, java.lang.String, int, int, int,
	 * int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void doPhysicalAllocation(String itemNumber, String facility,
			int allocationTimeFence, int minimumStockAllocationPercentage,
			int ignorableShippedQuantityPercentage, int thresholdPrice,
			String ignoredLocations, String leastAllocationPriorityLocation,
			String considerCustomerPreferences) throws Exception {
		AllocationEngineService client = createConnectionIntoAllocationEngine();
		try {
			client.doPhysicalAllocation(itemNumber, facility,
					allocationTimeFence, minimumStockAllocationPercentage,
					ignorableShippedQuantityPercentage, thresholdPrice,
					ignoredLocations, leastAllocationPriorityLocation,
					considerCustomerPreferences);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Common method to create instance for the AllocationEngineService.
	 * 
	 * @return the allocation engine service
	 */
	private AllocationEngineService createConnectionIntoAllocationEngine() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(AllocationEngineService.class);
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		// factory.setAddress(applicationPropertyBean
		// .getAllocationEngineServicePath());
		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
		return (AllocationEngineService) factory.create();
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

}
