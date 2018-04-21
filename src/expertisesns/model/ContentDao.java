package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContentDao 
{
	public static ContentDao instance = null;
	
	private ContentDao(){};
	
	public static ContentDao getInstance()
	{
		if(instance == null)
		{
			synchronized(ContentDao.class)
			{
				instance = new ContentDao();
			}
		}
		return instance;
	}
	
	public int updateContent(ContentNeededMemberInfo contentneeded, String content, String date)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int contentdb_seq = -1;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into contentdbs(seq, contentdate, likes, content, member_seq, name, lastcompany) values(content_seq.nextval, ?, 0, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, date);
			pstmt.setString(2, content);
			pstmt.setInt(3, contentneeded.getSeq());
			pstmt.setString(4, contentneeded.getMember_name());
			pstmt.setString(5, contentneeded.getMember_career());
			pstmt.executeUpdate();
			
			query = "select seq from contentdbs where contentdate=? and member_seq=?";
			pstmt.close();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, date);
			pstmt.setInt(2, contentneeded.getSeq());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				contentdb_seq = rs.getInt("seq");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(pstmt != null)
				try{pstmt.close();}catch(SQLException sqle1){}
			if(conn != null)
				try{conn.close();}catch(SQLException sqle1){}
			if(rs != null)
				try{rs.close();}catch(SQLException sqle1){}
		}
		return contentdb_seq;
	}
	
	public void updateContentImage(int seq, String imagename)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into picturecontents(contentseq, picturename) values(?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, seq);
			pstmt.setString(2, imagename);
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
	
	public List<ContentDto> getContentDB(int member_seq, int start, int end)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ContentDto contentdto = null;
		
		List<ContentDto> content_list = new ArrayList<ContentDto>();
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select*from(select rownum RNUM, CONTENTDATE, LIKES, CONTENT, SEQ, MEMBER_SEQ, NAME, LASTCOMPANY from (select*from CONTENTDBS where MEMBER_SEQ = ? order by seq desc)) where RNUM >= ? and RNUM <= ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, member_seq);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				contentdto = new ContentDto();
				
				contentdto.setDate(rs.getString("CONTENTDATE"));
				contentdto.setLikes(rs.getInt("LIKES"));
				contentdto.setContent(rs.getString("CONTENT"));
				contentdto.setSeq(rs.getInt("SEQ"));
				contentdto.setMemberseq(rs.getInt("MEMBER_SEQ"));
				contentdto.setName(rs.getString("NAME"));
				contentdto.setCompany(rs.getString("LASTCOMPANY"));
				
				content_list.add(contentdto);
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
		return content_list;
	}
	
	public List<String> getImageName(int contentseq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<String> imageurl_list = new ArrayList<String>();
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select picturename from picturecontents where contentseq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, contentseq);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String imagename = rs.getString("picturename");
				
				imageurl_list.add(imagename);
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
		return imageurl_list;
	}
	
	//작성한 글마다 좋아요 누른 사람의 seq 저장
	public void updateLikesdb(int content_seq, String member)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into LIKESDB(SEQ, CONTENT_SEQ, MEMBER) values(LIKES_SEQ.nextval, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
			pstmt.setString(2, member);
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
	
	//글에 대한 좋아요를 했었는지 안했었는지를 체크
	public int likesCheck(int content_seq, String member)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select * from LIKESDB where CONTENT_SEQ=? and MEMBER=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
			pstmt.setString(2, member);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				check = 1;
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
		return check;
	}
	
	//글 db에 좋아요 1 올리기
	public void updateLikesInContentDB(int content_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int likes = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select LIKES from CONTENTDBS where SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				likes = rs.getInt("LIKES");
			}
			
			likes ++;
			
			pstmt.close();
			query = "update CONTENTDBS set LIKES = ? where SEQ =?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, likes);
			pstmt.setInt(2, content_seq);
			pstmt.executeUpdate();
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
	}
	
	//자신이 쓴 글과 자신의 팔로잉들의 글을 가져옴
	public List<ContentDto> getContentsDB(int member_seq, List<Integer> following_seq_list, int start, int end)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ContentDto contentdto = null;
		
		List<ContentDto> content_list = new ArrayList<ContentDto>();
		StringBuilder query_builder = new StringBuilder();
		
		int index = 1;
		try
		{
			conn = ConnUtil.getConnection();
			query_builder.append("select*from(select rownum RNUM, CONTENTDATE, LIKES, CONTENT, SEQ, MEMBER_SEQ, NAME, LASTCOMPANY ");
			query_builder.append("from (select*from CONTENTDBS where MEMBER_SEQ = ? ");
			for(int i=0; i<following_seq_list.size(); i++)
			{
				query_builder.append("or ");
				query_builder.append("MEMBER_SEQ = ? ");
			}
			query_builder.append("order by seq desc)) where RNUM >= ? and RNUM <= ?");
			pstmt = conn.prepareStatement(query_builder.toString());
			
			pstmt.setInt(1, member_seq);
			
			Iterator<Integer> iter = following_seq_list.iterator();
			
			while(iter.hasNext())
			{
				int seq = iter.next();
				index++;
				pstmt.setInt(index, seq);
			}
			pstmt.setInt(index+1, start);
			pstmt.setInt(index+2, end);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				contentdto = new ContentDto();
				
				contentdto.setDate(rs.getString("CONTENTDATE"));
				contentdto.setLikes(rs.getInt("LIKES"));
				contentdto.setContent(rs.getString("CONTENT"));
				contentdto.setSeq(rs.getInt("SEQ"));
				contentdto.setMemberseq(rs.getInt("MEMBER_SEQ"));
				contentdto.setName(rs.getString("NAME"));
				contentdto.setCompany(rs.getString("LASTCOMPANY"));
				
				content_list.add(contentdto);
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
		return content_list;
	}
	
	//글 삭제
	public void deleteContent(int content_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "delete from CONTENTDBS where SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
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
}
