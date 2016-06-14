package com.gt.rldm.midterm;

import java.util.List;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;

public class OnePresentation {

private final int dimension = 5;
	
	private final double alpha;
	private final double lambda;
	
	private RealVector w;
	
	public OnePresentation(String filePath, double alpha, double lambda) {		
		this.alpha = alpha;
		this.lambda = lambda;
		compute(filePath);
	}

	private void compute(String filePath) {
		List<TrainingSet> trainingSets = new DataGenerator(filePath).getTrainingSets();
		double[] initialValues = initW();
		RealVector w = new OpenMapRealVector(initialValues);
		RealVector res = new OpenMapRealVector(new double[]{0D,0D,0D,0D,0D});
		for (TrainingSet trainingSet : trainingSets) {
			res = res.add(trainingSet.computeWAfterEach(alpha, lambda, w));
		}
		this.w = res.mapDivide(trainingSets.size());
	}

	private double[] initW() {
		double[] res = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			res[i] = 0.5D;
		}
		return res;
	}
	
	public RealVector getW() {
		return w;
	}
	
}
