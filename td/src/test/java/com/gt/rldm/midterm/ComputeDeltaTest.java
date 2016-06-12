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
	public void sequence() {
		List<RealVector> observations = Arrays.asList(new RealVector[] {D, C, B});
		int outcome = 0;
		RealVector w = new OpenMapRealVector(
				new double[] { 1 / (double) 6, 2 / (double) 6, 3 / (double) 6, 4 / (double) 6, 5 / (double) 6 });
		Sequence seq = new Sequence(observations, outcome);
		RealVector deltaW = seq.computeDeltaW(0.1, 0.5, w);
		assertEquals(new double[]{-1/(double)60, -3/(double)120, -7/(double)240, 0D, 0D}, deltaW);
	}

	private void assertEquals(double[] expected, RealVector deltaW) {
		for (int i = 0; i < deltaW.getDimension(); i++) {
			Assert.assertEquals("index " + i + " incorrect", expected[i], deltaW.getEntry(i), 0.001);
		}
	}

}
