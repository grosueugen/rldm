package com.gt.rldm;

import static com.gt.rldm.DieDomain.*;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class DieTerminalState implements TerminalFunction {
	
	@Override
	public boolean isTerminal(State s) {
		ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
		int maxAmount = agent.getIntValForAttribute(STATE_PLAY);
		if (maxAmount >= MAX_AMOUNT) return true;
		
		int end = agent.getIntValForAttribute(STATE_END);
		boolean win = (end == WIN);
		boolean lose = (end == LOSE);
		return (win || lose);
	}

}
