package com.gavs.hishear.web.domain;

public class FSTIResult {
	private boolean executionStatus;
	private String message;

	public boolean isExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(boolean executionStatus) {
		this.executionStatus = executionStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * creates a new instance of FSTIResult
	 */
	public FSTIResult(boolean executionStatus, String message) {
		super();
		this.executionStatus = executionStatus;
		this.message = message;
	}

	/**
	 * creates a new instance of FSTIResult
	 */
	public FSTIResult() {

	}
}
