<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.Calendar" %>
    <%@ page import="java.net.URLDecoder" %>
    <%@ page import="java.util.*" %>
    <%
    String check = (String)request.getAttribute("check");
    
    Map<String, String> recentLogin = null;
    
    Cookie[] cookies = request.getCookies();
	if(cookies != null && cookies.length > 0)
	{
		recentLogin = new HashMap<String, String>();
		
		for(int i=0; i<cookies.length; i++)
		{
			String name = URLDecoder.decode(cookies[i].getName(),"UTF-8");
			if(name.contains("@"))
			{
				recentLogin.put(URLDecoder.decode(cookies[i].getName(),"UTF-8"), URLDecoder.decode(cookies[i].getValue(), "UTF-8"));
			}
		}
	}else
	{
		System.out.println("쿠키가 존재하지 않음.");
	}
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
<%if(check=="exists"){ %>
<script>
alert('이미 존재하는 회원입니다.');
</script>
<%} %>
<%if(check=="sendemail"){ %>
<script>
alert('인증 이메일을 보냈습니다.');
</script>
<%} %>
<%if(check=="wrongpw"){ %>
<script>
alert('비밀번호가 틀렸습니다.');
</script>
<%} %>
<%if(check == "emailAuthenticationComplete"){ %>
<script>
alert('이메일 인증이 완료되었습니다.');
</script>
<%} %>
<%if(check == "sendemail2"){ %>
<script>
alert('임시 비밀번호가 발급되었습니다.');
</script>
<%} %>
<script>
$(function()
{
	var dialog, recent_dialog;

	dialog = $("#dialog-form").dialog({
		autoOpen: false,
		height: 400,
		width: 350,
		modal: true,
		close: function(){

		}
	});
	
	recent_dialog = $("#recent_dialog_form").dialog({
		autoOpen: false,
		height: 400,
		width: 350,
		modal: true,
		close: function(){

		}
	});

	//로그인 버튼 클릭
	$( "#create-user" ).button().on( "click", function() {
      dialog.dialog( "open" );
    });
	
	//최근 로그인 이벤트
	//동적으로 생성된 객체는 아래와 같이 작업을 해야 전체가 다 선택되어짐.
	$(document).on('mouseover','.form-group2',function(){
		$(this).css('background-color','#BDBDBD');
	});
	$(document).on('mouseleave','.form-group2',function(){
		$(this).css('background-color','white');
	});
	$(document).on('click','.form-group2',function(){
		var recent_id = $(this).children('#person_id').val();
		//alert(alarm_seq);
		$('#recent_dialog_form #email').val(recent_id);
		recent_dialog.dialog( "open" );
	});
});
</script>

<!-- 페이스북 로그인 -->
<script>  
function statusChangeCallback(response)
{
	console.log('statusChangeCallback');
	console.log(response);
	//response객체는 현재 로그인 상태를 나타내는 정보를 보여줌
	//앱에서 현재의 로그인 상태에 따라 동작하면 됨
	//FB.getLoginStatus()의 레퍼런스에서 더 자세한 내용이 참조 가능
	if(response.status == 'connected')
	{
		//페이스북을 통해 로그인이 되어있다 
		console.log('response.status is connected, 페이스북을 통해 로그인이 되어있다');
		testAPI();
	}else if(response.status == 'not_authorized')
	{
		//페이스북에는 로그인 했으나, 앱에는 로그인 되어 있지 않다.
		console.log('response.status is not_authorized, 페이스북에는 로그인 했으나, 앱에는 로그인 되어 있지 않다.');
		$('#status').html('Please Log into this app.');
	}else
	{
		//페이스북에 로그인 되어 있지 않다. 따라서 앱에 로그인이 되어있는지 여부가 불확실하다. 
		console.log('response.status is ..., 페이스북에 로그인 되어 있지 않다. 따라서 앱에 로그인이 되어있는지 여부가 불확실하다.');
	}
}

//이 함수는 누군가가 로그인 버튼에 대한 처리가 끝났을 때 호출됨 
//onlogin 핸들러를 아래와 같이 첨부하면 됨 
function checkLoginState()
{
	FB.getLoginStatus(function(response)
	{
		statusChangeCallback(response);
	})	
}

//FB.AppEvents.logPageView();   
//자바스크립트 sdk를 초기화했으니 FB.getLoginStatus()를 호출
//이 함수는 이 페이지의 사용자가 현재 로그인 되어있는 상태 3가지 중 하나를 콜백에 리턴함
//그 3가지 상태를 아래와 같음
//1. 앱과 페이스북에 로그인('connected'), 2. 페이스북에 로그인되어있으나, 앱에는 로그인되어있지 않음('not_authorized'), 3.페이스북에 로그인되어 있지 않아서 앱에 로그인이 되어있는지 불확실
window.fbAsyncInit = function() {
  FB.init({
    appId      : '',
    cookie     : true,
    xfbml      : true,
    version    : 'v2.8'
  });
  FB.getLoginStatus(function(response)
  {
	  statusChangeCallback(response);
  });
};

(function(d, s, id){
	   var js, fjs = d.getElementsByTagName(s)[0];
	   if (d.getElementById(id)) {return;}
	   js = d.createElement(s); js.id = id;
	   js.src = "//connect.facebook.net/en_US/sdk.js";
	   fjs.parentNode.insertBefore(js, fjs);
	 }(document, 'script', 'facebook-jssdk'));
	 
