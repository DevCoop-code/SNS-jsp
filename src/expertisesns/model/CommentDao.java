package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao 
{
	public static CommentDao instance = null;
	
	private CommentDao(){};
	
	public static CommentDao getInstance()
	{
		if(instance == null)
		{
			synchronized(CommentDao.class)
			{
				instance = new CommentDao();
			}
		}
		return instance;
	}
	
	//댓글 저장
	public void insertComment(String commenterId, int contentSeq, String comment, String time)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into COMMENTSDB(SEQ, CONTENT_SEQ, COMMENTER_ID, TODAY_DATE, COMMENTS) values(COMMENT_SEQ.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, contentSeq);
			pstmt.setString(2, commenterId);
			pstmt.setString(3, time);
			pstmt.setString(4, comment);
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
	
	//해당하는 글의 댓글 불러오기 
	public List<CommentDto> getComments(int content_seq, int start, int end)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<CommentDto> comments_list = new ArrayList<CommentDto>();
		CommentDto commentdto = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select*from(select rownum RNUM, SEQ, CONTENT_SEQ, COMMENTER_ID, TODAY_DATE, COMMENTS from (select*from COMMENTSDB where CONTENT_SEQ = ? order by seq desc)) where RNUM >= ? and RNUM <= ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				commentdto = new CommentDto();
				
				commentdto.setComment_seq(rs.getInt("SEQ"));
				commentdto.setContent_seq(rs.getInt("CONTENT_SEQ"));
				commentdto.setCommerter_id(rs.getString("COMMENTER_ID"));
				commentdto.setToday_date(rs.getString("TODAY_DATE"));
				commentdto.setComments(rs.getString("COMMENTS"));
				
				comments_list.add(commentdto);
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
		return comments_list;
	}
}
