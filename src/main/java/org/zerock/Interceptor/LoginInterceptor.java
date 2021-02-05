package org.zerock.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

 @Override
 public boolean preHandle(HttpServletRequest req,
    HttpServletResponse res, Object obj) throws Exception {
  
  HttpSession session = req.getSession();
  Object customerType = session.getAttribute("customerType");
  
  //로그인되어 있지 않은데 강제로 /myPage/order/list(주문목록) 으로 이동하려고 할때 무시하고 login창으로 넘겨버림.
  //로그인되어 있지 않은데 강제로 /login/userModify(개인정보수정) 으로 이동하려고 할때 무시하고 login창으로 넘겨버림.
  if(customerType == null) {
	  
   res.sendRedirect("/login");
   return false;
  }
  
  return true;
 }
}