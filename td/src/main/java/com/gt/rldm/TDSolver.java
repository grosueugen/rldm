package com.gt.rldm;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.analysis.solvers.PolynomialSolver;

/*
V(s0) = (1-lambda)*R1 + (1-lambda)*lambda*R2 + ... + (1-lambda)*lambda^3*R4 + lambda^4*R5
 */
public class TDSolver {
	
	private double valueS0;
	private double probToS1;
	private double[] valueEstimates;
	private double[] rewards;
	
	private double R1;
	private double R2;
	private double R3;
	private double R4;
	private double R5;
	private double Rt;

	public TDSolver(double correctValueS0, double probToS1, double[] valueEstimates, double[] rewards) {
		this.valueS0 = correctValueS0;
		this.probToS1 = probToS1;
		this.valueEstimates = valueEstimates;
		this.rewards = rewards;
		
		computeR1();
		computeR2();
		computeR3();
		computeR4();
		computeR5();
		computeRt();
	}

	// R1 = r(t+1) + gamma*V(t)(s(t+1))
	// our case: gamma = 1 => R1 = r1 + V(s') (see 7.1 Sutton)
	private void computeR1() {
		double r01 = rewards[0] + valueEstimates[1] - valueEstimates[0];
		double r02 = rewards[1] + valueEstimates[2] - valueEstimates[0];
		double expectation = r01 * probToS1 + r02 * (1-probToS1);
		R1 = expectation;
		System.out.println("R1=" + R1);
	}
	
	private void computeR2() {
		double r01 = rewards[0] + rewards[2] + valueEstimates[3] - valueEstimates[0];
		double r02 = rewards[1] + rewards[3] + valueEstimates[3] - valueEstimates[0];
		double expectation = r01 * probToS1 + r02 * (1-probToS1);
		R2 = expectation;
		System.out.println("R2=" + R2);
	}
	
	private void computeR3() {
		double r01 = rewards[0] + rewards[2] + rewards[4] + valueEstimates[4] - valueEstimates[0];
		double r02 = rewards[1] + rewards[3] + rewards[4] + valueEstimates[4] - valueEstimates[0];
		double expectation = r01 * probToS1 + r02 * (1-probToS1);
		R3 = expectation;
		System.out.println("R3=" + R3);
	}
	
	private void computeR4() {
		double r01 = rewards[0] + rewards[2] + rewards[4] + rewards[5] + valueEstimates[5] - valueEstimates[0];
		double r02 = rewards[1] + rewards[3] + rewards[4] + rewards[5] + valueEstimates[5] - valueEstimates[0];
		double expectation = r01 * probToS1 + r02 * (1-probToS1);
		R4 = expectation;
		System.out.println("R4=" + R4);
	}
	
	private void computeR5() {
		double r01 = rewards[0] + rewards[2] + rewards[4] + rewards[5] + rewards[6] + valueEstimates[6] - valueEstimates[0];
		double r02 = rewards[1] + rewards[3] + rewards[4] + rewards[5] + rewards[6] + valueEstimates[6] - valueEstimates[0];
		double expectation = r01 * probToS1 + r02 * (1-probToS1);
		R5 = expectation;
		System.out.println("R5=" + R5);
	}
	
	private void computeRt() {
		double r01 = rewards[0] + rewards[2] + rewards[4] + rewards[5] + rewards[6];
		double r02 = rewards[1] + rewards[3] + rewards[4] + rewards[5] + rewards[6];
		double expectation = r01 * probToS1 + r02 * (1-probToS1);
		Rt = expectation;
		System.out.println("Rt=" + Rt);
	}
	
	/*
	 valueS0 = (1-lambda)*R1 + (1-lambda)*lambda*R2 + (1-lambda)*lambda^2*R3 + (1-lambda)*lambda^3*R4 + lambda^4*R5 
	 */
	public double get() {
		PolynomialSolver poly = new LaguerreSolver();
		double[] coef = new double[] {(R1-valueS0), (R2-R1), (R3-R2), (R4-R3), (R5-R4), (Rt-R5)};
		StringBuilder p = new StringBuilder("").append(R1-valueS0);
		p.append("+").append((R2-R1)).append("*x");
		p.append("+").append((R3-R2)).append("*x^2");
		p.append("+").append((R4-R3)).append("*x^3");
		p.append("+").append((R5-R4)).append("*x^4");
		p.append("+").append((Rt)).append("*x^5");
		p.append("=0");
		System.out.println("poly is: " + p);
		PolynomialFunction f = new PolynomialFunction(coef);
		double sol = poly.solve(100, f, 0, 0.99, 0.5);
		return sol;
	}

}
