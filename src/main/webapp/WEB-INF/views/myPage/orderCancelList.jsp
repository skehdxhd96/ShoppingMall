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
        		<h2 class="main-title">주문취소목록</h2>
        		<c:forEach items="${ orderCancelLi }" var="orderCancelLi">
        			<table class="table table-sm order-list-table">
        				<tr>
        					<th colspan="2" id="orderCode">No.${ orderCancelLi.order_code }</th>
        				</tr>
        				<tr>
        					<th colspan="2">${ orderCancelLi.order_date }</th>
        				</tr>
        				<c:forEach items="${ orderCancelLi.odProInfo }" var="productInfo">
        					<tr>
        						<th rowspan="3"><a href="/ProductDetail/${ productInfo.product_code }"><img src="${ productInfo.thumbnail_url }" ></a></th>
        						<th><a href="/ProductDetail/${ productInfo.product_code }">${ productInfo.product_name }</a></th>
        					</tr>
        					<tr>
        						<th>${ productInfo.product_quantity }</th>
        					</tr>
        					<tr>
        						<th>결제금액(할인, 포인트 사용후)</th>
        					</tr>
        				</c:forEach>
        				<tr>
        					<th>총 결제금액</th>
        					<th>총 결제금액(할인, 포인트 사용후)</th>
        				</tr>
        			</table>
        		</c:forEach>
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
</body>
</html>