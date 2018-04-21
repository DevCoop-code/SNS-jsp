package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.data.DataInfo;
import expertisesns.model.ContentDao;
import expertisesns.model.ContentDto;
import expertisesns.model.FollowDao;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;
import expertisesns.model.YouTubeDao;

public class HomeController extends HttpServlet
{
	ContentDao contentdao = null;
	ContentDto contentdto = null;
	ProfileDao profiledao = null;
	FollowDao followdao = null;
	MemberDao memberdao = null;
	YouTubeDao youtubedao = null;
	
	List<ContentDto> content_list = null;
	List<String> imageurl = null;;
	List<Integer> followings_list = null;
	
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
		youtubedao = YouTubeDao.getInstance();
		
		content_list = new ArrayList<ContentDto>();
		
		String email = (String)session.getAttribute("id");
		
		int member_seq = profiledao.getSeq(email);
		
		end = Integer.parseInt(request.getParameter("start"));
		System.out.println(end);
		//System.out.println("start"+start);
		
		followings_list = new ArrayList<Integer>();
		followdao = FollowDao.getInstance();
		
		//내가 팔로우 하는 사람들(following) seq 값 가져오기 
		followings_list = followdao.getFollowingSeq(member_seq);
		//글 가져오기
		content_list = contentdao.getContentsDB(member_seq, followings_list, start, end);
		
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
			
			//회원 이름
			String name = contentdto.getName();
			out.println("\"name\" : " + "\"" + name + "\",");
			
			//회원 회사
			String lastcompany = contentdto.getCompany();
			out.println("\"career\" : " + "\"" + lastcompany + "\",");
			
			//프로필 url
			//seq값을 이용해 id값 가져오기
			String member_id = memberdao.getIdFromSeq(memberseq);
			
			String profile_folder = DataInfo.getIDFolderName(member_id);
			String profileimage_name = memberdao.getProfile(member_id);
			
			String profilepath = DataInfo.getProfileURL(member_id);
			
			out.println("\"profileurl\" : " + "\"" + profilepath + "\",");
			
			imageurl = contentdao.getImageName(seq);
			
			out.println("\"imageurl\" : [");
			
			Iterator<String> imageiter = imageurl.iterator();
			while(imageiter.hasNext())
			{
				String image_url = imageiter.next();
				//이미지를 서버에서 불러올수 있는 여부를 확인
				
				out.println("\""+image_url+"\"");
			}
			
			out.println("],");
			
			out.println("\"youtubeids\" : [");
			
			String videoids = youtubedao.getVideoId(seq);
			if(videoids != null)
			{
				StringTokenizer videoid_stk = new StringTokenizer(videoids.trim(), ",");
				while(videoid_stk.hasMoreTokens())
				{
					String videoid = videoid_stk.nextToken();
					
					out.println("\""+videoid+"\"");
				}
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
						"profileurl": " ",
						"imageurl" :
						[
							" ",
							" "
						],
						"youtubeids" :
						[
							" ",
							" "
						]
					},
				],
				"total": " "
			}
		 */
	}
}
