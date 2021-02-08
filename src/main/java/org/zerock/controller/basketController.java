package org.zerock.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageVO;
import org.zerock.domain.basketVO;
import org.zerock.service.basketService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class basketController {
	
	@Setter(onMethod_ = @Autowired)
	private basketService bs;

	@GetMapping(value = "/myPage/basket/{customerCode}/{page}",
			produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<basketVO>> getList(
			@PathVariable("customerCode") Long customerCode,
			@PathVariable("page") int page) {
		Criteria cri = new Criteria(page, 6);
		
		return new ResponseEntity<>(bs.getList(cri, customerCode), HttpStatus.OK);
	}
	
	@PostMapping(value = "/myPage/basket/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> addBasketProduct(@RequestBody basketVO b) {

		int insertCount = bs.getBasketProduct(b);
			
		return insertCount == 1 ? new ResponseEntity<>("Finish to add Product to Your Basket", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
