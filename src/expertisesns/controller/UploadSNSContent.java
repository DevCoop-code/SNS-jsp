package expertisesns.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import expertisesns.data.DataInfo;
import expertisesns.model.ContentDao;
import expertisesns.model.ContentNeededMemberInfo;
import expertisesns.model.ProfileDao;
import expertisesns.model.YouTubeDao;

public class UploadSNSContent extends HttpServlet
{
	String profileImageName = null;
	ContentDao contentdao = null;
	ProfileDao profiledao = null;
	YouTubeDao youtubedao = null;
	
	String email = null;
	
	int contentdb_seq = 0;
	
	ContentNeededMemberInfo contentneeded = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		contentdao = ContentDao.getInstance();
		profiledao = ProfileDao.getInstance();
		youtubedao = YouTubeDao.getInstance();
		
		contentneeded = new ContentNeededMemberInfo();
		
		HttpSession session = request.getSession();
		email = (String)session.getAttribute("id");
		
		//sns 글자 데이터
		String content = request.getParameter("comment");
		
		//유트브 아이디값들
		String[] youtubeVideoId = request.getParameterValues("selected_id");
		
		//엔터처리
		content = content.replaceAll("\r\n","<br>");
		
		//sns 날짜 데이터
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA);
		Date currentTime = new Date();
		String time = formatter.format(currentTime);
		
		//회원정보 seq 가져오기(seq, 이름, 경력)
		//int member_seq = profiledao.getSeq(email);
		contentneeded = profiledao.getMemberInfo(email);
		
		//contentdb_seq = contentdb의 테이블 seq값
		contentdb_seq = contentdao.updateContent(contentneeded, content, time);
		
		//비디오 아이디 넣음
		youtubedao.insertVideoId(youtubeVideoId, contentdb_seq);
		
		PrintWriter writer = response.getWriter();
		
		String contentType = request.getContentType();
		
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
		String id_folder = null;
		
		StringTokenizer stk = new StringTokenizer(email, ".");
		if(stk.hasMoreTokens())
		{
			id_folder = stk.nextToken();
		}
		
		Collection<Part> parts = request.getParts();
		
		for(Part part : parts)
		{
			profileImageName = part.getName();
			
			String contentType = part.getContentType();
			if(contentType == null)
			{
				System.out.println("UploadSNSContent part deleted");
				part.delete();
			}else if(contentType.startsWith("image/"))
			{
				long size = part.getSize();
				
				String filename = getFileName(part);
				String img_folder = "other";
				
				char front_word = filename.charAt(0);
				
				//숫자 이름
				if(front_word >= 48 && front_word <= 57)
				{
					img_folder = Character.toString(front_word);
				}
				//영어 이름(소문자)
				else if(front_word >= 97 && front_word <= 122)
				{
					img_folder = Character.toString(front_word);
				}
				//영어 이름(대문자) - 대문자 알파벳을 소문자로 바꿈 
				else if(front_word >= 65 && front_word <= 90)
				{
					front_word += 32;
					img_folder = Character.toString(front_word);
				}
				
				if(size>0 && id_folder != null)
				{
					//Ex]/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/zubwark@naver/r
					/*
					 *  /Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/+아이디(뒤에 com 혹은 net을 제거)/이미지 이름의 첫글자 초성
					 */
					part.write("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+id_folder+"/"+img_folder+"/"+filename);
					part.delete();
					
					HttpURLConnection httpConn = null;
					try
					{
						URL url = new URL(""+DataInfo.localhost_path+"/ExpertiseSNS/memberImageStorage/"+id_folder+"/"+img_folder+"/"+URLEncoder.encode(filename,"utf-8"));
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
					if(contentdb_seq != -1)
						contentdao.updateContentImage(contentdb_seq, "/ExpertiseSNS/memberImageStorage/"+id_folder+"/"+img_folder+"/"+filename);
					
					writer.println("FileUpload Success!!");
				}
			}else if(contentType.startsWith("video/"))
			{
				long size = part.getSize();
				
				String filename = getFileName(part);
				System.out.println("동영상 이름 : " + filename);
				String movie_file_loc = null;
				if(size > 0)
				{
					movie_file_loc = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/temp_movie_store/"+filename;
					part.write(movie_file_loc);
					part.delete();
				}
				
				//유튜브에 동영상 업로드
				//UploadYouTube.uploadVideo(movie_file_loc);
				//writer.println("FileUpload Success!!");
			}
			else
			{
				writer.println("not image or video");
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
