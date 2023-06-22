package com.lisi.docviewer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Venkat Gonepudi
 *
 */
@Entity
@Table(name="DocViewer_Index")
public class DocViewer implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name = "IndexId")  
	private Integer indexId;
	
	@Column(name = "Location")  
	private String location;
	
	@Column(name = "MetadataValue")  
	private String metadataValue;
	
	@Column(name = "FilePath")  
	private String filePath;
	
	
	@Column(name = "ScanDate")
	private String scanDate;
	
	@Column(name = "DocType")  
	private String docType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IndexDate")
	private Date indexDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MergedIndexDate")
	private Date mergedDate;
	
	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMetadataValue() {
		return metadataValue;
	}

	public void setMetadataValue(String metadataValue) {
		this.metadataValue = metadataValue;
	}

	public String getFilePath() {
		return filePath.substring(filePath.lastIndexOf("\\")+1);
	}
	public String getFileFullPath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Date getIndexDate() {
		return indexDate;
	}

	public void setIndexDate(Date indexDate) {
		this.indexDate = indexDate;
	}

	public Date getMergedDate() {
		return mergedDate;
	}

	public void setMergedDate(Date mergedDate) {
		this.mergedDate = mergedDate;
	}
	
	

}
