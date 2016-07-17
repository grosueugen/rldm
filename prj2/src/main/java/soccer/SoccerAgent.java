package soccer;

import static soccer.SoccerGame.CLASS_AGENT;
import static soccer.SoccerGame.VAR_HAS_BALL;
import static soccer.SoccerGame.VAR_PN;
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
public class SoccerAgent implements ObjectInstance, MutableState {
	
	public int x;
	public int y;
	public boolean hasBall;
	public int player;

	protected String name;

	private static final List<Object> keys = Arrays.<Object>asList(VAR_X, VAR_Y, VAR_PN, VAR_HAS_BALL);


	public SoccerAgent(int x, int y, boolean hasBall, int player, String name) {
		this.x = x;
		this.y = y;
		this.hasBall = hasBall;
		this.player = player;
		this.name = name;
	}

	@Override
	public String className() {
		return CLASS_AGENT;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ObjectInstance copyWithName(String objectName) {
		return new SoccerAgent(x, y, hasBall, player, objectName);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {

		if(variableKey.equals(VAR_X)){
			this.x = Integer.parseInt(value.toString());
		}
		else if(variableKey.equals(VAR_Y)){
			this.y = Integer.parseInt(value.toString());
		}
		else if(variableKey.equals(VAR_PN)){
			this.player = Integer.parseInt(value.toString());
		} else if (variableKey.equals(VAR_HAS_BALL)) {
			this.hasBall = Boolean.parseBoolean(value.toString());
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
		else if(variableKey.equals(VAR_PN)){
			return player;
		} 
		else if (variableKey.equals(VAR_HAS_BALL)) {
			return hasBall;
		}
		else{
			throw new UnknownKeyException(variableKey);
		}
	}

	@Override
	public State copy() {
		return new SoccerAgent(x, y, hasBall, player, name);
	}

	@Override
	public String toString() {
		return OOStateUtilities.objectInstanceToString(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
