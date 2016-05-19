package com.gt.rldm;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.states.State;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

public class RunDie {
	
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("Provide N and B. Example: 4 0 1 1 1");
			return;
		}
		int n = Integer.parseInt(args[0]);
		Die die = new Die(n);
		for (int i = 1; i <= n; i++) {
			String good = args[i];
			boolean isGood;
			if ("0".equals(good)) {
				isGood = true;
			} else if ("1".equals(good)) {
				isGood = false;
			} else {
				System.out.println("Provide correct B, with 0(good) 1(bad)");
				return;
			}
			die.addSide(i, isGood);
		}
		
		runVI(die);
	}

	private static void runVI(Die die) {
		DieDomain dieDomain = new DieDomain(die);
		Domain domain = dieDomain.generateDomain();
		State initialState = dieDomain.createInitialState(domain);
		DieReward dieReward = new DieReward();
		DieTerminalState dieTs = new DieTerminalState();
		HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
		
		ValueIteration planner = new ValueIteration(domain, dieReward, dieTs, 1, hashingFactory, 0.001, 100);
		planner.toggleReachabiltiyTerminalStatePruning(true);
		double initialStateValue = planner.value(initialState);
		System.out.println("initialStateValue: " + initialStateValue);
		
		Policy policy = planner.planFromState(initialState);
		EpisodeAnalysis episodeAnalysis = policy.evaluateBehavior(initialState, dieReward, dieTs);
		double discountedReturn = episodeAnalysis.getDiscountedReturn(1);
		System.out.println("return: " + discountedReturn);
	}

}
