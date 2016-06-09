package com.gt.rldm.midterm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;

public class Sequence {
	
	private List<RealVector> observations = new ArrayList<>();
	private int outcome;
	
	public final RealVector B = new OpenMapRealVector(new Double[]{1D, 0D, 0D, 0D, 0D});
	public final RealVector C = new OpenMapRealVector(new Double[]{0D, 1D, 0D, 0D, 0D});
	public final RealVector D = new OpenMapRealVector(new Double[]{0D, 0D, 1D, 0D, 0D});
	public final RealVector E = new OpenMapRealVector(new Double[]{0D, 0D, 0D, 1D, 0D});
	public final RealVector F = new OpenMapRealVector(new Double[]{0D, 0D, 0D, 0D, 1D});
	
	private final Map<Integer, RealVector> intToVector = new HashMap<>();
	{
		intToVector.put(1, B);
		intToVector.put(2, C);
		intToVector.put(3, D);
		intToVector.put(4, E);
		intToVector.put(5, F);
	}
	
	public Sequence() {
		compute();
	}

	private void compute() {
		int currentState = 3;
		while (currentState != 0 && currentState != 6) {
			observations.add(intToVector.get(currentState));
			boolean goLeft = (Math.random() < 0.5D);
			if (goLeft) {
				currentState--;
			} else {
				currentState++;
			}
		}
		if (currentState == 0) outcome = 0;
		else outcome = 1;
	}
	
	public int size() {
		return observations.size();
	}
	
	public List<RealVector> getObservations() {
		return observations;
	}
	
	public int getOutcome() {
		return outcome;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (RealVector obs : observations) {
			if (obs == B) sb.append("B").append(" ");
			else if (obs == C) sb.append("C").append(" ");
			else if (obs == D) sb.append("D").append(" ");
			else if (obs == E) sb.append("E").append(" ");
			else if (obs == F) sb.append("F").append(" ");
			else throw new IllegalStateException("Incorrect state observation: " + obs);
		}
		sb.append(outcome);
		return sb.toString();
	}
	
}
