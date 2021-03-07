<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">
  	<title>주문 완료</title>
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
			<h1 class="main-title" style="text-align:center">주문이 완료되었습니다!</h1>
		</div>
		<div class="container sub-container">
			<table class="table">
			  <tbody>
			    <tr>
			      <th scope="row" class="row-title">주문 번호</th>
			      <td>${ orderCode }</td>
			      <td><a href="/order/detail?orderCode=${ orderCode }"><button type="button" class="btn btn-secondary btn-sm">주문상세내역 확인</button></a></td>
			    </tr>
			    <tr>
			      <th scope="row" class="row-title">배송지</th>
			      <c:set var="delivery" value="${ delivery }"></c:set>
			      <td>${ delivery.recipient }<br>
			      ${ delivery.deliverPhone }<br>
			      ${ delivery.shippingAddress }<br>
			      ${ delivery.requests }</td>
			      <td><a href="/order/delivery/form?orderCode=${ orderCode }"><button type="button" class="btn btn-secondary btn-sm DeliUpdateBtn">배송지 수정</button></a></td>
			    </tr>
			    <tr>
			      <th scope="row" class="row-title">최종 결제금액</th>
			      <td>${ totalPaymentPrice } 원</td>
			      <td></td>
			    </tr>
			  </tbody>
			</table>
		</div>
		<div class="container sub-container" style="text-align:center">
			<a href="/"><button type="button" class="btn btn-dark btn-lg">홈으로 이동하기</button></a>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	
	<!-- Bootstrap core JavaScript -->
	<script src="<%=request.getContextPath() %>/resources/mainPage/vendor/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/resources/mainPage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>