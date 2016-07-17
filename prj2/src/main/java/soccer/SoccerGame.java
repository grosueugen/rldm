package soccer;

import java.util.Arrays;
import java.util.List;

import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.state.GGWall;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.oo.OODomain;
import burlap.mdp.core.oo.propositional.GroundedProp;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.generic.GenericOOState;
import burlap.mdp.core.state.State;
import burlap.mdp.stochasticgames.JointAction;
import burlap.mdp.stochasticgames.SGDomain;
import burlap.mdp.stochasticgames.agent.SGAgent;
import burlap.mdp.stochasticgames.agent.SGAgentType;
import burlap.mdp.stochasticgames.model.JointRewardFunction;
import burlap.mdp.stochasticgames.oo.OOSGDomain;
import burlap.shell.visual.SGVisualExplorer;
import burlap.visualizer.Visualizer;

public class SoccerGame implements DomainGenerator {
	
	/**
	 * A constant for the name of the "has ball" attribute
	 */
	public static final String VAR_HAS_BALL = "hasBall";
	
	/**
	 * A constant for the name of the "own goal" attribute
	 */
	public static final String VAR_POSITIVE_GOAL = "positiveGoal"; 
	
	/**
	 * A constant for the name of the x position attribute
	 */
	public static final String VAR_X = "x";
	
	/**
	 * A constant for the name of the y position attribute
	 */
	public static final String VAR_Y = "y";
	
	/**
	 * A constant for the name of the player number attribute. The first player number is 0.
	 */
	public static final String VAR_PN = "playerNum";
	
	/**
	 * A constant for the name of the goal type attribute. Type 0 corresponds to a universal goal. Type i corresponds to a personal goal for player i-1.
	 */
	public static final String VAR_GT = "gt";
	
	/**
	 * A constant for the name of the first wall end position attribute. For a horizontal wall,
	 * this attribute represents the left end point; for a vertical wall, the bottom end point.
	 */
	public static final String VAR_E1 = "end1";
	
	/**
	 * A constant for the name of the second wall end position attribute. For a horizontal wall,
	 * this attribute represents the right end point; for a vertical wall, the top end point.
	 */
	public static final String VAR_E2 = "end2";
	
	/**
	 * A constant for the name of the attribute for defining the walls position along its orthogonal direction.
	 * For a horizontal wall, this attribute represents the y position of the wall; for a vertical wall,
	 * the x position.
	 */
	public static final String VAR_POS = "pos";
	
	/**
	 * A constant for the name of the wall type attribute.
	 */
	public static final String VAR_WT = "wallType";
	
	public static final String PLAYER_A_NAME = "agent0";
	public static final String PLAYER_B_NAME = "agent1";
	
	/**
	 * A constant for the name of the agent class.
	 */
	public static final String CLASS_AGENT = "agent";
	
	/**
	 * A constant for the name of the goal class.
	 */
	public static final String CLASS_GOAL = "goal";
	
	/**
	 * A constant for the name of the horizontal wall class.
	 */
	public static final String CLASS_DIM_H_WALL = "dimensionlessHorizontalWall";
	
	/**
	 * A constant for the name of the vertical wall class.
	 */
	public static final String CLASS_DIM_V_WALL = "dimensionlessVerticalWall";
	
	/**
	 * A constant for the name of the north action.
	 */
	public static final String ACTION_NORTH = "north";
	
	/**
	 * A constant for the name of the south action.
	 */
	public static final String ACTION_SOUTH = "south";
	
	/**
	 * A constant for the name of the east action.
	 */
	public static final String ACTION_EAST = "east";
	/**
	 * A constant for the name of the west action.
	 */
	public static final String ACTION_WEST = "west";
	
	/**
	 * A constant for the name of the no operation (do nothing) action.
	 */
	public static final String ACTION_NOOP = "noop";
	
	/**
	 * A constant for the name of a propositional function that evaluates whether an agent is in a personal goal location for just them.
	 */
	public static final String PF_IN_P_GOAL = "inPersonalGoal";
	
	public static final int MAX_X = 3;
	public static final int MAX_Y = 1;
	
