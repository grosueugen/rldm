package com.gt.rldm;

import static com.gt.rldm.H2Domain.H2_CLASS_AGENT;
import static com.gt.rldm.H2Domain.STATE_NR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.common.SimpleAction;

public class H2NextAction extends SimpleAction implements FullActionModel {
	
	private final double probToS1;

	public H2NextAction(String name, Domain domain, double probToS1) {
		super(name, domain);
		this.probToS1 = probToS1;
	}
	
	@Override
	public List<TransitionProbability> getTransitions(State s, GroundedAction groundedAction) {
		List<TransitionProbability> result = new ArrayList<>();		
		int currentStateNr = getNr(s);
		if (currentStateNr == 0) {
			State s1 = s.copy();
			ObjectInstance agent1 = s1.getFirstObjectOfClass(H2_CLASS_AGENT);
			agent1.setValue(STATE_NR, 1);
			TransitionProbability toS1 = new TransitionProbability(s1, probToS1);
			result.add(toS1);
			
			State s2 = s.copy();
			ObjectInstance agent2 = s2.getFirstObjectOfClass(H2_CLASS_AGENT);
			agent2.setValue(STATE_NR, 2);
			TransitionProbability toS2 = new TransitionProbability(s2, 1-probToS1);
			result.add(toS2);
		} else if (currentStateNr == 1) {
			State nextState = s.copy();
			ObjectInstance agent = nextState.getFirstObjectOfClass(H2_CLASS_AGENT);
			agent.setValue(STATE_NR, 3);
			TransitionProbability tp = new TransitionProbability(nextState, 1);
			result.add(tp);
		} else {
			State nextState = s.copy();
			ObjectInstance agent = nextState.getFirstObjectOfClass(H2_CLASS_AGENT);
			agent.setValue(STATE_NR, currentStateNr+1);
			TransitionProbability tp = new TransitionProbability(nextState, 1);
			result.add(tp);
		}
		return result;
	}
	
	public int getNr(State s) {
		ObjectInstance agent = s.getFirstObjectOfClass(H2_CLASS_AGENT);
		return agent.getIntValForAttribute(STATE_NR);
	}
	
	@Override
	protected State performActionHelper(State s, GroundedAction groundedAction) {
		ObjectInstance agent = s.getFirstObjectOfClass(H2_CLASS_AGENT);
		int currentStateNr = agent.getIntValForAttribute(STATE_NR);
		if (currentStateNr == 0) {
			int nextNr = nextRandomFromS0();
			agent.setValue(STATE_NR, nextNr);
		} else if (currentStateNr == 1){
			agent.setValue(STATE_NR, 3);
		} else {
			agent.setValue(STATE_NR, ++currentStateNr);
		}
		return s;
	}

	private int nextRandomFromS0() {
		Random r = new Random();
		double prob = r.nextDouble();
		if (prob <= probToS1) return 1;
		return 2;
	}

}
