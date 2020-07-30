package com.gavs.hishear.m3interface.dto;

public class POTransaction implements Comparable {
	private String poNumber;
	private String poLineNumber;
	private String subLineNumber;
	private String poLineSuffix;
	private int transactionQuantity;
	private String receivingNumber;
	private int status;
	private boolean reversable;
	private String transactionDate;
	private String warehouse;
	private String itemNumber;
	private String poLineType;
	private String verifiedStatus;
	private String location;
	private String packingNumber;
	private String lotNumber;
	private String qtyAccepted;
	private String qtyRejected;
	private String userName;

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

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isReversable() {
		return reversable;
	}

	public void setReversable(boolean reversable) {
		this.reversable = reversable;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoLineNumber() {
		return poLineNumber;
	}

	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}

	public String getSubLineNumber() {
		return subLineNumber;
	}

	public void setSubLineNumber(String subLineNumber) {
		this.subLineNumber = subLineNumber;
	}

	public String getPoLineSuffix() {
		return poLineSuffix;
	}

	public void setPoLineSuffix(String poLineSuffix) {
		this.poLineSuffix = poLineSuffix;
	}

	public int getTransactionQuantity() {
		return transactionQuantity;
	}

	public void setTransactionQuantity(int transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}

	public String getReceivingNumber() {
		return receivingNumber;
	}

	public void setReceivingNumber(String receivingNumber) {
		this.receivingNumber = receivingNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int compareTo(Object o) {
		POTransaction transaction = (POTransaction) o;

		int polineNumber = 0;
		if (this.poLineNumber != null && !"".equals(polineNumber)) {
			polineNumber = Integer.parseInt(poLineNumber);
		}

		int polineNumberComparing = 0;
		if (transaction.getPoLineNumber() != null
				&& !"".equals(transaction.getPoLineNumber())) {
			polineNumberComparing = Integer.parseInt(transaction
					.getPoLineNumber());
		}

		if (polineNumber > polineNumberComparing) {
			return 1;
		} else if (polineNumber < polineNumberComparing) {
			return -1;
		}

		int receivingNum = 0;
		if (this.receivingNumber != null && !"".equals(receivingNumber)) {
			receivingNum = Integer.parseInt(receivingNumber);
		}

		int receivingNumComparing = 0;
		if (transaction.getReceivingNumber() != null
				&& !"".equals(transaction.getReceivingNumber())) {
			receivingNumComparing = Integer.parseInt(transaction
					.getReceivingNumber());
		}

		if (receivingNum > receivingNumComparing) {
			return 1;
		} else if (receivingNum < receivingNumComparing) {
			return -1;
		}

		if (this.status > transaction.getStatus()) {
			return 1;
		} else if (this.status < transaction.getStatus()) {
			return -1;
		}
		return 0;
	}

	/**
	 * @return the itemNumber
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber
	 *            the itemNumber to set
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the poLineType
	 */
	public String getPoLineType() {
		return poLineType;
	}

	/**
	 * @param poLineType
	 *            the poLineType to set
	 */
	public void setPoLineType(String poLineType) {
		this.poLineType = poLineType;
	}

	/**
	 * @return the verifiedStatus
	 */
	public String getVerifiedStatus() {
		return verifiedStatus;
	}

	/**
	 * @param verifiedStatus
	 *            the verifiedStatus to set
	 */
	public void setVerifiedStatus(String verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the packingNumber
	 */
	public String getPackingNumber() {
		return packingNumber;
	}

	/**
	 * @param packingNumber
	 *            the packingNumber to set
	 */
	public void setPackingNumber(String packingNumber) {
		this.packingNumber = packingNumber;
	}

	/**
	 * @return the lotNumber
	 */
	public String getLotNumber() {
		return lotNumber;
	}

	/**
	 * @param lotNumber
	 *            the lotNumber to set
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	/**
	 * @return the qtyAccepted
	 */
	public String getQtyAccepted() {
		return qtyAccepted;
	}

	/**
	 * @param qtyAccepted
	 *            the qtyAccepted to set
	 */
	public void setQtyAccepted(String qtyAccepted) {
		this.qtyAccepted = qtyAccepted;
	}

	/**
	 * @return the qtyRejected
	 */
	public String getQtyRejected() {
		return qtyRejected;
	}

	/**
	 * @param qtyRejected
	 *            the qtyRejected to set
	 */
	public void setQtyRejected(String qtyRejected) {
		this.qtyRejected = qtyRejected;
	}

}
