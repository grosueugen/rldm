package com.gt.rldm.midterm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealVector;

public class DataGenerator {
	
	private final int n = 100;
	
	private final List<TrainingSet> trainingSets = new ArrayList<>(n);
	
	public DataGenerator() {
		compute();
	}
	
	public DataGenerator(String filePath) {
		addData(filePath);
	}

	private void addData(String filePath) {
		try {
			Path targetPath = Paths.get(filePath);
			List<String> lines = Files.readAllLines(targetPath);
			TrainingSet currentTs = new TrainingSet(false);
			trainingSets.add(currentTs);
			for (String line : lines) {
				Sequence s = readSequence(line);
				if (currentTs.size() == 10) {
					currentTs = new TrainingSet(false);
					trainingSets.add(currentTs);
				}
				currentTs.addSequence(s);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private Sequence readSequence(String line) {
		List<RealVector> observations = new ArrayList<>();
		String[] data = line.split(" ");
		int size = data.length;
		for (int i = 0; i < size - 1; i++) {
			String obs = data[i];
			if ("B".equals(obs)) {
				observations.add(Sequence.B);
			} else if ("C".equals(obs)) {
				observations.add(Sequence.C);
			} else if ("D".equals(obs)) {
				observations.add(Sequence.D);
			} else if ("E".equals(obs)) {
				observations.add(Sequence.E);
			} else if ("F".equals(obs)) {
				observations.add(Sequence.F);
			}
			 
		}
		int outcome = Integer.parseInt(data[size-1]);
		return new Sequence(observations, outcome);
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
