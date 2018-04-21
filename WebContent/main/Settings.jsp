<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="expertisesns.model.SettingDto" %>
    <%@ page import="java.util.Calendar" %>
    <%@ page import="expertisesns.data.DataInfo" %>
    <%
    String email = null;
    String name = null;
    int follower = 0;
    int following = 0;
    int boardnum = 0;
    
    int uncheckingalarm = 0;
    String profilepath = null;
    
    SettingDto settingdto = null;
    
    if(session.getAttribute("id") != null)
    {
		email = (String)session.getAttribute("id");	
		name = (String)session.getAttribute("name");
    	follower = (int)session.getAttribute("follower");
    	following = (int)session.getAttribute("following");
    	boardnum = (int)session.getAttribute("boardnum");
    	
    	uncheckingalarm = (int)request.getAttribute("uncheckingalarm");
    	profilepath = (String)request.getAttribute("profilepath");
    	
    	settingdto = (SettingDto)request.getAttribute("member_data");
    }
    
    boolean login = email == null ? false : true;
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Bacon Network - 설정</title>
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
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- jQuery UI -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- Facebook 로그아웃 -->
<script src="<%=request.getContextPath()%>/js/facebooklogout.js"></script>
<style>
.form-group
{
	border: 1px solid black;
	padding: 10px;
	margin: 20px;
}
</style>
<%if(login){ %>
<script>
$(document).ready(function()
{
	//다이얼로그 변수
	var corret_name_dialog, corret_pw_dialog, corret_birth_dialog, corret_sex_dialog, correct_addr_dialog, correct_tel_dialog;
	
	//수정 데이터 서버로 이동
	function updateData(dataType, data)
	{
		var allData = {"dataType": dataType, "data": data};
		
		var domain = "<%=DataInfo.domain_path%>";
		
		$.ajax({
			type: "POST",
			url: domain+'/updateSettings',
			data: allData,
			success:function(data)
			{
				if(data == 'success')
				{
					alert('성공적으로 수정되었습니다.');
					
					corret_name_dialog.dialog('close');
					corret_pw_dialog.dialog('close');
					corret_birth_dialog.dialog('close');
					corret_sex_dialog.dialog('close');
					correct_addr_dialog.dialog('close');
					correct_tel_dialog.dialog('close');
				}
			},
			error: function(request, status, error)
			{
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	//다이얼로그 이벤트 
	corret_name_dialog = $('#setting_name_form').dialog({
		autoOpen: false,
		height: 150,
		width: 500,
		modal: true,
		close: function(){
			$(this).children('p').text('');
		}
	});
	
	corret_pw_dialog = $('#setting_pw_form').dialog({
		autoOpen: false,
		height: 150,
		width: 500,
		modal: true,
		close: function(){
			$(this).children('p').text('');
		}
	});
	
	corret_birth_dialog = $('#setting_birth_form').dialog({
		autoOpen: false,
		height: 150,
		width: 500,
		modal: true,
		close: function(){
			$(this).children('p').text('');
		}
	});
	corret_sex_dialog = $('#setting_sex_form').dialog({
		autoOpen: false,
		height: 150,
		width: 500,
		modal: true,
		close: function(){
			$(this).children('p').text('');
		}
	});
	
	correct_addr_dialog = $('#setting_addr_form').dialog({
		autoOpen: false,
		height: 150,
		width: 500,
		modal: true,
		close: function(){
			$(this).children('p').text('');
		}
	});
	
	correct_tel_dialog = $('#setting_tel_form').dialog({
		autoOpen: false,
		height: 150,
		width: 500,
		modal: true,
		close: function(){
			$(this).children('p').text('');
		}
	});
	
	//다이얼로그 여는 이벤트
	$('#correct_name').on('click', function(event){
		event.preventDefault();
		//var content_name = $(this).attr("href");
		corret_name_dialog.dialog("open");
	});
	
	$('#correct_pw').on('click', function(event){
		event.preventDefault();
		//var content_pw = $(this).attr("href");
		corret_pw_dialog.dialog("open");
	});
	
	$('#correct_birth').on('click', function(event){
		event.preventDefault();
		//var content_birth = $(this).attr("href");
		corret_birth_dialog.dialog("open");
	});
	
	$('#correct_sex').on('click', function(event){
		event.preventDefault();
		//var content_sex = $(this).attr("href");
		corret_sex_dialog.dialog("open");
	});
	
	$('#correct_addr').on('click', function(event){
		event.preventDefault();
		//var content_addr = $(this).attr("href");
		correct_addr_dialog.dialog("open");
	});
	
	$('#correct_tel').on('click', function(event){
		event.preventDefault();
		//var content_tel = $(this).attr("href");
		correct_tel_dialog.dialog("open");
	});
	
	//수정 하는 이벤트
	$('#correctName_btn').click(function(event){
		var content = $('#correctNameText').val();
		updateData('NAME', content);
	});
	
	$('#correctPW_btn').click(function(event){
		var content = $('#correctPWText').val();
		updateData('PASSWORD', content);
	});
	
	$('#correctBirth_btn').click(function(event){
		var content;

		var year = $('select[name=year]').val();
		var month = $('select[name=month]').val();
		var days = $('select[name=days]').val();
		
		content = year+'-'+month+'-'+days;
		updateData('BIRTH', content);
	});
	
	$('#correctSex_btn').click(function(event){
		var content = $(':input:radio[name=sex]:checked').val();
		updateData('SEX', content);
	});
	
	$('#correctAddr_btn').click(function(event){
		var content = $('#correctAddrText').val();
		updateData('ADDRESS', content);
	});
	
	$('#correctTel_btn').click(function(event){
		var content = $('#correctTelText').val();
		updateData('TELEPHONE', content);
	});
});
</script>
</head>
<body>
<!-- 다이얼로그 폼(이름) -->
<div id="setting_name_form" title="계정 설정">
	<label for="correctNameText">새로운 이름 : </label>
	<input type="text" name="correctNameText" id="correctNameText" class="ext ui-widget-content ui-corner-all"/>
	<p></p><p></p>
	<button id="correctName_btn" class="btn btn-lg btn-primary btn-block" >수정</button>
</div>

<!-- 다이얼로그 폼(비밀번호) -->
<div id="setting_pw_form" title="계정 설정">
	<label for="correctPWText">새로운 비밀번호 : </label>
	<input type="text" name="correctPWText" id="correctPWText" class="ext ui-widget-content ui-corner-all"/>
	<p></p><p></p>
	<button id="correctPW_btn" class="btn btn-lg btn-primary btn-block" >수정</button>
</div>

<!-- 다이얼로그 폼(생년) -->
<div id="setting_birth_form" title="계정 설정">
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
		<p></p><p></p>
	<button id="correctBirth_btn" class="btn btn-lg btn-primary btn-block" >수정</button>
</div>

<!-- 다이얼로그 폼(성별) -->
<div id="setting_sex_form" title="계정 설정">
	<div style="align:center">
		<input type="radio" name="sex" value="1">남성
		<input type="radio" name="sex" value="0">여성
	</div>
	<p></p><p></p>
	<button id="correctSex_btn" class="btn btn-lg btn-primary btn-block" >수정</button>
</div>

<!-- 다이얼로그 폼(주소) -->
<div id="setting_addr_form" title="계정 설정">
	<label for="correctAddrText">새로운 주소 : </label>
	<input type="text" name="correctAddrText" id="correctAddrText" class="ext ui-widget-content ui-corner-all"/>
	<p></p><p></p>
	<button id="correctAddr_btn" class="btn btn-lg btn-primary btn-block" >수정</button>
</div>

<!-- 다이얼로그 폼(전화번호) -->
<div id="setting_tel_form" title="계정 설정">
	<label for="correctTelText">새로운 전화번호 : </label>
	<input type="text" name="correctTelText" id="correctTelText" class="ext ui-widget-content ui-corner-all"/>
	<p></p><p></p>
	<button id="correctTel_btn" class="btn btn-lg btn-primary btn-block" >수정</button>
</div>

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

<div class="container-fluid" style="margin-top: 30px;">
	<div class="row">
	    	<!--사이드 바 부분-->
	        <div class="col-sm-2" style="padding:30px; border: 1px solid black; margin:20px">
	            <div class="nav nav-sidebar">
	            	<p align="center"><img src="<%=profilepath %>" width="100" height="100"/></p>
	                <p align="center"><%=name %> 회원님</p>
	            </div>
	            <hr/>
	            <div class="nav nav-sidebar">
	                <p align="center">팔로워 : <%=follower %></p>
	                <p align="center">팔로잉 : <%=following %></p>
	            </div>
	        </div>
	        
	        <!-- 본문 -->
	        <div class="col-sm-6">
	        	<div class="header">
	     	   		<h2>계정 설정</h2>
	     	   	</div>
	     	   	<div id="contentArea">
	     	   		<table class="table">
	     	   			<tbody>
	     	   				<tr>
	     	   					<th>이름</th>
	     	   					<td id="name"><%=settingdto.getName() %></td>
	     	   					<td><a href="name" id="correct_name">수정</a></td>
	     	   				</tr>
	     	   				<tr>
	     	   					<th>비밀번호</th>
	     	   					<td>*****</td>
	     	   					<td><a href="#" id="correct_pw">수정</a></td>
	     	   				</tr>
	     	   				<tr>
	     	   					<th>생일</th>
	     	   					<td id="birth"><%=settingdto.getBirth() %></td>
	     	   					<td><a href="birth" id="correct_birth">수정</a></td>
	     	   				</tr>
	     	   				<%
	     	   				int sex = settingdto.getSex();
	     	   				String sex_str = null;
	     	   				if(sex == 1)
	     	   					sex_str = "남성";
	     	   				else if(sex == 0)
	     	   					sex_str = "여성";
	     	   				%>
	     	   				<tr>
	     	   					<th>성별</th>
	     	   					<td id="sex"><%=sex_str %></td>
	     	   					<td><a href="sex" id="correct_sex">수정</a></td>
	     	   				</tr>
	     	   				<tr>
	     	   					<th>주소</th>
	     	   					<td id="address"><%=settingdto.getAddress() %></td>
	     	   					<td><a href="address" id="correct_addr">수정</a></td>
	     	   				</tr>
	     	   				<tr>
	     	   					<th>전화번호</th>
	     	   					<td id="tel"><%=settingdto.getTel() %></td>
	     	   					<td><a href="tel" id="correct_tel">수정</a></td>
	     	   				</tr>
	     	   			</tbody>
	     	   		</table>
	     	   	</div>
     	   </div>
</div>
<%} else{%>
<h1 align="center">그러한 접근은 불가능 합니다.</h1>
<p align="center"><a href="<%=request.getContextPath()%>/baconnetwork.main">로그인이 필요합니다.</a></p>
<%} %>
</body>
</html>