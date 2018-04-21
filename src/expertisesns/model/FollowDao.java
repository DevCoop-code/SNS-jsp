package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowDao 
{
	public static FollowDao instance = null;
	
	private FollowDao(){};
	
	public static FollowDao getInstance()
	{
		if(instance == null)
		{
			synchronized(FollowDao.class)
			{
				instance = new FollowDao();
			}
		}
		return instance;
	}
	
	//팔로잉 처리 
	public void followingProcess(int my_seq, int person_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into FOLLOWINGDB(SEQ, FOLLOWING_SEQ) values(?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, my_seq);
			pstmt.setInt(2, person_seq);
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
	
	//팔로우 처리 
	public void followerProcess(int person_seq, int my_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into FOLLOWERDB(SEQ, FOLLOWER_SEQ) values(?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, person_seq);
			pstmt.setInt(2, my_seq);
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
	
	//자신의 팔로잉 갯수 올리기 
	public void IncrementFollowing(int my_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set FOLLOWING=FOLLOWING+1 where seq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, my_seq);
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
	
	//상대의 팔로워 갯수 올리기
	public void IncrementFollower(int person_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set FOLLOWER=FOLLOWER+1 where seq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, person_seq);
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
	
	//내가 팔로잉 하는 사람들(Following) SEQ값 가져오기 
	public List<Integer> getFollowingSeq(int my_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Integer> following_seq = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select FOLLOWING_SEQ from FOLLOWINGDB where SEQ = ? order by FOLLOWING_SEQ asc";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, my_seq);
			rs = pstmt.executeQuery();
			
			following_seq = new ArrayList<Integer>();
			
			while(rs.next())
			{
				int followingseq = rs.getInt("FOLLOWING_SEQ");
				
				following_seq.add(followingseq);
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
		return following_seq;
	}
	
	//나를 팔로우 하는 사람들(Follower)
	public List<Integer> getFollowerSeq(int my_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Integer> follower_seq = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select FOLLOWER_SEQ from FOLLOWERDB where SEQ = ? order by FOLLOWER_SEQ asc";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, my_seq);
			rs = pstmt.executeQuery();
			
			follower_seq = new ArrayList<Integer>();
					
			while(rs.next())
			{
				int followerseq = rs.getInt("FOLLOWER_SEQ");
				
				follower_seq.add(followerseq);
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
		
		return follower_seq;
	}
	
	//내가 상대방 팔로잉 취소 
	public void CancelFollowing(int seq, int following_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "delete from FOLLOWINGDB where SEQ = ? and FOLLOWING_SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, seq);
			pstmt.setInt(2, following_seq);
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
	
	//상대방의 팔로워로써 취소
	public void CancelFollower(int following_seq, int seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "delete from FOLLOWERDB where SEQ = ? and FOLLOWER_SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, following_seq);
			pstmt.setInt(2, seq);
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
	
	//자신의 팔로잉 줄이기 올리기 
	public void DecrementFollowing(int my_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set FOLLOWING=FOLLOWING-1 where seq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, my_seq);
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
		
	//상대의 팔로워 갯수 줄이기
	public void DecrementFollower(int person_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set FOLLOWER=FOLLOWER-1 where seq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, person_seq);
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
