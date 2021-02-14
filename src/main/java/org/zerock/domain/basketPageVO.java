package org.zerock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class basketPageVO {

	private int BasketCount;
	private List<basketVO> list;
}
