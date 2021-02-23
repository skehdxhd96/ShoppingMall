package org.zerock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInfoListVO {

	private List<GetOrderInfoVO> Products;
	private int totalPrice;
}
