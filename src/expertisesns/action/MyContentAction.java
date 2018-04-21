package expertisesns.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;
import expertisesns.data.DataInfo;

public class MyContentAction implements CommandAction
{
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
		String id = (String)session.getAttribute("id");
		
		if(id != null)
		{
			//프로필 이미지 경로 넘기기 
			String profile_path = DataInfo.getProfileURL(id);
			
			request.setAttribute("profilepath", profile_path);	
			
			//알람 갯수
			int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(id);
			
			request.setAttribute("uncheckingalarm", alarm_num);
		}
		return "/main/MyContentActivity.jsp";
	}
}
