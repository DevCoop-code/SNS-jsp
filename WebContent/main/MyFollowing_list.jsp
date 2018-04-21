<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="expertisesns.model.PeopleInfoDto" %>
    <%@ page import="java.util.*" %>
    <%@ page import="expertisesns.data.DataInfo" %>
    <%
    String email = null;
    String name = null;
    int follower = 0;
    int following = 0;
    int boardnum = 0;
    
    int uncheckingalarm = 0;
    
    List<PeopleInfoDto> following_data_list = null;
    PeopleInfoDto peopleinfodto = null;
    
    if(session.getAttribute("id") != null)
    {
    	email = (String)session.getAttribute("id");
    	name = (String)session.getAttribute("name");
    	follower = (int)session.getAttribute("follower");
    	following = (int)session.getAttribute("following");
    	boardnum = (int)session.getAttribute("boardnum");
    	
    	following_data_list = (List)request.getAttribute("following_data");	
    	
    	uncheckingalarm = (int)request.getAttribute("uncheckingalarm");
    }
    boolean login = email==null ? false : true;
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bacon Network - 팔로잉보기</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ie10-viewport-bug-workaround.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dashboard.css"/>

<script src="http://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Facebook 로그아웃 -->
<script src="<%=request.getContextPath()%>/js/facebooklogout.js"></script>
<style>
.header
{
	border: 1px solid black;
	padding:10px;
	margin-top:20px;
}
.form-group
{
	border: 1px solid black;
	padding: 10px;
}
</style>
<%if(login){ %>
<script>
function CancelFollowingEvent(canceledFollowing)
{
	var cancel_following = $(canceledFollowing).parent().children('#person_seq').val();
	var my_email = '<%=email%>';
	
	var cancel_data = {"person_seq": cancel_following, "my_email":my_email};
	
	$.ajax({
		type: 'POST',
		url: '<%=DataInfo.domain_path%>'+'/CancelFollowAjax',
		data: cancel_data,
		success: function(data)
		{
			if(data == 'success')
			{
				alert('성공적으로 팔로잉 취소 완료되었습니다.');
			}
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
$(document).ready(function(){
	
});
</script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/baconnetwork/home.do">Bacon Network</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <div class="navbar-form navbar-right">
                <div class="dropdown">
                  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    내 계정
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                    <li><%=name %> 님</li>
                    <li role="separator" class="divider"></li>
                    <li><a href="<%=request.getContextPath()%>/baconnetwork/profile.do">프로필 보기</a></li>
                    <li><a href="<%=request.getContextPath()%>/baconnetwork/mycontent.do">글&활동</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="<%=request.getContextPath()%>/baconnetwork/logout.do" onclick="logoutmethod()">로그아웃</a></li>
                  </ul>
                </div>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<%=request.getContextPath()%>/baconnetwork/home.do">홈</a></li>
                <li><a href="<%=request.getContextPath()%>/baconnetwork/humanconnection.do">인맥</a></li>
                <li><a href="<%=request.getContextPath()%>/baconnetwork/alarming.do">알림<span class="badge" id="dbstatus"><%=uncheckingalarm %></span></a></li>
                <li><a href="<%=request.getContextPath()%>/baconnetwork/jobinfo.do">채용공고</a></li>
            </ul>
            <form class="navbar-form navbar-right" method = "POST" action="<%=request.getContextPath()%>/baconnetwork/searchpeople.do">
                <input type="text" class="form-control" name="people" placeholder="검색">
                <button type="submit" class="btn btn-success">검색</button>
            </form>
        </div>
    </div>
</nav>
<div class="container-fluid" style="margin-top: 30px;">
	<div class="row">
		<!-- 사이드 바 부분 -->
		<div class="col-sm-3" style="padding:30px; border: 1px solid black; margin:20px">
			<div class="nav nav-sidebar">
				<p align="center"><b>팔로워 : <%=follower %>명</b></p>
				<p align="center"><b>팔로잉 : <%=following %>명</b></p>
				<hr/>
				<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork/follower_list.do">팔로워 모두 보기</a></p>
				<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork/following_list.do">팔로잉 모두 보기</a></p>
			</div>
		</div>
		<!-- 본문 -->
		<div class="col-sm-6">
			<div class="header">
				<h4>내가 팔로잉 하는 사람들</h4>
			</div>
			<%
			Iterator<PeopleInfoDto> peopleinfo_iter = following_data_list.iterator();
			while(peopleinfo_iter.hasNext())
			{
				peopleinfodto = peopleinfo_iter.next();
			%>
			<div class="form-group" id="following_list_item">
				<img src="<%=peopleinfodto.getProfile_path() %>" width="70" height="70" align="left" vspace="0" hspace="5">
				<p>이름 : <%=peopleinfodto.getName() %></p>
				<p>경력 : <%=peopleinfodto.getCompany() %></p>
				<p>학력 : <%=peopleinfodto.getSchool() %></p>
				<input type="hidden" id="person_seq" value="<%=peopleinfodto.getSeq()%>">
				<button type="button" id="following_btn" class="btn btn-primary" onclick="CancelFollowingEvent(this);">팔로잉 취소</button>
			</div>
			<%} %>
		</div>
	</div>
</div>
<%}else{ %>
<h1 align="center">그러한 접근은 불가능 합니다.</h1>
<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork.main">로그인이 필요합니다.</a></p>
<%} %>
</body>
</html>