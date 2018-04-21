package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.ContentDao;

public class DeleteContent extends HttpServlet
{
	PrintWriter out = null;
	
	ContentDao contentdao = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		out = response.getWriter();
		
		int content_seq = Integer.parseInt(request.getParameter("contentseq"));
		
		contentdao = ContentDao.getInstance();
		
		contentdao.deleteContent(content_seq);
		
		out.print("success");
	}
}
