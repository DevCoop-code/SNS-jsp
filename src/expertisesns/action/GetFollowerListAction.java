package expertisesns.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;
import expertisesns.model.FollowDao;
import expertisesns.model.MemberDao;
import expertisesns.model.PeopleInfoDto;
import expertisesns.model.ProfileDao;

public class GetFollowerListAction implements CommandAction
{
	ProfileDao profiledao = null;
	FollowDao followdao = null;
	MemberDao memberdao = null;
	
	PeopleInfoDto peopleinfodto = null;
	
	//나를 팔로우하는 사람들의 정보 모음(seq, 이름, 전문분야, 학교, 회사)
	List<PeopleInfoDto> follower_data_list = null;
	
	//나를 팔로우 하는 사람의 seq값 모음
	List<Integer> follower_seq_list = null;
	
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
		
		HttpSession session = request.getSession();
		
		profiledao = ProfileDao.getInstance();
		followdao = FollowDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		String my_id = (String)session.getAttribute("id");
		
		if(my_id != null)
		{
			int my_seq = profiledao.getSeq(my_id);
			
			follower_data_list = new ArrayList<PeopleInfoDto>();
			
			follower_seq_list = followdao.getFollowerSeq(my_seq);
			
			Iterator<Integer> follower_seq_iter = follower_seq_list.iterator();
			while(follower_seq_iter.hasNext())
			{
				int follower_seq_value = follower_seq_iter.next();
				
				//팔로워들의 seq를 가지고 id값을 가져와야 함
				String follower_id = memberdao.getIdFromSeq(follower_seq_value);
				
				peopleinfodto = memberdao.getMyInfo(follower_id);
				
				follower_data_list.add(peopleinfodto);
			}
			
			request.setAttribute("follower_data", follower_data_list);
			
			session.setAttribute("follower", memberdao.getFollower(my_id));
			session.setAttribute("following", memberdao.getFollowing(my_id));	
			
			int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(my_id);

			//알람 갯수를 jsp 페이지로 넘긴다 
			request.setAttribute("uncheckingalarm", alarm_num);
		}
		
		return "/main/MyFollower_list.jsp";
	}
}
