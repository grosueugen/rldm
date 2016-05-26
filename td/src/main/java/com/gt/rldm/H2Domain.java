package com.gt.rldm;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.singleagent.SADomain;

public class H2Domain implements DomainGenerator {
	
	private final double probToS1;
	
	public static final String STATE_NR = "STATE_NR"; 
	
	public static final String H2_CLASS_AGENT = "h2-agent";
	
	public static final String ACTION_NEXT = "ACTION_NEXT";
	
	public H2Domain(double probToS1) {
		this.probToS1 = probToS1;
	}
	
	@Override
	public Domain generateDomain() {
		Domain domain = new SADomain();
		ObjectClass agent = new ObjectClass(domain, H2_CLASS_AGENT);
		
		Attribute nr = new Attribute(domain, STATE_NR, AttributeType.INT);
		nr.setLims(0, 6);
		agent.addAttribute(nr);
		
		new H2NextAction(ACTION_NEXT, domain, probToS1);
		
		return domain;
	}

	public State createInitialState(Domain domain) {
		State s = new MutableState();
		ObjectInstance agent = new MutableObjectInstance(domain.getObjectClass(H2_CLASS_AGENT), "H2-Agent");
		agent.setValue(STATE_NR, 0);
		s.addObject(agent);
		return s;
	}

}
