<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="expertisesns.data.DataInfo" %>
<%
     String userID = null;
     if(session.getAttribute("id")!=null)
     {
    	 userID=(String)session.getAttribute("id");
     }
     
     String toID = (String)request.getParameter("toID");
     %>
<!DOCTYPE html>
<html>
<head>
 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="vewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/custom.css"/>
<title>채팅</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>

<script type="text/javascript">
    function submitFunction()
    {
    	var fromID = '<%= userID %>'
    	var toID = '<%= toID %>'
    	var chatContent=$('#chatContent').val();
    	$.ajax({
    		type: "POST",
    		url: '<%=DataInfo.domain_path%>'+'/chatSubmitServlet',
    		data: {
    			fromID: encodeURIComponent(fromID),
    			toID: encodeURIComponent(toID),
    			chatContent: encodeURIComponent(chatContent),
    		},    	
    	});
    	$('#chatContent').val('');
    }
    
    var lastID=0;
    function chatListFunction(type)
    {
    	var fromID ='<%= userID %>';
    	var toID ='<%= toID %>';
    	
    	var domain = "<%=DataInfo.domain_path%>";
    	$.ajax({
    		type: "POST",
    		url: domain+'/chatListServlet',
	    	data: {
	    		fromID: encodeURIComponent(fromID),
	    		toID: encodeURIComponent(toID),
	    		listType: type
	    	},
	    	success:function(data){
	    		if(data=="") return;
	    		var parsed = JSON.parse(data);
	    		var result = parsed.result;
	    		for(var i= 0; i < result.length; i++){
	    			if(result[i][0].value == fromID){
	    				result[i][1].value = '나';
	    			}
	    			addChat(result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value);
	    		}
	    		lastID = Number(parsed.last);
	    	}
    	});
    }
    
    function addChat(chatName, chatContent, chatTime, profilepath)
    {
    	$('#chatList').append('<div class-row">'+
      	      '<div class="col-lg-12">' +
      	      '<div class="media">' +  
      	      '<a class="pull-left" href="#">' +   //추가
      	      '<img class="media-object img-circle" style="width: 30px; height: 30px;" src="'+profilepath+'" alt="">' +  //추가
      	      '</a>' +
      	      '<div class="media-body">' +
      	      '<h5 class="media-heading">' +
      	      chatName + 
      	      '<span class="small pull-right">'+
      	      chatTime +
      	      '</span>' +
      	      '</h5>' +
      	      '<p>' +
      	      chatContent +
      	      '</p>' +
      	      '</div>' +
      	      '</div>' +
      	      '</div>' +
      	      '</div>' +
      	      '<hr>');
      	$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
    }
    
    function getInfiniteChat()
    {
	   	setInterval(function(){
	   		chatListFunction(lastID);
	       	}, 1000);
    }
    //엔터키로 전송보내기, 줄바꿈 쉬프트+엔터키
    $(function()
	{
    	$('textarea').on('keydown', function(event)
		{
	   		if(event.keyCode==13)
	   		{
	   			if(!event.shiftKey)
	   			{
	   				event.preventDefault();
	   				var button=document.getElementById('enter');
	   				
	   				button.click();
	   				$('textarea').empty()
   				}
	   		}
    	});
    });
</script>
</head>
<body>     
<input type="button" class="close" value="X" onclick="parent.hide();">
<div class="container bootstrap snippet">        
	<div class="row">
			<div class="col-xs-14">
			<div class="portlet portlet-default">         
				<div class="portlet-heading">             
					<span>채팅창</span>
					<div class="clearfix">
					
					</div>
				</div>
				<div id="chat" class="panel-collapse collapse in">
					<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto; width:auto; height: 220px;">

					</div>
				<div class="portlet-footer">                   
					<div class="row" style="height: 30px;">
						<div class="form-group col-xs-10">
							<textarea style="height:30px; resize:none;" id="chatContent" class="form-control" placeholder="메시지를 입력하세요." maxlength="50" ></textarea>
						</div>
						<div class="form-group col-xs-2">
						<button type="button" class="btn btn-default pull-right" id="enter" onclick="submitFunction();">전송</button>
							<div class="clearfix">
							
							</div>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
  $(document).ready(function(){
 	 chatListFunction('ten');
 	 getInfiniteChat();
  });
</script>
</body>
</html>