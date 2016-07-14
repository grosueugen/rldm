package com.gt.rldm.midterm;

import org.apache.commons.math3.linear.RealVector;

public class RMSE {
	
	public final static double[] idealW = { 1.0/6, 2.0/6, 3.0/6, 4.0/6, 5.0/6 };
	
	private final RealVector w;
	
	private double rmse;
	
	public RMSE(RealVector w) {
		this.w = w;
		compute();
	}

	private void compute() {
		double sum = 0;
		for (int i = 0; i < w.getDimension(); i++) {
			double error = w.getEntry(i) - idealW[i];
			sum += Math.pow(error, 2);
		}
		rmse = Math.sqrt(sum/w.getDimension());
	}
	
	public double rmse() {
		return rmse;
	}

}
