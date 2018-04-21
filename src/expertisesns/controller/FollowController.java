package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.model.FollowDao;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class FollowController extends HttpServlet
{
	ProfileDao profiledao = null;
	MemberDao memberdao = null;
	FollowDao followdao = null;
	
	int person_seq;
	int my_seq;
	int check;
	
	PrintWriter out = null;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		person_seq = Integer.parseInt(request.getParameter("person_seq"));
		String my_email = request.getParameter("my_email");
		
		profiledao = ProfileDao.getInstance();
		
		my_seq = profiledao.getSeq(my_email);
		
		memberdao = MemberDao.getInstance();
		followdao = FollowDao.getInstance();
		/*
		 * check=-1 : 이미 팔로잉 한 사람  
		 * check=1: 팔로잉 안한 사람 
		 */
		check = memberdao.checkFollowing(my_seq, person_seq);
		
		out = response.getWriter();
		
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("id");
		//이미 팔로잉 한 상태
		if(check == -1)
		{
			out.print("alreadyfollowing");
		}else if(check == 1)	//이제 팔로잉 해야 하는 상태
		{
			followdao.followingProcess(my_seq, person_seq);
			followdao.followerProcess(person_seq, my_seq);
			
			//members의 follower, following 값 개편
			followdao.IncrementFollowing(my_seq);
			followdao.IncrementFollower(person_seq);
			
			//session 값 개편
			int follower = memberdao.getFollower(email);
			int following = memberdao.getFollowing(email);
			
			session.setAttribute("follower", follower);
			session.setAttribute("following", following);
			
			out.print("followsuccess");
		}
	}
}
