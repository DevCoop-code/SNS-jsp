package expertisesns.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class ProfileProcessAction implements CommandAction
{
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		ProfileDao profiledao = new ProfileDao();
		
		MemberDao memberDao = new MemberDao();
		
		HttpSession session = request.getSession();
		String email = null;
		
		String status = null;
		
		String companyname = null;
		
		String univkind = null;
		String univname = null;
		String univbranch = null;
		String univestablishment = null;
		String univaddress = null;
		
		String ownexpertise = null;
		
		String owntechs[] = null;
		String ownlicenses[] = null;
		String ownawardes[] = null;
		String educareers[] = null;
		
		try
		{
			request.setCharacterEncoding("utf-8");
			
			email = (String)session.getAttribute("id");
			
			//1. 등록한 현재상태
			status = request.getParameter("job_data");
			
			//2. 등록한 회사
			companyname = request.getParameter("companyname_data");
			
			//3. 등록한 대학
			univkind = request.getParameter("univkind_data");
			univname = request.getParameter("univname_data");
			univbranch = request.getParameter("univbranch_data");
			univestablishment = request.getParameter("univestablishment");
			System.out.println(univestablishment);
			//String univaddress = request.getParameter("univaddress_data");
			
			//4. 등록한 전문분야 
			ownexpertise = request.getParameter("expertise_data");
			
			//필수 항목 업데이트
			//1. 휴직재직여부
			if(!status.equals(""))
			{
				profiledao.updateProfile(status, email, "seekingwork");
				request.setAttribute("seekingwork", status);
			}else
			{
				status = profiledao.getProfile(email, "seekingwork");
				request.setAttribute("seekingwork", status);
			}
			//2. 회사 항목 업데이트
			if(!companyname.equals(""))
			{
				profiledao.updateProfile(companyname, email, "lastcompany");
				request.setAttribute("lastcompany", companyname);
			}else
			{
				companyname = profiledao.getProfile(email, "lastcompany");
				request.setAttribute("lastcompany", companyname);
			}
			//3. 학력 항목 업데이트
			if(!univname.equals(""))
			{
				profiledao.updateProfile(univkind+"."+univname+"."+univbranch+"."+univestablishment, email, "highestschooling");
				request.setAttribute("highestschooling", univname);
			}else
			{
				univname = profiledao.getProfile(email, "highestschooling");
				request.setAttribute("highestschooling", univname);
			}
			//4. 전문분야 항목 업데이트
			if(!ownexpertise.equals(""))
			{
				profiledao.updateProfile(ownexpertise, email, "expertise");
				request.setAttribute("expertise", ownexpertise);
			}else
			{
				ownexpertise = profiledao.getProfile(email, "expertise");
				request.setAttribute("expertise", ownexpertise);
			}
			
			
			//등록한 기술분야
			owntechs = request.getParameterValues("owntech_datas");
			
			//등록한 자격증 분야
			ownlicenses = request.getParameterValues("ownlicense_data");
			
			//등록한 수상 분야
			ownawardes = request.getParameterValues("award_data");
			
			//등록한 교육이수 분야
			educareers = request.getParameterValues("educareer_data");
			
			/*
			 * 옵션항목업데이트 
			 */
			//회원 seq값 가져오기
			int seq = profiledao.getSeq(email);
			
			//수상경력 업데이트
			if(ownawardes != null)
			{
				System.out.println("ownawardes : "+ownawardes[0]);
				profiledao.optionProfile(ownawardes, email, "AWARDEDCAREERS");
				request.setAttribute("AWARDEDCAREERS", ownawardes);
			}else
			{
				List<String> awardes_list = profiledao.getOptionalProfile(seq, "AWARDEDCAREER", "AWARDEDCAREERS");
				ownawardes = awardes_list.toArray(new String[(awardes_list.size())]);
				request.setAttribute("AWARDEDCAREERS", ownawardes);
			}
			
			//기술분야 업데이트
			if(owntechs != null)
			{
				profiledao.optionProfile(owntechs, email, "EXPERTISETECHNICS");
				request.setAttribute("EXPERTISETECHNICS", owntechs);
			}else
			{
				List<String> techs_list = profiledao.getOptionalProfile(seq, "EXPERTISETECHNIC", "EXPERTISETECHNICS");
				owntechs = techs_list.toArray(new String[(techs_list.size())]);
				request.setAttribute("EXPERTISETECHNICS", owntechs);
			}
			
			//자격증분야 업데이트
			if(ownlicenses != null)
			{
				profiledao.optionProfile(ownlicenses, email, "LICENSES");
				request.setAttribute("LICENSES", ownlicenses);
			}else
			{
				List<String> email_list = profiledao.getOptionalProfile(seq, "LICENSE", "LICENSES");
				ownlicenses = email_list.toArray(new String[(email_list.size())]);
				request.setAttribute("LICENSES", ownlicenses);
			}
			
			//교육이수분야 업데이트
			if(educareers != null)
			{
				profiledao.optionProfile(educareers, email, "EDUCATIONS");
				request.setAttribute("EDUCATIONS", educareers);
			}else
			{
				List<String> educareer_list = profiledao.getOptionalProfile(seq, "EDUCATION", "EDUCATIONS");
				ownlicenses = educareer_list.toArray(new String[(educareer_list.size())]);
				request.setAttribute("EDUCATIONS", ownlicenses);
			}
			
			/*
			 * 클라이언트로 전송 항목
			 */
			//프로필 사진 제목 
			String profilename = memberDao.getProfile(email);
			request.setAttribute("profile", profilename);
			
			 int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(email);
			//알람 갯수를 jsp 페이지로 넘긴다 
			request.setAttribute("uncheckingalarm", alarm_num);
			
		}catch(UnsupportedEncodingException usee)
		{
			usee.printStackTrace();
		}
		return "/main/Profile.jsp";
	}
}
