package com.gt.rldm;

import static com.gt.rldm.DieDomain.CLASS_AGENT;
import static com.gt.rldm.DieDomain.STATE_PLAY;

import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class Utils {
	
	public static int getCurrentAmount(State s) {
		ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
		return agent.getIntValForAttribute(STATE_PLAY);
	}

}
