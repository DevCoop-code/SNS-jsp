package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class SettingDao 
{
	public static SettingDao instance = null;
	
	private SettingDao(){}
	
	public static SettingDao getInstance()
	{
		if(instance == null)
		{
			synchronized(SettingDao.class)
			{
				instance = new SettingDao();
			}
		}
		return instance;
	}
	
	public SettingDto getSettingsData(String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		SettingDto settingdto = new SettingDto();
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select NAME, BIRTH, SEX, ADDRESS, TELEPHONE from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				settingdto.setName(rs.getString("NAME"));
				settingdto.setBirth(rs.getString("BIRTH"));
				settingdto.setSex(rs.getInt("SEX"));
				settingdto.setAddress(rs.getString("ADDRESS"));
				settingdto.setTel(rs.getString("TELEPHONE"));
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
		return settingdto;
	}
	
	public void updateSettingData(String type, String content, String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set "+type+"=? where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, content);
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
	
	public void updateSexData(String type, int content, String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set "+type+"=? where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content);
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
	
	public void updatePasswordData(String type, String content, String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String hashPassword = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set "+type+"=? where id=?";
			pstmt = conn.prepareStatement(query);
			
			hashPassword = BCrypt.hashpw(content, BCrypt.gensalt());
			
			pstmt.setString(1, hashPassword);
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
}
