package com.gt.rldm.midterm;

public class Figure3 {
	
	public static void main(String[] args) {
		String filePath = "C:\\gen\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt";
		double alpha = 0.001;		
		double epsilon = 0.0001;
		double[] lambdas = new double[] {0, 0.1, 0.3, 0.5, 0.7, 0.9, 1};
		for (double lambda : lambdas) {
			RepeatedPresentation rp = new RepeatedPresentation(filePath, alpha, lambda, epsilon);
			double rms = rp.rmse();
			System.out.println("lambda," + lambda + ",RMS," + rms);
		}
	}

}
