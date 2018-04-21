package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.ChatDao;
import expertisesns.model.ChatDto;

/*
 * ajax 수행 
 * 메시지 리스트 가져오기
 */
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet 
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
		String listType=request.getParameter("listType");
		
		out = response.getWriter();
		
		chatdao = ChatDao.getInstance();
		
		if(fromID==null || fromID.equals("") || toID==null||toID.equals("") || listType==null || listType.equals(""))
		{
			out.write("");
		}
		else if(listType.equals("ten"))
			out.write(getTen(URLDecoder.decode(fromID,"UTF-8"), URLDecoder.decode(toID, "UTF-8")));
		else 
		{
			try
			{
				out.write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
			}catch(Exception e)
			{
				out.write("");
			}
		}
	}
	public String getTen(String fromID, String toID)
	{
		StringBuffer result = new StringBuffer("");
		
		result.append("{\"result\":[");
		
		
		ArrayList<ChatDto> chatList = chatdao.getChatListByRecent(fromID, toID, 10);
		
		if(chatList.size() == 0)
			return"";
		for(int i=0 ; i < chatList.size(); i++)
		{
			result.append("[{\"value\": \"" + chatList.get(i).getFromID()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getName()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime()+ "\"}]");
			result.append("{\"value\": \"" + chatList.get(i).getProfilePath()+ "\"}]");
			
			if(i != chatList.size()-1)
				result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
	    return result.toString();
	}
	
	public String getID(String fromID, String toID, String chatID)
	{
		StringBuffer result = new StringBuffer("");
		
		result.append("{\"result\":[");
		
		ArrayList<ChatDto> chatList = chatdao.getChatListByID(fromID, toID, chatID);
		
		if(chatList.size()==0)
			return"";
		for(int i=0 ; i < chatList.size(); i++)
		{
			result.append("[{\"value\": \"" + chatList.get(i).getFromID()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getName()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getProfilePath()+ "\"}]");
		if(i != chatList.size()-1)
			result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
	    return result.toString();
	}
}
