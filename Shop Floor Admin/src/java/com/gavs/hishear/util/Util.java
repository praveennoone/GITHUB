package com.gavs.hishear.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.util.HtmlUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;

public final class Util {

	private static final int MINS_PER_HOUR = 60;
	private static final double MILS_PER_HOUR = 3600000.00;
	private static final int SECS_PER_HOUR = 3600;

	// Utility classes should not have a public or default constructor.
	private Util() {
		super();
	}

	static final DecimalFormat decfmt = new DecimalFormat("0.00");

	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String getActualHrs(String acthrs) {
		// String actHrs ="";
		try {
			if ((acthrs != null) && !(" ".equals(acthrs))) {
				float fHrs = Float.valueOf(acthrs.trim()).floatValue();

				String sHrs = (String) decfmt.format(fHrs);

				String temphrs = sHrs.substring(0, sHrs.length() - 3);
				String tempmin = sHrs.substring(sHrs.length() - 2, sHrs
						.length());

				float fmins = Float.valueOf(tempmin).floatValue() / 100;

				float fv = (float) fmins;
				fv = fv * MINS_PER_HOUR / 100;
				String sMins = (String) decfmt.format(fv);

				tempmin = sMins.substring(sMins.length() - 2, sMins.length());

				acthrs = temphrs + ":" + tempmin;

			} else {

				acthrs = "00:00";

			}

		} catch (Exception e) {
			System.out.print("Exception ;" + e);
		}
		return acthrs;
	}

	public static float getfloatActualHrs(String acthrs) {
		float fActHrs = 0.0f;
		try {
			if (!acthrs.equals("") && (acthrs != null)) {
				float fHrs = Float.valueOf(acthrs.trim()).floatValue();

				String sHrs = (String) decfmt.format(fHrs);

				String temphrs = sHrs.substring(0, sHrs.length() - 3);
				String tempmin = sHrs.substring(sHrs.length() - 2, sHrs
						.length());

				float fmins = Float.valueOf(tempmin).floatValue() / 100;

				float fv = (float) fmins;
				fv = fv * MINS_PER_HOUR / 100;
				String sMins = (String) decfmt.format(fv);

				tempmin = sMins.substring(sMins.length() - 2, sMins.length());

				acthrs = temphrs + "." + tempmin;
				fActHrs = Float.valueOf(acthrs).floatValue();

			}
		} catch (Exception e) {
			System.out.print("Exception ;" + e);
		}
		return fActHrs;
	}

	public static String getTodDateForShift(String reportDate, String shift,
			String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date dtReportDate = null;
		try {
			dtReportDate = dateFormat.parse(reportDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dtReportDate);

		if ("B".equalsIgnoreCase(shift) || "D".equalsIgnoreCase(shift)) {
			calendar.add(Calendar.DATE, 1);
		}

		String date = dateFormat.format(calendar.getTime());

		return date;
	}

	public static String trimMessage(String message) {
		// Bug fix. Rahjesh. Mar 25, 2010.
		if (message != null && message.contains(":")) {
			String[] errorMsg = message.split(":");
			if (errorMsg[1] != null) {
				String[] messageArray = errorMsg[1].split(" ");
				String afterRemovingSpace = "";
				for (int i = 0; i < messageArray.length; i++) {
					if (!messageArray[i].trim().equals("")) {
						afterRemovingSpace = afterRemovingSpace + " "
								+ messageArray[i].trim();
					}
				}
				// afterRemovingSpace =

				if (afterRemovingSpace.trim().equals("")) {
					afterRemovingSpace = "No data found";
				}
				return afterRemovingSpace.trim();
			} else {
				return "No data found";
			}
		} else {
			return message;
		}
	}

	private static double convertCalculatedTimeToHours(
			double calculatedTimeInSecs) {
		int decimalPlace = 2;
		double calculatedTimeInHrs = calculatedTimeInSecs / SECS_PER_HOUR;
		return roundDBL(calculatedTimeInHrs, decimalPlace);
	}

	public static double roundDBL(double targetDBL, int decimalPlace) {
		BigDecimal bd = new BigDecimal(targetDBL);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_EVEN);
		return (bd.doubleValue());
	}

	public static String roundString(String targetDBL, int decimalPlace) {
		BigDecimal bd = new BigDecimal(targetDBL);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_EVEN);
		return (bd.toString());
	}

	public static String decode(String rawString) {
		rawString = rawString.replace("\\", "\\\\");
		return HtmlUtils.htmlEscape(rawString);
	}

	/*
	 * public static double calculatePPH(int qty, Date logineTime, Date
	 * logoffTime) { // double hourFactor = 1000.0 60 60; // double pph = qty /
	 * ((logoffTime.getTime() - logineTime.getTime()) / hourFactor); double
	 * partsPerHour = currentActivity.getPriceTimeQty() /
	 * currentActivity.getRunTime(); return pph; }
	 */
	public static double calculatePPH(double runTime, double priceTimeQty) {
		try {
			double partsPerHour = priceTimeQty / runTime;
			if (!NumberUtils.isNumber(String.valueOf(partsPerHour))) {
				return 0.0;
			}
			return partsPerHour;
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * Calculate the actual Run Time. It is the Run time - Break Time - Retool
	 * Time
	 * 
	 * @param sequenceDAO
	 * @param activityNumber
	 * @param logonDate
	 * @param logoffDate
	 * @param userContext
	 * @return
	 */
	public static double calulateActualRunTime(SequenceDAO sequenceDAO,
			String activityNumber, Date logonDate, Date logoffDate,
			UserContext userContext) {
		float breakTime = sequenceDAO.getBreakTime((String) userContext
				.getQueries().get("SHOP_153"), activityNumber, logonDate,
				logoffDate);
		System.out.println("Break Time ->" + breakTime);

		float retoolTime = sequenceDAO.getRetoolTime((String) userContext
				.getQueries().get("SHOP_154"), activityNumber, logonDate,
				logoffDate);
		System.out.println("Retool Time ->" + retoolTime);

		long timeDifference = logoffDate.getTime() - logonDate.getTime();

		double timeDifferenceInHours = (timeDifference / MILS_PER_HOUR)
				- (breakTime / SECS_PER_HOUR) - (retoolTime / SECS_PER_HOUR);
		System.out.println("timeDifferenceInHours = " + timeDifferenceInHours);
		return timeDifferenceInHours;
	}

}
