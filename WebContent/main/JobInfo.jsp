<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %> 
<%@ page import="expertisesns.data.DataInfo" %>
 <%
 String email = null;
 String name = null;
 int follower = 0;
 int following = 0;
 int boardnum = 0;
 int uncheckingalarm = 0;
 
 if(session.getAttribute("id") != null)
 {
	   	email = (String)session.getAttribute("id");
	   	name = (String)session.getAttribute("name");
	   	follower = (int)session.getAttribute("follower");
	   	following = (int)session.getAttribute("following");
	   	boardnum = (int)session.getAttribute("boardnum"); 
	   	
	 	 //알림
	   	uncheckingalarm = (int)request.getAttribute("uncheckingalarm");
 }
   	/*
    String userID=null;
 	String toID=null;
	 
    if(session.getAttribute("userID")!=null)
    {
   	 userID=(String)session.getAttribute("userID");
    }
    
    if(request.getParameter("toID")!=null)
    {
   	 toID=(String)request.getParameter("toID");
    }
     */
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
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/custom.css">
<title>Bacon Network - 취업게시판</title>
<!-- Latest compiled and minified CSS -->
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Facebook 로그아웃 -->
<script src="<%=request.getContextPath()%>/js/facebooklogout.js"></script>
<script>
$(document).ready(function(){
	
	fetchSearchResult('initial');
	
	$('input[name="q"]').keydown(function(event){
		if(event.keyCode == 13){ // enter-click
			$('#searchBtn').trigger('click');
		}
	});

	$('#searchBtn').click(function(){
		var q = $('input[name="q"]').val();
		if(q.length > 1){
			fetchSearchResult(q);
		} else {
			alert('검색어는 2글자 이상 입력하세요!');
		}
	});

	function fetchSearchResult(q){
		console.log(q);
		var domain = "<%=DataInfo.domain_path%>";
		$.get(domain + '/jobinfo' ,{
			keywords:q
		}).done(function(data){
			if(data == 'none')
			{
				$('#result').html('맞춤형 기업 정보가 존재하지 않습니다.');
			}else
			{
				var items = $(data).find('job');
				var results = [];
				
				$.each(items, function(k, v){
					var location= $(this).find('location').html();
					console.log(location);
					var title = $(this).find('title').html();
					var name =$(this).find('name').html();
					var jobtype = $(this).find('job-type').text();
					var jobcategory = $(this).find('job-category').text();
					var salary = $(this).find('salary').text();
					var url = $(this).find('url').text();
					//console.log(url);
					results.push('<a href="'+url+'" class="list-group-item" target="_blank" style="margin:10px"><div class="row"><div class="col-md-10"><h4 class="list-group-item-heading"><span class="glyphicon glyphicon-star" aria-hidden="true"></span> '+title+'</h4><p class="list-group-item-text"><span class="label label-success"></span><h4> '+name+'</h4><p class="list-group-item-text"><span class="label label-success">근무형태</span> '+jobtype+'</p><p class="list-group-item-text"><span class="label label-success">카테고리</span> '+jobcategory+'</p><p class="list-group-item-text"><span class="label label-success">지역</span> '+location+'</p><p class="list-group-item-text"><span class="label label-success">연봉</span> '+salary+'</p></div></div></a>');
				});
				$('#result').html(results.join(''));
			}
			
		});
	};

	// click item
	$(document).on('click', 'a.list-group-item', function(evt){
		$(this).siblings().removeClass('active').end().addClass('active');
	});
});
</script>
</head>
<body>
<%if(login){ %>
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
                    <li><a href="<%=request.getContextPath()%>/baconnetwork/setting.do">설정</a></li>
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

<!-- 오른쪽부분 -->
<div class="container-fluid" style="background-color:#8C8C8C">
	<div class="navbar-form navbar-center" id="jobsearch">
		<input type="text" name="q" id="q" class="form-control" placeholder="직업 혹은 지역 입력">
		<span class="navbar-form-btn">
 			<button class="btn btn-default" id="searchBtn" type="button">검색</button>
		</span>
	</div>
    	<hr>
    	<br>
    <div id="result"></div>
</div>
<div id="pocket"></div>
<%}else{ %>
<h1 align="center">그러한 접근은 불가능 합니다.</h1>
<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork.main">로그인이 필요합니다.</a></p>
<%} %>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</html>