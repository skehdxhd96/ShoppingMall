<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	
	<!------ Include the above in your HEAD tag ---------->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
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
				<form>
					<div class="form-group input-group">
						<div class="input-group-prepend">
						    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
						 </div>
						 <!-- div:input-group-prepend -->
				        <input name="" class="form-control" placeholder="Full name" type="text">
				    </div>
				    <!-- div:form-group input-group -->
				    <div class="form-group input-group">
				    	<div class="input-group-prepend">
						    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
				        <input name="" class="form-control" placeholder="Email address" type="email">
				    </div>
				    <!-- div:form-group input-group -->
				    <div class="form-group input-group">
				    	<div class="input-group-prepend">
						    <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
						<select class="custom-select" style="max-width: 120px;">
						    <option selected="">+971</option>
						    <option value="1">+972</option>
						    <option value="2">+198</option>
						    <option value="3">+701</option>
						</select>
				    	<input name="" class="form-control" placeholder="Phone number" type="text">
				    </div>
				    <!-- div:form-group input-group -->
				    <div class="form-group input-group">
				    	<div class="input-group-prepend">
						    <span class="input-group-text"> <i class="fa fa-building"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
						<select class="form-control">
							<option selected=""> Select job type</option>
							<option>Designer</option>
							<option>Manager</option>
							<option>Accaunting</option>
						</select>
					</div>
					<!-- div:form-group input-group -->
				    <div class="form-group input-group">
				    	<div class="input-group-prepend">
						    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
				        <input class="form-control" placeholder="Create password" type="password">
				    </div> 
				    <!-- div:form-group input-group -->
				    <div class="form-group input-group">
				    	<div class="input-group-prepend">
						    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
						</div>
						<!-- div:input-group-prepend -->
				        <input class="form-control" placeholder="Repeat password" type="password">
				    </div>
				    <!-- div:form-group input-group -->                
				    <div class="form-group">
				        <button type="submit" class="btn btn-primary btn-block"> Create Account  </button>
				    </div>
				    <!-- div:form-group -->      
		    		<p class="text-center">Have an account? <a href="/login">Log In</a> </p>                                                                 
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
</body>
</html>