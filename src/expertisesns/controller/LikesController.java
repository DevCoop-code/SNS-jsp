package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.ContentDao;

public class LikesController extends HttpServlet
{
	ContentDao contentdao = null;
	
	PrintWriter out = null;
	
	int check = 0;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			out = response.getWriter();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		String member = request.getParameter("member");
		
		int content_seq = Integer.parseInt(request.getParameter("content_seq"));
		
		contentdao = ContentDao.getInstance();
		
		/*
		 * check = -1 : 좋아요를 누르지 않은 컨텐츠
		 * check = 1 : 좋아요를 누른 컨텐츠  
		 */
		check = contentdao.likesCheck(content_seq, member);
		
		if(check == -1)
		{
			contentdao.updateLikesdb(content_seq, member);
			contentdao.updateLikesInContentDB(content_seq);
			out.print("success");
		}
		else if(check == 1)
		{
			out.print("exits");
		}
	}
}