//로그인이 성공한 다음에는 간단한 그래프 API를 호출
function testAPI()
 {
	 console.log('Welcome! Fetching your information...');
	 FB.api('/me', function(response){
		 console.log(response);
		 console.log('Successful login for : ' + response.name);
		 
		 location.replace("/ExpertiseSNS/baconnetwork/facebooklogin.do?name="+response.name+"&id="+response.id);
		 
		 //'http://localhost:8080/ExpertiseSNS/baconnetwork/facebooklogin.do'
		//$('#facebook_login_btn')
	 });
 }
</script>

<style>
.form-group2
{
	border: 1px solid black;
	padding: 10px;
	margin: 5px;
}
</style>
</head>
<body>
<!-- 로그인 다이얼로그 창 -->
<div id="dialog-form" title="로그인">
	<form action="<%=request.getContextPath()%>/baconnetwork/signin.do" method="post">
		<fieldset>
			<label for="email">이메일</label>
			<input type="text" name="email" id="email" class="text ui-widget-content ui-corner-all">
			<label for="password">비밀번호</label>
			<input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all">
			<button type="submit" class="btn btn-lg btn-primary btn-block">이메일로 로그인</button>
		</fieldset>
	</form>
	<hr>
	<!-- 페이스북으로 로그인 -->
	<form class="form-group login-oauth text-center">
		<fb:login-button scope="public_profile,email" onlogin="checkLoginState()" data-size="large" data-button-type="login_with"></fb:login-button>
	</form>
	<div id="status"></div>
</div>

<!-- 최근 로그인 다이얼로그 창 -->
<div id="recent_dialog_form" title="로그인">
	<form action="<%=request.getContextPath()%>/baconnetwork/signin.do" method="post">
		<fieldset>
			<label for="email">이메일</label>
			<input type="text" name="email" id="email" class="text ui-widget-content ui-corner-all">
			<label for="password">비밀번호</label>
			<input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all">
			<button type="submit" class="btn btn-lg btn-primary btn-block">이메일로 로그인</button>
		</fieldset>
	</form>
	<hr>
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
					<a href="<%=request.getContextPath()%>/baconnetwork/EmailInput.do">비밀번호 찾기</a>
				</div>
			</div>
		</div>
	</div>
</nav>
<br><br>
<div style="float: left; width:40%;" class="learningintro">
	<h2><b>최근 로그인 기록</b></h2>
	<h4>로그인하려면 클릭하세요</h4>
	<%
	if(recentLogin != null)
	{
		
	Iterator<String> recent_iter = recentLogin.keySet().iterator();
	while(recent_iter.hasNext())
	{
		String id = recent_iter.next();
		String name = recentLogin.get(id);
		
		System.out.println("ID : " + id);
		System.out.println("NAME : " + name);
	%>
	<div class="form-group2">
		<!-- <img src="" width="70" height="70" align="left" vspace="0" hspace="5"> -->
		<p><%=name %> 님</p>
		<input type="hidden" id="person_id" value="<%=id%>">	
	</div>
	<% 
	}
	}
	%>
</div>
<div style="float: left; width:50%;" id="loginForm" class="container">
	<form class="form-signin" action="<%=request.getContextPath()%>/baconnetwork/login.do" method="post">
		<h2><b>새 계정 만들기</b></h2>
		<div style="float: left; width:45%; padding-right:10px">
			<label for="inputUserName" class="sr-only">성</label>
			<input type="text" id="inputUserName" class="form-control" placeholder="성" name="familyname" required autofocus>
		</div>
		<div style="float: left; width:45%">
			<label for="inputUserName" class="sr-only">이름(성은 제외)</label>
			<input type="text" id="inputUserName" class="form-control" placeholder="이름(성은 제외)" name="username" required autofocus>
		</div>
		<br><br><br>
		<label for="inputEmail" class="sr-only">이메일</label>
		<input type="email" id="inputEmail" class="form-control" placeholder="이메일" name="email" required>
		<br>
		<label for="inputPassword" class="sr-only">새 비밀번호</label>
		<input type="password" id="inputPassword" class="form-control" placeholder="새 비밀번호" name="password" required>
		<p class="form-signin-heading">생일</p>
		<select name="year">
		<%
			Calendar cal = Calendar.getInstance();
			int year = cal.get(cal.YEAR);
		%>
		<%for(int i = year;i>year-100;i--){ %>
		<option value="<%=i%>"><%=i%></option>
		<%} %>
		</select>
		<select name="month">
			<option value="january">1월</option><option value="february">2월</option><option value="march">3월</option><option value="april">4월</option>
			<option value="may">5월</option><option value="june">6월</option><option value="july">7월</option><option value="august">8월</option>
			<option value="september">9월</option><option value="october">10월</option><option value="november">11월</option><option value="december">12월</option>
		</select>
		<select name="days">
			<%for(int i=1;i<=31;i++){ %>
			<option value="<%=i%>"><%=i%></option>
			<%} %>
		</select>
		<br><br>
		<div style="float: left; width:20%; padding-right:10px">
			<input type="radio" name="sex" value="1">남성
		</div>
		<div style="float: left; width:20%">
			<input type="radio" name="sex" value="0">여성
		</div>
		<br><br>
		<p>계정만들기 버튼을 클릭하면, Bacon Network의 약관에 동의하며 쿠키 사용을
		포함한 Bacon Network <a href="#">데이터 정책</a>을 읽었음을 인정하게 됩니다.</p>
		<button class="btn btn-lg btn-primary btn-block" type="submit">계정 만들기</button>
	</form>
</div>
</body>
</html>