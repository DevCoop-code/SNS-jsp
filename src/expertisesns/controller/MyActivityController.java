package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.data.DataInfo;
import expertisesns.model.ContentDao;
import expertisesns.model.ContentDto;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class MyActivityController extends HttpServlet
{
	ContentDao contentdao = null;
	ContentDto contentdto = null;
	ProfileDao profiledao = null;
	MemberDao memberdao = null;
	
	List<ContentDto> content_list = null;
	List<String> imageurl = null;;
	
	int start = 0;
	int end = 5;
	
	PrintWriter out = null;
	
	boolean flag = false;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
		flag = false;
		HttpSession session = request.getSession();
		
		contentdao = ContentDao.getInstance();
		profiledao = ProfileDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		content_list = new ArrayList<ContentDto>();
		
		String email = (String)session.getAttribute("id");
		
		int member_seq = profiledao.getSeq(email);
		
		end = Integer.parseInt(request.getParameter("start"));
		System.out.println(end);
		//System.out.println("start"+start);
		
		content_list = contentdao.getContentDB(member_seq, start, end);
		
		out.println("{");
		out.println("\"results\" : ");
		out.println("[");
		Iterator<ContentDto> iter = content_list.iterator();
		
		int total = 0;
		while(iter.hasNext())
		{
			imageurl = new ArrayList<String>();
			
			contentdto = iter.next();
			
			if(flag)
				out.print(",");
			flag = true;
			total++;
			
			out.println("{");
			
			String contentdate = contentdto.getDate();
			out.println("\"date\" : " + "\"" + contentdate + "\",");
			
			int likes = contentdto.getLikes();
			out.println("\"likes\" : " + "\"" + likes + "\",");
			
			String content = contentdto.getContent();
			out.println("\"content\" : " + "\"" + content + "\",");
			
			//글 sequence
			int seq = contentdto.getSeq();
			out.println("\"seq\" : " + "\"" + seq + "\",");
			
			//회원 sequence
			int memberseq = contentdto.getMemberseq();
			out.println("\"memberseq\" : " + "\"" + memberseq + "\",");
			
			String name = contentdto.getName();
			out.println("\"name\" : " + "\"" + name + "\",");
			
			String lastcompany = contentdto.getCompany();
			out.println("\"career\" : " + "\"" + lastcompany + "\",");
			
			String id = memberdao.getIdFromSeq(memberseq);
			String profile_path = DataInfo.getProfileURL(id);
			out.println("\"profileurl\" : " + "\"" + profile_path + "\",");
			
			imageurl = contentdao.getImageName(seq);
			out.println("\"imageurl\" : [");
			
			Iterator<String> imageiter = imageurl.iterator();
			while(imageiter.hasNext())
			{
				String image_url = imageiter.next();
				
				out.println("\""+image_url+"\"");
			}
			
			out.println("]");
			out.println("}");	
		}
		
		out.println("],");
		out.println("\""+"total"+"\" : "+"\""+total+"\"");
		out.println("}");
		
		//response
		/*
		 * {
				"results" : 
				[
					{
						"date" : " ",
						"likes" : " ",
						"content" : " ",
						"seq" : " ",
						"memberseq" : " ",
						"name" : " ",
						"career" : " ",
						"profileurl" : " ",
						"imageurl" :
						[
							" ",
							" "
						]
					},
				]
			}
		 */
	}
}
