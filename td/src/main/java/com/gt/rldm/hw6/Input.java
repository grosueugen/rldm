package com.gt.rldm.hw6;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Input {
	
	private int n;
	private int[] fights;
	private int[][] episodes;

	public Input(String file) {
		compute(file);
	}

	private void compute(String filePath) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filePath).getFile());
		try (Scanner scanner = new Scanner(file)) {
			n = Integer.parseInt(scanner.nextLine());
			readFights(scanner.nextLine());
			readEpisodes(scanner.nextLine());
			
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readFights(String line) {
		String[] split = line.split(",");
		fights = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			fights[i] = Integer.parseInt(split[i]);
		}
	}

	private void readEpisodes(String line) {
		String[] split = line.split(",");
		episodes = new int[fights.length][n];
		System.out.println("BEFORE nr in file: " + split.length + ", fights.lenght:" + fights.length + ", n:" + n);
		int k = 0;
		int e = 0;
		int[] episode = new int[n];
		for (int i = 0; i < split.length; i++) {
			episode[k] = Integer.parseInt(split[i]);			
			if (k == (n-1)) {
				episodes[e] = episode;
				e++;
				episode = new int[n];
				k = 0;
			} else {
				k++;
			}
		}
		System.out.println("AFTER episodes.length: " + episodes.length + ", episodes.length[0]: " + episodes[0].length);
	}

	public int n() {
		return n;
	}
	
	public int[][] episodes() {
		return episodes;
	}
	
	public int[] fights() {
		return fights;
	}

}
