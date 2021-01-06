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
  
  <title>Shop Homepage - Start Bootstrap Template</title>

  <!-- Bootstrap core CSS -->
  <link href="<%=request.getContextPath() %>/resources/ProductList/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  
  <!-- Custom styles for this template -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/ProductList/css/shop-homepage.css">

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
  <div id="container" class="container">

    <div class="row">

      <div class="col-lg-3">

        <h1 class="my-4">Shop Name</h1>
        <div id="categories" class="list-group">
	        <c:forEach items="${ categories }" var="category">
	          	<a href="/ProductList/${ category.category_code }" class="list-group-item">${ category.category_name }</a>
	        </c:forEach>
        </div>

      </div>
      <!-- /.col-lg-3 -->

      <div class="col-lg-9">

        <!-- <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
          <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
          </ol>
          <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
              <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
            </div>
            <div class="carousel-item">
              <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
            </div>
            <div class="carousel-item">
              <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
            </div>
          </div>
          <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div> -->
        <div id="productRow" class="row">
		<c:forEach items = "${products}" var = "product">
          <div class="col-lg-4 col-md-6 mb-4">
            <div class="card h-100">
              <a href="#"><img class="card-img-top" src="http://placehold.it/700x400" alt=""></a>
              <div class="card-body">
                <h4 class="card-title">
                  <a href="#">${product.product_name}</a>
                </h4>
                <h5>${product.product_price }</h5>
                <p class="card-text">${product.product_manufacturer }</p>
              </div>
              <div class="card-footer">
              	<c:set var="score" value="${ product.product_score }" />
              	<c:choose>
              		<c:when test="${ score==5 }">
              			<small class="text-muted">⭐️ ⭐️ ⭐️ ⭐️ ⭐️</small>
              		</c:when>
              		<c:when test="${ score>=4 }">
              			<small class="text-muted">⭐️ ⭐️ ⭐️ ⭐️</small>
              		</c:when>
              		<c:when test="${ score>=3 }">
              			<small class="text-muted">⭐️ ⭐️ ⭐️</small>
              		</c:when>
              		<c:when test="${ score>=2 }">
              			<small class="text-muted">⭐️ ⭐️</small>
              		</c:when>
              		<c:when test="${ score>=1 }">
              			<small class="text-muted">⭐️</small>
              		</c:when>
              		<c:otherwise>
              			<small class="text-muted">&#9734; &#9734; &#9734; &#9734; &#9734;</small>
              		</c:otherwise>
              	</c:choose>
              </div>
            </div>
          </div>
		</c:forEach>
        </div>
        <!-- /.row -->
		<div class="row">
			<div class="col-md-12 text-center">
				<div class="btn-group me-2" role="group" aria-label="First group">
					<c:forEach var="page" begin="1" end="${ pageNum }" step="1">
						<button type="button" id="pageButton" class="btn btn-dark">${ page }</button>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- /.row -->
      </div>
      <!-- /.col-lg-9 -->

    </div>
    <!-- /.row -->

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
  <script src="<%=request.getContextPath() %>/resources/ProductList/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath() %>/resources/ProductList/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath() %>/resources/ProductList/js/ProductList.js"></script>
</body>

</html>
