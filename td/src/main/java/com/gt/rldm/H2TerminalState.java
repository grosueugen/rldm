package com.gt.rldm;

import static com.gt.rldm.H2Domain.H2_CLASS_AGENT;
import static com.gt.rldm.H2Domain.STATE_NR;

import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class H2TerminalState implements TerminalFunction {
	
	@Override
	public boolean isTerminal(State s) {
		ObjectInstance agent = s.getFirstObjectOfClass(H2_CLASS_AGENT);
		int currentStateNr = agent.getIntValForAttribute(STATE_NR);
		return (currentStateNr == 6);
	}

}
