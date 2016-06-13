package com.gt.rldm.midterm;

import java.util.Arrays;

import org.apache.commons.math3.linear.RealVector;

public class Utils {
	
	public static void printVector(String msg, RealVector v) {
		System.out.println(msg + ": " + Arrays.toString(v.toArray()));
	}

}
