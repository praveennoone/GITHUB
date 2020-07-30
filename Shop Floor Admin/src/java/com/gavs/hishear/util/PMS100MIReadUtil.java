// File:         PMS100MIReadUtil.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.M3APIParameter;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.web.domain.Sequence;

/**
 * The Class PMS100MIReadUtil.
 * 
 */
public final class PMS100MIReadUtil {

	// Utility classes should not have a public or default constructor.
	private PMS100MIReadUtil() {
		super();
	}

	/** The result. */
	private static boolean result;

	/**
	 * Gets the operation.
	 * 
	 * @param dto
	 *            the dto
	 * @param client
	 *            the client
	 * @return the operation
	 */
	public static ArrayList getOperation(Sequence dto, M3APIWSClient client) {

		System.out.println("getOperation//////////////");
		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMWOHEDX2Item");
		parameter.setOutputItem("GETMWOHEDX2ResponseItem");
		parameter.setFunctionName("getDetailsForMONumber");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), client.getApplicationPropertyBean().getCompany());
		map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());

		parameter.setInputData(map);

		MVXDTAMI mvxdtami = null;
		List list = null;

		try {
			mvxdtami = (MVXDTAMI) client.callM3API(parameter);

			parameter = new M3APIParameter();
			parameter.setWebServiceName("PMS100MIRead");
			parameter.setInputItem("SelOperationsItem");
			parameter.setOutputItem("SelOperationsResponseItem");
			parameter.setFunctionName("selOperations");

			map = new HashMap<String, String>();
			map.put(M3Parameter.CONO.getValue(), client
					.getApplicationPropertyBean().getCompany());
			map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());
			map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());
			map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
			parameter.setInputData(map);
			list = (List) client.callM3API(parameter);
			System.out.println("BOM List size--->" + list.size());

		} catch (Exception e) {

			e.printStackTrace();

		}

		return (ArrayList) list;

	}

	/**
	 * Gets the operation by facility.
	 * 
	 * @param dto
	 *            the dto
	 * @param client
	 *            the client
	 * @return the operation by facility
	 * @throws Exception
	 *             the exception
	 */
	public static ArrayList getOperationByFacility(Sequence dto,
			M3APIWSClient client) throws Exception {

		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMWOHEDX2Item");
		parameter.setOutputItem("GETMWOHEDX2ResponseItem");
		parameter.setFunctionName("getDetailsForMONumber");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), client
				.getApplicationPropertyBean().getCompany());
		map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());

		parameter.setInputData(map);

		MVXDTAMI mvxdtami = null;
		List list = null;

		mvxdtami = (MVXDTAMI) client.callM3API(parameter);

		System.out.println("dto.getUserFacility() 333333333        = "
				+ dto.getUserFacility());
		parameter = new M3APIParameter();
		parameter.setWebServiceName("PMS100MIRead");
		parameter.setInputItem("SelOperationsItem");
		parameter.setOutputItem("SelOperationsResponseItem");
		parameter.setFunctionName("selOperations");

		map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), client
				.getApplicationPropertyBean().getCompany());
		map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());
		map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());
		map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
		System.out.println("mvxdtami.getFacility() ================== "
				+ mvxdtami.getFacility());
		parameter.setInputData(map);
		list = (List) client.callM3API(parameter);
		System.out.println("BOM List size--->" + list.size());

		return (ArrayList) list;

	}

	/**
	 * Checks if is valid facility.
	 * 
	 * @param dto
	 *            the dto
	 * @param mvxdtami
	 *            the mvxdtami
	 * @return true, if is valid facility
	 */
	public static boolean isValidFacility(Sequence dto, MVXDTAMI mvxdtami) {

		if (dto.getUserFacility().equals(mvxdtami.getFacility())) {
			result = true;
		} else {
			result = false;
		}
		return result;

	}

	/**
	 * Gets the materials.
	 * 
	 * @param company
	 *            the company
	 * @param moNumber
	 *            the mo number
	 * @param client
	 *            the client
	 * @return the materials
	 */
	@SuppressWarnings("unchecked")
	public static List<Sequence> getMaterials(String company, String moNumber,
			M3APIWSClient client) {
		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMWOHEDX2Item");
		parameter.setOutputItem("GETMWOHEDX2ResponseItem");
		parameter.setFunctionName("getDetailsForMONumber");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), company);
		map.put(M3Parameter.MFNO.getValue(), moNumber);

		parameter.setInputData(map);

		MVXDTAMI mvxdtami = null;
		List<Sequence> materials = null;

		try {
			mvxdtami = (MVXDTAMI) client.callM3API(parameter);

			parameter = new M3APIParameter();
			parameter.setWebServiceName("PMS100MIRead");
			parameter.setInputItem("SelMaterialsItem");
			parameter.setOutputItem("SelMaterialsResponseItem");
			parameter.setFunctionName("selMaterials");

			map = new HashMap<String, String>();
			map.put(M3Parameter.CONO.getValue(), company);
			map.put(M3Parameter.MFNO.getValue(), moNumber);
			map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());
			map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
			parameter.setInputData(map);
			materials = (List<Sequence>) client.callM3API(parameter);
			System.out.println(" @@@@@@@@@@@@@@@@ SelMaterials size--->"
					+ materials.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return materials;
	}

	/**
	 * Gets the mO facility.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param client
	 *            the client
	 * @return the mO facility
	 */
	public static String getMOFacility(String moNumber, M3APIWSClient client) {
		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMWOHEDX2Item");
		parameter.setOutputItem("GETMWOHEDX2ResponseItem");
		parameter.setFunctionName("getDetailsForMONumber");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), client
				.getApplicationPropertyBean().getCompany());
		map.put(M3Parameter.MFNO.getValue(), moNumber);

		parameter.setInputData(map);

		MVXDTAMI mvxdtami = null;

		try {
			mvxdtami = (MVXDTAMI) client.callM3API(parameter);

			return mvxdtami.getFacility();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
