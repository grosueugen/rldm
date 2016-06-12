package com.gt.rldm.midterm;

import org.apache.commons.math3.linear.RealVector;

public class Figure3 {
	
	public final static double[] idealW = { 1 / ((double) 6), 2 / ((double) 6), 3 / ((double) 6), 4 / ((double) 6),
			5 / ((double) 6) };
	
	public static void main(String[] args) {
		String filePath = "D:\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt";
		double alpha = 0.1;
		double lambda = 0.1;
		double epsilon = 0.0001;
		RepeatedPresentation rp = new RepeatedPresentation(filePath, alpha, lambda, epsilon);
		RealVector w = rp.getW();
		double rms = computeRMS(w);
		System.out.println(rms);
	}

	private static double computeRMS(RealVector w) {
		double sum = 0;
		for (int i = 0; i < w.getDimension(); i++) {
			double error = w.getEntry(i) - idealW[i];
			sum += Math.pow(error, 2);
		}
		return Math.sqrt(sum/w.getDimension());
	}

}
