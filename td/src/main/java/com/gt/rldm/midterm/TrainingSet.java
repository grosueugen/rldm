package com.gt.rldm.midterm;

import java.util.ArrayList;
import java.util.List;

public class TrainingSet {
	
	private final int n = 10;
	private final List<Sequence> sequences = new ArrayList<>(n);
	
	public TrainingSet() {
		compute();
	}

	private void compute() {
		for (int i = 0; i < n; i++) {
			sequences.add(new Sequence());
		}
	}
	
	public List<Sequence> getSequences() {
		return sequences;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			Sequence s = sequences.get(i);
			sb.append(s);
			if (i < (n-1)) sb.append("\n");
		}
		return sb.toString();
	}

}
