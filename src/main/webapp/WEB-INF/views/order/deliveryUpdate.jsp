<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">
  	<title>배송지변경</title>
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
		<div class="container sub-container delivery-container">
			<h3 class="buyer-title">변경 전 배송지</h3>
				<table class="table">
				  <tbody>
				    <tr>
				      <th scope="row" class="row-title">받는사람 이름</th>
				      <td>${ delivery.recipient }</td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">주소</th>
				      <td>${ delivery.shippingAddress }</td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">받는사람 전화번호</th>
				      <td>${ delivery.deliverPhone }</td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">요청사항</th>
				      <td>${ delivery.requests }</td>
				    </tr>
				  </tbody>
				</table>
		</div>
		<div class="container sub-container delivery-container">
			<h3 class="buyer-title">배송지 변경하기</h3>
				<input name="orderCode" type="hidden" value="${ delivery.orderCode }">
				<input type="hidden" name="_method" value="put" />
				<table class="table">
				  <tbody>
				    <tr>
				      <th scope="row" class="row-title">받는사람 이름</th>
				      <td><input class="form-control" name="recipient" placeholder="${ delivery.recipient }" type="text" value="${ delivery.recipient }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">주소</th>
				      <td>
				      	<input type="button" onClick="goPopup();" value="우편번호 찾기"/>
					      <input class="form-control" name="delveryZipcode" placeholder="${ delivery.deliveryZipcode }" type="text" value="${ delivery.deliveryZipcode }">
				      	  <input class="form-control" name="shippingAddress" placeholder="${ delivery.shippingAddress }" type="text" value="${ delivery.shippingAddress }">
				      </td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">받는사람 전화번호</th>
				      <td><input class="form-control" name="deliverPhone" placeholder="${ delivery.deliverPhone }" type="text" value="${ delivery.deliverPhone }"></td>
				    </tr>
				    <tr>
				      <th scope="row" class="row-title">요청사항</th>
				      <td><input class="form-control" name="requests" type="text" placeholder="${ delivery.requests }" value="${ delivery.requests }"></td>
				    </tr>
				  </tbody>
				</table>
				<div class="submit-container">
					<input type="button" class="btn btn-dark submit-button" value="변경하기"></input>
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