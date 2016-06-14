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
	
	private final int dimension = 5;
	
	private final double alpha;
	private final double lambda;
	private final double epsilon;
	
	private RealVector w;
	
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
		RealVector res = new OpenMapRealVector(new double[]{0D,0D,0D,0D,0D});
		for (TrainingSet trainingSet : trainingSets) {
			res = res.add(trainingSet.computeW(alpha, lambda, epsilon, w));
		}
		this.w = res.mapDivide(trainingSets.size());
	}

	private double[] initW() {
		double[] res = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			res[i] = Math.random();
		}
		return res;
	}
	
	public RealVector getW() {
		return w;
	}

}
