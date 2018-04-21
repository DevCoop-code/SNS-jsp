package mobileapp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.action.CommandAction;
import expertisesns.model.MemberDao;

public class MobileSignIn implements CommandAction
{
	PrintWriter out = null;
	
	MemberDao memberDao = null;
	
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		out = response.getWriter();
		
		int check = -1;
	    String email = null;
	    String password = null;
	    String name = null;
	    int follower = -1;
	    int following = -1;
	    int boardnum = -1;
	    
	    HttpSession session = request.getSession();
	    
	    memberDao = MemberDao.getInstance();
	      
	    email = request.getParameter("email");
	    
	    password = request.getParameter("password");
	    
	    if(email != null && password != null)
	    {
			/*
			 * check = -1 : 존재하지 않는 이메일 계정
			 * check = 0 : 이메일 인증이 되지 않은 계정 
			 * check = 1 : 인증완료
			 * check = 2 : pw가 틀림 
	         */
			check = memberDao.loginCheck(email, password);
			System.out.println(check);
			 
			if(check == 0)
			{
				request.setAttribute("status", "0");
			}
			else if(check == 1)
			{
				name = memberDao.getName(email);
				follower = memberDao.getFollower(email);
				following = memberDao.getFollowing(email);
				boardnum = memberDao.getBoardNum(email);
				      
				session.setAttribute("id", email);
				session.setAttribute("name", name);
				session.setAttribute("follower", follower);
				session.setAttribute("following", following);
				session.setAttribute("boardnum", boardnum);
				
				request.setAttribute("status", "1");
			 }
			 else if(check == 2)
			 {			  
			      request.setAttribute("status", "2");
			 }
			 else
			 {
				 request.setAttribute("status", "-1");
			 } 
	    }
	    else
	    {
	    	request.setAttribute("status", "-1");
	    }
	    return "/mobile/mobileSignIn.jsp";
	}
}