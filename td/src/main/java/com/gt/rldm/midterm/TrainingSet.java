package com.gt.rldm.midterm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;

public class TrainingSet {
	
	private final int n = 10;
	private final List<Sequence> sequences = new ArrayList<>(n);
	
	public TrainingSet() {
		this(true);
	}
	
	public TrainingSet(boolean generate) {
		if (generate) {
			compute();
		} 
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

	public void addSequence(Sequence s) {
		this.sequences.add(s);
	}

	public int size() {
		return sequences.size();
	}

	public RealVector computeDeltaW(double alpha, double lambda, RealVector w) {
		RealVector res = new OpenMapRealVector(new double[] {0D, 0D, 0D, 0D, 0D});
		for (Sequence sequence : sequences) {
			RealVector deltaW = sequence.computeDeltaW(alpha, lambda, w);
			res = res.add(deltaW);
		}
		return res;
	}

}
