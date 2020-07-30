/**
 * 
 */
package com.gavs.hishear.web.reports.management.domain;

import java.util.ArrayList;

/**
 * @author mohammeda
 * 
 */
public class CorrectionByUser {
	private String correctedBy;
	private ArrayList<Correction> corrections;

	public String getCorrectedBy() {
		return correctedBy;
	}

	public void setCorrectedBy(String correctedBy) {
		this.correctedBy = correctedBy;
	}

	public ArrayList<Correction> getCorrections() {
		return corrections;
	}

	public void setCorrections(ArrayList<Correction> corrections) {
		this.corrections = corrections;
	}

}
