// File:         PhysicalAllocationThread.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.controller;

import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;

/**
 * The Class PhysicalAllocationThread.
 */
public class PhysicalAllocationThread extends Thread {

	/** The dao. */
	private SequenceDAO dao;

	/** The seq. */
	private Sequence seq;

	/**
	 * Instantiates a new physical allocation thread.
	 * 
	 * @param dao
	 *            the dao
	 * @param seq
	 *            the seq
	 */
	public PhysicalAllocationThread(SequenceDAO dao, Sequence seq) {
		super();
		this.dao = dao;
		this.seq = seq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		// dao.doPhysicalAllocation(seq.getProductNumber(), seq.getFacility());
		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
	}

}
