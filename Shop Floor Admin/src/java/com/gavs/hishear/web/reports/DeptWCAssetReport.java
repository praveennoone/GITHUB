/**
 * 
 */
package com.gavs.hishear.web.reports;

import java.util.ArrayList;

import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.Department;
import com.gavs.hishear.web.domain.WorkCenter;

/**
 * @author rahjeshd
 * 
 */
public class DeptWCAssetReport {

	private String userName;
	private String displayName;
	private String deptNumber;
	private ArrayList<Department> departments;
	private ArrayList<Asset> assets;
	private ArrayList<WorkCenter> workcenters;
	private String query;
	private String newDeptNumber;
	private String addDept;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the departments
	 */
	public ArrayList<Department> getDepartments() {
		return departments;
	}

	/**
	 * @param departments
	 *            the departments to set
	 */
	public void setDepartments(ArrayList<Department> departments) {
		this.departments = departments;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the deptNumber
	 */
	public String getDeptNumber() {
		return deptNumber;
	}

	/**
	 * @param deptNumber
	 *            the deptNumber to set
	 */
	public void setDeptNumber(String deptNumber) {
		this.deptNumber = deptNumber;
	}

	/**
	 * @return the assets
	 */
	public ArrayList<Asset> getAssets() {
		return assets;
	}

	/**
	 * @param assets
	 *            the assets to set
	 */
	public void setAssets(ArrayList<Asset> assets) {
		this.assets = assets;
	}

	/**
	 * @return the newDeptNumber
	 */
	public String getNewDeptNumber() {
		return newDeptNumber;
	}

	/**
	 * @param newDeptNumber
	 *            the newDeptNumber to set
	 */
	public void setNewDeptNumber(String newDeptNumber) {
		this.newDeptNumber = newDeptNumber;
	}

	/**
	 * @return the add
	 */
	public String getAddDept() {
		return addDept;
	}

	/**
	 * @param add
	 *            the add to set
	 */
	public void setAddDept(String add) {
		this.addDept = add;
	}

	/**
	 * @return the workcenters
	 */
	public ArrayList<WorkCenter> getWorkcenters() {
		return workcenters;
	}

	/**
	 * @param workcenters
	 *            the workcenters to set
	 */
	public void setWorkcenters(ArrayList<WorkCenter> workcenters) {
		this.workcenters = workcenters;
	}
}