package expertisesns.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutProcessAction implements CommandAction
{
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			
			HttpSession session = request.getSession();
			session.invalidate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "/baconnetwork.main";
	}
}
