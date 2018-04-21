package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacebookMemberDao 
{
	public static FacebookMemberDao instance = null;
	
	public FacebookMemberDao(){}
	
	public static FacebookMemberDao getInstance()
	{
		if(instance == null)
		{
			synchronized(FacebookMemberDao.class)
			{
				instance = new FacebookMemberDao();
			}
		}
		return instance;
	}
	
	//facebook 로그인시 회원정보가 db에 있는지 확인 
	public int checkFacebookMember(String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//존재하지 않는 계정
		int check = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select ID from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			//존재하는 계정
			while(rs.next())
			{
				check = 0;
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
		return check;
	}
	
	//facebook 로그인시 회원정보 db에 저장 
	public void getFacebookMember(String id, String name)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into MEMBERS(SEQ, NAME, ID, PASSWORD, FOLLOWER, FOLLOWING, BOARDNUM) values(MEMBERS_SEQ.nextval,?,?,?,0,0,0)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, "itJustFacebook");
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
