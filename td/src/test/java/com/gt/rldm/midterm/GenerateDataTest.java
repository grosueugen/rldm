package com.gt.rldm.midterm;

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
		new DataGenerator().saveToFile("D:\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt");
	}

}
