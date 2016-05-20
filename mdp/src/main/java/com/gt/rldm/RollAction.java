package com.gt.rldm;

import static com.gt.rldm.Utils.*;
import static com.gt.rldm.DieDomain.*;
import static com.gt.rldm.DieDomain.STATE_END;

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

public class RollAction extends SimpleAction implements FullActionModel {
	
	private final Die die;

	public RollAction(String rollActionName, Domain domain, Die die) {
		super(rollActionName, domain);
		this.die = die;
	}
 
	@Override
	protected State performActionHelper(State s, GroundedAction action) {
		int side = rollDie();
		ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
		if (die.isGood(side)) {
			agent.setValue(STATE_PLAY, (getCurrentAmount(s)+side));
			agent.setValue(STATE_END, IN_PROGRESS);			
		} else {
			agent.setValue(STATE_PLAY, 0);
			agent.setValue(STATE_END, LOSE);
		}
		return s;
	}
	
	private int rollDie() {
		Random r = new Random();
		return r.nextInt(die.getN()) + 1;
	}

	/**
	 * With win probability the agent goes to state PLAY=newAmount??? and END=IN_PROGRESS, 
	 * with lose probability the agent goes to PLAY=0 and END=LOSE;
	 */
	@Override
	public List<TransitionProbability> getTransitions(State s1, GroundedAction action) {
		List<TransitionProbability> res = new ArrayList<>();
		double wp = die.winProbability();
		int nrGoodSides = die.getNrGoodSides();
		
		double winProbability = wp/nrGoodSides;
		double loseProbability = die.loseProbability();
		int currentAmount = getCurrentAmount(s1);
		
		for (int goodSide : die.getGoodSides()) {
			State successState = s1.copy();
			ObjectInstance successAgent = successState.getFirstObjectOfClass(CLASS_AGENT);
			successAgent.setValue(STATE_PLAY, currentAmount+goodSide);
			successAgent.setValue(STATE_END, IN_PROGRESS);
			TransitionProbability rollSuccess = new TransitionProbability(successState, winProbability);
			res.add(rollSuccess);
		}
		
		State loseState = s1.copy();
		ObjectInstance loseAgent = loseState.getFirstObjectOfClass(CLASS_AGENT);
		loseAgent.setValue(STATE_PLAY, 0);
		loseAgent.setValue(STATE_END, LOSE);
		TransitionProbability rollLose = new TransitionProbability(loseState, loseProbability);
		res.add(rollLose);
		
		return res;
	}
	
}
