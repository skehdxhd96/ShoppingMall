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
  <title>Shop Item - Start Bootstrap Template</title>

  <!-- Bootstrap core CSS -->
  <link href="<%=request.getContextPath() %>/resources/ProductDetail/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath() %>/resources/ProductDetail/css/shop-item.css" rel="stylesheet">

</head>

<body>

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="#">Start Bootstrap</a>
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
        </ul>
      </div>
    </div>
  </nav>

  <!-- Page Content -->
  <div class="container">

    <div class="row">

      <div class="col-lg-3">
        <h1 class="my-4">Shop Name</h1>
        <div class="list-group">
          <a href="#" class="list-group-item active">Category 1</a>
          <a href="#" class="list-group-item">Category 2</a>
          <a href="#" class="list-group-item">Category 3</a>
        </div>
      </div>
      <!-- /.col-lg-3 -->

      <div class="col-lg-9">

		<form role="form" method="post" autocomplete="off" enctype = "multipart/form-data">
		
		<input type="hidden" name="product_code" value="${ProductById.product_code}" />
		
        <div class="card mt-4">
           <c:if test="${ProductById.thumbnail_url != 'none.png'}">
              <img class="card-img-top" src="${ProductById.thumbnail_url}" alt=""></c:if>
           <c:if test="${ProductById.thumbnail_url == 'none.png'}">
              <img class="card-img-top" src="https://www.namdokorea.com/site/jeonnam/tour/images/noimage.gif" alt="https://www.namdokorea.com/site/jeonnam/tour/images/noimage.gif"></c:if>
          <div class="card-body">
            <h3 class="card-title">${ProductById.product_name }</h3>
            <h4>${ProductById.product_price }원</h4>
            <p class="card-text">제조사 : ${ProductById.product_manufacturer }</p>
            <p class="card-text">판매자 : ${ProductById.product_seller }</p>
            <p class="card-text">포인트 : ${ProductById.product_point }</p>
            <p class="card-text">재고 : ${ProductById.product_stock }</p>
            <p class="card-text">카테고리 : ${ProductById.category_name }</p>
            <span class="text-warning">&#9733; &#9733; &#9733; &#9733; &#9734;</span> <!-- 별점 이미지는 나중에 -->
            ${ProductById.product_score }
            <div>
	            <button type="button" id="modify_Btn" class="btn btn-warning">수정</button>
				<button type="button" id="delete_Btn" class="btn btn-danger">삭제</button>
            </div>
          </div>
        </div>
        <!-- /.card -->
        <!-- 상품end -->
        
        <script>
	        var formObj = $("form[role='form']");
	        
	        $("#modify_Btn").click(function(){
	         formObj.attr("action", "/ProductModify/${product_code}");
	         formObj.attr("method", "get")
	         formObj.submit();
	        });
	        
	        $("#delete_Btn").click(function(){    
	      	var con = confirm("정말로 삭제하시겠습니까?");
	      	
	      	if(con) {  
	         	formObj.attr("action", "/Delete");
	         	formObj.submit();}
	        });
        </script>

		<!-- 댓글/대댓글 -->
        <div class="card card-outline-secondary my-4">
          <div class="card-header">
            Product Reviews
          </div>
          <div class="card-body">
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Omnis et enim aperiam inventore, similique necessitatibus neque non! Doloribus, modi sapiente laboriosam aperiam fugiat laborum. Sequi mollitia, necessitatibus quae sint natus.</p>
            <small class="text-muted">Posted by Anonymous on 3/1/17</small>
            <hr>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Omnis et enim aperiam inventore, similique necessitatibus neque non! Doloribus, modi sapiente laboriosam aperiam fugiat laborum. Sequi mollitia, necessitatibus quae sint natus.</p>
            <small class="text-muted">Posted by Anonymous on 3/1/17</small>
            <hr>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Omnis et enim aperiam inventore, similique necessitatibus neque non! Doloribus, modi sapiente laboriosam aperiam fugiat laborum. Sequi mollitia, necessitatibus quae sint natus.</p>
            <small class="text-muted">Posted by Anonymous on 3/1/17</small>
            <hr>
            <a href="#" class="btn btn-success">Leave a Review</a>
          </div>
        </div>
        <!-- /.card -->

      </div>
      <!-- /.col-lg-9 -->


    </div>

  </div>
  <!-- /.container -->

  <!-- Footer -->
  <footer class="py-5 bg-dark">
    <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; Your Website 2020</p>
    </div>
    <!-- /.container -->
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="<%=request.getContextPath() %>/resources/ProductDetail/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath() %>/resources/ProductDetail/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>