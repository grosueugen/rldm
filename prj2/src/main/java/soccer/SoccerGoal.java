package soccer;

import static soccer.SoccerGame.CLASS_GOAL;
import static soccer.SoccerGame.VAR_GT;
import static soccer.SoccerGame.VAR_POSITIVE_GOAL;
import static soccer.SoccerGame.VAR_X;
import static soccer.SoccerGame.VAR_Y;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

@DeepCopyState
public class SoccerGoal implements ObjectInstance, MutableState {

	public int x;
	public int y;
	public boolean positiveGoal;
	public int type;

	protected String name;

	private static final List<Object> keys = Arrays.<Object>asList(VAR_X, VAR_Y, VAR_POSITIVE_GOAL, VAR_GT);

	public SoccerGoal(int x, int y, boolean positiveGoal, int type, String name) {
		this.x = x;
		this.y = y;
		this.positiveGoal = positiveGoal;
		this.type = type;
		this.name = name;
	}

	@Override
	public String className() {
		return CLASS_GOAL;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ObjectInstance copyWithName(String objectName) {
		return new SoccerGoal(x, y, positiveGoal, type, objectName);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_X)){
			this.x = Integer.parseInt(value.toString());
		}
		else if(variableKey.equals(VAR_Y)){
			this.y = Integer.parseInt(value.toString());
		}
		else if(variableKey.equals(VAR_GT)){
			this.type = Integer.parseInt(value.toString());
		} 
		else if (variableKey.equals(VAR_POSITIVE_GOAL)) {
			this.positiveGoal = Boolean.parseBoolean(value.toString());
		}
		else{
			throw new UnknownKeyException(variableKey);
		}

		return this;
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {
		if(variableKey.equals(VAR_X)){
			return x;
		}
		else if(variableKey.equals(VAR_Y)){
			return y;
		}
		else if(variableKey.equals(VAR_GT)){
			return type;
		}
		else if (variableKey.equals(VAR_POSITIVE_GOAL)) {
			return positiveGoal;
		}
		else{
			throw new UnknownKeyException(variableKey);
		}
	}

	@Override
	public State copy() {
		return new SoccerGoal(x, y, positiveGoal, type, name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return OOStateUtilities.objectInstanceToString(this);
	}
	
}
