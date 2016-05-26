package com.gt.rldm;

/*
Sample Tests Cases:
input:
probToState1=0.81
valueEstimates={0.0,4.0,25.7,0.0,20.1,12.2,0.0};
rewards={7.9,-5.1,2.5,-7.2,9.0,0.0,1.6};
output:
bestLambda=0.623
 */
public class RunTD {
	
	public static void main(String[] args) {
		if (args.length != 15) {
			System.out.println("You must provide 15 params: probToS1, 7 valueEstimates, 7 rewards!");
			return;
		}
		double probToS1 = Double.valueOf(args[0]);
		double[] valueEstimates = new double[7];
		double[] rewards = new double[7];
		for (int i = 1; i <= 7; i++) {
			valueEstimates[i-1] = Double.valueOf(args[i]);
		}
		for (int i = 8; i <= 14; i++) {
			rewards[i-8] = Double.valueOf(args[i]);
		}
		
		double valueS0 = new H2MDPSolver(probToS1, rewards).get();
		System.out.println("MDP S0 value function: " + valueS0);
		double lambda = new TDSolver(valueS0, probToS1, valueEstimates, rewards).get();
		System.out.println("Solution (lambda): " + lambda);
	}

}
