<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  	<meta name="description" content="">
  	<meta name="author" content="">
  	<title>회원정보 수정</title>
  	<!-- Bootstrap core CSS -->
  	<link href="<%=request.getContextPath() %>/resources/myPage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  	<!-- Custom styles for this template -->
  	<link href="<%=request.getContextPath() %>/resources/common/css/shop-homepage.css" rel="stylesheet">
  	<link href="<%=request.getContextPath() %>/resources/common/css/common.css" rel="stylesheet">
  	<link href="<%=request.getContextPath() %>/resources/myPage/css/updateProfile.css" rel="stylesheet">
<body>
	<jsp:include page="/WEB-INF/views/common/nav.jsp"></jsp:include>
	
	<!-- Page Content -->
  	<div class="container">
    	<div class="row">
      		<jsp:include page="/WEB-INF/views/common/myPageNav.jsp"></jsp:include>
      		
      		<div class="col-lg-9 main-col-lg-9">
        		<h2 class="main-title">회원정보 수정</h2>
        		<form action="/login/userModify/${ customerCode }" method="post" class="modifySubmit">
			    	<input type="hidden" name="_method" value="put" />
			    	<table class="table">
					  <tbody>
					  	<c:set var="profile" value="${ profile }" />
					  	<tr>
							<th scope="row">이메일</th>
						    <td><input name="customerEmail" class="form-control" placeholder="${ profile.customerEmail }" type="email" value="${ profile.customerEmail }"></td>
						</tr>
						<tr>
						    <th scope="row">이름</th>
						    <td><input name="customerName" class="form-control" placeholder="${ profile.customerName }" type="text" value="${ profile.customerName }"></td>
						</tr>
						<tr>
						    <th scope="row">전화번호 (-기호 포함)</th>
						    <td><input name="customerPhone" class="form-control" placeholder="${ profile.customerPhone }" type="text" value="${ profile.customerPhone }"></td>
						</tr>
					  	<% if(session.getAttribute("customerType").equals(1)){ %>
					  		<tr>
					  			<th scope="row" rowspan="2">주소</th>
					  			<td><input type="button" onClick="goPopup();" value="우편번호 찾기"/>
					  			<input name="zipcode" class="form-control" placeholder="${ profile.zipcode }" type="text" value="${ profile.zipcode }"></td>
					  			
					  		</tr>
					  		<tr>
					  			<td><input name="customerAddress" class="form-control" placeholder="${ profile.customerAddress }" type="text" value="${ profile.customerAddress }"></td>
					  		</tr>
			 			<% } else { %>
						    <tr>
						      <th scope="row">회사명</th>
						      <td colspan="2"><input name="companyName" class="form-control" placeholder="${ profile.companyName }" type="text" value="${ profile.companyName }"></td>
						    </tr>
						    <tr>
						      <th scope="row">회사 전화번호</th>
						      <td colspan="2"><input name="companyPhone" class="form-control" placeholder="${ profile.companyPhone }" type="text" value="${ profile.companyPhone }"></td>
						    </tr>
						    <tr>
					  			<th scope="row" rowspan="2">주소</th>
					  			<td><input type="button" onClick="goPopup();" value="우편번호 찾기"/>
					  			<input name="zipcode" class="form-control" placeholder="${ profile.zipcode }" type="text" value="${ profile.zipcode }"></td>
					  			
					  		</tr>
					  		<tr>
					  			<td><input name="customerAddress" class="form-control" placeholder="${ profile.customerAddress }" type="text" value="${ profile.customerAddress }"></td>
					  		</tr>
			 			<% } %>
					  </tbody>
					</table>
					<div class="userModify-submit">
						<input type="submit" class="btn btn-dark" value="수정하기"></input>
					</div>
					<div class="userDelete-button">
						<a href="/login/userDelete">회원탈퇴</a>
					</div>
		    	</form>
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
  	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  	<script src="<%=request.getContextPath() %>/resources/common/js/common_submit.js"></script>
  	<script src="<%=request.getContextPath() %>/resources/common/js/addressAPI.js"></script>
</body>
</html>