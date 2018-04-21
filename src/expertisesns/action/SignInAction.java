package expertisesns.action;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.model.MemberDao;

public class SignInAction implements CommandAction
{
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		MemberDao memberDao = new MemberDao();
		
		HttpSession session = request.getSession();
		
		int check = -1;
		String email = null;
		String password = null;
		String name = null;
		int follower = -1;
		int following = -1;
		int boardnum = -1;
		
		try
		{
			request.setCharacterEncoding("utf-8");
			
			email = request.getParameter("email");
			password = request.getParameter("password");
			
			/*
			 * check = 0 : 이메일 인증이 되지 않은 계정 
			 * check = 1 : 인증완료
			 * check = 2 : pw가 틀림 
			 */
			check = memberDao.loginCheck(email, password);
			System.out.println(check);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		if(check == 0)
		{
			return "/sign/AuthenticateEmail.jsp";
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
			
			//최근로그인 상태를 저장할 쿠키 생성
			try
			{
				Cookie cookie = new Cookie(URLEncoder.encode(email,"UTF-8"), URLEncoder.encode(name,"UTF-8"));
				cookie.setMaxAge(3*60*60); 		//3일 유효기간
				cookie.setPath("/");
				response.addCookie(cookie);
				System.out.println("cookie 생성");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return "/refresh/HomeRefresh.jsp";
		}
		else if(check == 2)
		{
			request.setAttribute("check", "wrongpw");
			return "/sign/MainEntrance.jsp";
		}
		else
		{
			return "/sign/MainEntrance.jsp";
		}
	}
}
