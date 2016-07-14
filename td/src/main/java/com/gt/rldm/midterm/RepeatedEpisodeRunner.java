package com.gt.rldm.midterm;

import java.util.Map;

public class RepeatedEpisodeRunner implements Runnable {
	
	private final double lambda;
	private final double alpha;
	private final double epsilon;
	private String filePath;
	private final Map<Double, Double> rmse;
	
	
	RepeatedEpisodeRunner(String filePath, double alpha, double lambda, double epsilon, 
			Map<Double, Double> rmse) {
		this.filePath = filePath;
		this.alpha = alpha;
		this.lambda = lambda;
		this.epsilon = epsilon;
		this.rmse = rmse;
	}
	@Override
	public void run() {
		RepeatedPresentation rp = new RepeatedPresentation(filePath, alpha, lambda, epsilon);
		double rms = rp.rmse();
		rmse.put(lambda, rms);
	}

}
