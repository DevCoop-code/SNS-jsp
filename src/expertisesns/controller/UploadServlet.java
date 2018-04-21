package expertisesns.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import expertisesns.data.DataInfo;
import expertisesns.model.MemberDao;

public class UploadServlet extends HttpServlet
{
	String profileImageName = null;
	String idStr = null;
	MemberDao memberDao = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		idStr = request.getParameter("userid");
		
		//System.out.println("userid : " + idStr);
		
		PrintWriter writer = response.getWriter();
		
		String contentType = request.getContentType();
		//contentType이 multipart/form-data인 경우 printPartInfo함수를 호출
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/"))
		{
			printPartInfo(request, writer);
		}else
		{
			writer.println("not_multipart");
		}
	}
	
	private void printPartInfo(HttpServletRequest request, PrintWriter writer) throws ServletException, IOException
	{	
		//getParts()함수로 Part의 전체 목록을 구함 
		Collection<Part> parts = request.getParts();
		
		for(Part part : parts)
		{
			profileImageName = part.getName();
			
			//writer.println("name = " + profileImageName);
			
			//Part의 ContentType을 얻어 파일업로드 여부를 확인 
			String contentType = part.getContentType();
			if(contentType == null)
				part.delete();
			else if(contentType.startsWith("image/"))
			{
				//getSize()를 호출하여 크기 정보를 가져옴
				long size = part.getSize();
				//writer.println("size = " + size);
				
				//getFileName()을 호출하여 Content-Disposition헤더 정보로부터 업로드한 파일이름을 얻음 
				String filename = getFileName(part);
				//writer.println("filename = " + filename);
				
				if(size > 0)
				{
					part.write("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+idStr+"/profile/"+filename);
					part.delete();
					HttpURLConnection httpConn = null;
					try
					{
						URL url = new URL(""+DataInfo.localhost_path+"/ExpertiseSNS/memberImageStorage/"+idStr+"/profile/"+filename);
						//총 20초 연결시도
						for(int i=0; i<20; i++)
						{
							httpConn = (HttpURLConnection)url.openConnection();
							httpConn.setRequestMethod("GET");
							httpConn.connect();
							int responseCode = httpConn.getResponseCode();
							if(responseCode < 300 && responseCode >= 200)
							{
								break;
							}
							Thread.sleep(1000);
							System.out.println("status delayed : "+responseCode+" ,"+i);
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					/*
					 * 썸네일 제작
					 */
					/*
					String orgFile = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+idStr+"/profile/"+filename;
					String thumbFile = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/thumbnail/thumb.jpg";
					
					try
					{
						Image thumbnail = JimiUtils.getThumbnail(orgFile, 50, 50, Jimi.IN_MEMORY);
						
						Jimi.putImage(thumbnail, thumbFile);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					*/
					memberDao = new MemberDao();
					
					HttpSession session = request.getSession();
					String email = (String)session.getAttribute("id");
					
					memberDao.updateProfile(filename, email);
					
					writer.println(DataInfo.domain_path+"/memberImageStorage/"+idStr+"/profile/"+filename);
				}
			}else
			{
				writer.println("not_image");
				String value = readParameterValue(part);
			}
		}
	}
	private String getFileName(Part part) throws UnsupportedEncodingException
	{
		for(String cd : part.getHeader("Content-Disposition").split(";"))
		{
			if(cd.trim().startsWith("filename"))
			{
				String tmp = cd.substring(cd.indexOf('=')+1).trim().replace("\"","");
				tmp = tmp.substring(tmp.indexOf(":")+1);
				return tmp;
			}
		}
		return null;
	}
	private String readParameterValue(Part part) throws IOException
	{
		InputStreamReader isr = new InputStreamReader(part.getInputStream(),"utf-8");
		char[] data = new char[512];
		int len = -1;
		StringBuilder builder = new StringBuilder();
		while((len=isr.read(data)) != -1)
		{
			builder.append(data,0,len);
		}
		return builder.toString();
	}
}
