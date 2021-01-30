package org.zerock.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BasicInterceptor extends HandlerInterceptorAdapter {

 @Override
 public boolean preHandle(HttpServletRequest req,
    HttpServletResponse res, Object obj) throws Exception {
  
  HttpSession session = req.getSession();
  Object customerType = session.getAttribute("customerType");
  
  //로그인되어있지 않거나, 사용자로 로그인중인데 상품업로드 url로 강제 접속하려고 할때 무시하고 login창으로 넘겨버림.
  if(customerType == null) {
	  
   res.sendRedirect("/login");
   return false;
  } else if((int)customerType != 2) {
	  //경고창 띄우는거 없나..
	  res.sendRedirect("/");
	  return false;
  }
  
  return true;
 }
}