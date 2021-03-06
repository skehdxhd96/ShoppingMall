 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">
  	<title>마이페이지</title>
  	<!-- Bootstrap core CSS -->
  	<link href="<%=request.getContextPath() %>/resources/myPage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  	<!-- Custom styles for this template -->
  	<link href="<%=request.getContextPath() %>/resources/common/css/shop-homepage.css" rel="stylesheet">
  	<link href="<%=request.getContextPath() %>/resources/common/css/common.css" rel="stylesheet">
  	<link href="<%=request.getContextPath() %>/resources/order/css/orderList.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/nav.jsp"></jsp:include>
	
	<!-- Page Content -->
  	<div class="container top-container">
    	<div class="row">
      		<jsp:include page="/WEB-INF/views/common/myPageNav.jsp"></jsp:include>
      		
      		<div class="col-lg-9 main-col-lg-9">
        		<h2 class="main-title">주문상세</h2>
        		<c:set var="order" value="${ order }" />
        		<c:set var="odPro" value="${ odPro }" />
        		<c:set var="delivery" value="${ delivery }" />
        		<p>${order.orderDate} 주문</p>
        		<p>주문번호 ${ order.orderCode }</p>
        		<div class="container round-container">
        			<c:if test="${ order.orderStatus=='done' }">
        				<h3>주문완료</h3>
        			</c:if>
        			<c:if test="${ order.orderStatus=='cancel' }">
        				<h3>주문취소</h3>
        			</c:if>
        			<table class="table table-sm">
        				<c:forEach items="${odPro}" var="odPro">
	        				<tr>
	        					<th rowspan="2"><a href="/ProductDetail/${ odPro.product_code }"><img src="${ odPro.thumbnail_url}"></a></th>
	        					<th colspan="2"><a href="/ProductDetail/${ odPro.product_code }">${ odPro.product_name }</a></th>
	        				</tr>
	        				<tr>
	        					<th>${ odPro.product_price }원</th>
	        					<th>${ odPro.product_quantity }개</th>
	        				</tr>
	        			</c:forEach>
        			</table>
        		</div>
        		<div class="container round-container">
        			<c:if test="${ delivery.deliveryStatus=='preparing' }">
        				<h3>배송 준비중</h3>
        			</c:if>
        			<c:if test="${ delivery.deliveryStatus=='start' }">
        				<h3>배송중</h3>
        			</c:if>
        			<c:if test="${ delivery.deliveryStatus=='arrive' }">
        				<h3>배송 완료</h3>
        			</c:if>
        			<c:if test="${ delivery.deliveryStatus=='cancel' }">
        				<h3>배송 취소</h3>
        			</c:if>
        			<a href="/order/delivery/form?orderCode=${ order.orderCode }"><button type="button" class="btn btn-secondary btn-sm DeliUpdateBtn">배송지 변경</button></a>
        			<table class="table table-sm">
	        				<tr>
	        					<th>받는사람 이름</th>
	        					<th>${ delivery.recipient }</th>
	        				</tr>
	        				<tr>
	        					<th>받는사람 연락처</th>
	        					<th>${ delivery.deliverPhone }</th>
	        				</tr>
	        				<tr>
	        					<th>받는주소</th>
	        					<th>${ delivery.shippingAddress }</th>
	        				</tr>
	        				<tr>
	        					<th>요청사항</th>
	        					<th>${ delivery.requests }</th>
	        				</tr>
        			</table>
        		</div>
        		<div class="container round-container">
        			총 주문금액 : ${ order.totalOrderPrice }원
        		</div>
		    </div>
		    <!-- /.col-lg-9 -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
  
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	<!-- Bootstrap core JavaScript -->
  	<script src="<%=request.getContextPath() %>/resources/myPage/vendor/jquery/jquery.min.js"></script>
  	<script src="<%=request.getContextPath() %>/resources/myPage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  	<!-- JavaScript -->
  	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</body>
</html>