package com.gt.rldm;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Die {
	
	private final int n;
	
	private final Map<Integer, Boolean> sides;
	
	public Die(int n) {
		this.n = n;
		sides = new LinkedHashMap<>(n);
	}

	public void addSide(int i, boolean good) {
		sides.put(i, good);
	}
	
	public double winProbability() {
		return getNrGoodSides()/n;
	}
	
	public double loseProbability() {
		return 1-winProbability();
	}

	public boolean isGood(int side) {
		return sides.get(side);
	}
	
	public boolean isBad(int side) {
		return !isGood(side);
	}

	public int getN() {
		return n;
	}
	
	public int getNrGoodSides() {
		int res = 0;
		for (Integer i : sides.keySet()) {
			if (sides.get(i) == true) res++;
		}
		return res;
	}
	
	public Set<Integer> getGoodSides() {
		Set<Integer> res = new LinkedHashSet<>();
		for (Integer i : sides.keySet()) {
			if (sides.get(i) == true) res.add(i);
		}
		return res;
	}

}
