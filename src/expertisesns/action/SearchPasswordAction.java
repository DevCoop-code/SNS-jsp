package expertisesns.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.MemberDao;

public class SearchPasswordAction implements CommandAction
{
	MemberDao memberdao = null;
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		int check = -1;
		
		request.setCharacterEncoding("utf-8");
			
		String email = request.getParameter("email");
		System.out.println("email : " + email);
		if(email != null)
		{
			memberdao = MemberDao.getInstance();
			//check = -1 : 유효하지 않은 이메일
			//check = 0 : 유효한 이메일
			check = memberdao.getEmail(email);	
			
			if(check == -1)
			{
				request.setAttribute("Emailcheck", "noeffect");
				return "/sign/SearchPassword.jsp";		
			}
			request.setAttribute("sender", email);
			return "/baconnetwork/PasswordEmail.do";
		}
		request.setAttribute("Emailcheck", "notexists");
		return "/sign/SearchPassword.jsp";
	}
}
