package com.gt.rldm.midterm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Figure3Multithreaded {

	private final Map<Double, Double> rmse = new ConcurrentHashMap<>();
	private final String filePath = "C:\\gen\\projects\\rldm\\td\\src\\main\\resources\\trainingSets.txt";
	private final double alpha = 0.001;		
	private final double epsilon = 0.000001;
	private final double[] lambdas = new double[] {0, 0.1, 0.3, 0.5, 0.7, 0.9, 1};
	
	public static void main(String[] args) throws InterruptedException {
		Figure3Multithreaded f3 = new Figure3Multithreaded();
		List<Thread> threads = new ArrayList<>();
		for (double lambda : f3.lambdas) {
			Thread t = new Thread(new RepeatedEpisodeRunner(f3.filePath, f3.alpha, lambda, f3.epsilon, f3.rmse));
			threads.add(t);
		}
		for (Thread t : threads) {
			t.start();
		}
		
		while (f3.rmse.size() != f3.lambdas.length) {
			Thread.sleep(10000);
		}
		System.out.println(f3.rmse);
		
	}

}
