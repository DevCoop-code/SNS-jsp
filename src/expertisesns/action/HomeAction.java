package expertisesns.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;
import expertisesns.data.DataInfo;
import expertisesns.eachfollow.SearchEachFollow;
import expertisesns.model.AlarmDao;
import expertisesns.model.FollowDao;
import expertisesns.model.FollowDto;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class HomeAction implements CommandAction
{
	AlarmDao alarmdao = null;
	ProfileDao profiledao = null;
	MemberDao memberdao = null;
	
	FollowDao followdao = null;
	
	FollowDto followdto = null;
	
	String email = null;
	
	HttpSession session = null;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		session = request.getSession();
			
		String email = (String)session.getAttribute("id");
		
		if(email != null)
		{
			//System.out.println("email : " + email);
			
			alarmdao = AlarmDao.getInstance();
			profiledao = ProfileDao.getInstance();
			followdao = FollowDao.getInstance();
			memberdao = MemberDao.getInstance();
			
			int receiver_seq = profiledao.getSeq(email);
			
			//알람 갯수를 jsp 페이지로 넘긴다 
			request.setAttribute("uncheckingalarm", UnCheckingAlarm.uncheckingAlarmNum(email));
						
			//맞팔로우 데이터를 jsp 페이지로 넘긴다.
			List<FollowDto> Follow_data_list = SearchEachFollow.searchFollow(email);
			request.setAttribute("follow_data", Follow_data_list);
			
			//팔로워수와 팔로잉 수 갱신
			session.setAttribute("follower", memberdao.getFollower(email));
			session.setAttribute("following", memberdao.getFollowing(email));
			
			//프로필 이미지 경로를 넘기기
			String profilepath = DataInfo.getProfileURL(email);
			
			request.setAttribute("profilepath", profilepath);
		}
		return "/main/MainScreen.jsp";
	}
}
