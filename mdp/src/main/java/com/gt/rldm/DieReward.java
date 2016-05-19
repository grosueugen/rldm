package com.gt.rldm;

import static com.gt.rldm.DieDomain.*;
import static com.gt.rldm.Utils.*;

import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

public class DieReward implements RewardFunction {
	
	@Override
	public double reward(State s, GroundedAction a, State sprime) {
		boolean isRollAction = (ACTION_ROLL.equals(a.actionName()));
		boolean isEndAction = (ACTION_END.equals(a.actionName()));
		int afterRollAmount = getCurrentAmount(sprime);
		int beforeRollAmount = getCurrentAmount(s);
		if (isRollAction) {
			boolean rollSuccess = (afterRollAmount > beforeRollAmount);
			if (rollSuccess) {
				int moneyEarned = (afterRollAmount - beforeRollAmount);
				return moneyEarned;
			} else {
				int lostAllMoney = beforeRollAmount;
				return lostAllMoney;
			}
		} else if (isEndAction) {
			return 0; //all rewards were accumulated before
		} else {
			throw new IllegalStateException("Only ROLL or END actions are possible for this game");
		}
	}
	
}
