package com.gavs.hishear.m3interface;

import java.util.Map.Entry;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import com.gavs.hishear.m3interface.dto.M3Entity;

public class M3ErrorLogger {
	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";
	private static final String PARAMETERS = "Parameters: ";
	private static final String FUNCTION_NAME = "Function name: ";
	private static final String SERVICE_NAME = "Service name: ";
	private static final String M3_PARAMETER_NOT_FOUND = "M3 parameter not found";
	private static final String ERROR_MESSAGE = "Error appeared during M3 API call: ";
	private static final Logger logger = Logger.getLogger(M3APIWSClient.class);

	private static final StringBuilder lastErrorBuilder = new StringBuilder();
	private static String lastM3Error;

	public static <T extends M3Entity> void logM3Error(AxisFault exception,
			T parameter) {
		logger.error(createErrorMessage(exception, parameter));
	}

	private static <T extends M3Entity> String createErrorMessage(
			AxisFault exception, T parameter) {
		lastErrorBuilder.setLength(0);
		lastErrorBuilder.append(ERROR_MESSAGE);
		lastErrorBuilder.append(NEW_LINE);
		lastErrorBuilder.append(exception.getMessage());
		lastErrorBuilder.append(NEW_LINE);
		if (parameter == null) {
			lastErrorBuilder.append(M3_PARAMETER_NOT_FOUND);
			lastM3Error = lastErrorBuilder.toString();
			return lastM3Error;
		}

		lastErrorBuilder.append(SERVICE_NAME).append(
				parameter.getWebServiceName());
		lastErrorBuilder.append(NEW_LINE);
		lastErrorBuilder.append(FUNCTION_NAME).append(
				parameter.getFunctionName());
		lastErrorBuilder.append(NEW_LINE);
		lastM3Error = lastErrorBuilder.toString();

		lastErrorBuilder.append(PARAMETERS);
		for (Entry<String, String> param : parameter.getInputData().entrySet()) {
			lastErrorBuilder.append(param.getKey()).append("=").append(
					param.getValue()).append(SPACE);
		}

		return lastErrorBuilder.toString();

	}

	public static String getLastM3Error() {
		return lastM3Error;
	}
}
