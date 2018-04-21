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

public class FollowCancelController extends HttpServlet
{
	int following_seq;
	String my_email;
	
	PrintWriter out = null;
	
	FollowDao followdao = null;
	ProfileDao profiledao = null;
	MemberDao memberdao = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		following_seq = Integer.parseInt(request.getParameter("person_seq"));
		my_email = request.getParameter("my_email");
		
		followdao = FollowDao.getInstance();
		profiledao = ProfileDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		int seq = profiledao.getSeq(my_email);
		
		//나의 팔로잉 취소
		followdao.CancelFollowing(seq, following_seq);

		//상대의 팔로워로써 취소
		followdao.CancelFollower(following_seq, seq);
		
		//나의 팔로잉 숫자 줄이기(seq)
		followdao.DecrementFollowing(seq);
		
		//상대의 팔로워 숫자 줄이기(following_seq)
		followdao.DecrementFollower(following_seq);
		
		//session값 개편
		out = response.getWriter();
		int follower = memberdao.getFollower(my_email);
		int following = memberdao.getFollowing(my_email);
		
		session.setAttribute("follower", follower);
		session.setAttribute("following", following);
		
		out.print("success");
		
	}
}
