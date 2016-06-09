package com.gt.rldm.midterm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
	
	private final int n = 100;
	
	private final List<TrainingSet> trainingSets = new ArrayList<>(n);
	
	public DataGenerator() {
		compute();
	}

	private void compute() {
		for (int i = 0; i < n; i++) {
			trainingSets.add(new TrainingSet());
		}
	}
	
	public List<TrainingSet> getTrainingSets() {
		return trainingSets;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (TrainingSet ts : trainingSets) {
			sb.append(ts).append("\n");
		}
		return sb.toString();
	}
	
	public void saveToFile(String filePath) {
		Path targetPath = Paths.get(filePath);
	    byte[] bytes = this.toString().getBytes(StandardCharsets.UTF_8);
	    try {
			Files.write(targetPath, bytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException("can not save data to file", e);
		}
	}

}
