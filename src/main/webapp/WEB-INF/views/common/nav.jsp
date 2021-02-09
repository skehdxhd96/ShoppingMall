<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	            		<a class="nav-link" href="/">Home<span class="sr-only">(current)</span></a>
	          		</li>
	          		<li class="nav-item">
	            		<a class="nav-link" href="/ProductList/1">Category</a>
	          		</li>
	          		<% if(session.getAttribute("customerCode")!=null){ %>
			          	<li class="nav-item">
			            	<a class="nav-link" href="/logout">logout</a>
			          	</li>
			          	<li class="nav-item">
			            	<a class="nav-link" href="/myPage/order/list">마이페이지</a>
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
