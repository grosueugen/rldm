package com.gt.rldm;

import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.states.State;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

public class H2MDPSolver {
	
	private final double probToS1;
	private final double[] reward;
	private double valueS0;

	public H2MDPSolver(double probToS1, double[] reward) {
		this.probToS1 = probToS1;
		this.reward = reward;
		compute();
	}

	private void compute() {
		H2Domain h2Domain = new H2Domain(probToS1);
		Domain domain = h2Domain.generateDomain();
		State s0 = h2Domain.createInitialState(domain);
		H2Reward h2Reward = new H2Reward(reward);
		H2TerminalState h2TerminalState = new H2TerminalState();
		HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
		
		ValueIteration vi = new ValueIteration(domain, h2Reward, h2TerminalState, 1, hashingFactory, 0.001, 100);
		vi.toggleReachabiltiyTerminalStatePruning(true);
		vi.planFromState(s0);
		valueS0 = vi.value(s0);
		System.out.println("S0 value function: " + valueS0);
	}

	/**
	 * @return the value function for S0, by solving MDP through Value Iteration.
	 */
	public double get() {
		return valueS0;
	}
	
}
