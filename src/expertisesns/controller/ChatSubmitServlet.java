package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.ChatDao;

/*
 * ajax 수행
 * 상대방에게 메세지를 전송
 */
@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
	PrintWriter out = null;
	
	ChatDao chatdao = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID=request.getParameter("fromID");
		String toID=request.getParameter("toID");
		String chatContent=request.getParameter("chatContent");
		
		out = response.getWriter();
		chatdao = ChatDao.getInstance();
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("")|| chatContent == null || chatContent.equals(""))
		{
			out.write("0");
		} 
		else 
		{
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			out.write(chatdao.submit(fromID, toID, chatContent) + "");
		}
	}
}