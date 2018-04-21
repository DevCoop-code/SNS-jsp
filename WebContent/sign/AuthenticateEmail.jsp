<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이메일 인증 페이지</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/AuthenticateEmail.css"/>
</head>
<body>
<!--Fixed navbar-->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Bacon Network</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
			<li><a href="<%=request.getContextPath()%>/SignIn.jsp">계정찾기</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			<li><a href="<%=request.getContextPath()%>/SignIn.jsp">회원가입</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			<li><a href="<%=request.getContextPath()%>/SignIn.jsp">로그인</a></li>
			</ul>
		</div>
	</div>
</nav>
<br/><br/>
<div class="site-wrapper">
	<div class="site-wrapper-inner">
		<div class="cover-container">
			<div class="inner cover">
				<h3 class="cover-heading">이메일 인증이 완료되지 않은 계정입니다.</h3>
				<p class="lead">아래 버튼을 눌러 이메일 인증을 완료해주시기 바랍니다.</p>
				<p class="lead"><a href="#" class="btn btn-lg btn-default">이메일 인증하기</a></p>
			</div>
		</div>
	</div>
</div>
</body>
</html>