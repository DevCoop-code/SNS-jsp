<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    	List<FollowDto> follow_data_list = null;
    	String profilepath = null;
    	if(session.getAttribute("id") != null)
    	{
    		email = (String)session.getAttribute("id");	
    		name = (String)session.getAttribute("name");
        	follower = (int)session.getAttribute("follower");
        	following = (int)session.getAttribute("following");
        	boardnum = (int)session.getAttribute("boardnum");
        	
        	uncheckingalarm = (int)request.getAttribute("uncheckingalarm");
        	
        	follow_data_list = (List<FollowDto>)request.getAttribute("follow_data");
        	
        	profilepath = (String)request.getAttribute("profilepath");
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
<title>Bacon Network</title>
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
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Facebook 로그아웃 -->
<script src="<%=request.getContextPath()%>/js/facebooklogout.js"></script>
<%if(login){ %>
<style>
#align
{
	display:inline;
}
</style>
<script type="text/javascript">
//chat.jsp페이지 띄우는 스크립트
var preid;
function move_box(an, box) 
{
	//링크된 위치에서 부터의 설정값 지정
  var cleft = -110;  //왼쪽마진  
  var ctop = 230;  //상단마진
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
.form-group
{
	border: 1px solid black;
	padding: 10px;
	margin: 20px;
}
#comment_form
{
	background-color: #EAEAEA;
}
#comment_font
{
	font-size:0.8em;
}
</style>
<script>
//이미지 업로드시 이미지 미리보기 기능
function getThumbnailPreview(html, $target)
{
	if(html.files && html.files[0])
	{
		var reader = new FileReader();
		reader.onload = function(e)
		{
			$target.css('display', '');
			$target.html('<img src="'+e.target.result+'" border="0" class="img-thumbnail" id="uploadimage" alt="Responsive image" />');
		
			$('#uploadimage').on('click', function(){
				var i = confirm("삭제 하시겠습니까?");
				//삭제
				if(i)
				{
					$('#uploadimage').remove();
					$target.css('display', 'none');
				}else	//삭제하지 않음
				{
				
				}
			});	
		}
		reader.readAsDataURL(html.files[0]);
	}
}

//당사자에게 알림을 주기 위한 이벤트
function AlarmEvent(contentsequence, alarmtype_data)
{
	//alarm_data = {게시 글번호, 알림을 보내는 사람 아이디,타입}
	//게시 글 번호는 알람을 보내야 할 당사자를 알기 위해서 보내는 데이터
	var alarm_data = {"content_seq": contentsequence, "member": '<%=email%>', "type": alarmtype_data, "alarm_type":"a"};
	//좋아요시 당사자에게 알림을 주기 위한 이벤트 
	var domain = "<%=DataInfo.domain_path%>";
	$.ajax({
		type:'POST',
		url: domain+'/updatealarm',
		data: alarm_data,
		success: function(data)
		{
			console.log('좋아요 이벤트 : ' + data);
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

//좋아요 버튼 눌렀을 때 이벤트
function LikesEvent(node)
{
	//글 번호
	var contentseq = $(node).parent().children('#content_seq').val();
	//좋아요 갯수
	var likenum = $(node).parent().children('like').html();
	
	var allData = {"content_seq":contentseq, "member":'<%=email%>'};
	
	var domain = "<%=DataInfo.domain_path%>";
	//좋아요 갯수 올리는 이벤트
	$.ajax({
		type:'POST',
		url: domain+'/updatelikes',
		data: allData,
		success: function(data)
		{
			if(data == 'exits')
			{
				alert('좋아요를 눌렀던 글입니다.');
			}else if(data == 'success')
			{
				likenum++;
				//좋아요 갯수 올리기
				$(node).parent().children('like').html(likenum);
				
				//좋아요 알림 이벤트 발생
				AlarmEvent(contentseq, 'B');
			}
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	//alert(likenum);
}

//댓글 이벤트 
function CommentEvent(node)
{
	var commenter_id = '<%=email%>';
	var content_seq = $(node).parent().children('#content_seq').val();
	var comment = $(node).parent().children('#comment_content').val();
	
	if(comment != '')
		AlarmEvent(content_seq, 'C');
	
	var allData = {"commenter_id":commenter_id, "content_seq":content_seq, "comment":comment};
	
	var domain = "<%=DataInfo.domain_path%>";
	$.ajax({
		type:'POST',
		url: domain+'/updatecomment',
		data: allData,
		dataType: "json",
		success: function(data)
		{
			var comment_data = data.results;
			
			$(node).parent().children('#comment_form').children('#comment_template').each(function(index){
				$(this).remove();	
			});
			
			for(var i=0; i<comment_data.length; i++)
			{
				var comment_seq = data.results[i].comment_seq;
				var commenter_seq = data.results[i].commenter_seq;
				var commenter_name = data.results[i].commenter_name;
				var today_date = data.results[i].today_date;
				var comment = data.results[i].comments;
				
				var comment_template = '<div id="comment_template"></div>';
				
				var comment_seq_template = '<input type="hidden" value="'+comment_seq+'">';
				var commenter_seq_template = '<input type="hidden" value="'+commenter_seq+'">';
				var commenter_name_template = '<p id="comment_font">'+'<b>' + commenter_name + '</b>' +' : ' + comment + '</p>';
				var today_date_template = '<p id="comment_font" style="text-align: right">'+today_date+'</p>';
				var divide = '<hr/>';
				
				$(node).parent().children('#comment_form').append(comment_template);
				$(node).parent().children('#comment_form').children('#comment_template').last().append(comment_seq_template, commenter_seq_template, commenter_name_template, today_date_template, divide);
				
			}
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	$(node).parent().children('#comment_content').val('');
	//alert('commenter_id : '+commenter_id + ' content_seq : '+content_seq+' comment : '+comment);
}
</script>
<script>
$(document).ready(function(){
	var flag = true;
	//메인화면이 로딩되자마자 글들을 불러오기
	var start = 5;
	var totalStorage = 0;
	
	$(window).scroll(function(){
		var scrollHeight = $(window).scrollTop() + $(window).height();
		var documentHeight = $(document).height();
		
		//스크롤바가 아래 화면까지 내려감
		if(scrollHeight == documentHeight)
		{
			start += 5;
			getContent(start);
		}
	});
	//글 가져와서 표시하기
	function getContent(start)
	{
		var domain = "<%=DataInfo.domain_path%>";
		$.ajax({
			type: "GET",
			url: domain +'/ajaxcontent?start='+start,
			dataType: "json",
			success: function(data)
			{
				console.log(data);
				var content_dataset = data.results;
				//전체 글 개수 가지고 오기
				var total_dataset = parseInt(data.total);
				
				//기존의 글 삭제
				$('.col-sm-6').children('#contents').each(function(index){
					$(this).remove();
				});
				
				for(var i=0; i<content_dataset.length; i++)
				{
					var first_div = '<div id="contents" class="form-group"></div>';
					var profile_data = '<img src="'+content_dataset[i].profileurl+'" width="50" height="50" align="left" vspace="0" hspace="5">';
					var name_data = '<p>이름 : ' + content_dataset[i].name + '</p>';
					var career_data = '<p>경력 : ' + content_dataset[i].career + '</p>';
					var date_data = '<p>날짜 : ' + content_dataset[i].date + '</p>';
					var divide = '<hr/>';
					var content_data = '<p>'+content_dataset[i].content+'</p>';
					var image_data = ' ';
					var video_data = ' ';
					var like_data = '좋아요 : <like>' + content_dataset[i].likes + '</like><br>';
					var like_btn = '<button type="button" id="like_btn" class="btn btn-primary" onclick="LikesEvent(this);" style="margin-right:10px">좋아요</button>';
					var comment_btn = '<button type="button" id="comment_btn" class="btn btn-primary" onclick="CommentEvent(this)" style="margin-right:10px">댓글</a>';
					var share_btn = '<button type="button" id="share_btn" class="btn btn-primary">공유</button>';
					var content_seq = '<input type="hidden" name="content_seq" id="content_seq" value = "' + content_dataset[i].seq + '"/>';
					var comment_form='<div id="comment_form"></div>';
					var comment_data = '<input type="text" id="comment_content" placeholder="댓글을 입력하세요..." style="width:80%">';
					var upload_comment = '<button type="button" id="uploadComment" class="btn btn-primary" onclick="CommentEvent(this)" style="margin-left:10px">댓글달기</a>';
					
					//여러 이미지 처리
					var image_array = content_dataset[i].imageurl;
					for(var j=0; j<image_array.length; j++)
					{
						image_data += '<img src="'+image_array[j] +'" width="100%" height="300" border="0" class="img-thumbnail" id="uploadimage" alt="Responsive image" />'
					}
					
					//여러 유튜브 동영상 처리
					var video_array = content_dataset[i].youtubeids;
					for(var j=0; j<video_array.length; j++)
					{
						video_data += '<iframe width="100%" height="300" src="https://www.youtube.com/embed/'+video_array[j] +'" frameborder="0" allowfullscreen></iframe>';
					}
					$('.form-group').last().after(first_div);
					$('.form-group').last().prepend(profile_data, name_data, career_data, date_data, divide, content_data, image_data, video_data, divide, like_data, like_btn, comment_btn, share_btn, content_seq, divide, comment_form, comment_data, upload_comment);
				}
				trans(total_dataset);
			},
			error: function(request, status, error)
			{
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	function trans(total_dataset)
	{
		start = total_dataset;
	}
	
	if(flag)
	{
		getContent(start);
		flag = false;
	}
	
	$('#contentSubmit').click(function(event){
		
		event.preventDefault();
		
		var form = $('#uploadContent')[0];
		
		var data = new FormData(form);
		
		var domain = "<%=DataInfo.domain_path%>";
		
		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: domain+'/uploadsnscontent',
			data: data,
			processData: false,
			contentType: false,
			cache: false,
			success: function(data)
			{
				//console.log('ajax Success : '+data);
				$('#uploadimage').remove();
				$('#comment').val('');
				//업로드시 기존 유튜브 업로드 썸네일 제거
				$('#youtube_thumb').html('');
				getContent(start);
				
			},
			error:function(request, status, error)
			{
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				$('#uploadimage').remove();
				$('#comment').val('');
			}
		});
	});
	
	//유튜브 이벤트
	$('#youtubeSearch').on({
		mousedown: function(event)
		{
			//기존 이벤트 삭제
			event.preventDefault();
			
			$('#youtubeContentBody').text('');
			
			$('#youtube_title').html('키워드 검색');
			$('#youtubeSearchBtn').val('검색');
		}
	});
	$('#youtubeSource').on({
		mousedown: function(event)
		{
			//기존 이벤트 삭제
			event.preventDefault();
			
			$('#youtubeContentBody').text('');
			
			$('#youtube_title').html('YouTube URL 첨부');
			$('#youtubeSearchBtn').val('첨부');
		}
	});
	
	$('#youtubeSearchBtn').click(function(event){
		
		event.preventDefault();
		event.stopPropagation();
		
		$('#youtube_content').remove();
		
		//전체 document 전부 제거(이걸 안하면 중복 이벤트 발생하게 됨);
		$(document).off();
		
		var domain = '<%=DataInfo.domain_path%>';
		var keyword = $('#youtube_query_keyword').val();
		
		$.ajax({
			type: 'GET',
			url: domain+'/youtubeData?keyword='+keyword,
			dataType: "json",
			success: function(data)
			{
				//console.log(data);
				var youtube_data_list = data.results;
				for(var i=0; i<youtube_data_list.length; i++)
				{
					//console.log('id : ' + youtube_data_list[i].videoID);
					//console.log('title : ' + youtube_data_list[i].videoTitle);
					//console.log('thumbnail : ' + youtube_data_list[i].videoThumbnail);
					
					$('#youtubeContentBody').prepend('<tr id="youtube_content"><td id="youtube_thumbnail"><img src="'+youtube_data_list[i].videoThumbnail+'"></td>'+'<td id="youtube_title">'+youtube_data_list[i].videoTitle+'</td>'+'<td id="youtube_id"><input type="hidden" value="'+youtube_data_list[i].videoID+'"></td></tr>');
				}
				
				$(document).on('mouseover', '#youtube_content', function(){
					$(this).css('background-color','#6799FF');
				});
				$(document).on('mouseleave', '#youtube_content', function(){
					$(this).css('background-color','white');
				});
				$(document).one('click', '#youtube_content', function(){
					var thumb = $(this).children('#youtube_thumbnail').children('img').attr('src');
					var id = $(this).children('#youtube_id').children('input').val();
					
					$(this).css('background-color','#6799FF');
					$(this).attr('id','checked_content');
					
					alert('clicked');
					
					$('#youtube_thumb').append('<div id="align"><img src="'+thumb+'" name="selected_thumbnail"><input type="hidden" value="' + id + '" name="selected_id"></div>');
				});
				$(document).on('click', '#checked_content', function(){
					var id = $(this).children('#youtube_id').children('input').val();
					
					$(this).css('background-color', 'white');
					$(this).attr('id', '#youtube_content');
					
					$('input[value="'+id+'"]').parent().remove();
				});
			},
			error: function(request, status, error)
			{
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});
});

</script>
</head>
<body>
<!-- 유튜브 검색 다이얼로그 -->
<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">유튜브 검색</h4>
			</div>
			<div class="modal-body">
				<div class="card text-center">
					<div class="card-header">
						<ul class="nav nav-tabs card-header-tabs">
							<li class="nav-item">
								<a class="nav-link active" id="youtubeSearch" href="#">검색</a>
							</li>
							<li class="nav-item">
								<a class="nav-link active" id="youtubeSource" href="#">소스코드첨부</a>
							</li>
						</ul>
					</div>
					<div class="cardbody">
						<h4 class="card-title" id="youtube_title">키워드 검색</h4>
						<input type="text" name="youtubeSearch" id="youtube_query_keyword" class="ext ui-widget-content ui-corner-all"/>
						<input type="button" id="youtubeSearchBtn" class="btn btn-primary" value="검색"/>
					</div>
				</div>
			</div>
			<div id="youtubeVideocontent">
				<table border="0.5" width="100%">
					<thead>
						<tr>
							<th>이미지</th>
							<th>이름</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="youtubeContentBody">
					
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
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
		<!-- 글쓰기 부분 -->
		<div class="col-sm-6">
			<div class="form-group">
				<form method="POST" action="<%=DataInfo.domain_path%>"+"/uploadsnscontent" enctype="multipart/form-data" id="uploadContent">
					<textarea class="form-control" rows="5" id="comment" name="comment" placeholder="<%=name %>님 무슨 생각을 하고 계시나요?"></textarea>
					<div id="cma_image" style="width:100%; max-width:100%;">
					
					</div>
					<div id="youtube_thumb" style="width:100%; max-width:100%;">
					
					</div>
					<hr/>
					<label class="btn btn-default btn-file">
						이미지<input type="file" name="cma_file" id="cma_file" accept="image/*" capture="camera" onchange="getThumbnailPreview(this,$('#cma_image'))" style="display: none;"/>
					</label>
					
					<button type="button" class="btn btn-default btn-file" data-toggle="modal" data-target="#myModal">유튜브 검색</button>
					
					<div style="float: right;">
						<input type="submit" value="올리기" id="contentSubmit" class="btn btn-primary"/>
					</div>
				</form>
			</div>
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