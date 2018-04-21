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

window.fbAsyncInit = function() {
	  FB.init({
	    appId      : '382126328848340',
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
	
function logoutmethod()
{
	console.log('facebook logout');
	FB.logout(function(response){
		
	});	
}