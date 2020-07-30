/**
 * FSTIAdapter.java 7:24:22 PMNov 5, 20082008 
 */
package com.gavs.hishear.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.axis.utils.XMLUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.gavs.hishear.web.domain.FSTIResult;

public final class FSTIAdapter {

	private static final Logger logger = Logger.getLogger(FSTIAdapter.class);

	// Utility classes should not have a public or default constructor.
	private FSTIAdapter() {
		super();
	}

	public static FSTIResult callFSTI(String operationName,
			String wsdlLocation, String[] arguements) {

		String portName = null;
		FSTIResult fstiResult = null;
		try {
			portName = operationName.substring(operationName.indexOf("(") + 1,
					operationName.indexOf(")"));
			operationName = operationName.substring(0, operationName
					.indexOf("("));
		} catch (Exception ignored) {
			logger.info(ignored.getMessage());
		}

		DynamicInvoker invoker;
		HashMap map = null;
		try {
			invoker = new DynamicInvoker(wsdlLocation);
			map = invoker.invokeMethod(operationName, portName, arguements);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		if (map != null) {
			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				if (value instanceof Element) {
					XMLUtils.ElementToStream((Element) value, System.out);
				} else {

					String[] result = ((String) value).split("~");

					if (result[0].equals("0") || result[0].equals("-")) {
						fstiResult = new FSTIResult();
						fstiResult.setMessage(result[1]);
						fstiResult.setExecutionStatus(false);
					} else {
						fstiResult = new FSTIResult();
						fstiResult.setMessage(result[1]);
						fstiResult.setExecutionStatus(true);
					}
				}
			}
		}

		if (fstiResult == null) {
			fstiResult = new FSTIResult(false, "-");
		}

		if (!fstiResult.isExecutionStatus()) {
			Logger.getLogger(FSTIAdapter.class).log(
					Priority.FATAL,
					"SFS-Web - FSTI Call Failed: "
							+ fstiResult.getMessage()
							+ "\n"
							+ "Operation Name: "
							+ operationName
							+ "\n"
							+ "Data Passed: \n"
							+ StringUtils.arrayToDelimitedString(arguements,
									"\n"));
		}
		return fstiResult;
	}
}
