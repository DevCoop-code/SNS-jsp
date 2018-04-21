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
import expertisesns.model.FollowDto;
import expertisesns.model.MemberDao;
import expertisesns.model.PeopleInfoDto;
import expertisesns.model.PersonGrade;

public class HumanConnectionAction implements CommandAction
{
	MemberDao memberdao = null;
	
	String id = null;
	
	PeopleInfoDto peopleinfodto = null;
	PersonGrade persongrade = null;
	
	List<PersonGrade> persongrade_list = null;
	
	List<PersonGrade> scored_people_list = null;
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		
		HttpSession session = request.getSession();
		
		id = (String)session.getAttribute("id");
		if(id != null)
		{
			scored_people_list = new ArrayList<PersonGrade>();
			
			memberdao = MemberDao.getInstance();
			
			persongrade_list = memberdao.getMemberWithEssentialOption();
			peopleinfodto = memberdao.getMyInfo(id);
			
			if(persongrade_list != null || peopleinfodto != null)
			{
				/*
				 * 전문분야, 학교, 회사 3가지를 통해 같으면 1점씩 올려 나와 유사도 점수를 매긴다
				 */
				Iterator<PersonGrade> persongrade_iter = persongrade_list.iterator();
				while(persongrade_iter.hasNext())
				{
					int grade = 0;
					
					persongrade = persongrade_iter.next();
					
					//자신이 나오지 않게 하기 위한 처치
					if(persongrade.getMember_seq() != peopleinfodto.getSeq())
					{
						if(persongrade.getExpertise() != null && peopleinfodto.getExpertise() != null)
						{
							if(persongrade.getExpertise().equals(peopleinfodto.getExpertise()))
							{
								grade++;
							}	
						}
						if(persongrade.getSchool() != null && peopleinfodto.getSchool() != null)
						{
							if(persongrade.getSchool().equals(peopleinfodto.getSchool()))
							{
								grade++;
							}	
						}
						if(persongrade.getCompany() != null && peopleinfodto.getCompany() != null)
						{
							if(persongrade.getCompany().equals(peopleinfodto.getCompany()))
							{
								grade++;
								persongrade.setGrade(grade);
							}	
						}
						if(grade > 0)
							scored_people_list.add(persongrade);
					}
				}
				request.setAttribute("knownpeople", scored_people_list);
				
				//팔로워수와 팔로잉 수 갱신
				session.setAttribute("follower", memberdao.getFollower(id));
				session.setAttribute("following", memberdao.getFollowing(id));
				
				String profilepath = DataInfo.getProfileURL(id);
				
				request.setAttribute("profilepath", profilepath);		
				
				int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(id);
				
				//알람 갯수를 jsp 페이지로 넘긴다 
				request.setAttribute("uncheckingalarm", alarm_num);
				
				//맞팔로우 데이터를 jsp 페이지로 넘긴다.
				List<FollowDto> Follow_data_list = SearchEachFollow.searchFollow(id);
				request.setAttribute("follow_data", Follow_data_list);
			}
		}
		
		return "/main/HumanConnection.jsp";
	}
}