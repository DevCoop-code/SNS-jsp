package expertisesns.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.data.DataInfo;
import expertisesns.model.AlarmDao;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;
import expertisesns.model.SettingDao;
import expertisesns.model.SettingDto;

public class SettingAction implements CommandAction
{
	SettingDao settingdao = null;
	ProfileDao profiledao = null;
	AlarmDao alarmdao = null;
	MemberDao memberdao = null;
	
	SettingDto settingdto = null;
	
	int receiver_seq = 0;
	int uncheckingAlarmNum = 0;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session = request.getSession();
		
		String my_id = (String)session.getAttribute("id");
		
		if(my_id != null)
		{
			settingdao = SettingDao.getInstance();
			profiledao = ProfileDao.getInstance();
			alarmdao = AlarmDao.getInstance();
			memberdao = MemberDao.getInstance();
			
			settingdto = new SettingDto();
			
			//읽지 않은 알람 갯수 가져오기
			receiver_seq = profiledao.getSeq(my_id);
			uncheckingAlarmNum = alarmdao.getNotCheckedAlarmNum(receiver_seq);
			request.setAttribute("uncheckingalarm", uncheckingAlarmNum);
			
			//프로필 이미지 경로 가져오기
			String profilepath = DataInfo.getProfileURL(my_id);
			request.setAttribute("profilepath", profilepath);
			
			//수정사항 데이터 가져오기
			settingdto = settingdao.getSettingsData(my_id);
			request.setAttribute("member_data", settingdto);
		}
		return "/main/Settings.jsp";
	}
}
