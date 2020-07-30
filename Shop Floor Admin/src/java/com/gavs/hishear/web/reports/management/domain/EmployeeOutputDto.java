/**
 * 
 */
package com.gavs.hishear.web.reports.management.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author subhashri
 * 
 */
public class EmployeeOutputDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	private String displayName;

	private String department;

	private String fromDate;

	private String toDate;

	private ArrayList departments;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public ArrayList getDepartments() {
		return departments;
	}

	public void setDepartments(ArrayList departments) {
		this.departments = departments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
