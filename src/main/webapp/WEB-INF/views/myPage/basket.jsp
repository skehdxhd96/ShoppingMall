<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

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
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/nav.jsp"></jsp:include>
	
	<!-- Page Content -->
  	<div class="container top-container">
    	<div class="row">
      		<jsp:include page="/WEB-INF/views/common/myPageNav.jsp"></jsp:include>
      		
      		<div class="col-lg-9 main-col-lg-9">
        		<h2 class="main-title">장바구니</h2>
        		<!-- 상품 -->
			    <div class = "BasketList">
			    	<!-- 장바구니리스트 -->
			    </div>
			    <!-- 상품end -->
			    <div class = "panel-footer"></div>
		    </div>
		    <!-- /.col-lg-9 -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
	
	<script>
	$(document).ready(function() {
		
		var customer_code = "${customerCode}";
		var BasketList = $(".BasketList");
		
		showList(1);
		
		function showList(page) {
			
			BasketService.getList({customer_code : customer_code, page : page || 1}, function(BasketCount, list) {
				
				if(page == -1) {
					pageNum = Math.ceil(replyCount/6.0);
					showList(pageNum);
					return;
				}
				
				var str = "";
				
				if(list == null || list.length == 0) {
					BasketList.html("");
					
					return;
				}
				
				str += "<span>상품이름&nbsp&nbsp&nbsp제조사&nbsp&nbsp&nbsp가격&nbsp&nbsp&nbsp수량</span>"
				
				for(var i=0, len = list.length || 0; i<len; i++) {
					str += "<div>";
					str += "<div><span><a href = '/ProductDetail/" + list[i].product_code + "'/>" + list[i].product_name + list[i].product_manufacturer + list[i].product_price + list[i].product_quantity +"</span><br></div>";
					str += "</div>";
				}
				BasketList.html(str);
				showReplyPage(BasketCount);
			});
		}
		
		var pageNum = 1;
    	var BasketPageFooter = $(".panel-footer");
    	
    	function showReplyPage(BasketCount) {
    		
    		var endNum = Math.ceil(pageNum / 6.0) * 6;
    		var startNum = endNum - 5;
    		
    		var prev = startNum != 1;
    		var next = false;
    		
    		if(endNum * 6 >= BasketCount) {
    			endNum = Math.ceil(BasketCount/6.0);
    		}
    		
    		if(endNum * 6 < BasketCount) {
    			next = true;
    		}
    		
    		var str = "<ul class = 'pagination pull-right'>";
    		
    		if(prev) {
    			
    			str += "<li class = 'page-item'><a class = 'page-link' href = '" + (startNum -1) + "'>Previous</a></li>";
    		}
    		
    		for(var i = startNum; i<=endNum; i++) {
    			
    			var active = pageNum == i? "active" : "";
    			
    			str += "<li class = 'page-item " + active + "'><a class = 'page-link' href = '" + i + "'>" + i + "</a></li>";
    		}
    		
    		if(next) {
    			
    			str += "<li class = 'page-item'><a class = 'page-link' href = '" + (endNum + 1) + "'>Next</a></li>";
    		}
    		
    		str += "</ul></div>";
    		
    		BasketPageFooter.html(str);
    	}
    	
    	BasketPageFooter.on("click", "li a", function(e) {
    		
    		e.preventDefault();
    		
    		var targetPageNum = $(this).attr("href");
    		
    		pageNum = targetPageNum;
    		
    		showList(pageNum);
    	});
	});
	</script>
  
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	<!-- Bootstrap core JavaScript -->
	
  	<script src="<%=request.getContextPath() %>/resources/myPage/vendor/jquery/jquery.min.js"></script>
  	<script src="<%=request.getContextPath() %>/resources/myPage/js/basket.js"></script>
  	<script src="<%=request.getContextPath() %>/resources/myPage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>