	@Override
	public OOSGDomain generateDomain() {
		OOSGDomain domain = new OOSGDomain();
		
		
		domain.addStateClass(CLASS_AGENT, SoccerAgent.class)
				.addStateClass(CLASS_GOAL, SoccerGoal.class)
				.addStateClass(CLASS_DIM_H_WALL, GGWall.GGHorizontalWall.class)
				.addStateClass(CLASS_DIM_V_WALL, GGWall.GGVerticalWall.class);
		

		domain.addActionType(new UniversalActionType(ACTION_NORTH))
				.addActionType(new UniversalActionType(ACTION_SOUTH))
				.addActionType(new UniversalActionType(ACTION_EAST))
				.addActionType(new UniversalActionType(ACTION_WEST))
				.addActionType(new UniversalActionType(ACTION_NOOP));

		
		
		OODomain.Helper.addPfsToDomain(domain, this.generatePFs());

		domain.setJointActionModel(new SoccerModel());
		
		return domain;
	}
	
	List<PropositionalFunction> generatePFs(){
		return Arrays.asList(new AgentInGoal(PF_IN_P_GOAL));
	}
	
	public static State getInitialState(){

		GenericOOState s = new GenericOOState(
				new SoccerAgent(2, 1, false, 0, PLAYER_A_NAME),//A
				new SoccerAgent(1, 1, true, 1, PLAYER_B_NAME),//B
				
				new SoccerGoal(0, 0, true, 1, "g00"),//positive goal A
				new SoccerGoal(0, 1, true, 1, "g01"),//positive goal A
				new SoccerGoal(3, 0, false, 1, "g02"),//negative goal A
				new SoccerGoal(3, 1, false, 1, "g03"),//negative goal A
				
				new SoccerGoal(3, 0, true, 2, "g10"),//positive goal B
				new SoccerGoal(3, 1, true, 2, "g11"),//positive goal B
				new SoccerGoal(0, 0, false, 2, "g12"),//negative goal B
				new SoccerGoal(0, 1, false, 2, "g13")//negative goal B
				
		);

		setBoundaryWalls(s, MAX_X+1, MAX_Y+1);

		return s;
	}
	
	/**
	 * Creates and returns a standard {@link SGAgentType} for grid games. This {@link SGAgentType}
	 * is assigned the type name "agent", grid game OO-MDP object class for "agent", and has its action space set to all possible actions in the grid game domain.
	 * Typically, all agents in a grid game should be assigned to the same type.
	 *
	 * @param domain the domain object of the grid game.
	 * @return An {@link SGAgentType} that typically all {@link SGAgent}'s of the grid game should play as.
	 */
	public static SGAgentType getAgentType(SGDomain domain){
		return new SGAgentType(CLASS_AGENT, domain.getActionTypes());
	}
	
	/**
	 * Sets boundary walls of a domain. This method will add 4 solid walls (top left bottom right) to create
	 * a playing field in which the agents can interact.
	 * @param s the state in which the walls should be added
	 * @param w the width of the playing field
	 * @param h the height of the playing field
	 */
	public static void setBoundaryWalls(GenericOOState s, int w, int h){
		int numV = s.objectsOfClass(CLASS_DIM_V_WALL).size();
		int numH = s.objectsOfClass(CLASS_DIM_H_WALL).size();
		
		s.addObject(new GGWall.GGVerticalWall(0, h-1, 0, 0, "h"+numH))
				.addObject(new GGWall.GGVerticalWall(0, h-1, w, 0, "h"+(numH+1)))
				.addObject(new GGWall.GGHorizontalWall(0, w-1, 0, 0, "v"+numV))
				.addObject(new GGWall.GGHorizontalWall(0, w-1, h, 0, "v"+(numV+1)) );

	}
	
	/**
	 * Defines a propositional function that evaluates to true when a given agent is in any of its goals
	 * @author James MacGlashan
	 *
	 */
	static class AgentInGoal extends PropositionalFunction{
		/**
		 * Initializes with the given name and domain and is set to evaluate on agent objects
		 * @param name the name of the propositional function
		 */
		public AgentInGoal(String name) {
			super(name, new String[]{CLASS_AGENT});
		}

		@Override
		public boolean isTrue(OOState s, String... params) {
			ObjectInstance agent = s.object(params[0]);
			int ax = (Integer)agent.get(VAR_X);
			int ay = (Integer)agent.get(VAR_Y);
			boolean aball = (Boolean) agent.get(VAR_HAS_BALL);
			int apn = (Integer)agent.get(VAR_PN);
			
			//find all universal goals
			List <ObjectInstance> goals = s.objectsOfClass(CLASS_GOAL);
			for(ObjectInstance goal : goals){
				
				int gt = (Integer)goal.get(VAR_GT);
				if(gt == apn+1){
				
					int gx = (Integer)goal.get(VAR_X);
					int gy = (Integer)goal.get(VAR_Y);
					//not used, as goal should not have this var
					if(gx == ax && gy == ay && aball){
						return true;
					}
				}
			}
			return false;
		}
	}
	
