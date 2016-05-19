package com.gt.rldm;

import static com.gt.rldm.DieDomain.CLASS_AGENT;
import static com.gt.rldm.DieDomain.STATE_END;
import static com.gt.rldm.DieDomain.STATE_PLAY;
import static com.gt.rldm.DieDomain.WIN;
import static com.gt.rldm.Utils.getCurrentAmount;

import java.util.ArrayList;
import java.util.List;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.common.SimpleAction;

public class EndAction extends SimpleAction implements FullActionModel {
	
	@SuppressWarnings("unused")
	private final Die die;

	public EndAction(String endGameActionName, Domain domain, Die die) {
		super(endGameActionName, domain);
		this.die = die;
	}
	
	@Override
	protected State performActionHelper(State s, GroundedAction action) {
		ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
		agent.setValue(STATE_PLAY, getCurrentAmount(s));
		agent.setValue(STATE_END, WIN);
		return s;
	}
	
	@Override
	public List<TransitionProbability> getTransitions(State s, GroundedAction action) {
		List<TransitionProbability> res = new ArrayList<>(1);
		
		State winEndState = s.copy();
		ObjectInstance agent = winEndState.getFirstObjectOfClass(CLASS_AGENT);
		agent.setValue(STATE_PLAY, getCurrentAmount(s));
		agent.setValue(STATE_END, WIN);
		TransitionProbability tp = new TransitionProbability(winEndState, 1);
		res.add(tp);
		
		return res;
	}
	
}
