package com.gt.rldm.midterm;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;

public class Figure5 {
	
	public static void main(String[] args) {
		List<Double> lambdas = Arrays.asList(new Double[] {0D, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1D});
		List<Double> alphas = Arrays.asList(new Double[] {0D, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6});
		String filePath = "C:\\gen\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt";
		
		// rows: lambdas, columns: alphas
		Table<Double, Double, Double> table = ArrayTable.create(lambdas, alphas);
		for (double lambda : lambdas) {
			for (double alpha : alphas) {
				double rmse = new OnePresentation(filePath, alpha, lambda).rmse();
				table.put(lambda, alpha, rmse);
			}
		}
		
		for (double lambda: table.rowKeySet()) {
			double minRms = Double.MAX_VALUE;
			double minAlpha = -1;
			for (double alpha : table.columnKeySet()) {
				Double rms = table.get(lambda, alpha);
				if (rms < minRms) {
					minRms = rms;
					minAlpha = alpha;
				}
			}
			System.out.println(lambda + "," + minRms + "," + minAlpha);
		}
	}

}
