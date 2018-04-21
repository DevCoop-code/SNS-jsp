package expertisesns.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.model.MemberDao;


public class JobInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String USER_AGENT = "Mozilla/5.0";
    private String Address; 
    private URL Url;
    private BufferedReader br;
    private HttpURLConnection con;
    private String protocol = "POST";
    
    MemberDao memberdao = null;
    
	@Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String keywords=request.getParameter("keywords");		    
		
		memberdao = MemberDao.getInstance();
		
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("id");
		
		if(keywords.equals("initial"))
		{
			keywords = memberdao.getExpertise(email);
			System.out.println("keywords : " + keywords);
		}
		
		if(keywords != null)
			keywords = keywords.replaceAll(" ", "");
		PrintWriter out = response.getWriter();
		
		if(!keywords.equals("none"))
		{
			Address = "http://api.saramin.co.kr/job-search?keywords="+keywords;   
		       
	        Url = new URL(this.Address); 
		        
	        con = (HttpURLConnection)Url.openConnection(); 
	        con.setRequestMethod(protocol); 
	        con.setRequestProperty("User-Agent", USER_AGENT);  
	        
	        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		        
	        String line;
		     
	        while ((line = br.readLine()) != null) 
	        {
	        	line=line.replace("<![CDATA[", "");
	        	line=line.replace("]]>","");
	            out.println(line);
	        }	
	        br.close();
		}else
		{
			out.println("none");
		}
	}
}