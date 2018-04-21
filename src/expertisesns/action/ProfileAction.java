package expertisesns.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class ProfileAction implements CommandAction
{
	MemberDao memberDao = null;
	ProfileDao profileDao = null;
	
	String profilename = null;
	
	String seekingwork = null;
	String lastcompany = null;
	String highestschooling = null;
	String expertise = null;
	
	String AWARDEDCAREERS[] = null;
	String EXPERTISETECHNICS[] = null;
	String LICENSES[] = null;
	String EDUCATIONS[] = null;
	
	int seq = -1;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			
			memberDao = MemberDao.getInstance();
			profileDao = ProfileDao.getInstance();
			
			HttpSession session = request.getSession();
			
			//session으로 부터 회원 아이디 값 가져오기
			String email = (String)session.getAttribute("id");
			
			if(email != null)
			{
				//회원 프로필 데이터 가져오기
				profilename = memberDao.getProfile(email);
				request.setAttribute("profile", profilename);	
				
				/*
				 * 회원필수항목
				 */
				// 1.휴직재직여부 데이터 가져오기
				seekingwork = profileDao.getProfile(email, "seekingwork");
				request.setAttribute("seekingwork", seekingwork);
				
				// 2.회사 항목 업데이트
				lastcompany = profileDao.getProfile(email, "lastcompany");
				request.setAttribute("lastcompany", lastcompany);
				
				// 3.학력 항목 업데이트
				highestschooling = profileDao.getProfile(email, "highestschooling");
				request.setAttribute("highestschooling", highestschooling);
				
				// 4.전문분야 항목 업데이트
				expertise = profileDao.getProfile(email, "expertise");
				request.setAttribute("expertise", expertise);
				
				/*
				 * 회원옵션항목
				 */
				//SEQ 데이터 가져오기
				seq = profileDao.getSeq(email);
				
				// 1.등록한 기술 데이터 가져오기
				 List<String> expertisetech_list = profileDao.getOptionalProfile(seq, "EXPERTISETECHNIC", "EXPERTISETECHNICS");
				 EXPERTISETECHNICS = expertisetech_list.toArray(new String[expertisetech_list.size()]);
				 request.setAttribute("EXPERTISETECHNICS", EXPERTISETECHNICS);
				
				// 2.등록한 자격증 데이터 가져오기
				 List<String> license_list = profileDao.getOptionalProfile(seq, "LICENSE", "LICENSES");
				 LICENSES = license_list.toArray(new String[license_list.size()]);
				 request.setAttribute("LICENSES", LICENSES);
				
				 // 3.등록한 수상 데이터 가져오기
				 List<String> award_list = profileDao.getOptionalProfile(seq, "AWARDEDCAREER", "AWARDEDCAREERS");
				 AWARDEDCAREERS = award_list.toArray(new String[award_list.size()]);
				 request.setAttribute("AWARDEDCAREERS", AWARDEDCAREERS);
				
				 // 4.등록한 교육이수 데이터 가져오기
				 List<String> career_list = profileDao.getOptionalProfile(seq, "EDUCATION", "EDUCATIONS");
				 EDUCATIONS = career_list.toArray(new String[career_list.size()]);
				 request.setAttribute("EDUCATIONS", EDUCATIONS);	
				 
				 int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(email);
				//알람 갯수를 jsp 페이지로 넘긴다 
				request.setAttribute("uncheckingalarm", alarm_num);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "/main/Profile.jsp";
	}
}
