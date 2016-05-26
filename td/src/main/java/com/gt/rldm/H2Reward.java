package com.gt.rldm;

import static com.gt.rldm.H2Domain.H2_CLASS_AGENT;
import static com.gt.rldm.H2Domain.STATE_NR;

import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

public class H2Reward implements RewardFunction {
	
	private final double[] reward;
	
	public H2Reward(double[] reward) {
		this.reward = reward;
	}
	
	@Override
	public double reward(State s, GroundedAction a, State sprime) {
		int currentStateNr = getNr(s);
		int nextStateNr = getNr(sprime);
		if (currentStateNr == 0 && nextStateNr == 1) {
			return reward[0];
		} else if (currentStateNr == 0 && nextStateNr == 2) {
			return reward[1];
		}
		switch (currentStateNr) {
		case 1: return reward[2];
		case 2: return reward[3];
		case 3: return reward[4];
		case 4: return reward[5];
		case 5: return reward[6];
		case 6: return 0;
		}
		throw new IllegalAccessError("something wrong in reward");
	}
	
	public int getNr(State s) {
		ObjectInstance agent = s.getFirstObjectOfClass(H2_CLASS_AGENT);
		return agent.getIntValForAttribute(STATE_NR);
	}

}
