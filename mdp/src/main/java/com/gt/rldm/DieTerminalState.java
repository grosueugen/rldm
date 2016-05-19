package com.gt.rldm;

import static com.gt.rldm.DieDomain.*;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class DieTerminalState implements TerminalFunction {
	
	@Override
	public boolean isTerminal(State s) {
		ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
		int end = agent.getIntValForAttribute(STATE_END);
		boolean win = (end == 1);
		boolean lose = (end == 2);
		return (win || lose);
	}

}
