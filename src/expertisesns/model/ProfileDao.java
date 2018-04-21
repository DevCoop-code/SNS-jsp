package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileDao 
{
	public static ProfileDao instance = null;
	
	//생성자 함수
	public ProfileDao(){}
	
	public static ProfileDao getInstance()
	{
		if(instance == null)
		{
			synchronized(ProfileDao.class)
			{
				instance = new ProfileDao();
			}
		}
		return instance;
	}
	
	//회원 profile 갱신 - 필수 항목 업데이트(현제상태, 현재근무중인회사, 학력, 전문분야)
	public void updateProfile(String value, String id, String field)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update members set " + field + " =? where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, value);
			pstmt.setString(2, id);
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
	
	//회원 profile 데이터 가져오기 - 필수 항목 데이터
	public String getProfile(String id, String field)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String value = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select "+field+" from members where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				value = rs.getString(field);
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
		return value;
	}
	
	//회원 profile 갱신 - 옵션 항목 업데이트(수상경력, 자격증, 교육이수내역, 전문기술)
	public void optionProfile(String[] optiondata, String id, String dbname)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int seq = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select seq from members where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				seq = rs.getInt("SEQ");
			}
			
			if(optiondata != null && seq != -1)
			{
				query = "delete from " + dbname + " where seq = ?";
				pstmt.close();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, seq);
				pstmt.executeUpdate();
				
				query = "insert into " + dbname + " values(?, ?)";
				pstmt.close();
				pstmt = conn.prepareStatement(query);
				for(int i=0; i<optiondata.length; i++)
				{
					pstmt.setInt(1, seq);
					pstmt.setString(2, optiondata[i]);
					pstmt.executeUpdate();
				}
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
	}
	
	//id로 회원 정보(seq)가져오기
	public int getSeq(String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int seq = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select seq from members where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				seq = rs.getInt("SEQ");
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
		return seq;
	}
	//회원 정보(seq, 이름, 경력)가져오기
	public ContentNeededMemberInfo getMemberInfo(String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int seq = -1;
		ContentNeededMemberInfo contentneeded = new ContentNeededMemberInfo();
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select seq, name, lastcompany from members where id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				contentneeded.setSeq(rs.getInt("SEQ"));
				contentneeded.setMember_name(rs.getString("NAME"));
				contentneeded.setMember_career(rs.getString("LASTCOMPANY"));
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
		return contentneeded;
	}
	
	//회원 profile 데이터 가져오기 - 필수 항목 데이터
	public List<String> getOptionalProfile(int seq, String field, String table)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<String> optionValue = new ArrayList<String>();
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select "+field+" from "+table+" where seq=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				optionValue.add(rs.getString(field));
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
		return optionValue;
	}
}
