package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YouTubeDao 
{
	public static YouTubeDao instance = null;
	
	private YouTubeDao(){};
	
	public static YouTubeDao getInstance()
	{
		if(instance == null)
		{
			synchronized(YouTubeDao.class)
			{
				instance = new YouTubeDao();
			}
		}
		return instance;
	}
	
	public void insertVideoId(String[] youtube_id, int content_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			for(int i=0; i<youtube_id.length; i++)
			{
				sb.append(youtube_id[i]);
				sb.append(",");
			}
			conn = ConnUtil.getConnection();
			String query = "insert into YOUTUBEVIDEOINDEX(CONTENTSEQ, MOVIEID) values(?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
			pstmt.setString(2, sb.toString());
			pstmt.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(pstmt != null)
				try{pstmt.close();}catch(SQLException sqle1){}
			if(conn != null)
				try{conn.close();}catch(SQLException sqle1){}
		}
	}
	
	public String getVideoId(int contentseq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String movieids = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select MOVIEID from YOUTUBEVIDEOINDEX where CONTENTSEQ=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, contentseq);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				movieids = rs.getString("MOVIEID");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
				try{rs.close();}catch(SQLException sqle1){}
			if(pstmt != null)
				try{pstmt.close();}catch(SQLException sqle1){}
			if(conn != null)
				try{conn.close();}catch(SQLException sqle1){}
		}
		return movieids;
	}
}
