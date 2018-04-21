package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.model.SettingDao;

public class UpdateSettingController extends HttpServlet
{
	PrintWriter out = null;
	
	String dataType = null;
	String data = null;
	
	SettingDao settingdao = null;
	
	HttpSession session = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		out = response.getWriter();
		
		dataType = request.getParameter("dataType");
		data = request.getParameter("data");
		
		if(dataType != null && data != null)
		{
			settingdao = SettingDao.getInstance();
			
			session = request.getSession();
			String id = (String)session.getAttribute("id");
			
			if(dataType.equals("SEX"))
			{
				settingdao.updateSexData(dataType, Integer.parseInt(data), id);	
			}
			else if(dataType.equals("PASSWORD"))
			{
				settingdao.updatePasswordData(dataType, data, id);
			}
			else
			{
				settingdao.updateSettingData(dataType, data, id);
				if(dataType.equals("NAME"))
				{
					session.setAttribute("name", data);
				}
			}
		}
		out.print("success");
	}
}
