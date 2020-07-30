package com.gavs.hishear.web.reports.management.controller;

import java.util.ArrayList;

import com.gavs.hishear.web.reports.management.domain.Correction;

public class CorrectionsByUser {
	private String correctedBy;
	private ArrayList<Correction> corrections;

	public CorrectionsByUser(String correctedBy,
			ArrayList<Correction> corrections) {
		super();
		this.correctedBy = correctedBy;
		this.corrections = corrections;
	}

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
