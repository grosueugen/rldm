package com.gt.rldm;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.SADomain;

public class DieDomain implements DomainGenerator {
	
	public static final String STATE_PLAY = "play";
	public static final String STATE_END = "end";
	public static final int WIN = 1;
	public static final int LOSE = 2;
	public static final int IN_PROGRESS = 3;
	
	public static final String ACTION_ROLL = "roll";
	public static final String ACTION_END = "endGame";
	
	public static final String CLASS_AGENT = "agent";
	
	private final Die die;
	
	public DieDomain(Die die) {
		this.die = die;
	}
	
	@Override
	public Domain generateDomain() {
		Domain domain = new SADomain();
		ObjectClass agent = new ObjectClass(domain, CLASS_AGENT);
		
		int maxAmount = 1000;
		Attribute play = new Attribute(domain, STATE_PLAY, AttributeType.INT);
		play.setLims(0, maxAmount);
		agent.addAttribute(play);
		
		Attribute end = new Attribute(domain, STATE_END, AttributeType.INT);
		end.setLims(1, 3);//1->win, 2->lose, 3->in_progress
		agent.addAttribute(end);
		
		new RollAction(ACTION_ROLL, domain, die);
		new EndAction(ACTION_END, domain, die);
		
		return domain;
	}
	
	public State createInitialState(Domain domain) {
		State s = new MutableState();
		ObjectInstance agent = new MutableObjectInstance(domain.getObjectClass(CLASS_AGENT), "dieN-Agent");
		agent.setValue(STATE_PLAY, 0);
		agent.setValue(STATE_END, IN_PROGRESS);
		s.addObject(agent);
		return s;
	}

}
