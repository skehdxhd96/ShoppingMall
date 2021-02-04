<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 오류페이지</title>
<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath() %>/resources/myPage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- CSS -->
<link href="<%=request.getContextPath() %>/resources/myPage/css/userDeleteError.css" rel="stylesheet">
</head>
<body>
	<div class="container header-container">
		<h3 class="header-title">회원 탈퇴 진행 과정에서 오류가 발생하였습니다.</h3>
		<a href="/"><button type="button" class="btn btn-primary">홈으로 이동</button></a>
		<a href="/login/userDelete"><button type="button" class="btn btn-light">다시 시도</button></a>
	</div>
</body>
</html>