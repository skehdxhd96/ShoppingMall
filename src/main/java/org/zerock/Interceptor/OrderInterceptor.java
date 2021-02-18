package org.zerock.Interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zerock.service.OrderServiceImpl;

public class OrderInterceptor extends HandlerInterceptorAdapter{
	@Resource
	OrderServiceImpl orderService;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		HttpSession session = req.getSession();
		Object customerType = session.getAttribute("customerType");
		
		if (customerType==null) {
			res.sendRedirect("/login");
			
			return false;
		} else if ((int) customerType==2) {
			res.sendRedirect("/error/accessDenied");
			
			return false;
		} else {
			long customerCode=0;
			
			try {
				customerCode = orderService.getCustomerCodeByOrder(Integer.parseInt(req.getQueryString().split("=")[1]));
//				if (req.getQueryString().split("=")[0].equals("deliveryCode")) {
//					customerCode = orderService.getCustomerCodeByDeliery(Integer.parseInt(req.getQueryString().split("=")[1]));
//				}
//				else {
//					customerCode = orderService.getCustomerCodeByOrder(Integer.parseInt(req.getQueryString().split("=")[1]));
//				}
			} catch(NullPointerException e) {
				return true;
			}
			
			if (customerCode!=(long) session.getAttribute("customerCode")) {
				res.sendRedirect("/error/accessDenied");
				
				return false;
			}
		}
		
		return true;
	 }
}
