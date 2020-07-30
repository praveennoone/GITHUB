package com.gavs.hishear.web.comparator;

import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;

import com.gavs.hishear.web.domain.ErrorLog;

/**
 * @author mohammeda
 * 
 */
public class M3TransactionComparator implements Comparator<ErrorLog> {

	private String comparableString = null;

	public M3TransactionComparator(String comparableString) {
		this.comparableString = comparableString;
	}

	@Override
	public int compare(ErrorLog arg0, ErrorLog arg1) {
		int headerCount = ArrayUtils.indexOf(
				arg0.getColumnHeaders().split(","), comparableString);
		if (headerCount != -1) {
			return arg0.getColumnValues().split(",")[headerCount]
					.compareTo(arg1.getColumnValues().split(",")[headerCount]);
		} else {
			return 0;
		}
	}

}
