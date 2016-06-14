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
	
	// Computes w after each sequence by applying the formula: w = w + sum(deltaWt)
	public RealVector computeWAfterEach(double alpha, double lambda, RealVector w) {
		for (Sequence sequence : sequences) {
			w = sequence.computeW(alpha, lambda, w);
		}
		return w;
	}

	// Computes w after ALL sequences (only once) by applying the formula: w = w + sum(sum(deltaWt))
	public RealVector computeWAfterAll(double alpha, double lambda, RealVector w) {
		return w.add(computeDeltaW(alpha, lambda, w));
	}
	
	// Computes w after ALL sequences by applying the formula: w = w + sum(sum(deltaWt))
	// until convergence
	public RealVector computeW(double alpha, double lambda, double epsilon, RealVector w) {
		RealVector prevW = null;
		int iterations = 0;
		do {
			iterations++;
			prevW = w;
			w = computeWAfterAll(alpha, lambda, w);
		} while (!almostSame(prevW, w, epsilon));
		//System.out.println("Converged in " + iterations + " iterations");
		return w;
	}
	
	private boolean almostSame(RealVector oldW, RealVector newW, double epsilon) {
		double absMax = Integer.MIN_VALUE;
		RealVector diff = oldW.subtract(newW);
		for (int i = 0; i < diff.getDimension(); i++) {
			double absValue = Math.abs(diff.getEntry(i));
			if (absMax < absValue) absMax = absValue;
		}
		return (absMax < epsilon);
	}

}
