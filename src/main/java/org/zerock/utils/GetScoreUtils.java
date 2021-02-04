package org.zerock.utils;

import java.util.ArrayList;

public class GetScoreUtils {

	public static int getScore(ArrayList<Integer> score) {
		
		int AverageScore = 0;
		
		for(int i=0; i<score.size(); i++) {
			
			AverageScore += score.get(i);
		}
		
		try {
			AverageScore = Math.round(AverageScore/score.size());
		} catch(ArithmeticException e) {
			AverageScore = 0;
		}
		return AverageScore;
	}
}
