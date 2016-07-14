package com.gt.rldm.midterm;

import java.util.List;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;

public class OnePresentation {
	
	private final int dimension = 5;
	
	private final double alpha;
	private final double lambda;
	
	private double rmse;
	
	public OnePresentation(String filePath, double alpha, double lambda) {		
		this.alpha = alpha;
		this.lambda = lambda;
		compute(filePath);
	}

	private void compute(String filePath) {
		List<TrainingSet> trainingSets = new DataGenerator(filePath).getTrainingSets(); 
		double[] initialValues = initW(); RealVector w = new OpenMapRealVector(initialValues);
		double sumRMSE = 0D; int i = 0;
		for (TrainingSet trainingSet : trainingSets) {
			RealVector tsW = trainingSet.computeW(alpha, lambda, w);
			double tsRMSE = new RMSE(tsW).rmse();
			if (tsRMSE >= 1.5) { // outliers
				//log.debug("ts[" + i + "]:" + rmse2 + ": outlier");
			} else {
				sumRMSE+= tsRMSE;			
				i++;
			}
		}
		if (i == 0) rmse = -1; else rmse = sumRMSE/i;
	}

	private double[] initW() {
		double[] res = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			res[i] = 0.5D;
		}
		return res;
	}
	
	public double rmse() {
		return rmse;
	}
	
}
