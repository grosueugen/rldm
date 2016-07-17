package soccer;

import static soccer.SoccerGame.*;
import static soccer.SoccerGame.PLAYER_B_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.GenericOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.stochasticgames.JointAction;
import burlap.mdp.stochasticgames.model.FullJointModel;

public class SoccerModel implements FullJointModel {
	
	public final Location2 ONE_UP = new Location2(0, 1);
	public final Location2 ONE_DOWN = new Location2(0, -1);
	public final Location2 ONE_RIGHT = new Location2(1, 0);
	public final Location2 ONE_LEFT = new Location2(-1, 0);
	
	@Override
	public State sample(State s, JointAction ja) {
		GenericOOState sp = nextState(s, ja);
		return sp;
	}
	
	@Override
	public List<StateTransitionProb> stateTransitions(State s, JointAction ja) {
		List <StateTransitionProb> tps = new ArrayList<StateTransitionProb>();
		GenericOOState sp = nextState(s, ja);
		tps.add(new StateTransitionProb(sp, 1));
		
		return tps;
	}

	private GenericOOState nextState(State s, JointAction ja) {
		GenericOOState sp = (GenericOOState) s.copy();
		sp.touch(PLAYER_A_NAME);
		sp.touch(PLAYER_B_NAME);
		
		SoccerAgent playerA = getPlayer(sp, PLAYER_A_NAME);
		SoccerAgent playerB = getPlayer(sp, PLAYER_B_NAME);
		
		Action actionA = ja.action(0);
		Action actionB = ja.action(1);
		
		boolean playerAMovesFirst = (new Random().nextDouble() < 0.5);
		if (playerAMovesFirst) {
			moveAndStealBallIfPossible(playerA, actionA, playerB);
			moveAndStealBallIfPossible(playerB, actionB, playerA);
		} else {
			moveAndStealBallIfPossible(playerB, actionB, playerA);
			moveAndStealBallIfPossible(playerA, actionA, playerB);
		}
		
		// this action is deterministic! randomness happens inside the Equilibrium classes
		/*sp.touch(playerA.name);
		sp.touch(playerB.name);*/
		return sp;
	}
	
	private SoccerAgent getPlayer(State s, String agentName) {
		return (SoccerAgent) ((OOState) s).object(agentName);
	}
	
	private Location2 moveAndStealBallIfPossible(SoccerAgent player, Action playerAction, SoccerAgent otherPlayer) {
		Location2 playerNextLoc = nextLocation(player, playerAction);
		boolean collision = playerNextLoc.equals(new Location2(otherPlayer));
		if (collision) {
			if (player.hasBall) {
				stealBall(player, otherPlayer);
			}
			return new Location2(player);
		} else {
			move(player, playerNextLoc);
			return playerNextLoc;
		}
	}

	private Location2 nextLocation(SoccerAgent player, Action action) {
		Location2 currentLoc = new Location2(player);
		if (action.actionName().equals(ACTION_NORTH)) {
			if (currentLoc.y == MAX_Y) return currentLoc;
			else return currentLoc.add(ONE_UP);
		} 
		if (action.actionName().equals(ACTION_SOUTH)) {
			if (currentLoc.y == 0) return currentLoc;
			else return currentLoc.add(ONE_DOWN);
		}
		if (action.actionName().equals(ACTION_EAST)) {
			if (currentLoc.x == MAX_X) return currentLoc;
			else return currentLoc.add(ONE_RIGHT);
		}
		if (action.actionName().equals(ACTION_WEST)) {
			if (currentLoc.x == 0) return currentLoc;
			else return currentLoc.add(ONE_LEFT);
		}
		if (action.actionName().equals(ACTION_NOOP)) {
			return currentLoc;
		}
		throw new IllegalStateException(action.actionName() + " not implemented");
	}

	private void move(SoccerAgent player, Location2 playerNextLoc) {
		player.set(VAR_X, playerNextLoc.x);
		player.set(VAR_Y, playerNextLoc.y);
	}

	private void stealBall(SoccerAgent from, SoccerAgent to) {
		boolean fromHasBall = (Boolean) from.get(SoccerGame.VAR_HAS_BALL);
		boolean toHasBall = (Boolean) to.get(SoccerGame.VAR_HAS_BALL);
		if (!(fromHasBall && !toHasBall)) throw new IllegalStateException("Steal Ball preconditions not correct");
		
		from.set(SoccerGame.VAR_HAS_BALL, false);
		to.set(SoccerGame.VAR_HAS_BALL, true);
	}

	/**
	 * A class for storing 2 dimensional position information. Add and subtract operations are defined for it.
	 * @author James MacGlashan
	 *
	 */
	class Location2{
		
		/**
		 * The x position
		 */
		public int x;
		
		/**
		 * The y position
		 */
		public int y;
		
		
		/**
		 * Constructs with the given position
		 * @param x the x position
		 * @param y the y position
		 */
		public Location2(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Constructs a new instance from a previous {@link Location2} instance
		 * @param l the {@link Location2} instance to copy.
		 */
		public Location2(Location2 l){
			this.x = l.x;
			this.y = l.y;
		}
		
		public Location2(SoccerAgent player) {
			this.x = player.x;
			this.y = player.y;
		}
		
		
		/**
		 * Returns a new {@link Location2} object that is the sum of this object and the provided object. This objects values
		 * are not affected by this operation.
		 * @param o the other object whose values should be added.
		 * @return a new {@link Location2} object that is the sum of this object and the provided object.
		 */
		public Location2 add(Location2 o){
			return new Location2(x+o.x, y+o.y);
		}
		
		
		/**
		 * Returns a new {@link Location2} object that is the subtraction of a provided object from this object (this - o). This objects values
		 * are not affected by this operation.
		 * @param o the other object whose values should be subtract.
		 * @return a new {@link Location2} object that is the subtraction of a provided object from this object (this - o).
		 */
		public Location2 subtract(Location2 o){
			return new Location2(x-o.x, y-o.y);
		}
		
		
		
		@Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

		@Override
        public boolean equals(Object o){
			if (this == o) return true;
            if(!(o instanceof Location2)){
                return false;
            }
            
            Location2 ol = (Location2)o;
            
            return x == ol.x && y == ol.y;
            
        }

        private SoccerModel getOuterType() {
            return SoccerModel.this;
        }
        
        @Override
        public String toString() {
        	return "[x][y]=["+x+"]["+y+"]";
        }
		
	}
	
	
	/**
	 * A class for holding a location and a probability associated with that location.
	 * @author James MacGlashan
	 *
	 */
	class Location2Prob{
		
		/**
		 * The location
		 */
		public Location2 l;
		
		/**
		 * The probability
		 */
		public double p;
		
		
		/**
		 * Initializes with the given location and probability
		 * @param l the location
		 * @param p the probability
		 */
		public Location2Prob(Location2 l, double p){
			this.l = l;
			this.p = p;
		}
		
	}
	
	
	/**
	 * A class for holding the joint probability for a particular set of location outcomes for each agent.
	 * @author James MacGlashan
	 *
	 */
	class LocationSetProb{
		
		/**
		 * The location outcomes for each agent
		 */
		public List <Location2> locs;
		
		/**
		 * The probability for this outcome sequence
		 */
		public double p;
		
		
		/**
		 * Initializes with a list of outcome locations for each agent and the probability of that joint outcome
		 * @param locs the location outcomes
		 * @param p the joint probability
		 */
		public LocationSetProb(List <Location2> locs, double p){
			this.locs = locs;
			this.p = p;
		}
	}
	
}
