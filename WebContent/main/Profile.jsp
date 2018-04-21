<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.*" %>
    <%@ page import="expertisesns.data.DataInfo" %>
    <%
    	String email = null;
    	String name = null;
    	
    	int follower = 0;
    	int following = 0;
    	int boardnum = 0;
    	
    	String profileName = null;
    	String idStr = null;
    	
    	String profile = null;
    	String seekingwork = null;
    	String lastcompany = null;
    	String highestschooling = null;
    	String expertise = null;
    	
    	String[] AWARDEDCAREERS = null;
    	String[] EXPERTISETECHNICS = null;
    	String[] LICENSES = null;
    	String[] EDUCATIONS = null;
    	
    	int uncheckingalarm = 0;
    	if(session.getAttribute("id") != null)
    	{
    		email = (String)session.getAttribute("id");
        	name = (String)session.getAttribute("name");
        	follower = (int)session.getAttribute("follower");
        	following = (int)session.getAttribute("following");
        	boardnum = (int)session.getAttribute("boardnum");
        	
        	profileName = (String)request.getAttribute("profile");	
        	
        	StringTokenizer id = new StringTokenizer(email,".");
        	idStr = null;
        	if(id.hasMoreTokens())
        	{
        		idStr = id.nextToken();
        	}
        	
        	profile = (String)request.getAttribute("profile");
        	seekingwork = (String)request.getAttribute("seekingwork");
        	lastcompany = (String)request.getAttribute("lastcompany");
        	highestschooling = (String)request.getAttribute("highestschooling");
        	expertise = (String)request.getAttribute("expertise");
        	
        	AWARDEDCAREERS = (String[])request.getAttribute("AWARDEDCAREERS");
        	EXPERTISETECHNICS = (String[])request.getAttribute("EXPERTISETECHNICS");
        	LICENSES = (String[])request.getAttribute("LICENSES");
        	EDUCATIONS = (String[])request.getAttribute("EDUCATIONS");
        	
        	uncheckingalarm = (int)request.getAttribute("uncheckingalarm");
    	}
    	
    	boolean login = email==null ? false : true;
    	boolean pe = profileName==null ? false : true;
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Bacon Network - 프로필</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/MainEnt.css"/> -->
<!-- jQuery UI -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- jquery cdn -->
<script src="http://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ToggleSwitch.css">

<!-- Facebook 로그아웃 -->
<script src="<%=request.getContextPath()%>/js/facebooklogout.js"></script>
<style>
body {
  padding-top: 50px;
}

