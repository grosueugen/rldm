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
		RealVector currentW = new OpenMapRealVector(initialValues);
		RealVector prevW = null;
		int iterations = 0;
		do {
			iterations++;
			prevW = currentW;
			for (TrainingSet trainingSet : trainingSets) {
				RealVector deltaW = trainingSet.computeDeltaW(alpha, lambda, currentW);
				currentW = currentW.add(deltaW);
			}
		} while (!almostSame(prevW, currentW));
		this.w = currentW;
		System.out.println(iterations);
	}

	private boolean almostSame(RealVector oldW, RealVector newW) {
		double absMax = Integer.MIN_VALUE;
		RealVector diff = oldW.subtract(newW);
		for (int i = 0; i < diff.getDimension(); i++) {
			double absValue = Math.abs(diff.getEntry(i));
			if (absMax < absValue) absMax = absValue;
		}
		return (absMax < epsilon);
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