	public static class SoccerJointRewardFunction implements JointRewardFunction {
		
		PropositionalFunction agentInGoal;
		
		/**
		 * Initialises for a given domain and unique goal reward for each player.
		 * 
		 * @param domain the domain
		 */
		public SoccerJointRewardFunction(OODomain domain){
			this.agentInGoal = domain.propFunction(PF_IN_P_GOAL);
		}
		
		/*
		 * If sp is a terminal state, then
		 * - if player A scored in B's spot => A: +100, B: -100 
		 * - if player A scored in A's own spot => A: -100, B: 100
		 * And vice-versa.
		 */
		@Override
		public double[] reward(State s, JointAction ja, State sp) {
			OOState osp = (OOState)sp;

			double [] rewards = new double[ja.size()];
			
			//get all agents and initialize reward to default
			List <ObjectInstance> obs = osp.objectsOfClass(CLASS_AGENT);
			for(ObjectInstance o : obs){
				int aid = ((SoccerAgent)o).player;
				rewards[aid] = 0.;
			}
			
			
			//check for any agents that reached a personal goal location and give them a goal reward if they did
			//List<GroundedProp> ipgps = sp.getAllGroundedPropsFor(agentInPersonalGoal);
			List<GroundedProp> ipgps = agentInGoal.allGroundings((OOState)sp);
			for(GroundedProp gp : ipgps){
				String agentName = gp.params[0];
				if(gp.isTrue(osp)){
					SoccerAgent sa = (SoccerAgent) osp.object(agentName);
					if (positiveGoal(sa)) {
						rewards[sa.player] = 100;
						rewards[other(sa.player)] = -100;
					} else {
						rewards[sa.player] = -100;
						rewards[other(sa.player)] = 100;
					}					
				}
			}
			return rewards;

		}

		private boolean positiveGoal(SoccerAgent sa) {
			int player = sa.player;
			if (player != 0 && player != 1) throw new IllegalStateException("bug with player ids");
			boolean playerA = (player == 0);
			int x = sa.x;
			int y = sa.y;
			if (playerA) {
				if (x == 0 && y == 0 || x == 0 && y == 1) return true;
				if (x == 3 && y == 0 || x == 3 && y == 1) return false;
				throw new IllegalStateException("Goal A state bug");
			}
			//playerB
			if (x == 0 && y == 0 || x == 0 && y == 1) return false;
			if (x == 3 && y == 0 || x == 3 && y == 1) return true;
			throw new IllegalStateException("Goal B state bug");
		}

		private int other(int player) {
			return 1-player;
		}
	}
	
	/**
	 * Causes termination when any agent reaches a personal goal location.
	 *
	 */
	public static class SoccerTerminalFunction implements TerminalFunction {

		PropositionalFunction agentInGoal;
		
		
		/**
		 * Initializes for the given domain
		 * @param domain the specific grid world domain.
		 */
		public SoccerTerminalFunction(OODomain domain){
			agentInGoal = domain.propFunction(PF_IN_P_GOAL);
		}
		
		
		@Override
		public boolean isTerminal(State s) {
			//check personal goals; if anyone reached their personal goal, it's game over
			//List<GroundedProp> ipgps = s.getAllGroundedPropsFor(agentInPersonalGoal);
			List<GroundedProp> ipgps = agentInGoal.allGroundings((OOState)s);
			for(GroundedProp gp : ipgps){
				if(gp.isTrue((OOState)s)){
					return true;
				}
			}
			return false;
		}
	}
	
	public static void main(String[] args) {
		SoccerGame soccer = new SoccerGame();
		OOSGDomain d = soccer.generateDomain();
		State s = SoccerGame.getInitialState();
		Visualizer v = GGVisualizer.getVisualizer(9, 9);
		SGVisualExplorer exp = new SGVisualExplorer(d, v, s);
		exp.addKeyAction("s", 0, ACTION_SOUTH, "");
		exp.addKeyAction("d", 1, ACTION_SOUTH, "");
		exp.initGUI();
	}

}
