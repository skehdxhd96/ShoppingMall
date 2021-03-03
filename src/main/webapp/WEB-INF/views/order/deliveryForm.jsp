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
  	<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
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
			      <td>${ buyer.customerName }</td>
			    </tr>
			    <tr>
			      <th scope="row" class="row-title">구매자 이메일</th>
			      <td>${ buyer.customerEmail }</td>
			    </tr>
			    <tr>
			      <th scope="row" class="row-title">구매자 전화번호</th>
			      <td>${ buyer.customerPhone }</td>
			    </tr>
			  </tbody>
			</table>
		</div>
		<div class="container sub-container delivery-container">
			<h3 class="buyer-title">받는사람 정보</h3>
				<input name="orderCode" type="hidden" value="${ orderCode }">
				<table class="table">
				  <tbody>
				    <tr>
				      <th scope="row" class="row-title">받는사람 이름</th>
				      <td><input class="form-control" name="recipient" placeholder="${ buyer.customerName }" type="text" value="${ buyer.customerName }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">주소</th>
				      <td>
				      <input type="button" onClick="goPopup();" value="우편번호 찾기"/>
				      <input class="form-control" name="delveryZipcode" placeholder="${ buyer.zipcode }" type="text" value="${ buyer.zipcode }">
				      <input class="form-control" name="shippingAddress" placeholder="${ buyer.customerAddress }" type="text" value="${ buyer.customerAddress }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">받는사람 전화번호</th>
				      <td><input class="form-control" name="deliverPhone" placeholder="${ buyer.customerPhone }" type="text" value="${ buyer.customerPhone }"></td>
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
	<script src="<%=request.getContextPath() %>/resources/common/js/addressAPI.js"></script>
</body>
</html>