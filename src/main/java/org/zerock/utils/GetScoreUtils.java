package org.zerock.utils;

import java.util.ArrayList;

public class GetScoreUtils {

	public static double getScore(ArrayList<Integer> score) {
		
		double AverageScore = 0;
		
		for(int i=0; i<score.size(); i++) {
			
			AverageScore += score.get(i);
		}
		
		try {
			AverageScore = Math.round(AverageScore/(double)score.size());
		} catch(ArithmeticException e) {
			AverageScore = 0;
		}
		return AverageScore;
	}
}
