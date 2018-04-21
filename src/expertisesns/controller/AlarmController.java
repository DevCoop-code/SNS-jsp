package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.AlarmDao;
import expertisesns.model.ProfileDao;

public class AlarmController extends HttpServlet
{
	PrintWriter out = null;
	
	ProfileDao profiledao = null;
	AlarmDao alarmdao = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		out = response.getWriter();
		
		profiledao = ProfileDao.getInstance();
		alarmdao = AlarmDao.getInstance();
		
		String AlarmType = request.getParameter("alarm_type");
		System.out.println("AlarmType : " + AlarmType);
		//게시글에 댓글, 좋아요시 알람
		if(AlarmType.equals("a"))
		{		
			//게시글의 seq
			int content_seq = Integer.parseInt(request.getParameter("content_seq"));
			//좋아요를 누른 로그인한 사람의 id
			String member = request.getParameter("member");
			//이벤트 타입
			/*
			 * 팔로우 신청 : A
			 * 게시물에 좋아요 : B
			 * 게시물에 댓글 : C
			 * 댓글에 답글 : D
			 */
			String type = request.getParameter("type");
			
			//id값을 seq로 바꾸기 
			int sender_seq = profiledao.getSeq(member);
			
			//contentseq를 receiverseq로 바꾸기 
			int receiver_seq = alarmdao.getReceiverSeq(content_seq);
			
			//알람 저장 
			alarmdao.addAlarmInfo(receiver_seq, sender_seq, type);
			
			out.print("success");	
		}
		//사람을 팔로우시 알람
		else if(AlarmType.equals("b"))
		{
			System.out.println("b타입 알람!");
			
			int receiver_seq = Integer.parseInt(request.getParameter("follow_seq"));
			
			String sender_id = request.getParameter("member");
			
			int sender_seq = profiledao.getSeq(sender_id);
			String type = request.getParameter("type");
			
			//알람 저장
			alarmdao.addAlarmInfo(receiver_seq, sender_seq, type);
			
			out.print("success");
		}
	}
}
