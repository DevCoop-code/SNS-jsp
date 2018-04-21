<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.Calendar" %>
    <%
    String check = (String)request.getAttribute("Emailcheck");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Bacon Network - 로그인 또는 가입</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/MainEnt.css"/>
<!-- jQuery UI -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- jquery cdn -->
<script src="http://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<%if(check=="wrongpw"){ %>
<script>
alert('비밀번호가 틀렸습니다.');
</script>
<%} %>
<%if(check=="noeffect"){ %>
<script>
alert('유효하지 않은 이메일 입니다.');
</script>
<%} %>
<%if(check=="notexists"){ %>
<script>
alert('존재하지 않는 이메일 입니다.');
</script>
<%} %>
<script>
$(function()
{
	var dialog;

	dialog = $("#dialog-form").dialog({
		autoOpen: false,
		height: 400,
		width: 350,
		modal: true,
		close: function(){

		}
	});

	$( "#create-user" ).button().on( "click", function() {
      dialog.dialog( "open" );
    });
});
</script>
</head>
<body>
<!-- 로그인 다이얼로그 창 -->
<div id="dialog-form" title="로그인">
	<p>Bacon Network로 로그인</p>
	<form action="<%=request.getContextPath()%>/baconnetwork/signin.do" method="post">
		<fieldset>
			<label for="email">이메일</label>
			<input type="text" name="email" id="email" class="text ui-widget-content ui-corner-all">
			<label for="password">비밀번호</label>
			<input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all">
			<button type="submit" class="btn btn-lg btn-primary btn-block">로그인</button>
		</fieldset>
	</form>
</div>
<!--Fixed navbar-->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Bacon Network</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<div class="navbar-form navbar-right">
				<button id="create-user" class="btn btn-success">로그인</button>
				<div class="form-group">
					<a href="<%=request.getContextPath()%>/baconnetwork/EmailInput.do">계정찾기</a>
				</div>
			</div>
		</div>
	</div>
</nav>
<br><br>
<!--  비밀번호 찾기 위한 이메일 입력폼 -->
<div style="float: center; width:50%; margin-top:8%" id="loginForm" class="container">
	<form class="form-signin" action="<%=request.getContextPath()%>/baconnetwork/SearchPassword.do" method="post">
		<h2><b>비밀번호 찾기</b></h2>
		<br>	
		<h4>가입하신 이메일을 입력해주세요.</h4>	
		<label for="inputEmail" class="sr-only">이메일</label>
		<input type="email" id="inputEmail" class="form-control" placeholder="이메일" name="email" required>
		<br>
		<button class="btn btn-lg btn-primary btn-block" type="submit">다음</button>
	</form>
</div>
</body>
</html>