table, th, td {
    border: 1px solid black;
}
</style>
<%if(login){ %>
<script>
$(document).ready(function(){
	
	//accordion 등록
	$('#accordion').accordion();
	
	/*
	다이얼로그 등록 부분 
	*/
	//다이얼로그
	var schooldialog, expertisedialog, companydialog, owntechdialog, ownlicensedialog, ownawardeddialog, owneducareerdialog;
	
	//회사 등록 다이얼로그 이벤트 
	companydialog = $("#companydialog-form").dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close: function(){
			$('#companybody').text('');
		}
	});
	$('#registercompany').button().on("click", function(){
		companydialog.dialog("open");
	});
	//학교 등록 다이얼로그 이벤트
	schooldialog = $("#schooldialog-form").dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close: function(){
			$('#univbody').text('');
		}
	});
	$('#registerSchool').button().on("click", function(){
		schooldialog.dialog("open");
	});
	
	//전문분야 등록 다이얼로그 이벤트 
	expertisedialog = $('#expertisedialog-form').dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close: function(){
			
		}
	});
	$('#registerownexpertise').button().on('click', function(){
		expertisedialog.dialog("open");
	});
	
	//가지고 있는 기술 등록 다이얼로그 이벤트
	owntechdialog = $('#owntechdialog-form').dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close: function(){
			$('#techbody').text('');
		}
	});
	$('#registerowntech').button().on('click', function(){
		owntechdialog.dialog("open");
		
		$('#owntechforexpertise > p').each(function(index){
			var data = $(this).text();
			$('#techbody').prepend('<tr id="owntechDatas"><td id="owntechname">'+data+'</td>'+'<td name="owntechbutton"><input type="button" id="deleteowntechbtn" value="삭제"/></td></tr>');
			
			$('#deleteowntechbtn').click(function(event){
				$(this).parents('#owntechDatas').remove();
			});
		});
	});
	
	//자격증 등록 다이얼로그 이벤트 
	ownlicensedialog = $('#ownlicensedialog-form').dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close: function(){
			$('#licensebody').text('');
		}
	});
	$('#registerlicense').button().on('click', function(){
		ownlicensedialog.dialog("open");
		
		$('#ownlicenses > p').each(function(index){
			var data = $(this).text();
			$('#licensebody').prepend('<tr id="ownlicenseDatas"><td id="ownlicensename">'+data+'</td>'+'<td name="ownlicensebutton"><input type="button" id="deleteownlicensebtn" value="삭제"/></td></tr>');
			
			//등록한 자격증 목록에서 삭제버튼을 클릭했을 경우
			$('#deleteownlicensebtn').click(function(event){
				$(this).parents('#ownlicenseDatas').remove();
			});
		});
	});
	
	//수상경력 등록 다이얼로그 이벤트
	ownawardeddialog = $('#ownawarddialog-form').dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close:function(){
			$('#awardedbody').text('');
		}
	});
	$('#registeraward').button().on('click', function(){
		ownawardeddialog.dialog("open");
		
		$('#ownawarded > p').each(function(index){
			var data = $(this).text();
			$('#awardedbody').prepend('<tr id="ownawardedDatas"><td id="ownawardedname">'+data+'</td>'+'<td name="ownawardedbutton"><input type="button" id="deleteownawardedbtn" value="삭제"/></td></tr>');
			
			//등록한 수상 목록에서 삭제버튼을 클릭했을 경우
			$('#deleteownawardedbtn').click(function(event){
				$(this).parents('#ownawardedDatas').remove();
			});
		});
	});
	
	//교육이수내역 등록 다이얼로그 이벤트
	owneducareerdialog = $('#owneducareerdialog-form').dialog({
		autoOpen: false,
		height: 400,
		width: 700,
		modal: true,
		close: function(){
			$('#educareerbody').text('');	
		}
	});
	$('#registereducareer').button().on('click', function(){
		owneducareerdialog.dialog("open");
		
		$('#owneducareer > p').each(function(index){
			var data = $(this).text();
			$('#educareerbody').prepend('<tr id="owneducareerDatas"><td id="owneducareername">'+data+'</td>'+'<td name="owneducareerbutton"><input type="button" id="deleteeducareerbtn" value="삭제"/></td></tr>');
			
			//등록한 교육이수 목록에서 삭제버튼을 클릭했을 경우
			$('#deleteeducareerbtn').click(function(event){
				$(this).parents('#owneducareerDatas').remove();
			});
		});
	});
	/*
	ajax 요청 부분 
	*/
	//프로필 사진 등록을 위한 Ajax 요청
	$('#btnSubmit').click(function(event){
		//submit 버튼의 이벤트를 제거
		event.preventDefault();
		
		//form(폼) 얻어오기
		var form = $('#fileUploadForm')[0];
		
		//폼데이터 객체를 생성
		var data = new FormData(form);
		
		//submit 버튼 재기불능으로 만들기
		//$("#btnSubmit").prop("disabled", true);
		
		var domain = "<%=DataInfo.domain_path%>";
		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: domain +'/upload',
			data: data,
			processData: false,
			contentType: false,
			cache: false,
			success: function(data)
			{
				console.log('ajax Success : '+data);
				if(data == 'not_multipart')
					alert('Error!');
				else if(data == 'not_image')
					alert('이미지 파일이 아닙니다. 이미지 파일을 업로드 하십시오.');
				else
				{
					$('img').attr('src',data);
				}
			}
		});
	});
	
	//학력 요청을 위한 ajax
	$('#schoolSearch').click(function(event){
		//submit 버튼의 이벤트 제거
		event.preventDefault();
		
		$('#univtbody').text('');
		
		$(document).off();
		
		var education = $('#education').val();
		var schoolname = $('#schoolname').val();
		
		var allData = {"education":education, "schoolname":schoolname};
		
		var domain = "<%=DataInfo.domain_path%>";
		
		$.ajax({
			type: "POST",
			url: domain+'/schoolsearch',
			data: allData,
			dataType: "json",
			success: function(data)
			{
				var schooldataset = data.results;
				
				for(var i=0; i<schooldataset.length; i++)
				{
					console.log("kindofUniv : " + schooldataset[i].kindofUniv);
					console.log("nameofUniv : " + schooldataset[i].nameofUniv);
					console.log("branchSchoolOrNot : " + schooldataset[i].branchSchoolOrNot);
					console.log("establishment : " + schooldataset[i].establishment);
					console.log("address : " + schooldataset[i].address);
					
					$('#univtbody').prepend('<tr id="univData"><td id="univkind">'+schooldataset[i].kindofUniv+'</td>'+'<td id="univname">'+schooldataset[i].nameofUniv+'</td>'+'<td id="univbranch">'+schooldataset[i].branchSchoolOrNot+'</td>'+'<td id="univ_establishment">'+schooldataset[i].establishment+'</td>'+'<td id="univaddress">'+schooldataset[i].address+'</td></tr>');
				}
				//다이얼로그의 대학 데이터 이벤트 등록
				$(document).on('mouseover', '#univData', function(){
					$(this).css('background-color','#6799FF');
				});
				$(document).on('mouseleave', '#univData', function(){
					$(this).css('background-color','white');
				});
				$(document).on('click', '#univData', function(){
					var univkind = $(this).children('#univkind').html();
					var univname = $(this).children('#univname').html();
					var univbranch = $(this).children('#univbranch').html();
					var univestablishment = $(this).children('#univ_establishment').html();
					var univaddress = $(this).children('#univaddress').html();
					
					console.log(univkind);
					console.log(univname);
					console.log(univbranch);
					console.log(univestablishment);
					console.log(univaddress);
					
					//$('#registerunivkind').html('학교 종류 : ' + univkind);
					$('#registerunivname').html('학교 이름 : ' + univname);
					//$('#registerunivbranch').html('본교/분교 : ' + univbranch);
					//$('#registerunivaddress').html('학교 주소 : ' + univaddress);
					
					$('#univkind_data').val(univkind);
					$('#univname_data').val(univname);
					$('#univbranch_data').val(univbranch);
					$('#univestablishment').val(univestablishment);
					$('#univaddress_data').val(univaddress);
					
					$('#univtbody').text('');
					schooldialog.dialog('close');
				});
			},
			error:function(request, status, error)
			{
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});
	
	//회사 요청을 위한 ajax
	$('#companySearch').click(function(event){
		
		event.preventDefault();
		
		$('#companybody').text('');
		
		$(document).off();
		
		var companyname = $('#companyname').val();
		var domain = "<%=DataInfo.domain_path%>";
		
		$.ajax({
			type: "GET",
			url: domain+'/companysearch?companyname='+companyname,
			dataType: "json",
			success: function(data)
			{
				var companydataset = data.results;
				
				for(var i=0; i<companydataset.length; i++)
				{
					console.log("companyname : " + companydataset[i].CompanyName);
					console.log("companyaddress : " + companydataset[i].CompanyAddress);
					
					$('#companybody').prepend('<tr id="companyData"><td id="companydataname">'+companydataset[i].CompanyName+'</td>'+'<td id="companydataaddress">'+companydataset[i].CompanyAddress+'</td></tr>');
				}
				//다이얼로그 이벤트
				$(document).on('mouseover', '#companyData', function(){
					$(this).css('background-color','#6799FF');
				});
				$(document).on('mouseleave', '#companyData', function(){
					$(this).css('background-color','white');
				});
				$(document).on('click', '#companyData', function(){
					var companynamefinal = $(this).children('#companydataname').html();
					var companyaddressfinal = $(this).children('#companydataaddress').html();
					
					console.log(companynamefinal);
					console.log(companyaddressfinal);
					
					$('#registeredcompanyname').html('회사 이름 : '+companynamefinal);
					//$('#registeredcompanyaddress').html('회사 주소 : '+companyaddressfinal);
					
					$('#companyname_data').val(companynamefinal);
					$('#companyaddress_data').val(companyaddressfinal);
					
					$('#companybody').text('');
					companydialog.dialog('close');
				});
			},
			error: function(request, status, error)
			{
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});

	/*
	다이얼로그 내의 이벤트들
	*/
	//구인 구직 상태를 입력하기 위한 이벤트
	$('#job').change(function(){
		if($('#job').is(":checked")){
			$('#jobprofile').html('재직');
			$('#job_data').val('재직');
		}else
		{
			$('#jobprofile').html('휴직');
			$('#job_data').val('휴직');
		}
	});
	
	//전문분야 등록 이벤트
	$('#registerownexpertisement').click(function(event){
		var ownexpertise = $('input:radio[name="ownexpertisement"]:checked').val();
		$('#ownexpertise').html('전문분야 : '+ownexpertise);
		$('#expertise_data').val(ownexpertise);
		expertisedialog.dialog('close');
	});
	
	//전문성을 강조할 수 있는 보유기술 등록
	$('#registerowntechnicbtn').click(function(event){
		var technames = $('#owntechnics').val();
		$('#techbody').prepend('<tr id="owntechDatas"><td id="owntechname">'+technames+'</td>'+'<td name="owntechbutton"><input type="button" id="deleteowntechbtn" value="삭제"/></td></tr>');
	
		//등록한 기술 목록에서 삭제버튼을 클릭했을 경우
		$('#deleteowntechbtn').click(function(event){
			$(this).parents('#owntechDatas').remove();
		});
	});
	//전문성을 강조할 수 있는 보유기술 최종 등록 버튼 이벤트
	$('#finalRegisterBtn').click(function(event){
		
		var owntechnames = $('#techbody').html();
		var owntechname = '<tbody>'+owntechnames+'</tbody>';
		
		$('#owntechforexpertise').text('');
		$(owntechname).find('#owntechDatas').each(function(index){
			var data = $(this).find('#owntechname').text();
			$('#owntechforexpertise').append('보유기술 '+(index+1)+'. <p>'+data+'</p>');
			$('#owntechforexpertise').append('<input type="hidden" id="owntech_datas" name="owntech_datas" value="'+data+'">');
		});
		owntechdialog.dialog('close');
	});
	
	//자격증 등록
	$('#registerownlicensebtn').click(function(event){
		var licensenames = $('#ownlicense').val();
		$('#licensebody').prepend('<tr id="ownlicenseDatas"><td id="ownlicensename">'+licensenames+'</td>'+'<td name="ownlicensebutton"><input type="button" id="deleteownlicensebtn" value="삭제"/></td></tr>');
		
		//등록한 자격증 목록에서 삭제버튼을 클릭했을 경우
		$('#deleteownlicensebtn').click(function(event){
			$(this).parents('#ownlicenseDatas').remove();
		});
	});
	//자격증 최종 등록 버튼 이벤트
	$('#LicensesRegisterBtn').click(function(event){
		var ownlicensenames = $('#licensebody').html();
		var ownlicensename = '<tbody>'+ownlicensenames+'</tbody>';
		
		$('#ownlicenses').text('');
		$(ownlicensename).find('#ownlicenseDatas').each(function(index){
			var data = $(this).find('#ownlicensename').text();
			$('#ownlicenses').append('보유자격증 '+(index+1)+'. <p>'+data+'</p>');
			$('#ownlicenses').append('<input type="hidden" id="ownlicense_data" name="ownlicense_data" value="'+data+'">');
		});
		ownlicensedialog.dialog('close');
	});
	
	//수상경력 등록 
	$('#registerownawardbtn').click(function(event){
		var awardednames = $('#myawarded').val();
		$('#awardedbody').prepend('<tr id="ownawardedDatas"><td id="ownawardedname">'+awardednames+'</td>'+'<td name="ownawardedbutton"><input type="button" id="deleteownawardedbtn" value="삭제"/></td></tr>');
		
		
		//등록한 수상 목록에서 삭제버튼을 클릭했을 경우
		$('#deleteownawardedbtn').click(function(event){
			$(this).parents('#ownawardedDatas').remove();
		});
	});
	//수상경력 최종 등록 버튼 이벤트
	$('#AwardedRegisterBtn').click(function(event){
		var ownawardednames = $('#awardedbody').html();
		var ownawardedname = '<tbody>'+ownawardednames+'</tbody>';
		
		$('#ownawarded').text('');
		$(ownawardedname).find('#ownawardedDatas').each(function(index){
			var data = $(this).find('#ownawardedname').text();
			$('#ownawarded').append('수상경력 '+(index+1)+'. <p>'+data+'</p>');
			$('#ownawarded').append('<input type="hidden" id="award_data" name="award_data" value="'+data+'">');
		});
		ownawardeddialog.dialog('close');
	});
	
	//교육이수 등록 
	$('#registerownaeducareerbtn').click(function(event){
		var educareernames = $('#myeducareer').val();
		$('#educareerbody').prepend('<tr id="owneducareerDatas"><td id="owneducareername">'+educareernames+'</td>'+'<td name="owneducareerbutton"><input type="button" id="deleteeducareerbtn" value="삭제"/></td></tr>');
		
		//등록한 교육이수 목록에서 삭제버튼을 클릭했을 경우
		$('#deleteeducareerbtn').click(function(event){
			$(this).parents('#owneducareerDatas').remove();
		});
	});
	//교육이수 최종 등록 버튼 이벤트 
	$('#EducareerBtn').click(function(event){
		var owneducareernames = $('#educareerbody').html();
		var owneducareername = '<tbody>'+owneducareernames+'</tbody>';
		
		$('#owneducareer').text('');
		$(owneducareername).find('#owneducareerDatas').each(function(index){
			var data = $(this).find('#owneducareername').text();
			$('#owneducareer').append('교육이수내역 '+(index+1)+', <p>'+data+'</p>');
			$('#owneducareer').append('<input type="hidden" id="educareer_data" name="educareer_data" value="'+data+'">');
		});
		owneducareerdialog.dialog('close');
	});
});
</script>
</head>
<body>
<!-- 회사등록 -->
<div id="companydialog-form" title="회사 등록">
	<p>자신의 현재 다니고 있는 회사를 등록해 주세요.</p>
	<form method="get" id="searchingSchoolForm">
		<label for="companyname">회사이름</label>
		<input type="text" name="companyname" id="companyname" class="text ui-widget-content ui-corner-all">
		<br>
		<button type="submit" class="btn btn-lg btn-primary btn-block" id="companySearch">회사검색</button>
		<br><br>
		<div id="schoolcontent">
			<table border="1" width="100%">
				<thead>
					<tr>
						<th>회사이름</th>
						<th>주소</th>
					</tr>
				</thead>
				<tbody id="companybody">
					
				</tbody>
			</table>
		</div>
	</form>
</div>
<!-- 대학교등록 -->
<div id="schooldialog-form" title="학교 등록">
	<p>자신의 최종학력에 해당하는 학교를 등록해 주세요.</p>
	<form method="post" enctype="multipart/form-data" id="searchingSchoolForm">
		<fieldset>
			<select name="education" id="education">
				<option value="highschool">고졸</option>
				<option value="university">대졸 이상</option>
			</select>
			<label for="schoolname">학교이름</label>
			<input type="text" name="schoolname" id="schoolname" class="text ui-widget-content ui-corner-all">
		</fieldset>
		<button type="submit" class="btn btn-lg btn-primary btn-block" id="schoolSearch">학교검색</button>
		<br><br>
		<div id="schoolcontent">
			<table border="1" width="100%">
				<thead>
					<tr>
						<th>학교종류</th>
						<th>학교이름</th>
						<th>분교,분교</th>
						<th>설립</th>
						<th>주소</th>
					</tr>
				</thead>
				<tbody id="univtbody">
					
				</tbody>
			</table>
		</div>
	</form>
</div>
<!-- 전문분야등록 -->
<div id="expertisedialog-form" title="전문분야 등록">
	<p>자신있는 자신의 전문분야를 등록해 주세요.</p>
	<div id="accordion">
		<h3>경영,사무</h3>
			<div>
				<p><input type="radio" id="strategy" name="ownexpertisement" value="기획,전략,경영" ><label for="strategy">기획,전략,경영</label></p>
				<p><input type="radio" id="pr" name="ownexpertisement" value="홍보,PR,사보"><label for="pr">홍보,PR,사보</label></p>
				<p><input type="radio" id="ir" name="ownexpertisement" value="회계,재무,세무,IR"><label for="ir">회계,재무,세무,IR</label></p>
				<p><input type="radio" id="secretary" name="ownexpertisement" value="비서,안내,수행원"><label for="secretary">비서,안내,수행원</label></p>
				<p><input type="radio" id="document" name="ownexpertisement" value="사무보조,문서작성"><label for="document">사무보조,문서작성</label></p>
				<p><input type="radio" id="marketing" name="ownexpertisement" value="마케팅,광고,분석"><label for="marketing">마케팅,광고,분석</label></p>
				<p><input type="radio" id="bookkeeping" name="ownexpertisement" value="경리,출납,결산"><label for="bookkeeping">경리,출납,결산</label></p>
				<p><input type="radio" id="judical" name="ownexpertisement" value="총무,법무,사무"><label for="judical">총무,법무,사무</label></p>
				<p><input type="radio" id="humanresource" name="ownexpertisement" value="인사,교육,노무"><label for="humanresource">인사,교육,노무</label></p>
				<p><input type="radio" id="seminar" name="ownexpertisement" value="전시,컨벤션,세미나"><label for="seminar">전시,컨벤션,세미나</label></p>
			</div>
		<h3>영업,고객상담</h3>
		<div>
			<p><input type="radio" id="generalbusiness" name="ownexpertisement" value="일반영업" ><label for="generalbusiness">일반영업</label></p>
			<p><input type="radio" id="technicalbusiness" name="ownexpertisement" value="기술영업"><label for="technicalbusiness">기술영업</label></p>
			<p><input type="radio" id="advertisebusiness" name="ownexpertisement" value="광고영업"><label for="advertisebusiness">광고영업</label></p>
			<p><input type="radio" id="seller" name="ownexpertisement" value="판매,매장관리"><label for="seller">판매,매장관리</label></p>
			<p><input type="radio" id="tmin" name="ownexpertisement" value="TM,인바운드"><label for="tmin">TM,인바운드</label></p>
			<p><input type="radio" id="qa" name="ownexpertisement" value="QA,CS강사,수퍼바이저"><label for="qa">QA,CS강사,수퍼바이저</label></p>
			<p><input type="radio" id="businessmanage" name="ownexpertisement" value="영업기획,관리,지원"><label for="businessmanage">영업기획,관리,지원</label></p>
			<p><input type="radio" id="itsolution" name="ownexpertisement" value="IT,솔루션영업"><label for="itsolution">IT,솔루션영업</label></p>
			<p><input type="radio" id="businesssolution" name="ownexpertisement" value="금융,보험영업"><label for="businesssolution">금융,보험영업</label></p>
			<p><input type="radio" id="tmout" name="ownexpertisement" value="TM,아웃바운드"><label for="tmout">TM,아웃바운드</label></p>
			<p><input type="radio" id="customercenter" name="ownexpertisement" value="고객센터,CS"><label for="customercenter">고객센터,CS</label></p>
		</div>
		<h3>IT,인터넷</h3>
		<div>
			<p><input type="radio" id="webdev" name="ownexpertisement" value="웹개발" ><label for="webdev">웹개발</label></p>
			<p><input type="radio" id="systemdev" name="ownexpertisement" value="시스템개발"><label for="systemdev">시스템개발</label></p>
			<p><input type="radio" id="erp" name="ownexpertisement" value="ERP,시스템분석,설계"><label for="erp">ERP,시스템분석,설계</label></p>
			<p><input type="radio" id="publishing" name="ownexpertisement" value="퍼블리싱,UI개발"><label for="publishing">퍼블리싱,UI개발</label></p>
			<p><input type="radio" id="hwsw" name="ownexpertisement" value="하드웨어,소프트웨어"><label for="hwsw">하드웨어,소프트웨어</label></p>
			<p><input type="radio" id="pm" name="ownexpertisement" value="웹기획,PM"><label for="pm">웹기획,PM</label></p>
			<p><input type="radio" id="ai" name="ownexpertisement" value="인공지능,빅데이터"><label for="ai">인공지능,빅데이터</label></p>
			<p><input type="radio" id="devprogram" name="ownexpertisement" value="응용프로그램개발"><label for="devprogram">응용프로그램개발</label></p>
			<p><input type="radio" id="security" name="ownexpertisement" value="서버,네트워크,보안"><label for="security">서버,네트워크,보안</label></p>
			<p><input type="radio" id="dba" name="ownexpertisement" value="데이터베이스,DBA"><label for="dba">데이터베이스,DBA</label></p>
			<p><input type="radio" id="communication" name="ownexpertisement" value="통신,모바일"><label for="communication">통신,모바일</label></p>
			<p><input type="radio" id="webmaster" name="ownexpertisement" value="웹마스터,QA,테스터"><label for="webmaster">웹마스터,QA,테스터</label></p>
			<p><input type="radio" id="gamedev" name="ownexpertisement" value="게임,Game"><label for="gamedev">게임,Game</label></p>
			<p><input type="radio" id="codec" name="ownexpertisement" value="동영상,편집,코덱"><label for="codec">동영상,편집,코덱</label></p>
		</div>
		<h3>디자인</h3>
		<div>
			<p><input type="radio" id="webdesign" name="ownexpertisement" value="웹디자인"><label for="webdesign">웹디자인</label></p>
			<p><input type="radio" id="graphicdesign" name="ownexpertisement" value="그래픽디자인,CG" ><label for="graphicdesign">그래픽디자인,CG</label></p>
			<p><input type="radio" id="productdesign" name="ownexpertisement" value="제품,산업디자인"><label for="productdesign">제품,산업디자인</label></p>
			<p><input type="radio" id="fashiondesign" name="ownexpertisement" value="의류,패션,잡화디자인"><label for="fashiondesign">의류,패션,잡화디자인</label></p>
			<p><input type="radio" id="advertisedesign" name="ownexpertisement" value="광고,시각디자인"><label for="advertisedesign">광고,시각디자인</label></p>
			<p><input type="radio" id="editdesign" name="ownexpertisement" value="출판,편집디자인"><label for="editdesign">출판,편집디자인</label></p>
			<p><input type="radio" id="spacedesign" name="ownexpertisement" value="전시,공간디자인"><label for="spacedesign">전시,공간디자인</label></p>
			<p><input type="radio" id="designetc" name="ownexpertisement" value="디자인기타"><label for="designetc">디자인기타</label></p>
		</div>
		<h3>서비스</h3>
		<div>
			<p><input type="radio" id="securityservice" name="ownexpertisement" value="보안,경호,안전"><label for="securityservice">보안,경호,안전</label></p>
			<p><input type="radio" id="asservice" name="ownexpertisement" value="AS,서비스,수리"><label for="asservice">AS,서비스,수리</label></p>
			<p><input type="radio" id="hotelservice" name="ownexpertisement" value="호텔,카지노,콘도"><label for="hotelservice">호텔,카지노,콘도</label></p>
			<p><input type="radio" id="lesuresport" name="ownexpertisement" value="레저,스포츠"><label for="lesuresport">레저,스포츠</label></p>
			<p><input type="radio" id="bakery" name="ownexpertisement" value="요리,제빵사,영양사"><label for="bakery">요리,제빵사,영양사</label></p>
			<p><input type="radio" id="guideassistant" name="ownexpertisement" value="안내,도우미,나레이터"><label for="guideassistant">안내,도우미,나레이터</label></p>
			<p><input type="radio" id="washcar" name="ownexpertisement" value="주차,세차,주유"><label for="washcar">주차,세차,주유</label></p>
			<p><input type="radio" id="restaurantfood" name="ownexpertisement" value="외식,식음료"><label for="restaurantfood">외식,식음료</label></p>
			<p><input type="radio" id="travelservice" name="ownexpertisement" value="여행,관광,항공"><label for="travelservice">여행,관광,항공</label></p>
			<p><input type="radio" id="dogservice" name="ownexpertisement" value="미용,피부관리,애견"><label for="dogservice">미용,피부관리,애견</label></p>
			<p><input type="radio" id="babyservice" name="ownexpertisement" value="가사,청소,육아"><label for="babyservice">가사,청소,육아</label></p>
		</div>
		<h3>전문직</h3>
		<div>
			<p><input type="radio" id="consultant" name="ownexpertisement" value="경영분석,컨설턴트" ><label for="consultant">경영분석,컨설턴트</label></p>
			<p><input type="radio" id="analyticsspecialized" name="ownexpertisement" value="설문,통계,리서치"><label for="analyticsspecialized">설문,통계,리서치</label></p>
			<p><input type="radio" id="translation" name="ownexpertisement" value="외국어,번역,통역"><label for="translation">외국어,번역,통역</label></p>
			<p><input type="radio" id="cpa" name="ownexpertisement" value="세무,회계,cpa"><label for="cpa">세무,회계,cpa</label></p>
			<p><input type="radio" id="libraryassistant" name="ownexpertisement" value="도서관사서"><label for="libraryassistant">도서관사서</label></p>
			<p><input type="radio" id="artisticspecialized" name="ownexpertisement" value="문화,예술,종교"><label for="artisticspecialized">문화,예술,종교</label></p>
			<p><input type="radio" id="fundspecialized" name="ownexpertisement" value="증권,투자,펀드,외환"><label for="fundspecialized">증권,투자,펀드,외환</label></p>
			<p><input type="radio" id="headhunting" name="ownexpertisement" value="헤드헌팅,노무,직업상담"><label for="headhunting">헤드헌팅,노무,직업상담</label></p>
			<p><input type="radio" id="lawyerspecialized" name="ownexpertisement" value="법률,특허,상표"><label for="lawyerspecialized">법률,특허,상표</label></p>
			<p><input type="radio" id="randd" name="ownexpertisement" value="연구소,R&D"><label for="randd">연구소,R&D</label></p>
			<p><input type="radio" id="ceospecialized" name="ownexpertisement" value="임원,CEO"><label for="ceospecialized">임원,CEO</label></p>
		</div>
		<h3>의료</h3>
		<div>
			<p><input type="radio" id="doctormedical" name="ownexpertisement" value="의사,치과,한의사" ><label for="doctormedical">의사,치과,한의사</label></p>
			<p><input type="radio" id="pharmacistmedical" name="ownexpertisement" value="약사"><label for="pharmacistmedical">약사</label></p>
			<p><input type="radio" id="doctorassistant" name="ownexpertisement" value="간호조무사"><label for="doctorassistant">간호조무사</label></p>
			<p><input type="radio" id="dogmedical" name="ownexpertisement" value="수의사"><label for="dogmedical">수의사</label></p>
			<p><input type="radio" id="medicaltechnique" name="ownexpertisement" value="의료기사"><label for="medicaltechnique">의료기사</label></p>
		</div>
		<h3>생산,제조</h3>
		<div>
			<p><input type="radio" id="packaing" name="ownexpertisement" value="생산,제조,포장,조립" ><label for="packaing">생산,제조,포장,조립</label></p>
			<p><input type="radio" id="machanicmanufacturing" name="ownexpertisement" value="기계,기계설비"><label for="machanicmanufacturing">기계,기계설비</label></p>
			<p><input type="radio" id="newmaterial" name="ownexpertisement" value="비금속,요업,신소재"><label for="newmaterial">비금속,요업,신소재</label></p>
			<p><input type="radio" id="semiconductor" name="ownexpertisement" value="반도체,디스플레이,LCD"><label for="semiconductor">반도체,디스플레이,LCD</label></p>
			<p><input type="radio" id="cad" name="ownexpertisement" value="설계,CAD,CAM"><label for="cad">설계,CAD,CAM</label></p>
			<p><input type="radio" id="matalmanufacturing" name="ownexpertisement" value="금속,금형"><label for="matalmanufacturing">금속,금형</label></p>
			<p><input type="radio" id="electronicalmanufacturing" name="ownexpertisement" value="전기,전자,제어"><label for="electronicalmanufacturing">전기,전자,제어</label></p>
			<p><input type="radio" id="bioenergy" name="ownexpertisement" value="화학,에너지"><label for="bioenergy">화학,에너지</label></p>
			<p><input type="radio" id="foodmanufacturing" name="ownexpertisement" value="바이오,제약,식품"><label for="foodmanufacturing">바이오,제약,식품</label></p>
			<p><input type="radio" id="lensmanufacturing" name="ownexpertisement" value="안경,렌즈,과학"><label for="lensmanufacturing">안경,렌즈,과학</label></p>
		</div>
		<h3>건설</h3>
		<div>
			<p><input type="radio" id="treebuild" name="ownexpertisement" value="토목,조경,도시,측량" ><label for="treebuild">토목,조경,도시,측량</label></p>
			<p><input type="radio" id="communicationbuild" name="ownexpertisement" value="전기,소방,통신,설비"><label for="communicationbuild">전기,소방,통신,설비</label></p>
			<p><input type="radio" id="housebuild" name="ownexpertisement" value="부동산,개발,경매,분양"><label for="housebuild">부동산,개발,경매,분양</label></p>
			<p><input type="radio" id="interiorbuild" name="ownexpertisement" value="건축,인테리어,설계"><label for="interiorbuild">건축,인테리어,설계</label></p>
			<p><input type="radio" id="plantbuild" name="ownexpertisement" value="환경,플랜트"><label for="plantbuild">환경,플랜트</label></p>
			<p><input type="radio" id="checkbuild" name="ownexpertisement" value="안전,품질,검사,관리"><label for="checkbuild">안전,품질,검사,관리</label></p>
		</div>
		<h3>미디어</h3>
		<div>
			<p><input type="radio" id="pdmedia" name="ownexpertisement" value="방송연출,PD,감독" ><label for="pdmedia">방송연출,PD,감독</label></p>
			<p><input type="radio" id="annouancermedia" name="ownexpertisement" value="진행,아나운서"><label for="annouancermedia">진행,아나운서</label></p>
			<p><input type="radio" id="gizamedia" name="ownexpertisement" value="기자"><label for="gizamedia">기자</label></p>
			<p><input type="radio" id="staffmedia" name="ownexpertisement" value="공연,무대,스탭"><label for="staffmedia">공연,무대,스탭</label></p>
			<p><input type="radio" id="cfmedia" name="ownexpertisement" value="광고,카피,CF"><label for="cfmedia">광고,카피,CF</label></p>
			<p><input type="radio" id="printmedia" name="ownexpertisement" value="인쇄,출판,편집"><label for="printmedia">인쇄,출판,편집</label></p>
			<p><input type="radio" id="cameramedia" name="ownexpertisement" value="카메라,조명,미술"><label for="cameramedia">카메라,조명,미술</label></p>
			<p><input type="radio" id="writermedia" name="ownexpertisement" value="작가,시나리오"><label for="writermedia">작가,시나리오</label></p>
			<p><input type="radio" id="entertainmentmedia" name="ownexpertisement" value="연예,엔터테인먼트"><label for="entertainmentmedia">연예,엔터테인먼트</label></p>
			<p><input type="radio" id="soundmedia" name="ownexpertisement" value="음악,음향,사운드"><label for="soundmedia">음악,음향,사운드</label></p>
			<p><input type="radio" id="photomedia" name="ownexpertisement" value="사진,포토그라퍼"><label for="photomedia">사진,포토그라퍼</label></p>
		</div>
		<h3>교육</h3>
		<div>
			<p><input type="radio" id="childrenedu" name="ownexpertisement" value="유치원,보육" ><label for="childrenedu">유치원,보육</label></p>
			<p><input type="radio" id="visitedu" name="ownexpertisement" value="학습지,과외,방문"><label for="visitedu">학습지,과외,방문</label></p>
			<p><input type="radio" id="professoredu" name="ownexpertisement" value="대학교수,행정직"><label for="professoredu">대학교수,행정직</label></p>
			<p><input type="radio" id="elementaryedu" name="ownexpertisement" value="초등학교"><label for="elementaryedu">초등학교</label></p>
			<p><input type="radio" id="middleedu" name="ownexpertisement" value="중학교"><label for="middleedu">중학교</label></p>
			<p><input type="radio" id="highedu" name="ownexpertisement" value="고등학교"><label for="highedu">고등학교</label></p>
			<p><input type="radio" id="foreigneredu" name="ownexpertisement" value="외국어,어학원"><label for="foreigneredu">외국어,어학원</label></p>
			<p><input type="radio" id="itedu" name="ownexpertisement" value="전문직업,IT강사"><label for="itedu">전문직업,IT강사</label></p>
			<p><input type="radio" id="edubook" name="ownexpertisement" value="교육기획,교재"><label for="edubook">교육기획,교재</label></p>
		</div>
	</div>
	<button class="btn btn-lg btn-primary btn-block" id="registerownexpertisement">전문분야 등록</button>
</div>
<!-- 보유기술등록 -->
<div id="owntechdialog-form" title="보유기술 등록">
	<p>자신있는 자신의 기술을 등록해주세요.</p>
	<label for="owntechnics">보유기술</label>
	<input type="text" name="owntechnics" id="owntechnics" class="ext ui-widget-content ui-corner-all">
	<input type="button" id="registerowntechnicbtn" value="등록"/>
	<br><br>
	<div id="owntechnicscontent">
		<table border="1" width="100%">
			<thead>
				<tr>
					<th>기술</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody id="techbody">
					
			</tbody>
		</table>
	</div>
	<br>
	<button id="finalRegisterBtn" class="btn btn-lg btn-primary btn-block" >보유기술 등록</button>
</div>
<!-- 자격증 등록 -->
<div id="ownlicensedialog-form" title="자격증 등록">
	<p>자신이 가지고 있는 자격증을 등록해주세요.</p>
	<label for="ownlicense">보유자격증</label>
	<input type="text" name="ownlicense" id="ownlicense" class="ext ui-widget-content ui-corner-all"/>
	<input type="button" id="registerownlicensebtn" value="등록"/>
	<br><br>
	<div id="ownlicensescontent">
		<table border="1" width="100%">
			<thead>
				<tr>
					<th>자격증</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody id="licensebody">
					
			</tbody>
		</table>
	</div>
	<br>
	<button id="LicensesRegisterBtn" class="btn btn-lg btn-primary btn-block" >보유기술 등록</button>
</div>
<!-- 수상경력 등록 -->
<div id="ownawarddialog-form" title="수상경력 등록">
	<p>자신의 수상경력을 등록해주세요.</p>
	<label for="myawarded">자신의 수상경력</label>
	<input type="text" name="myawarded" id="myawarded" class="ext ui-widget-content ui-corner-all"/>
	<input type="button" id="registerownawardbtn" value="등록"/>
	<br><br>
	<div id="ownawardcontent">
		<table border="1" width="100%">
			<thead>
				<tr>
					<th>수상경력</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody id="awardedbody">
					
			</tbody>
		</table>
	</div>
	<br>
	<button id="AwardedRegisterBtn" class="btn btn-lg btn-primary btn-block" >수상경력 등록</button>
</div>
<!-- 교육이수 등록 -->
<div id="owneducareerdialog-form" title="교육이수 등록">
	<p>자신의 교육이수 내역을 등록해주세요</p>
	<label for="myeducareer">자신의 교육이수내역</label>
	<input type="text" name="myeducareer" id="myeducareer" class="ext ui-widget-content ui-corner-all"/>
	<input type="button" id="registerownaeducareerbtn" value="등록"/>
	<br><br>
	<div id="owneducareercontent">
	<table border="1" width="100%">
			<thead>
				<tr>
					<th>교육이수내역</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody id="educareerbody">
					
			</tbody>
		</table>
	</div>
	<br>
	<button id="EducareerBtn" class="btn btn-lg btn-primary btn-block" >교육이수내역 등록</button>
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
                <form class="navbar-form navbar-right" method="POST" action="<%=request.getContextPath()%>/baconnetwork/searchpeople.do">
                    <input type="text" class="form-control" name="people" placeholder="검색">
                    <button type="submit" class="btn btn-success">검색</button>
                </form>
            </div>
        </div>
    </nav>

  <div class="container">
      <div class="jumbotron">
          <fieldset>
            <legend>프로필 사진 등록</legend>
              <div class="row">
                <div class="col-xs-6 col-md-3">
                  <div class="thumbnail">
                  <%
                  String profileImagePath = null;
                  if(pe)
                  {
                	  profileImagePath = DataInfo.domain_path+"/memberImageStorage/"+idStr+"/profile/"+profileName;
                  }else
                  {
                	  profileImagePath = request.getContextPath()+"/data/peoplewho.png";
                  }
                  %>
                    <img src=<%=profileImagePath %>>
                  </div>
                </div>
              </div>
              <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
              	  <input type="text" name="userid" value="<%=idStr %>" style="display:none;">
	              <input type="file" name="files"/>
	              <input type="submit" value="프로필 등록" id="btnSubmit"/>
              </form>
        </fieldset>
        <br>
        
        <form action="<%=request.getContextPath() %>/baconnetwork/profiledone.do" method="post">
	       <%
	       if(seekingwork == null)
	       {
        	seekingwork = "재직"; 
	       }
	       %>
	        <fieldset>
	            <legend>현재 상태 : <b id="jobprofile" name="jobsituation"><%=seekingwork %></b></legend>
	              <label class="switch">
	                <input type="checkbox" id="job" name="job" checked>
	                <span class="slider round"></span>
	              </label>
	              <input type="hidden" id="job_data" name="job_data">
	        </fieldset>
	        <%
	        if(seekingwork != null){
	        	if(seekingwork.equals("재직"))
	        	{
	   		%>
	   		<script>
	   			$('#job').prop("checked", true);
	   			$('#job_data').val('재직');
	   		</script>
	   		<% 
	        	}else if(seekingwork.equals("휴직"))
	        	{
	    	%>
	   		<script>
	   			$('#job').prop("checked", false);
	   			$('#job_data').val('휴직');
	   		</script>    	
	    	<% 
	        	}
	        }
	        %>
	        <br>
	        <%
	        if(lastcompany == null)
	        {
	        	lastcompany = "";
	        }
	        %>
	        <fieldset>
	            <legend>현재 근무 중인 회사</legend>
	            	<div id="registeredcompanyname" name="registeredcompanyname"><%=lastcompany %></div>
	            	<input type="hidden" id="companyname_data" name="companyname_data"/>
	            	<!-- <div id="registeredcompanyaddress" name="registeredcompanyaddress"></div> -->
	            	<input type="hidden" id="companyaddress_data" name="companyaddress_data"/>
	              <button type="button" id="registercompany">현재 회사 등록</button>
	        </fieldset>
	        <br>
	        <%
	        if(highestschooling == null)
	        {
	        	highestschooling = "";
	        }
	        %>
	        <fieldset>
	            <legend>학력 사항</legend>
	              <!-- <div id="registerunivkind" name="registerunivkind"></div> -->
	              <input type="hidden" id="univkind_data" name="univkind_data"/>
	              
	              <div id="registerunivname" name="registerunivname"><%=highestschooling %></div>
	              <input type="hidden" id="univname_data" name="univname_data"/>
	              
	              <!-- <div id="registerunivbranch" name="registerunivbranch"></div> -->
	              <input type="hidden" id="univbranch_data" name="univbranch_data"/>
	              
	              <input type="hidden" id="univestablishment" name="univestablishment"/>
	              <!-- <div id="registerunivaddress" name="registerunivaddress"></div> -->
	              <input type="hidden" id="univaddress_data" name="univaddress_data"/>
	              
	              <button type="button" id="registerSchool">학교 등록</button>  
	        </fieldset>
	        <br>
	        <fieldset>
	            <legend>전문성을 강조할 수 있는 보유기술</legend>
	              <button type="button" id="registerowntech">보유 기술 등록</button>
	              <div id="owntechforexpertise">
	              	<% 
	              	if(EXPERTISETECHNICS != null)
	              	{
	              		for(int i=0; i<EXPERTISETECHNICS.length; i++)
	              		{
	              	%>
	              	보유기술 <%=i+1 %>
	              	<p><%=EXPERTISETECHNICS[i] %></p>
	              	<%		
	              		}
	              	}
	              	%>
	              </div>
	        </fieldset>
	        <br>
	        <%
	        if(expertise == null)
	        {
	        	expertise = "";
	        }
	        %>
	        <fieldset>
	            <legend>전문분야 혹은 관심사</legend>
	            <div id="ownexpertise" name="ownexpertise"><%=expertise %></div>
	            <input type="hidden" id="expertise_data" name="expertise_data"/>
	              <button type="button" id="registerownexpertise">전문분야 등록</button>
	        </fieldset>
	        <br>
	        <fieldset>
	            <legend>보유하고 있는 자격증</legend>
	            <button type="button" id="registerlicense">자격증 등록</button>
	            <div id="ownlicenses" name="ownlicenses">
	            <% 
			      if(LICENSES != null)
			      {
			        for(int i=0; i<LICENSES.length; i++)
			        {
			      %>
			      보유자격증 <%=i+1 %>
			      <p><%=LICENSES[i] %></p>
			      <%    
			        }
			      }
			      %>
			     </div>
	        </fieldset>
	        <br>
	        <fieldset>
	            <legend>수상 경력</legend>
	            <button type="button" id="registeraward">수상 경력 등록</button>
	            <div id="ownawarded" name="ownawarded">
	            <% 
			      if(AWARDEDCAREERS != null)
			      {
			        for(int i=0; i<AWARDEDCAREERS.length; i++)
			        {
			      %>
			      수상경력 <%=i+1 %>
			      <p><%=AWARDEDCAREERS[i] %></p>
			      <%    
			        }
			      }
			      %>
			     </div>
	        </fieldset>
	        <br>
	        <fieldset>
	            <legend>교육 이수 내역</legend>
	            <button type="button" id="registereducareer">교육 이수 등록</button>
	            <div id="owneducareer" name="owneducareer">
	            <% 
			      if(EDUCATIONS != null)
			      {
			        for(int i=0; i<EDUCATIONS.length; i++)
			        {
			      %>
			      교육이수내역 <%=i+1 %>
			      <p><%=EDUCATIONS[i] %></p>
			      <%    
			        }
			      }
			      %>
			     </div>
	        </fieldset>
	        <br/><br/><br/><br/>
	        <input type="submit" class="btn btn-lg btn-primary btn-block" value="저장">
        </form>
      </div>
    </div>
 <%}else{ %>
 <h1 align="center">그러한 접근은 불가능 합니다.</h1>
 <p align="center"><a href="<%=request.getContextPath()%>/baconnetwork.main">로그인이 필요합니다.</a></p>
 <%} %>
</body>
</html>