<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
    <%@ page import="expertisesns.model.AlarmInfoDto" %>
    <%@ page import="expertisesns.model.FollowDto" %>
    <%@ page import="expertisesns.data.DataInfo" %>
    <%
    String email = null;
    String name = null;
    int follower = 0;
    int following = 0;
    int boardnum = 0;
    
    List<AlarmInfoDto> alarm_info_list = null;
    int uncheckingalarm = 0;
    Iterator<AlarmInfoDto> alarm_info_iter = null;
    
    List<FollowDto> follow_data_list = null;

    if(session.getAttribute("id") != null)
    {
        email = (String)session.getAttribute("id");
    	name = (String)session.getAttribute("name");
    	follower = (int)session.getAttribute("follower");
    	following = (int)session.getAttribute("following");
    	boardnum = (int)session.getAttribute("boardnum");	
    	
    	alarm_info_list = (List<AlarmInfoDto>)request.getAttribute("alarminfo");
        alarm_info_iter = alarm_info_list.iterator();
        
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
<title>Baconnetwork - 알림</title>
<style>
.form-group
{
	border: 1px solid black;
	padding: 10px;
	margin: 20px;
}
</style>
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
function updateAlarmInfo(alarm_seq)
{
	var sendData = {"alarm_seq":alarm_seq};
	
	var domain = "<%=DataInfo.domain_path%>";
	$.ajax({
		type: 'POST',
		url: domain + '/updateAlarmInfo',
		data: sendData,
		dataType: 'json',
		success: function(data)
		{
			var uncheckedAlarmData = data.results;
			var alarmTotalNum = data.total;
			
			//전체 알람 삭제
			$('.col-sm-6').children('#contents').each(function(index){
				$(this).remove();
			});
			
			//알람 내용 갱신 
			for(var i=0; i<uncheckedAlarmData.length; i++)
			{
				var alarm_seq = uncheckedAlarmData[i].alarm_seq;
				var sender_name = uncheckedAlarmData[i].sender_name;
				var message = uncheckedAlarmData[i].message;
				var profile_url = uncheckedAlarmData[i].profile_url;
				
				var alarm_template = '<div id="contents" class="form-group" ></div>'
				$('.col-sm-6').append(alarm_template);
				$('.col-sm-6 > #contents').last().append('<input type="hidden" name="alarm_seq" id="alarm_seq" value="'+alarm_seq+'"/><p><img src="'+profile_url+'" width="40" height="40"/>'+sender_name+'님이' +message+'</p>');
			}
			
			//알람 갯수 갱신 
			$('#dbstatus').html(alarmTotalNum);
			$('#dbstatus2').html(alarmTotalNum);
			
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
$(document).ready(function(){
	/*
	$('#contents').on({
		mouseover: function(){
			//alert('mouseover!!');
		},
		click: function(){
			alert('click!');
		}
	});
	*/
	//동적으로 생성된 객체는 아래와 같이 작업을 해야 전체가 다 선택되어짐.
	$(document).on('mouseover','#contents',function(){
		$(this).css('background-color','#BDBDBD');
	});
	$(document).on('mouseleave','#contents',function(){
		$(this).css('background-color','white');
	});
	$(document).on('click','#contents',function(){
		var alarm_seq = $(this).children('#alarm_seq').val();
		//alert(alarm_seq);
		updateAlarmInfo(alarm_seq);
	});
});
</script>
<!-- 채팅부분 -->
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
<div class="container-fluid" style="margin-top: 30px">
	<div class="row">
    	<!--사이드 바 부분-->
        <div class="col-sm-3" style="padding:30px; border: 1px solid black; margin:20px">
        	<div class="nav nav-sidebar">
                <h3 align="center">알림</h3>
                <p align="center">아직 읽어보지 않은 알림 : <span id="dbstatus2"><%=uncheckingalarm %></span></p>
            </div>
        </div>
        <!-- 본문 -->
		<!-- 알람 부분 -->
		<div class="col-sm-6">	
			<%
			while(alarm_info_iter.hasNext())
		    {
		    	AlarmInfoDto alarminfodto = alarm_info_iter.next();
		    	int seq = alarminfodto.getSeq();
		    	String sender_name = alarminfodto.getName();
		    	String message = alarminfodto.getMessage();
		    	String profileurl = alarminfodto.getProfile_path();
			%>
			<div id="contents" class="form-group" >
				<input type="hidden" name="alarm_seq" id="alarm_seq" value="<%=seq %>"/>
				<p><img src="<%=profileurl %>" width="40" height="40"/><%=sender_name %>님이 <%=message %></p>
			</div>
			<%}%>
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
<%} else{%>
<h1 align="center">그러한 접근은 불가능 합니다.</h1>
<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork.main">로그인이 필요합니다.</a></p>
<%} %>
</body>
</html>