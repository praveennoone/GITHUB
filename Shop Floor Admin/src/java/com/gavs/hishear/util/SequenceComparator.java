package com.gavs.hishear.util;

import java.util.Comparator;

import com.gavs.hishear.web.domain.Sequence;

public class SequenceComparator implements Comparator<Sequence> {

	@Override
	public int compare(Sequence seq1, Sequence seq2) {
		// TODO Auto-generated method stub
		if (seq1 != null && seq2 != null && (seq1.getSequence() != null)
				&& (seq2.getSequence() != null)) {
			return Integer.parseInt(seq1.getSequence())
					- Integer.parseInt(seq2.getSequence());
		} else {
			return 0;
		}
	}

}
