package org.zerock.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.service.DeliveryServiceImpl;
import org.zerock.service.OrderServiceImpl;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class OrderControllerTests {
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		log.info("complete setup!");
	}
	
	@Resource
	private OrderServiceImpl orderService;
	@Resource
	private DeliveryServiceImpl deliveryService;
	
	public int testorderCancel() {
		int orderCode = 186;
		
		if (orderService.updateStatus(orderCode, "cancel")==0) {
			log.info("업데이트 될 데이터가 존재하지 않습니다.");
			
			return 0;
		}
		else {
			log.info("orderStatus : cancel로 변경되었습니다. 주문코드 : " + orderCode);
			
			return deliveryService.updateDeliveryStatus(orderCode, "cancel");
		}
	}
	
	@Test
	public void checkOrderCancel() {
		log.info(testorderCancel());
	}
	
//	@Test
//	public void checkOrderPaging() throws Exception {
//		HashMap<String, Object> hm = new HashMap<String, Object>();
//		hm.put("page", 1);
//		hm.put("orderStatus", "done");
//		
//		mockMvc.perform(post("/myPage/order/paging")
//				.param("page", "1")
//				.param("orderStatus", "done"));
//	}
}
