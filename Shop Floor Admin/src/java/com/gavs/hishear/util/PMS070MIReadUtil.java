/**
 * 
 */
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
 * @author saravanam
 * 
 */
public final class PMS070MIReadUtil {

	// Utility classes should not have a public or default constructor.
	private PMS070MIReadUtil() {
		super();
	}

	public static ArrayList getOperation(Sequence dto, M3APIWSClient client) {

		M3APIParameter parameter = new M3APIParameter();
		parameter.setWebServiceName("MVXDTAMIRead");
		parameter.setInputItem("GETMWOHEDX2Item");
		parameter.setOutputItem("GETMWOHEDX2ResponseItem");
		parameter.setFunctionName("getDetailsForMONumber");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(M3Parameter.CONO.getValue(), dto.getCompany());
		map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());

		parameter.setInputData(map);

		MVXDTAMI mvxdtami = null;
		List list = null;
		try {
			mvxdtami = (MVXDTAMI) client.callM3API(parameter);
			parameter = new M3APIParameter();
			parameter.setWebServiceName("PMS070MIRead");
			parameter.setInputItem("GetOperationItem");
			parameter.setOutputItem("GetOperationResponseItem");
			parameter.setFunctionName("getOperation");

			map = new HashMap<String, String>();
			map.put(M3Parameter.CONO.getValue(), dto.getCompany());
			map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());
			map.put(M3Parameter.PRNO.getValue(), mvxdtami.getProductNumber());
			map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
			map.put(M3Parameter.OPNO.getValue(), dto.getSequence());

			dto.setPartNumber(mvxdtami.getProductNumber());
			parameter.setInputData(map);
			list = (List) client.callM3API(parameter);

		} catch (Exception e) {
			list = new ArrayList();
			Sequence seq = new Sequence();
			seq.setErrorMessage(e.getMessage());
			e.printStackTrace();
			list.add(seq);
		}

		return (ArrayList) list;

	}
}
