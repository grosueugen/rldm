package com.gt.rldm.midterm;

import static com.gt.rldm.midterm.Sequence.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Test;

public class ComputeDeltaTest {
	
	// D C B 0
	@Test
	public void sequenceDeltaW1() {
		List<RealVector> observations = Arrays.asList(new RealVector[] {D, C, B});
		int outcome = 0;
		RealVector w = new OpenMapRealVector(
				new double[] { 1/(double)6, 2/(double)6, 3/(double)6, 4/(double)6, 5/(double)6 });
		Sequence seq = new Sequence(observations, outcome);
		RealVector deltaW = seq.computeDeltaW(0.1, 0.5, w);
		assertEquals(new double[]{-1/(double)60, -3/(double)120, -7/(double)240, 0D, 0D}, deltaW);
	}
	
	// D C D E F 1
	@Test
	public void sequenceDeltaW2() {
		RealVector w = new OpenMapRealVector(
				new double[] { 1/(double)6, 2/(double)6, 3/(double)6, 4/(double)6, 5/(double)6 });
		Sequence seq = new Sequence(Arrays.asList(new RealVector[] {D, C, D, E, F}), 1);
		RealVector deltaW = seq.computeDeltaW(0.1, 0.5, w);
		assertEquals(new double[]{0D, 15/(double)480, 27/(double)960, 3/(double)120, 1/(double)60}, deltaW);
	}
	
	@Test
	public void sequenceW() {
		List<RealVector> observations = Arrays.asList(new RealVector[] {D, C, B});
		int outcome = 0;
		RealVector w = new OpenMapRealVector(
				new double[] { 1/(double)6, 2/(double)6, 3/(double)6, 4/(double)6, 5/(double)6 });
		Sequence seq = new Sequence(observations, outcome);
		w = seq.computeW(0.1, 0.5, w);
		assertEquals(new double[] { -1 / (double) 60 + 1 / (double) 6, -3 / (double) 120 + 2 / (double) 6,
				-7 / (double) 240 + 3 / (double) 6, 4 / (double) 6, 5 / (double) 6D }, w);
	}
	
	@Test
	public void trainingSetW() {
		RealVector w = new OpenMapRealVector(
				new double[] { 1/(double)6, 2/(double)6, 3/(double)6, 4/(double)6, 5/(double)6 });
		Sequence seq1 = new Sequence(Arrays.asList(new RealVector[] {D, C, B}), 0);
		Sequence seq2 = new Sequence(Arrays.asList(new RealVector[] {D, C, D, E, F}), 1);
		TrainingSet ts = new TrainingSet(false);
		ts.addSequence(seq1);
		ts.addSequence(seq2);
		RealVector deltaW = ts.computeDeltaW(0.1D, 0.5D, w);
		double[] expectedDeltaW = seq1.computeDeltaW(0.1, 0.5, w).add(seq2.computeDeltaW(0.1, 0.5, w)).toArray();
		assertEquals(expectedDeltaW, deltaW);
		assertEquals(w.add(deltaW).toArray(), ts.accumulateAllChanges(0.1, 0.5, w));
	}

	private void assertEquals(double[] expected, RealVector deltaW) {
		for (int i = 0; i < deltaW.getDimension(); i++) {
			Assert.assertEquals("index " + i + " incorrect", expected[i], deltaW.getEntry(i), 0.001);
		}
	}

}
