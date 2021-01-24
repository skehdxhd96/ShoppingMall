package org.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class ReplyController {

	@Setter(onMethod_ = @Autowired)
	private ReplyService rs;
	
	@PostMapping(value = "replies/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE} )
	public ResponseEntity<String> create(@RequestBody ReplyVO r) {
		
		int insertCount = rs.insert(r);
		
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@GetMapping(value = "ProductDetail/replies/pages/{product_code}/{page}",
			produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ReplyVO>> getList(
			@PathVariable("page") int page,
			@PathVariable("product_code") int product_code) {
		
		Criteria cri = new Criteria(page, 10);
		
		return new ResponseEntity<>(rs.getListWithPaging(cri, product_code), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "replies/{review_code}",
			produces = {MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("review_code") int review_code) {
		
		return new ResponseEntity<>(rs.read(review_code), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "replies/{review_code}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("review_code") int review_code) {
		
		return rs.delete(review_code) == 1?
				new ResponseEntity<>("success", HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "replies/{review_code}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO r, @PathVariable("review_code") int review_code) {
		
		r.setReview_code(review_code);
		
		return rs.update(r) == 1?
				new ResponseEntity<>("success", HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
