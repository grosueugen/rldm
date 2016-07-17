package soccer;

import java.util.ArrayList;
import java.util.List;

import burlap.behavior.policy.support.ActionProb;
import burlap.behavior.stochasticgames.GameEpisode;
import burlap.behavior.stochasticgames.PolicyFromJointPolicy;
import burlap.behavior.stochasticgames.agents.madp.MultiAgentDPPlanningAgent;
import burlap.behavior.stochasticgames.auxiliary.GameSequenceVisualizer;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.CorrelatedQ;
import burlap.behavior.stochasticgames.madynamicprogramming.dpplanners.MAValueIteration;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.ECorrelatedQJointPolicy;
import burlap.behavior.stochasticgames.solvers.CorrelatedEquilibriumSolver;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.stochasticgames.agent.SGAgentType;
import burlap.mdp.stochasticgames.oo.OOSGDomain;
import burlap.mdp.stochasticgames.world.World;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;
import soccer.SoccerGame.SoccerJointRewardFunction;
import soccer.SoccerGame.SoccerTerminalFunction;

public class RunSoccer {
	
	public static void main(String[] args) {
		final SoccerGame soccer = new SoccerGame();
		final OOSGDomain domain = soccer.generateDomain();
		final HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
		State s = SoccerGame.getInitialState();
		
		SoccerJointRewardFunction rf = new SoccerGame.SoccerJointRewardFunction(domain);
		SoccerTerminalFunction tf = new SoccerGame.SoccerTerminalFunction(domain);
		
		SGAgentType at = SoccerGame.getAgentType(domain);
		
		CorrelatedQ correlatedQ = new CorrelatedQ(CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective.UTILITARIAN);
		MAValueIteration vi = new MAValueIteration(domain, rf, tf, 0.9, hashingFactory, 0., correlatedQ, 0.00015, 100);//50
		
		World w = new World(domain, rf, tf, s);
		
		//for correlated Q, use a correlated equilibrium policy joint policy
		ECorrelatedQJointPolicy jointPolicy = new ECorrelatedQJointPolicy(
				CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective.UTILITARIAN, 0.);
		
		//how the agents synchonize themselfs
		PolicyFromJointPolicy policyPlayerA = new PolicyFromJointPolicy(0, jointPolicy, true);
		MultiAgentDPPlanningAgent playerA = new MultiAgentDPPlanningAgent(domain, vi,
				policyPlayerA, SoccerGame.PLAYER_A_NAME, at);
		PolicyFromJointPolicy policyPlayerB = new PolicyFromJointPolicy(1, jointPolicy, true);
		MultiAgentDPPlanningAgent playerB = new MultiAgentDPPlanningAgent(domain, vi,
				policyPlayerB, SoccerGame.PLAYER_B_NAME, at);
		
		w.join(playerA);
		w.join(playerB);
		
		GameEpisode ga = null;
		List<GameEpisode> games = new ArrayList<GameEpisode>();
		for(int i = 0; i < 10; i++){
			System.out.println("Game run: " + (i+1));
			ga = w.runGame();
			games.add(ga);
		}

		/*Visualizer v = SoccerVisualizer.getVisualizer(9, 9);
		new GameSequenceVisualizer(v, domain, games);*/
		
		System.out.println("Joint Policy Distribution ");
		List<ActionProb> policyDistribution = jointPolicy.policyDistribution(s);
		for (ActionProb ap : policyDistribution) {
			Action action = ap.ga;
			double prob = ap.pSelection;
			if (prob != 0) System.out.println(action.actionName() + ": " + prob);
		}
		System.out.println("Policy Distribution A");
		List<ActionProb> policyDistributionA = policyPlayerA.policyDistribution(s);
		for (ActionProb ap : policyDistributionA) {
			Action action = ap.ga;
			double prob = ap.pSelection;
			if (prob != 0) System.out.println(action.actionName() + ": " + prob);
		}
		
		System.out.println("Policy Distribution B");
		List<ActionProb> policyDistributionB = policyPlayerB.policyDistribution(s);
		for (ActionProb ap : policyDistributionB) {
			Action action = ap.ga;
			double prob = ap.pSelection;
			if (prob != 0) System.out.println(action.actionName() + ": " + prob);
		}
	}

}
