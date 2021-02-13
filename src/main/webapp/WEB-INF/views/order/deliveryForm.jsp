<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">
  	<title>배송지입력</title>
  	<!-- Bootstrap core CSS -->
  	<link href="<%=request.getContextPath() %>/resources/myPage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  	<!-- Custom styles for this template -->
  	<link href="<%=request.getContextPath() %>/resources/common/css/shop-homepage.css" rel="stylesheet">
  	<link href="<%=request.getContextPath() %>/resources/common/css/common.css" rel="stylesheet">
  	<link href="<%=request.getContextPath() %>/resources/order/css/deliveryForm.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/nav.jsp"></jsp:include>
	<div class="container">
		<div class="container sub-container">
			<h1 class="main-title">배송지입력</h1>
		</div>
		<div class="container sub-container">
			<h3 class="buyer-title">구매자 정보</h3>
			<c:set var="buyer" value="${ buyer }"></c:set>
			<table class="table">
			  <tbody>
			    <tr>
			      <th scope="row" class="row-title">구매자 이름</th>
			      <td>${ buyer.customer_name }</td>
			    </tr>
			    <tr>
			      <th scope="row" class="row-title">구매자 이메일</th>
			      <td>${ buyer.customer_email }</td>
			    </tr>
			    <tr>
			      <th scope="row" class="row-title">구매자 전화번호</th>
			      <td>${ buyer.customer_phone }</td>
			    </tr>
			  </tbody>
			</table>
		</div>
		<div class="container sub-container delivery-container">
			<h3 class="buyer-title">받는사람 정보</h3>
			<c:set var="recipient" value="${ recipient }"></c:set>
				<input type="hidden" name="deliveryCode" value="${ recipient.deliveryCode }">
				<table class="table">
				  <tbody>
				    <tr>
				      <th scope="row" class="row-title">받는사람 이름</th>
				      <td><input class="form-control" name="recipient" placeholder="${ recipient.recipient }" type="text" value="${ recipient.recipient }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">주소</th>
				      <td><input class="form-control" name="shippingAddress" placeholder="${ recipient.shipping_address }" type="text" value="${ recipient.shipping_address }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">받는사람 전화번호</th>
				      <td><input class="form-control" name="deliverPhone" placeholder="${ recipient.deliver_phone }" type="text" value="${ buyer.customer_phone }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">요청사항</th>
				      <td><input class="form-control" name="requests" type="text"></td>
				    </tr>
				  </tbody>
				</table>
				<div class="submit-container">
					<input type="button" class="btn btn-dark submit-button" value="입력완료"></input>
				</div>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

	<!-- Bootstrap core JavaScript -->
	<script src="<%=request.getContextPath() %>/resources/mainPage/vendor/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/resources/mainPage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
	<!-- JavaScript -->
	<script src="<%=request.getContextPath() %>/resources/order/js/deliveryForm.js"></script>
</body>
</html>