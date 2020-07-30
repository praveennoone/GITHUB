/**
 * 
 */
package com.gavs.hishear.web.domain;

import java.util.Date;

/**
 * @author rahjeshd
 * 
 */
public class FourthShiftData {
	private String moNumber;
	private String lineNumber;
	private String sequenceNumber;
	private String componentNumber;
	private Date pickTime;
	private String parentNumber;
	private double requiredQty;
	private String qtyType;

	/**
	 * @return the moNumber
	 */
	public String getMoNumber() {
		return moNumber;
	}

	/**
	 * @param moNumber
	 *            the moNumber to set
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	/**
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the componentNumber
	 */
	public String getComponentNumber() {
		return componentNumber;
	}

	/**
	 * @param componentNumber
	 *            the componentNumber to set
	 */
	public void setComponentNumber(String componentNumber) {
		this.componentNumber = componentNumber;
	}

	/**
	 * @return the pickTime
	 */
	public Date getPickTime() {
		return pickTime;
	}

	/**
	 * @param pickTime
	 *            the pickTime to set
	 */
	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}

	/**
	 * @return the parentNumber
	 */
	public String getParentNumber() {
		return parentNumber;
	}

	/**
	 * @param parentNumber
	 *            the parentNumber to set
	 */
	public void setParentNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}

	/**
	 * @return the requiredQty
	 */
	public double getRequiredQty() {
		return requiredQty;
	}

	/**
	 * @param requiredQty
	 *            the requiredQty to set
	 */
	public void setRequiredQty(double requiredQty) {
		this.requiredQty = requiredQty;
	}

	/**
	 * @return the qtyType
	 */
	public String getQtyType() {
		return qtyType;
	}

	/**
	 * @param qtyType
	 *            the qtyType to set
	 */
	public void setQtyType(String qtyType) {
		this.qtyType = qtyType;
	}
}