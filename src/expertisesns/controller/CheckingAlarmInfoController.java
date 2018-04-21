package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.data.DataInfo;
import expertisesns.model.AlarmDao;
import expertisesns.model.AlarmDto;
import expertisesns.model.AlarmInfoDto;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class CheckingAlarmInfoController extends HttpServlet
{
	PrintWriter out = null;
	
	AlarmDao alarmdao = null;
	ProfileDao profiledao = null;
	MemberDao memberdao = null;
	
	AlarmDto alarmdto = null;
	
	List<AlarmDto> alarmdto_list = null;
	
	boolean flag;
	
	int total = 0;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		flag = false;
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		out = response.getWriter();
		
		int check_seq = Integer.parseInt(request.getParameter("alarm_seq"));
		
		alarmdao = AlarmDao.getInstance();
		profiledao = ProfileDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		//한번 본 알람에 대해서 체크값을 1로 만들어 준다 
		alarmdao.checkTheCheckedAlarm(check_seq);
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		int my_seq = profiledao.getSeq(id);
		
		alarmdto_list = alarmdao.getUncheckAlarmInfo(my_seq);
		
		Iterator<AlarmDto> alarmdto_iter = alarmdto_list.iterator();
		
		total = 0;
		
		out.println("{");
		out.println("\"results\": ");
		out.println("[");
		while(alarmdto_iter.hasNext())
		{
			total++;
			
			if(flag)
				out.print(",");
			flag = true;
			
			out.println("{");
			
			alarmdto = alarmdto_iter.next();
			
			//알람의 seq값을 설정 
			int seq = alarmdto.getSeq();
			out.println("\"alarm_seq\": " + "\""+seq+"\",");
			
			//알람을 보낸 사람의 이름을 설정 
			int sender_seq = alarmdto.getSender_seq();
			String sender_name = memberdao.getNameFromSeq(sender_seq);
			out.println("\"sender_name\": " + "\""+sender_name+"\",");
			
			String sender_id = memberdao.getIdFromSeq(sender_seq);
			String profilepath = DataInfo.getProfileURL(sender_id);
			out.println("\"profile_url\" : " + "\"" + profilepath + "\",");
			//알람의 내용을 설정 
			String type = alarmdto.getType();
			String message = null;
			switch(type)
			{
			case "A":
				message = "팔로우를 신청하셨습니다.";
			break;
			case "B":
				message = "게시물에 좋아요를 하셨습니다.";	
			break;
			case "C":
				message = "게시물에 댓글을 다셨습니다.";
			break;
			case "D":
				message = "댓글에 답글을 다셨습니다.";
			break;
			}
			out.println("\"message\": " + "\""+message+"\"");
			out.println("}");
		}
		out.println("],");
		out.println("\"total\": "+"\""+total+"\"");
		out.println("}");
	}
}
