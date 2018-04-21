package expertisesns.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.MemberDao;

public class EmailAuthenticationAction implements CommandAction
{
	MemberDao memberDao = new MemberDao();
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			
			String email = request.getParameter("email");
			
			memberDao.authenticateEmail(email);
			
			request.setAttribute("check", "emailAuthenticationComplete");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "/sign/MainEntrance.jsp";
	}
}
