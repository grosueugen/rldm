package com.gt.rldm.hw6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HW6 {
	
	public static void main(String[] args) {
		Input in = new Input("hw6");
		int numOfPatrons = in.n();
		int[][] atEstablishment = in.episodes();
		int[] fightOccurred = in.fights();
		
		Set<PatronFunction> H = new LinkedHashSet<>(numOfPatrons * (numOfPatrons-1));
		for (int i = 0; i < numOfPatrons; i++) {
			for (int p = 0; p < numOfPatrons; p++) {
				if (i != p) {
					PatronFunction fct = new PatronFunction(numOfPatrons, i, p);
					H.add(fct);
				}
			}
		}
		List<PatronResult> res = new ArrayList<>();
		
		Set<PatronFunction> HPrime = new LinkedHashSet<>(numOfPatrons * (numOfPatrons-1));
		HPrime.addAll(H);
		Set<PatronResult> currentEval = new HashSet<>();
		for (int i = 0; i < atEstablishment.length; i++) {
			int[] episode = atEstablishment[i];
			for (PatronFunction f : HPrime) {
				currentEval.add(f.evaluate(episode));
			}
			if (currentEval.size() == 0) throw new RuntimeException("ERROR: didn't find any function");
			if (currentEval.size() == 1) res.add(currentEval.iterator().next());
			if (currentEval.size() == 2) {
				res.add(PatronResult.I_DO_NOT_KNOW);
				HPrime = computeNewH(HPrime, episode, fightOccurred[i]);
			}
			currentEval.clear();
		}
		
		
		
		StringBuilder sb = new StringBuilder("");
		int i = 0;
		for (PatronResult pr : res) {
			sb.append(pr);
			if (i < (res.size()-1)) {
				sb.append(",");
			}
			i++;
		}
		
		System.out.println(sb);
		//System.out.println(res);
	}

	private static Set<PatronFunction> computeNewH(Set<PatronFunction> h, int[] x, int y) {
		PatronResult Y = null;
		if (y == 0) {
			Y = PatronResult.NO_FIGHT;
		} else if (y == 1) {
			Y = PatronResult.FIGHT;
		} else {
			throw new IllegalArgumentException("can not transform y: " + y + " in PatronResult");
		}
		
		Set<PatronFunction> res = new LinkedHashSet<>();
		for (PatronFunction f : h) {
			if (Y == f.evaluate(x)) {
				res.add(f);
			}
		}
		return res;
	}

}
