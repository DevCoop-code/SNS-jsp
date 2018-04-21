<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="expertisesns.data.DataInfo" %>
    <%
    String email = null;
    String name = null;
    int follower = 0;
    int following = 0;
    int boardnum = 0;
    
    String profilepath = null;
    
    int uncheckingalarm = 0;
    
    if(session.getAttribute("id") != null)
    {
    	email = (String)session.getAttribute("id");
    	name = (String)session.getAttribute("name");
    	follower = (int)session.getAttribute("follower");
    	following = (int)session.getAttribute("following");
    	boardnum = (int)session.getAttribute("boardnum");	
    	
    	profilepath = (String)request.getAttribute("profilepath");
    	
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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
function getThumbnailPreview(html, $target)
{
	if(html.files && html.files[0])
	{
		var reader = new FileReader();
		reader.onload = function(e)
		{
			$target.css('display', '');
			$target.html('<button type="button" id="delete_btn" value="삭제 style="float:right;"/>');
			$target.html('<img src="'+e.target.result+'" border="0" class="img-thumbnail" id="uploadimage" alt="Responsive image" />');
		
			$('#uploadimage').on('click', function(){
				var i = confirm("삭제 하시겠습니까?");
				//삭제
				if(i)
				{
					$('#uploadimage').remove();
				}else	//삭제하지 않음
				{
				
				}
			});	
		}
		reader.readAsDataURL(html.files[0]);
	}
}

function LikesEvent(node)
{
	//글 번호
	var contentseq = $(node).parent().children('#content_seq').val();
	//좋아요 갯수
	var likenum = $(node).parent().children('like').html();
	
	var allData = {"content_seq":contentseq, "member":'<%=email%>'};
	
	$.ajax({
		type:'POST',
		url: '<%=DataInfo.domain_path%>'+'/updatelikes',
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
			}
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	//alert(likenum);
}

function deleteEvent(node)
{
	var contentseq = $(node).parent().children('#content_seq').val();
	
	allData = {"contentseq": contentseq};
	
	$.ajax({
		type: 'POST',
		url: '<%=DataInfo.domain_path%>'+'/deleteContent',
		data: allData,
		success: function(data)
		{
			if(data == 'success')
				alert('글 삭제가 성공적으로 이뤄졌습니다.');
			$(node).parent().remove();
		},
		error: function(request, status, error)
		{
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
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
		
		//스크롤바가 아래 화면 까지 내려감
		if(scrollHeight == documentHeight)
		{
			start+=5;
			//console.log('start : '+start);
			getContent(start);
		}
	});
	//글 가져와서 표시하기
	function getContent(start)
	{
		var domain = "<%=DataInfo.domain_path%>";
		$.ajax({
			type: "GET",
			url: domain+'/myajaxcontent?start='+start,
			dataType: "json",
			success: function(data)
			{
				console.log(data);
				var content_dataset = data.results;
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
					var like_data = '좋아요 : <like>' + content_dataset[i].likes + '</like><br>';
					var like_btn = '<button type="button" id="like_btn" class="btn btn-primary" onclick="LikesEvent(this);" style="margin-right:10px">좋아요</button>';
					var comment_btn = '<button type="button" id="commend_btn" class="btn btn-primary" style="margin-right:10px">댓글</button>';
					var share_btn = '<button type="button" id="share_btn" class="btn btn-primary" style="margin-right:10px">공유</button>';
					var delete_btn = '<button type="button" id="share_btn" class="btn btn-danger" onclick="deleteEvent(this)" style="margin-right:10px">삭제</button>';
					var content_seq = '<input type="hidden" name="content_seq" id="content_seq" value = "' + content_dataset[i].seq + '"/>';
					var image_data = ' ';
					
					var image_array = content_dataset[i].imageurl;
					for(var j=0; j<image_array.length; j++)
					{
						image_data += '<img src="'+image_array[j] +'" border="0" class="img-thumbnail" id="uploadimage" alt="Responsive image" />'
					}
					$('.form-group').last().after(first_div)
					$('.form-group').last().prepend(profile_data, name_data, career_data, date_data, divide, content_data, image_data, divide, like_data, like_btn, comment_btn, share_btn, delete_btn, content_seq);
				}
				//start = total_dataset;
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
	
	//처음 실행시 한번만 실행하게 하기 위함 
	if(flag)
	{
		getContent(start);
		flag = false;
	}
	
	$('#contentSubmit').click(function(event){
		
		event.preventDefault();
		
		var form = $('#imageupload')[0];
		
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

<div class="container-fluid" style="margin-top: 30px;">
    <div class="row">
    	<!--사이드 바 부분-->
        <div class="col-sm-3" style="padding:30px; border: 1px solid black; margin:20px">
            <div class="nav nav-sidebar">
            	<p align="center"><img src="<%=profilepath %>" width="100" height="100"/></p>
                <p align="center"><%=name %> 회원님</p>
            </div>
            <hr/>
            <div class="nav nav-sidebar">
                <p align="center"><a href="<%=request.getContextPath()%>/baconnetwork/profile.do">전체 프로필 보기</a></p>
            </div>
        </div>
        <!-- 본문 -->
		<!-- 글쓰기 부분 -->
		<div class="col-sm-6">
			<div class="form-group">
				<form method="POST" action="<%=DataInfo.domain_path%>"+"/uploadsnscontent" enctype="multipart/form-data" id="imageupload">
					<textarea class="form-control" rows="5" id="comment" name="comment" placeholder="<%=name %>님 무슨 생각을 하고 계시나요?"></textarea>
					<div id="cma_image" style="width:100%; max-width:100%;border:1px solid #000;display:none;">
					
					</div>
					<hr/>
					<label class="btn btn-default btn-file">
						이미지<input type="file" name="cma_file" id="cma_file" accept="image/*" capture="camera" onchange="getThumbnailPreview(this,$('#cma_image'))" style="display: none;"/>
					</label>
					<div style="float: right;">
						<input type="submit" value="올리기" id="contentSubmit" class="btn btn-primary"/>
					</div>
				</form>
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