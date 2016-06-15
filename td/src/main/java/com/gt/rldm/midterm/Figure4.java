package com.gt.rldm.midterm;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.RealVector;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;

public class Figure4 {
	
	public final static double[] idealW = { 1 / ((double) 6), 2 / ((double) 6), 3 / ((double) 6), 4 / ((double) 6),
			5 / ((double) 6) };
	
	public static void main(String[] args) {
		List<Double> lambdas = Arrays.asList(new Double[] {0D, 0.3D, 0.8D, 1D});
		List<Double> alphas = Arrays.asList(new Double[] {0D, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6});
		String filePath = "C:\\gen\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt";
		
		// rows: lambdas, columns: alphas
		Table<Double, Double, Double> table = ArrayTable.create(lambdas, alphas);
		for (double lambda : lambdas) {
			for (double alpha : alphas) {
				RealVector w = new OnePresentation(filePath, alpha, lambda).getW();
				double rms = computeRMS(w);
				table.put(lambda, alpha, rms);
			}
		}
		System.out.println(table);
		for (double lambda: table.rowKeySet()) {
			System.out.println("============" + lambda);
			for (double alpha : table.columnKeySet()) {
				Double rms = table.get(lambda, alpha);
				System.out.println(alpha + "," + rms);
			}
		}
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
