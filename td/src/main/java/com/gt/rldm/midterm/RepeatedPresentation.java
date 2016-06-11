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
	
	private final double[] idealW = {1/((double)6), 2/((double)6), 3/((double)6), 4/((double)6), 5/((double)6)};
	
	private final double alpha;
	private final double lambda;
	private final double epsilon;
	
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
		RealVector oldW = w;
		do {
			for (TrainingSet trainingSet : trainingSets) {
				oldW = w;
				RealVector deltaW = trainingSet.computeDeltaW(alpha, lambda, w);
				w = w.add(deltaW);
			}
		} while (almostSame(oldW, w));
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

}
