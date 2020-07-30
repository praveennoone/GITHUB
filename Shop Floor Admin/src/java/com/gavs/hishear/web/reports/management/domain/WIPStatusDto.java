package com.gavs.hishear.web.reports.management.domain;

import java.util.List;

public class WIPStatusDto implements Comparable {
	private String userName;
	private String displayName;

	private String factory;
	private String partNumber;

	private List matchingPartNumbers;
	private List wipStatusReport;

	private String query;

	private String moNumber;
	private String lineNumber;
	private String completedSequence;
	private String latestPickQty;
	private String processingSequence;
	private String processingQty;
	private String department;
	private String location;
	private int priority;
	private String itemNumber;
	private String itemDescription;

	public String getCompletedSequence() {
		return completedSequence;
	}

	public void setCompletedSequence(String completedSequence) {
		this.completedSequence = completedSequence;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLatestPickQty() {
		return latestPickQty;
	}

	public void setLatestPickQty(String latestPickQty) {
		this.latestPickQty = latestPickQty;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMoNumber() {
		return moNumber;
	}

	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	public String getProcessingSequence() {
		return processingSequence;
	}

	public void setProcessingSequence(String processingSequence) {
		this.processingSequence = processingSequence;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List getMatchingPartNumbers() {
		return matchingPartNumbers;
	}

	public void setMatchingPartNumbers(List matchingPartNumbers) {
		this.matchingPartNumbers = matchingPartNumbers;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List getWipStatusReport() {
		return wipStatusReport;
	}

	public void setWipStatusReport(List wipStatusReport) {
		this.wipStatusReport = wipStatusReport;
	}

	public String getProcessingQty() {
		return processingQty;
	}

	public void setProcessingQty(String processingQty) {
		this.processingQty = processingQty;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(Object arg) {
		WIPStatusDto dto = (WIPStatusDto) arg;
		if (this.getPriority() > dto.getPriority()) {
			return 1;
		}
		return -1;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
}
