package com.richardchan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumericUtil {
	
	public static double calculateMedian(List<Long> input) {
		if (input == null || input.isEmpty()) {
			return Double.NaN;
		}
		
		List<Long> clone = new ArrayList<>(input);
		
		Collections.sort(clone);
		int size = clone.size();
		if (size % 2 == 0) {
			long l1 = clone.get(size/2-1).longValue();
			long l2 = clone.get(size/2).longValue();
			return (l1 + l2)/ 2d;
		} else {
			return clone.get(size/2);
		}
	}
	
	
	public static double calculateMedianDouble(List<Double> input) {
		if (input == null || input.isEmpty()) {
			return Double.NaN;
		}
		
		List<Double> clone = new ArrayList<>(input);
		
		Collections.sort(clone);
		int size = clone.size();
		if (size % 2 == 0) {
			double l1 = clone.get(size/2-1).doubleValue();
			double l2 = clone.get(size/2).doubleValue();
			return (l1 + l2)/ 2d;
		} else {
			return clone.get(size/2);
		}
	}

}
