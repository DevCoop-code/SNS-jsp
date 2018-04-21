package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlarmDao 
{
	public static AlarmDao instance = null;
	
	private AlarmDao(){};
	
	public static AlarmDao getInstance()
	{
		if(instance == null)
		{
			synchronized(AlarmDao.class)
			{
				instance = new AlarmDao();
			}
		}
		return instance;
	}
	
	//게시글 seq를 통해 게시글 작성한 사람의 seq값 가져오기
	public int getReceiverSeq(int content_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int receiver_seq = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select MEMBER_SEQ from CONTENTDBS where SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, content_seq);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				receiver_seq = rs.getInt("MEMBER_SEQ");
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
		return receiver_seq;
	}
	
	//알람 정보를 저장
	public void addAlarmInfo(int receiver_seq, int sender_seq, String type)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "insert into ALARM_DB(SEQ, RECEIVER_SEQ, SENDER_SEQ, ALARM_TYPE, CHECKING) values(ALARM_SEQ.nextval, ?, ?, ?, 0)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, receiver_seq);
			pstmt.setInt(2, sender_seq);
			pstmt.setString(3, type);
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
	
	//확인하지 않은 알람의 갯수를 가져옴
	public int getNotCheckedAlarmNum(int receiver_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int unchecking_alarm = 0;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select CHECKING from ALARM_DB where RECEIVER_SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, receiver_seq);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int checkornot = rs.getInt("CHECKING");
				if(checkornot == 0)
					unchecking_alarm ++;
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
		
		return unchecking_alarm;
	}
	
	//체크하지 않은 알람의 정보를 가져옴
	public List<AlarmDto> getUncheckAlarmInfo(int receiver_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<AlarmDto> alarm_list = new ArrayList<AlarmDto>();
		
		AlarmDto alarmdto = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select SEQ, SENDER_SEQ, ALARM_TYPE from ALARM_DB where CHECKING=0 and RECEIVER_SEQ=? order by seq desc";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, receiver_seq);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				alarmdto = new AlarmDto();
				
				alarmdto.setSeq(rs.getInt("SEQ"));
				alarmdto.setSender_seq(rs.getInt("SENDER_SEQ"));
				alarmdto.setType(rs.getString("ALARM_TYPE"));
				
				alarm_list.add(alarmdto);
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
		return alarm_list;
	}
	
	//알람을 체크했을시 체크
	public void checkTheCheckedAlarm(int seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update ALARM_DB set CHECKING=1 where SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, seq);
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
