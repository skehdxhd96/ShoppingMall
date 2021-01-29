<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored = "false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

	<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous"></script>
  
  <title>Heroic Features - Start Bootstrap Template</title>

  <!-- Bootstrap core CSS -->
  <link href="<%=request.getContextPath() %>/resources/mainPage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath() %>/resources/mainPage/css/heroic-features.css" rel="stylesheet">

</head>

<body>

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="/">ShoppingMall</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="#">Home
              <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">About</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Services</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Contact</a>
          </li>
          <% if(session.getAttribute("customerCode")!=null){ %>
          	<li class="nav-item">
            	<a class="nav-link" href="/logout">logout</a>
          	</li>
 		  <% } else { %>
 		  	<li class="nav-item">
            	<a class="nav-link" href="/login">login</a>
          	</li>
 		  <% } %>
        </ul>
      </div>
    </div>
  </nav>
  
	<h3>상품등록</h3>
	
	<form role="form" method="post" autocomplete="off" enctype = "multipart/form-data">
	
	<label>카테고리</label>
	 <select class="category" name = "category_code">
	</select>
	
	<div class="inputArea">
	 <label for="name">상품명</label>
	 <input type="text" id = "product_name" name="product_name" />
	</div>
	
	<div class="inputArea">
	 <label for="manufacturer">제조사</label>
	 <input type="text" id = "product_manufacturer" name="product_manufacturer" />
	</div>
	
	<div class="inputArea">
	 <label for="price">상품가격</label>
	 <input type="text" id = "product_price" name="product_price" />
	</div>
	
	<div class="inputArea">
	 <label for="stock">상품수량</label>
	 <input type="text" id = "product_stock" name="product_stock" />
	</div>
	
	<div class="inputArea">
	 <label for="point">적립포인트</label>
	 <input type="text" id = "product_point" name="product_point" />
	</div>
	
	<div class="inputArea">
	 <label for="point">판매사</label>
	 <input type="text" id = "product_point" name="product_seller" value = "${CompanyName }" readonly = "true"/>
	</div>
	
	<div class="inputArea">
	 <label for="point">평점(SQL Null 방지용 기입)</label>
	 <input type="text" id = "product_point" name="product_score" />
	</div>
	
	<div class = "inputArea">
		<label for = "gdsImg">이미지</label>
		<input type = "file" id = "product_image" name = "file" />
		<div class = "select_img"><img src = "" /></div>
	</div>
	
	<script>
	<!-- 이미지 띄우기 -->
	<!-- 배포시 경로 수정해야함 -->
	$("#product_image").change(function() {
		if(this.files && this.files[0]) { //만약 파일이 하나 or 복수일경우 첫번째가 있을경우
			var reader = new FileReader;
			reader.onload = function(data) {
				$(".select_img img").attr("src", data.target.result).width(200);
			}
			reader.readAsDataURL(this.files[0]);
		}
	});
	</script>
	
	<script>
	//컨트롤러에서 데이터 받기
	var jsonData = JSON.parse('${category}');
	console.log(jsonData);
	
	var cate1Arr = new Array();
	var cate1Obj = new Object();
	
	//삽입할 데이터 준비
	for(var i = 0; i < jsonData.length; i++) {
	 
	  cate1Obj = new Object();  //초기화
	  cate1Obj.category_code = jsonData[i].category_code;
	  cate1Obj.category_name = jsonData[i].category_name;
	  cate1Arr.push(cate1Obj);
	}
	
	// 1차 분류 셀렉트 박스에 데이터 삽입
	var cate1Select = $("select.category");
	
	for(var i = 0; i < cate1Arr.length; i++) {
	 cate1Select.append("<option value='" + cate1Arr[i].category_code + "'>"
	      + cate1Arr[i].category_name + "</option>"); 
	}
	
	var regExp = /[^0-9]/gi;

	$("#product_price").keyup(function(){ numCheck($(this)); });
	$("#product_stock").keyup(function(){ numCheck($(this)); });
	$("#product_point").keyup(function(){ numCheck($(this)); });

	function numCheck(selector) {
	 var tempVal = selector.val();
	 selector.val(tempVal.replace(regExp, ""));
	}
	
	</script>
	
	<%= request.getRealPath("/") %>
	
	<div class="inputArea">
	 <button type="submit" id="register_Btn" class="btn btn-primary">등록</button>
	</div>

  <!-- Footer -->
  <footer class="py-5 bg-dark">
    <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Your Website 2020</p>
    </div>
    <!-- /.container -->
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="<%=request.getContextPath() %>/resources/mainPage/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath() %>/resources/mainPage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
