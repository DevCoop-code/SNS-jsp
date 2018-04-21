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

public class GetFollowingListAction implements CommandAction
{
	ProfileDao profiledao = null;
	FollowDao followdao = null;
	MemberDao memberdao = null;
	
	PeopleInfoDto peopleinfodto = null;
	
	//내가 팔로잉한 사람들의 정보 모음(seq, 이름, 전문분야, 학교, 회사)
	List<PeopleInfoDto> following_data_list = null;
	
	//내가 팔로잉한 사람들의 seq값 모음
	List<Integer> following_seq_list = null;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session = request.getSession();
		
		profiledao = ProfileDao.getInstance();
		followdao = FollowDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		String my_id = (String)session.getAttribute("id");
		
		if(my_id != null)
		{
			int my_seq = profiledao.getSeq(my_id);
			
			following_data_list = new ArrayList<PeopleInfoDto>();
			following_seq_list = followdao.getFollowingSeq(my_seq);
			
			Iterator<Integer> following_seq_iter = following_seq_list.iterator();
			while(following_seq_iter.hasNext())
			{
				int following_seq_value = following_seq_iter.next();
				
				//내가 팔로잉 하는 사람들의 seq값으로 id값을 가져옴
				String following_id = memberdao.getIdFromSeq(following_seq_value);
				
				peopleinfodto = memberdao.getMyInfo(following_id);
				
				following_data_list.add(peopleinfodto);
			}
			
			request.setAttribute("following_data", following_data_list);
			
			session.setAttribute("follower", memberdao.getFollower(my_id));
			session.setAttribute("following", memberdao.getFollowing(my_id));	
			
			int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(my_id);

			//알람 갯수를 jsp 페이지로 넘긴다 
			request.setAttribute("uncheckingalarm", alarm_num);
		}
		return "/main/MyFollowing_list.jsp";
	}
}
