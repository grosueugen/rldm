package com.gt.rldm.midterm;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

public class GenerateDataTest {
	
	@Test
	public void sequence() {
		int n = 10;
		for (int i = 0; i < n; i++) {
			System.out.println(new Sequence());
		}
	}
	
	@Test
	public void trainingSet() {
		TrainingSet ts = new TrainingSet();
		System.out.println(ts);
	}
	
	@Test
	public void saveTrainingSets() {
		//new DataGenerator().saveToFile("D:\\projects\\rldm\\td\\src\\main\\resources\\trainingSets2.txt");
	}
	
	@Test
	public void loadTrainingSets() {
		List<TrainingSet> trainingSets = new DataGenerator(
				"D:\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt").getTrainingSets();
		assertEquals(100, trainingSets.size());
		for (int i = 0; i < trainingSets.size(); i++) {
			assertEquals(10, trainingSets.get(i).size());
		}
		//D E F E F E F E F 1
		Sequence sequence1 = trainingSets.get(0).getSequences().get(0);
		List<RealVector> observations = sequence1.getObservations();
		assertEquals(9, observations.size());
		assertEquals(Sequence.D, observations.get(0));
		assertEquals(Sequence.E, observations.get(1));
		assertEquals(Sequence.F, observations.get(2));
		assertEquals(Sequence.E, observations.get(3));
		assertEquals(Sequence.F, observations.get(4));
		assertEquals(Sequence.E, observations.get(5));
		assertEquals(Sequence.F, observations.get(6));
		assertEquals(Sequence.E, observations.get(7));
		assertEquals(Sequence.F, observations.get(8));
		
		assertEquals(sequence1.getOutcome(), 1);
		System.out.println(trainingSets.get(0).getSequences().get(9));
		System.out.println(trainingSets.get(99).getSequences().get(9));
	}

}
