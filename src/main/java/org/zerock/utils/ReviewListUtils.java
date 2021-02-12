package org.zerock.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;

public class ReviewListUtils {
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper rm;

	public static List<Integer> getReviewList(List<HashMap<String, Integer>> rl) {

		List<Integer> sol = new ArrayList<Integer>();

		for (int i=0; i< rl.size(); i++)
		{
			sol.add(rl.get(i).get("review_code"));
		}

		return sol;
	}
}
