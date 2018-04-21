package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import expertisesns.data.DataInfo;

public class ChatDao 
{
	private static ChatDao instance = null;
	public ChatDao(){}
	public static ChatDao getInstance()
	{
		if(instance == null)
		{
			synchronized(ChatDao.class)
			{
				instance = new ChatDao();
			}
		}
		return instance;
	}
	
	public ArrayList<ChatDto> getChatListByID(String fromID, String toID, String chatID)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		ArrayList<ChatDto> chatList = new ArrayList<ChatDto>();
		
		String sql="SELECT * FROM CHAT WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > ? ORDER BY chatTime";
	    try
	    {
	    	conn = ConnUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				ChatDto chat=new ChatDto();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			    chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			    chat.setName(rs.getString("NAME").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			    chat.setProfilePath(rs.getString("PROFILEPATH"));
			    
			    int chatTime=Integer.parseInt(rs.getString("chatTime").substring(11, 13));
			    
			    String timeType="오전";
			    if(chatTime > 12){
			    	timeType = "오후";
			    	chatTime -= 12;
			    }
			    chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);			
			}
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally
	    {
	    	try
	    	{
               if(rs!=null)rs.close();
               if(pstmt!=null)pstmt.close();
               if(conn!=null)conn.close();
	    	}
		    catch(Exception e)
	    	{
		    	e.printStackTrace();
		    }
		}
	    return chatList;
	}
	
	public ArrayList<ChatDto> getChatListByRecent(String fromID, String toID, int number)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		ArrayList<ChatDto> chatList = new ArrayList<ChatDto>();
		
	    try
	    {
	    	conn = ConnUtil.getConnection();
	    	String sql="SELECT * FROM CHAT WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > (SELECT MAX(chatID) - ? FROM CHAT) ORDER BY chatTime";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				ChatDto chat=new ChatDto();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			    chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
			    int chatTime=Integer.parseInt(rs.getString("chatTime").substring(11, 13));
			    String timeType="오전";
			    if(chatTime > 12)
			    {
			    	timeType = "오후";
			    	chatTime -= 12;
			    }
			    chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);			
			}
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally
	    {
	    	try
	    	{
               if(rs!=null)rs.close();
               if(pstmt!=null)pstmt.close();
               if(conn!=null)conn.close();
	    	}catch(Exception e)
	    	{
		    	e.printStackTrace();
		    }
	    }
	    return chatList;
	}
	
	public int submit(String fromID, String toID, String chatContent)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
	    try
	    {
	    	conn = ConnUtil.getConnection();
	    	//프로필 경로 추가
	    	String profilePath = DataInfo.getProfileURL(fromID);
	    	
	    	String sql="INSERT INTO CHAT(CHATID, FROMID, TOID, CHATCONTENT, CHATTIME, CHECKING, NAME, PROFILEPATH) VALUES (CHAT_SEQ.nextval, ?, ?, ?, sysdate, 0, (select NAME from MEMBERS where ID = ?), ?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			pstmt.setString(4, fromID);
			pstmt.setString(5, profilePath);
			
			return pstmt.executeUpdate();				
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally
	    {
	    	try
	    	{
               if(rs!=null)rs.close();
               if(pstmt!=null)pstmt.close();
               if(conn!=null)conn.close();
	    	}
		    catch(Exception e)
	    	{
		    	e.printStackTrace();
		    }
	    }
	    return -1;
	}
	public int readChat(String fromID, String toID){
		Connection conn=null;
		PreparedStatement pstmt=null;

		
	    try
	    {
	    	conn = ConnUtil.getConnection();
	    	String sql="UPDATE CHAT SET CHECKING = 1 WHERE (fromID = ? AND toID = ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, toID);
			pstmt.setString(2, fromID);
		    return pstmt.executeUpdate();
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally
	    {
	    	try
	    	{
               if(pstmt!=null)pstmt.close();
               if(conn!=null)conn.close();
	    	}
		    catch(Exception e)
	    	{
		    	e.printStackTrace();
		    }
	    }
	    return -1;
	}
	public String getAllunReadChat(String toID){
		Connection conn=null;
		PreparedStatement pstmt=null;
        ResultSet rs=null;
		String status=null;
	    try
	    {
	    	conn = ConnUtil.getConnection();
	    	String sql="SELECT CHECKING FROM CHAT WHERE FROMID = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, toID);
			
		    rs=pstmt.executeQuery();	    
		    while(rs.next()){
		    	int check=rs.getInt("CHECKING");
		    	if(check==0){
		    		
		    		status="!";
		    	}
		    	else if(check==1){

		    		status="@";
		    	}
		    	
		    }
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally
	    {
	    	try
	    	{
               if(pstmt!=null)pstmt.close();
               if(conn!=null)conn.close();
	    	}
		    catch(Exception e)
	    	{
		    	e.printStackTrace();
		    }
	    }
	    return status;
	}
}
