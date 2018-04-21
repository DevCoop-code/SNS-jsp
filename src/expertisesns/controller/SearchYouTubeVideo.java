package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.YouTubeChannelDto;
import expertisesns.youtubedataapi.SearchVideoID;

public class SearchYouTubeVideo extends HttpServlet
{
	PrintWriter out = null;
	
	List<YouTubeChannelDto> channels = null;
	
	int next = 0;
	
	YouTubeChannelDto youtubeVideoInfo = null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		out = response.getWriter();
		
		next = 0;
		
		String keyword = request.getParameter("keyword");
		System.out.println("keyword : " + keyword);
		
		channels = SearchVideoID.SearchVideoID(keyword);
		
		if(channels != null)
		{
			out.println("{");
			out.println("\"results\" : ");
			out.println("[");
			
			Iterator<YouTubeChannelDto> channel_iter = channels.iterator();
			while(channel_iter.hasNext())
			{
				youtubeVideoInfo = channel_iter.next();
				
				if(next > 0)
					out.print(",");
				next++;
				out.println("{");
				
				String youtube_id = youtubeVideoInfo.getId();
				out.println("\"videoID\" : " + "\"" + youtube_id + "\",");
				
				String youtube_title = youtubeVideoInfo.getTitle();
				out.println("\"videoTitle\" : " + "\"" + youtube_title + "\",");
				
				String youtube_thumbnail = youtubeVideoInfo.getThumbnail();
				out.println("\"videoThumbnail\" : " + "\"" + youtube_thumbnail + "\"");
				
				out.println("}");
			}
			out.println("]");
			out.println("}");
		}
		else
		{
			out.print("novideo");	
		}
	}
}

/*
{
	results:
	[
		{
			"videoID": " ",
			"videoTitle": " ",
			"videoThumbnail": " ",
		},
	]
 }
*/