<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="expertisesns.model.PersonGrade" %>
    <%@ page import="java.util.*" %>
    <%@ page import="expertisesns.model.FollowDto" %>
    <%@ page import="expertisesns.data.DataInfo" %>
    <%
    String email = null;
    String name = null;
    int follower = 0;
    int following = 0;
    int boardnum = 0;
    int uncheckingalarm = 0;
    
    List<PersonGrade> person_list = null;
    PersonGrade persongrade = null;
    String profilepath = null;
    
    List<FollowDto> follow_data_list = null;
    
    if(session.getAttribute("id") != null)
    {
    	email = (String)session.getAttribute("id");
    	name = (String)session.getAttribute("name");
    	follower = (int)session.getAttribute("follower");
    	following = (int)session.getAttribute("following");
    	boardnum = (int)session.getAttribute("boardnum");	
    	
    	person_list = (ArrayList<PersonGrade>)request.getAttribute("knownpeople");
    	profilepath = (String)request.getAttribute("profilepath");
    	
    	uncheckingalarm = (int)request.getAttribute("uncheckingalarm");
    	
    	follow_data_list = (List<FollowDto>)request.getAttribute("follow_data");
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
<title>Bacon Network - 인맥</title>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Facebook 로그아웃 -->
<script src="<%=request.getContextPath()%>/js/facebooklogout.js"></script>
<%if(login){ %>
<script>
function AlarmEvent(follow_seq)
{
	var alarm_data = {"follow_seq": follow_seq, "member":'<%=email%>', "type":"A", "alarm_type": "b"};
	
	$.ajax({
		type:'POST',
		url: '<%=DataInfo.domain_path%>'+'/updatealarm',
		data: alarm_data,
		success: function(data)
		{
			console.log('알림 이벤트 : '+data);
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
function FollowerEvent(searched_person)
{
	var person_seq = $(searched_person).parent().children('#person_seq').val();
	var my_email = '<%=email%>';
	//alert(person_seq);
	var allData = {"person_seq":person_seq, "my_email":my_email};
	
	//알림 
	AlarmEvent(person_seq);
	
	$.ajax({
		type: 'POST',
		url: '<%=DataInfo.domain_path%>'+'/FollowAjax',
		data: allData,
		success: function(data)
		{
			console.log(data);
			
			if(data == 'alreadyfollowing')
			{
				alert('이미 팔로우한 상대입니다.');
			}else if(data == 'followsuccess')
			{
				alert('팔로우 하였습니다.')
			}
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
</script>
<!-- 채팅 부분 -->
<script type="text/javascript">
//chat.jsp페이지 띄우는 스크립트
var preid;
function move_box(an, box) 
{
	//링크된 위치에서 부터의 설정값 지정
  var cleft = -110;  //왼쪽마진  
  var ctop = 430;  //상단마진
  var obj = an;
  while (obj.offsetParent) 
  {
    cleft += obj.offsetLeft;
   
    obj = obj.offsetParent;
  }
  box.style.left = cleft + 'px';
  ctop += an.offsetHeight + 8;
  if (document.body.currentStyle && document.body.currentStyle['marginTop']) 
  {
    ctop += parseInt(document.body.currentStyle['marginTop']);
  }
  box.style.top = ctop + 'px';
}

function show_hide_box(an, width, height, borderStyle) 
{
  //a 태그의 url: http://localhost:8080/ExpertiseSNS/chat.jsp?toID=2
  var href = an.href;
  //빈 객체 생성
  var boxdiv = document.getElementById(href);
  
  if (boxdiv != null) 
  {
    if (boxdiv.style.display=='none') 
    {
      move_box(an, boxdiv);
      boxdiv.style.display='block';
    }else
    {
      boxdiv.style.display='none';
      preid = href;
  	}
    return false;
  }
  preid = href;
  boxdiv = document.createElement('div');
  boxdiv.setAttribute('id', href);
  boxdiv.style.display = 'block';
  boxdiv.style.position = 'fixed';
  boxdiv.style.width = width + 'px';
  boxdiv.style.height = height + 'px';
  boxdiv.style.border = borderStyle;
  boxdiv.style.backgroundColor = '#fff';

  var contents = document.createElement('iframe');
  contents.scrolling = 'no';
  contents.frameBorder = '0';
  contents.style.width = width + 'px';
  contents.style.height = height + 'px';
  contents.src = href;

  boxdiv.appendChild(contents);
  document.body.appendChild(boxdiv);
  move_box(an, boxdiv);
  
  
  return false; 
}
//chat.jsp에서 닫기버튼 기능
function hide()
{
   document.getElementById(preid).style.display = 'none';
   document.getElementById(preid).removeNode(true);
}
</script>
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
</head>
<body>
<!-- 네비게이션 바 -->
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

<!-- 본문 -->
<div class="container-fluid" style="margin-top: 30px;">
	<div class="row">
		<!-- 사이드 바 부분 -->
		<div class="col-sm-3" style="padding:30px; border: 1px solid black; margin:20px">
			<div class="nav nav-sidebar">
				<p align="center"><img src="<%=profilepath %>" width="100" height="100"/></p>
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
				<h4>나와 유사할 수 있는 사람들</h4>
			</div>
			<%
			Iterator<PersonGrade> persongrade_iter = person_list.iterator();
			while(persongrade_iter.hasNext())
			{
				persongrade = persongrade_iter.next();
			%>
			<div class="form-group">
				<img src="<%=persongrade.getProfilePath() %>" width="70" height="70" align="left" vspace="0" hspace="5">
				<p>이름 : <%=persongrade.getName() %></p>
				<p>경력 : <%=persongrade.getCompany() %></p>
				<p>학력 : <%=persongrade.getSchool() %></p>
				<input type="hidden" id="person_seq" value="<%=persongrade.getMember_seq() %>">
				<button type="button" id="following_btn" class="btn btn-primary" onclick="FollowerEvent(this);">팔로잉</button>	
			</div>
			<%} %>
		</div>
		
		<!-- 채팅 부분 -->
		<div class="col-sm-2">
			<div class="panel panel-default" style="margin-top: 20px">
					<div class="panel-heading"><p align="center">채팅</p></div>
		 		<div class="panel-body">
		  		<% 
		  		if(follow_data_list != null)
		  		{
		  		Iterator<FollowDto> follow_iter = follow_data_list.iterator(); 
		  		while(follow_iter.hasNext())
		  		{
		  			FollowDto followdto = follow_iter.next();
		  		%>    
		      		<div id="contents">
		      			<p align="left">  
			    			<a href="<%=request.getContextPath() %>/main/chat.jsp?toID=<%=followdto.getId() %>" onClick="return show_hide_box(this,300,300)">
							<input type="hidden" name="follow_seq_data" id="follow_seq_data" value="<%=followdto.getSeq()%>"/>
						  	<img src="<%=followdto.getProfile_path() %>" width="40" height="40"><%=followdto.getName()%>
						  	</a>
					  	</p>
		  		<%}
		  		}%>  
				   	</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%}else{ %>
<h1 align="center">그러한 접근은 불가능 합니다.</h1>
<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork.main">로그인이 필요합니다.</a></p>
<%} %>
</body>
</html>