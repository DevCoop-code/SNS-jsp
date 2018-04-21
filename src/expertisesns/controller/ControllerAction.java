package expertisesns.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.action.CommandAction;
import expertisesns.data.DataInfo;
import expertisesns.model.MemberDao;

public class ControllerAction extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	//명령어와 명령어 처리 클래스를 쌍으로 저장해두는 MAP
	private Map<String, Object> commandMap = new HashMap<String, Object>();
	
	MemberDao memberdao = null;
	
	public void init(ServletConfig config) throws ServletException
	{
		//미리 web.xml 에다가 지정해둔 파라미터들의 값을 얻어 올 수 있음 
		String props = config.getInitParameter("propertyConfig");
		
		//프로그램의 설정정보를 헛고생하지않고 개발코드에 불러오거나 또는 설정정보에 새로운 정보를 추가, 저장 할 수 있음 
		//프로퍼티 파일은 일련의 키-값의 쌍들로 이루어지며 파일에 저장됨(파일의 이름은 .properties로 끝남)
		Properties pr = new Properties();
		
		FileInputStream f = null;
		String path = config.getServletContext().getRealPath("/WEB-INF");
		
		try
		{
			f = new FileInputStream(new File(path, props));
			pr.load(f);
		}catch(IOException e)
		{
			throw new ServletException(e);
		}finally
		{
			if(f != null)
				try{f.close();}catch(IOException e){}
		}
		
		Iterator<Object> keyIter = pr.keySet().iterator();
		while(keyIter.hasNext())
		{
			//키 값을 가져옴
			String command = (String)keyIter.next();
			//가져온 키 값을 가지고 클래스 이름을 가져옴
			String className = pr.getProperty(command);
			try
			{
				Class<?> commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				
				System.out.println(className + "의 인스턴스 생성");
				
				commandMap.put(command, commandInstance);
			}catch(ClassNotFoundException e)
			{
				throw new ServletException(e);
			}catch(InstantiationException e)
			{
				throw new ServletException(e);
			}catch(IllegalAccessException e)
			{
				throw new ServletException(e);
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		requestPro(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		requestPro(request, response);
	}
	
	private void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String view = null;
		CommandAction com = null;
		
		try
		{
			String command = request.getRequestURI();
			if(command.indexOf(request.getContextPath()) == 0)
				command = command.substring(request.getContextPath().length());
			com = (CommandAction)commandMap.get(command);
			view = com.requestPro(request, response);
		}catch(Throwable e)
		{
			throw new ServletException(e);
		}
		
		System.out.println("view = " + view);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
}
