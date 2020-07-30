/**
 * 
 */
package com.gavs.hishear.util;

import java.util.HashMap;

import com.gavs.hishear.constant.M3Parameter;
import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.M3APIParameter;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.web.domain.Sequence;

/**
 * @author saravanam
 * 
 */
public final class PMS070MIWriteUtil {

	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";
	private static final String ONE = "1";

	// Utility classes should not have a public or default constructor.
	private PMS070MIWriteUtil() {
		super();
	}

	public static String pickSequenceInM3(Sequence dto, M3APIWSClient client) {

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
		String message = null;
		try {
			mvxdtami = (MVXDTAMI) client.callM3API(parameter);
			System.out.println("Facility===>" + mvxdtami.getFacility());
			parameter = new M3APIParameter();
			parameter.setWebServiceName("PMZ070MIWrite");
			parameter.setInputItem("RptOperationItem");
			parameter.setOutputItem("RptOperationResponseItem");
			parameter.setFunctionName("rptOperation");

			map = new HashMap<String, String>();
			map.put(M3Parameter.CONO.getValue(), dto.getCompany());
			map.put(M3Parameter.FACI.getValue(), mvxdtami.getFacility());
			map.put(M3Parameter.MAQA.getValue(), String.valueOf((int) dto
					.getQtyCompleted()));
			map.put(M3Parameter.UOM.getValue(), dto.getUOM());
			map.put(M3Parameter.MFNO.getValue(), dto.getMoNumber());
			map.put(M3Parameter.OPNO.getValue(), dto.getSequence());
			map.put(M3Parameter.REND.getValue(), dto.getCompletionFlag());
			map.put(M3Parameter.DSP1.getValue(), ONE); // Suppress warning
														// "multiple of 250..."
			map.put(M3Parameter.DSP2.getValue(), ONE); // Suppress warning
			// "Qty greater than order qty"

			parameter.setInputData(map);

			System.out.println("Input params-->Qty::"
					+ String.valueOf((int) dto.getQtyCompleted()) + "MONO::"
					+ dto.getMoNumber() + "Sequence::" + dto.getSequence());
			System.out.println("Before calling the PMZ070MIWrite webservice");
			message = (String) client.callM3API(parameter);
			System.out.println("message - - - - ->>>" + message);
		} catch (Exception e) {
			message = e.getMessage();

		}
		return message;
	}

	private static String trimMessage(String message) {
		String[] errorMsg = message.split(":");
		if (errorMsg[1] != null) {
			String[] messageArray = errorMsg[1].split(SPACE);
			String afterRemovingSpace = EMPTY_STRING;
			for (int i = 0; i < messageArray.length; i++) {
				if (!messageArray[i].trim().equals(EMPTY_STRING)) {
					afterRemovingSpace = afterRemovingSpace + SPACE
							+ messageArray[i].trim();
				}
			}

			return afterRemovingSpace.trim();
		} else {
			return message;
		}

	}
}
