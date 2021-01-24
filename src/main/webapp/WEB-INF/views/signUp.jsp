<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	
	<!------ Include the above in your HEAD tag ---------->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
	<link href="<%=request.getContextPath() %>/resources/signUp/css/signUp.css" rel="stylesheet">
	<title>SignUp</title>
</head>
<body>
	<div class="container">
		<p class="text-center">More bootstrap 4 components on <a href="http://bootstrap-ecommerce.com/"> Bootstrap-ecommerce.com</a></p>
		<hr>
		<div class="card bg-light">
			<article class="card-body mx-auto" style="max-width: 400px;">
			    <h4 class="card-title mt-3 text-center">Create Account</h4>
			    <br>   
				<form class="signupForm" action="/signUp" method="POST">
					<c:set var="customer" value="${ newCustomer }" />
					<div class="form-desc">고객유형</div>
					<div class="form-group input-group">
					    <div class="input-group-prepend">
							<span class="input-group-text"> <i class="fas fa-filter"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
						<select class="form-control" name="customerCheck">
							<option value="">고객 유형을 선택하세요</option>
							<option value="1">구매자</option>
							<option value="2">판매자</option>
						</select>
					</div>
					<!-- div:form-group input-group -->
					<div class="form-desc">이름</div>
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-user"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
					    <input name="customerName" class="form-control" placeholder="${ customer.customerName }" type="text" value="${ customer.customerName }">
					</div>
					<!-- div:form-group input-group -->
					<div class="form-desc">이메일</div>
					<div class="form-group input-group">
					    <div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
					    <input name="customerEmail" class="form-control" placeholder="${ customer.customerEmail }" type="email" value="${ customer.customerEmail }">
					</div>
					<!-- div:form-group input-group -->
					<div class="form-desc">전화번호</div>
					<div class="form-group input-group">
					    <div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-phone"></i> </span>
						</div>
					    <input name="customerPhone" class="form-control" placeholder="${ customer.customerPhone }" type="text" value="${ customer.customerPhone }">
					</div>
					<div class="addinput">
					
					</div>
				    <!-- div:form-group input-group -->
				    <div class="form-group">
				        <input type="submit" class="btn btn-primary btn-block" value="계정 만들기"></input>
				    </div>
				    <!-- div:form-group -->      
		    		<p class="text-center"> 계정이 이미 존재하나요?  <a href="/login">Log In</a> </p>                                                                 
				</form>
			</article>
			<!-- article:card-body mx-auto -->
		</div> 
		<!-- div:card bg-light -->
	</div> 
	<!-- div:container -->
	<br><br>
	<article class="bg-secondary mb-3">  
		<div class="card-body text-center">
		    <h3 class="text-white mt-3">Bootstrap 4 UI KIT</h3>
			<p class="h5 text-white">Components and templates<br>for Ecommerce, marketplace, booking websites and product landing pages</p>
			<br>
			<p><a class="btn btn-warning" target="_blank" href="http://bootstrap-ecommerce.com/"> Bootstrap-ecommerce.com <i class="fa fa-window-restore "></i></a></p>
		</div>
		<!-- div:card-body text-center -->
		<br><br>
	</article>
	<!-- article:bg-secondary mb-3 -->
	
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath() %>/resources/signUp/js/signUp.js"></script>
</body>
</html>