/**
 * 
 */
package com.gavs.hishear.m3interface.dto;

/**
 * @author saravanam
 * 
 */
public class MVXDTAMI {
	private String productNumber;
	private String facility;
	private String costCenter;

	private String purchaseOrderNumber;
	private String purchaseOrderLineNo;
	private String purchaseOrderSubLineNo;
	private String wareHouse;
	private String supplierNumber;
	private String unitOfMeasure;

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getPurchaseOrderLineNo() {
		return purchaseOrderLineNo;
	}

	public void setPurchaseOrderLineNo(String purchaseOrderLineNo) {
		this.purchaseOrderLineNo = purchaseOrderLineNo;
	}

	public String getPurchaseOrderSubLineNo() {
		return purchaseOrderSubLineNo;
	}

	public void setPurchaseOrderSubLineNo(String purchaseOrderSubLineNo) {
		this.purchaseOrderSubLineNo = purchaseOrderSubLineNo;
	}

	public String getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter
	 *            the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}
}
