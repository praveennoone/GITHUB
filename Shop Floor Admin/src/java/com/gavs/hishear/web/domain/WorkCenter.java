/**
 * 
 */
package com.gavs.hishear.web.domain;

import java.util.Date;

/**
 * @author rahjeshd
 * 
 */
public class WorkCenter {
	private String workcenterCode;
	private String workcenterDesc;
	private String moNumber;
	private String lineNumber;
	private String sequencenumber;
	private Date requiredDate;
	private String itemNumber;
	private String itemDesc;
	private int requiredQuantity;
	private String run;
	private String setup;
	/** Capacity */
	private int capacity;
	/** Department */
	private String department;

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the workcenterCode
	 */
	public String getWorkcenterCode() {
		return workcenterCode;
	}

	/**
	 * @param workcenterCode
	 *            the workcenterCode to set
	 */
	public void setWorkcenterCode(String workcenterCode) {
		this.workcenterCode = workcenterCode;
	}

	/**
	 * @return the workcenterDesc
	 */
	public String getWorkcenterDesc() {
		return workcenterDesc;
	}

	/**
	 * @param workcenterDesc
	 *            the workcenterDesc to set
	 */
	public void setWorkcenterDesc(String workcenterDesc) {
		this.workcenterDesc = workcenterDesc;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getMoNumber() {
		return moNumber;
	}

	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	public int getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(int requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getSequencenumber() {
		return sequencenumber;
	}

	public void setSequencenumber(String sequencenumber) {
		this.sequencenumber = sequencenumber;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

}
