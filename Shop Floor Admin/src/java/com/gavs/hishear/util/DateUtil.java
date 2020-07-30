/**
 * 
 */
package com.gavs.hishear.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @author sundarrajanr
 * 
 */
public class DateUtil {

	private static final int MINUTES_PER_HOUR = 60;
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int MILS_PER_SECOND = 1000;
	private static final String DEFAULT_DATE_FORMAT = "MM-dd-yyyy hh:mm:ss aa";
	private static final String EMPTY_STRING = "";
	private static final Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String date = dateFormat.format(new Date());
		return date;
	}

	public static String getDateFormat(String date, String format) {
		Date d = new Date(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String formatdate = dateFormat.format(d);
		return formatdate;

	}

	public static String getLogOnDetailsDateFormat(String date, String format) {
		String formatdate = EMPTY_STRING;
		SimpleDateFormat sdfInput = new SimpleDateFormat(format);
		SimpleDateFormat sdfOutput = new SimpleDateFormat(format);

		Date inputdate;
		try {
			inputdate = sdfInput.parse(date);
			formatdate = sdfOutput.format(inputdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatdate;
	}

	public String getFormattedDate(String date) {
		String month = date.substring(0, 2);
		String d = date.substring(3, 5);
		String year = date.substring(6);

		return year + "-" + month + "-" + d;

	}

	public String getDiffDate(String date1, String date2) {

		String diffDate = null;
		StringTokenizer tokenizer = null;

		tokenizer = new StringTokenizer(date1, ":");

		Date date11 = new Date();
		date11.setHours(Integer.parseInt(tokenizer.nextToken()));
		date11.setMinutes(Integer.parseInt(tokenizer.nextToken()));
		date11.setSeconds(Integer.parseInt(tokenizer.nextToken()));

		tokenizer = new StringTokenizer(date2, ":");
		Date date21 = new Date();
		date21.setHours(Integer.parseInt(tokenizer.nextToken()));
		date21.setMinutes(Integer.parseInt(tokenizer.nextToken()));
		date21.setSeconds(Integer.parseInt(tokenizer.nextToken()));

		long hours = ((date11.getTime() - date21.getTime()) / (MILS_PER_SECOND * SECONDS_PER_MINUTE))
				/ MINUTES_PER_HOUR;
		long minutes = ((date11.getTime() - date21.getTime()) / (MILS_PER_SECOND * SECONDS_PER_MINUTE))
				% MINUTES_PER_HOUR;

		System.out.println("Total Minutes  --> "
				+ (date11.getTime() - date21.getTime())
				/ (MILS_PER_SECOND * SECONDS_PER_MINUTE));
		System.out
				.println("Hours --> "
						+ ((date11.getTime() - date21.getTime()) / (MILS_PER_SECOND * SECONDS_PER_MINUTE))
						/ MINUTES_PER_HOUR);
		System.out
				.println("minutes --> "
						+ ((date11.getTime() - date21.getTime()) / (MILS_PER_SECOND * SECONDS_PER_MINUTE))
						% MINUTES_PER_HOUR);

		diffDate = hours + ":" + minutes;

		return diffDate;
	}

	public static String getHoursMinutes(int seconds) {

		String HoursMinutes = null;
		int minutes = seconds / SECONDS_PER_MINUTE;
		HoursMinutes = (minutes / MINUTES_PER_HOUR) + ":"
				+ (minutes % MINUTES_PER_HOUR);
		return HoursMinutes;
	}

	public static String getStartDateOfMonth(String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		return new SimpleDateFormat(format).format(new Date(calendar
				.getTimeInMillis()));

	}

	public static Date formatDate(String inputDate, String format)
			throws Exception {
		DateFormat formatter = new SimpleDateFormat(format);
		Date outputDate = null;
		outputDate = (Date) formatter.parse(inputDate);
		System.out.println("Output Date ====" + outputDate);
		return outputDate;
	}

	public static Date parseDate(Date inputDate) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// DateFormat formatter = new SimpleDateFormat(format);
		Date outputDate = null;
		String formattedDate = df1.format(inputDate);
		try {
			// outputDate = (Date)formatter.parse(inputDate);
			outputDate = df1.parse(formattedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputDate;
	}

	public static long getSecondsDifference(Date earlyDate, Date lateDate) {
		long diff = (lateDate.getTime() - earlyDate.getTime());
		return diff;
	}

	public static int getSecondsBetween(Date earlyDate, Date lateDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		System.out.println("DateUtil.......................");
		System.out.println("date1=" + earlyDate);
		System.out.println("date2=" + lateDate);
		String formattedDate = df.format(lateDate);
		System.out.println("String formattedDate==" + formattedDate);
		Date date = null;
		try {
			date = df.parse(formattedDate);
			System.out.println("Parsed Date =" + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diff = earlyDate.getTime() - date.getTime();
		int days = (int) Math.floor(diff / 1000);
		return Math.abs(days);
	}

	public static String uiDateFormat(Date inputDate, String format) {
		if (inputDate != null) {
			DateFormat formatter = new SimpleDateFormat(format);
			String strDate = null;
			try {
				strDate = formatter.format(inputDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return strDate;
		} else {
			return EMPTY_STRING;
		}
	}

	public static long timeStampValidation(Date date) {
		long value = 0;
		if (date == null || date.equals(EMPTY_STRING)) {
			value = 0;
		} else {
			value = date.getTime();
		}
		return value;
	}

	/**
	 * Get the Date Begining ie 00:00 hours of the Source Date
	 * 
	 * @param sourceDate
	 * @param sourceFormat
	 * @return
	 */
	public static Date getDayBegining(String sourceDate, String sourceFormat) {
		try {
			Date dt = DateUtil.formatDate(sourceDate, sourceFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.AM_PM, calendar.AM);
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get the Date Begining ie 00:00 hours of the Source Date
	 * 
	 * @param sourceDate
	 * @param sourceFormat
	 * @return
	 */
	public static Date getDayBegining(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, calendar.AM);
		return calendar.getTime();
	}

	/**
	 * Get the Date end ie 24:00 hours of the Source Date
	 * 
	 * @param sourceDate
	 * @param sourceFormat
	 * @return
	 */
	public static Date getDayEnd(String sourceDate, String sourceFormat) {
		try {
			Date dt = DateUtil.formatDate(sourceDate, sourceFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.AM_PM, calendar.PM);
			return calendar.getTime();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Get the Date Begining ie 00:00 hours of the Source Date
	 * 
	 * @param sourceDate
	 * @param sourceFormat
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.AM_PM, calendar.AM);
		return calendar.getTime();
	}

	/**
	 * Ramanan.M : Method to change the Date/Time format from 24 Hours to 12
	 * Hours AM/PM Format.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateFormatToAMPM(String date, String format) {
		if (date != null) {
			SimpleDateFormat dfrm = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss.S");
			Date dat = null;
			try {
				dat = dfrm.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			String formatdate = dateFormat.format(dat);
			return formatdate;
		} else {
			return EMPTY_STRING;
		}

	}

	/**
	 * Returns validated timestamp for input date or null if input date is null.
	 * 
	 * @param date
	 *            - input date
	 * @return Timestamp - validated timestamp for input date or null if input
	 *         date is null
	 */
	public static Timestamp getTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(timeStampValidation(date));

	}

	/**
	 * Transforms date to string or to <code>default value</code> if input date
	 * is <code>null</code>.
	 * 
	 * @param date
	 *            - input date
	 * @param defaultVal
	 *            - value to return if input date is <code>null</code>
	 * @param dateFormat
	 *            - format of date
	 * @return String representation of input date or <code>default value</code>
	 *         if input date is <code>null</code>
	 */
	public static String dateAsDefaultString(final Date date,
			final String defaultVal, String dateFormat) {
		if (date == null) {
			return defaultVal;
		}
		return String.valueOf(uiDateFormat(date, dateFormat));

	}

	/**
	 * Transforms date to string or to <code>default value</code> if input date
	 * is <code>null</code>.
	 * 
	 * @param date
	 *            - input date
	 * @param defaultVal
	 *            - value to return if input date is <code>null</code>
	 * @return String representation of input date or <code>default value</code>
	 *         if input date is <code>null</code>
	 */
	public static String dateAsDefaultString(final Date date,
			final String defaultVal) {
		return dateAsDefaultString(date, defaultVal, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Transforms date to string or to empty string if input date is
	 * <code>null</code>.
	 * 
	 * @param date
	 *            - input date
	 * @return String representation of input date or empty string if input date
	 *         is <code>null</code>
	 */
	public static String dateAsDefaultString(final Date date) {
		return dateAsDefaultString(date, EMPTY_STRING);
	}
}