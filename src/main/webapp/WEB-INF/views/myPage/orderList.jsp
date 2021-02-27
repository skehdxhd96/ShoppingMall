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
        		<h2 class="main-title">주문목록</h2>
        		<div class="table-container">
        		</div>
        		<%-- <c:forEach items="${ orderInfo }" var="orderInfo">
        			<table class="table table-sm order-list-table">
        				<tr>
        					<th id="orderCode">No.${ orderInfo.order_code }</th>
        					<th class=""><button type="button" class="btn btn-secondary btn-sm cancelBtn">주문 취소</button></th>
        				</tr>
        				<tr>
        					<th>${ orderInfo.order_date }</th>
        					<c:if test="${ orderInfo.delivery_status=='preparing' }">
        						<th>배달 준비중
        							<button type="button" class="btn btn-secondary btn-sm DeliUpdateBtn">배송지 변경</button>
        						</th>
        					</c:if>
        					<c:if test="${ orderInfo.delivery_status=='start' }">
        						<th>배송중</th>
        					</c:if>
        					<c:if test="${ orderInfo.delivery_status=='arrive' }">
        						<th>배송 완료</th>
        					</c:if>
        					<c:if test="${ orderInfo.delivery_status=='cancel' }">
        						<th>배송 취소</th>
        					</c:if>
        				</tr>
        				<c:forEach items="${ orderInfo.odProInfo }" var="productInfo">
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
        		</c:forEach> --%>
        		
        		<div class="paging-container">
        			<!-- <button type="button" class="btn btn-secondary btn-sm btn-prev">이전</button> -->
		    		<!-- <button type="button" class="btn btn-secondary btn-sm btn-next">다음</button> -->
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
  	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
  	<script src="<%=request.getContextPath() %>/resources/myPage/js/orderList.js"></script>
</body>
</html>