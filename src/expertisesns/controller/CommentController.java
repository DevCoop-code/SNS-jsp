package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.CommentDao;
import expertisesns.model.CommentDto;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class CommentController extends HttpServlet
{
	PrintWriter out = null;
	
	String commenterId = null;
	int contentSeq;
	String comment = null;
	String time = null;
	
	CommentDao commentdao = null;
	CommentDto commentdto = null;
	
	ProfileDao profiledao = null;
	MemberDao memberdao = null;
	
	List<CommentDto> comment_list = null;
	
	boolean flag = false;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		out = response.getWriter();
		comment_list = new ArrayList<CommentDto>();
		
		commenterId = request.getParameter("commenter_id");
		contentSeq = Integer.parseInt(request.getParameter("content_seq"));
		comment = request.getParameter("comment");
		
		//댓글 날짜 데이터
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA);
		Date currentTime = new Date();
		time = formatter.format(currentTime);
		
		commentdao = CommentDao.getInstance();
		profiledao = ProfileDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		if(!comment.equals(""))
		{
			//댓글을 댓글DB에 저장
			commentdao.insertComment(commenterId, contentSeq, comment, time);
		}
		
		//해당 글의 댓글 데이터 가져오기
		comment_list = commentdao.getComments(contentSeq, 0, 100);
		
		out.println("{");
		out.println("\"results\": ");
		out.println("[");
		
		flag = false;
		
		Iterator<CommentDto> iter = comment_list.iterator();
		while(iter.hasNext())
		{
			commentdto = iter.next();
			
			if(flag)
				out.println(",");
			flag = true;
			out.println("{");
			
			out.println("\"comment_seq\" : " + "\"" + commentdto.getComment_seq() + "\",");
			String id = commentdto.getCommerter_id();
			
			//아이디로 사용자 SEQ값 가져오기 
			int commenter_seq = profiledao.getSeq(id);
			out.println("\"commenter_seq\" : " + "\"" + commenter_seq + "\",");
			
			//아이디로 사용자 이름값 가져오기 
			String commenter_name = memberdao.getName(id);
			out.println("\"commenter_name\" : " + "\"" + commenter_name + "\",");
			
			out.println("\"today_date\" : " + "\"" + commentdto.getToday_date() + "\",");
			out.println("\"comments\" : " + "\"" + commentdto.getComments() + "\"");
			
			out.println("}");
		}
		out.println("]");
		out.println("}");
		/*
		 * ==Response==
		 * {
				"results":
				[
					{
						"comment_seq": " ",
						"commenter_seq": " ",
						"commenter_name": " ",
						"today_date": " ",
						"comments": " "
					},
				]
			}
		 *
		 */
	}
}
