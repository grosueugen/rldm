package com.gt.rldm.midterm;

import java.util.List;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Used for generating figure 3
 * @author Eugen
 *
 */
public class RepeatedPresentation {
	
	public final static double[] idealW = { 1.0/6, 2.0/6, 3.0/6, 4.0/6, 5.0/6 };
	
	private final int dimension = 5;
	
	private final double alpha;
	private final double lambda;
	private final double epsilon;
	
	private double rmse;
	
	public RepeatedPresentation(String filePath, double alpha, double lambda, double epsilon) {		
		this.alpha = alpha;
		this.lambda = lambda;
		this.epsilon = epsilon;
		compute(filePath);
	}

	private void compute(String filePath) {
		List<TrainingSet> trainingSets = new DataGenerator(filePath).getTrainingSets();
		double[] initialValues = initW();
		RealVector w = new OpenMapRealVector(initialValues);
		double sumRMSE = 0.0;
		for (TrainingSet trainingSet : trainingSets) {
			RealVector tsW = trainingSet.computeW(alpha, lambda, epsilon, w);
			sumRMSE += computeRMS(tsW);
		}
		rmse = sumRMSE/trainingSets.size();
	}

	private double[] initW() {
		double[] res = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			res[i] = Math.random();
		}
		return res;
	}
	
	private double computeRMS(RealVector w) {
		double sum = 0;
		for (int i = 0; i < w.getDimension(); i++) {
			double error = w.getEntry(i) - idealW[i];
			sum += Math.pow(error, 2);
		}
		return Math.sqrt(sum/w.getDimension());
	}
	
	public double rmse() {
		return rmse;
	}

}
