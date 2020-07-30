package com.gavs.hishear.web.reports.management.domain;

public class Correction {
	private String correctedBy;
	private String moNumber;
	private String sequenceNumber;
	private String activity;
	private String error;
	private String correction;
	private String completedSequence;
	private String addedSequence;
	private String stampNo;
	private String quantity;
	private String login;
	private String logout;
	private String assetNumber;
	private String operator;
	private String date;
	private String add;

	public Correction(String correctedBy, String moNumber,
			String sequenceNumber, String operator, String activity,
			String error, String correction) {
		super();
		this.correctedBy = correctedBy;
		this.moNumber = moNumber;
		this.sequenceNumber = sequenceNumber;
		this.operator = operator;
		this.activity = activity;
		this.error = error;
		this.correction = correction;
	}

	public Correction(String correctedBy, String moNumber,
			String sequenceNumber, String operator, String date,
			String activity, String quantity, String login, String logout,
			String completedSequence) {
		super();
		this.correctedBy = correctedBy;
		this.moNumber = moNumber;
		this.sequenceNumber = sequenceNumber;
		this.operator = operator;
		this.date = date;
		this.activity = activity;
		this.quantity = quantity;
		this.login = login;
		this.logout = logout;
		this.completedSequence = completedSequence;
	}

	public Correction(String correctedBy, String moNumber,
			String sequenceNumber, String stampNo, String operator,
			String date, String activity, String quantity, String assetNumber) {
		super();
		this.correctedBy = correctedBy;
		this.moNumber = moNumber;
		this.sequenceNumber = sequenceNumber;
		this.stampNo = stampNo;
		this.operator = operator;
		this.date = date;
		this.activity = activity;
		this.quantity = quantity;
		this.assetNumber = assetNumber;
		// this.addedSequence = addedSequence;
	}

	public Correction(String moNumber, String sequenceNumber, String activity,
			String operator, String date, String login, String logout,
			String quantity, String assetNumber, String completedSequence,
			String multiple) {
		super();
		// this.correctedBy = correctionBy;
		this.moNumber = moNumber;
		this.sequenceNumber = sequenceNumber;
		this.activity = activity;
		this.operator = operator;
		this.date = date;
		this.login = login;
		this.logout = logout;
		this.quantity = quantity;
		this.assetNumber = assetNumber;
		this.completedSequence = completedSequence;
	}

	/**
	 * @return the correctedBy
	 */
	public String getCorrectedBy() {
		return correctedBy;
	}

	/**
	 * @param correctedBy
	 *            the correctedBy to set
	 */
	public void setCorrectedBy(String correctedBy) {
		this.correctedBy = correctedBy;
	}

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
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the correction
	 */
	public String getCorrection() {
		return correction;
	}

	/**
	 * @param correction
	 *            the correction to set
	 */
	public void setCorrection(String correction) {
		this.correction = correction;
	}

	public String getCompletedSequence() {
		return completedSequence;
	}

	public void setCompletedSequence(String completedSequence) {
		this.completedSequence = completedSequence;
	}

	public String getAddedSequence() {
		return addedSequence;
	}

	public void setAddedSequence(String addedSequence) {
		this.addedSequence = addedSequence;
	}

	public String getStampNo() {
		return stampNo;
	}

	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}