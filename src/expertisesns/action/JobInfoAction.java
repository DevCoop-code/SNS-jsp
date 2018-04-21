package expertisesns.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;

public class JobInfoAction implements CommandAction
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
		
		String email = (String)session.getAttribute("id");
		
		System.out.println("job info!");
		if(email != null)
		{
			//알람 갯수를 jsp 페이지로 넘긴다 
			request.setAttribute("uncheckingalarm", UnCheckingAlarm.uncheckingAlarmNum(email));	
		}
		return "/main/JobInfo.jsp";
	}
}
