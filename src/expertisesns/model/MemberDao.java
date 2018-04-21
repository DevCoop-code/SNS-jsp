package expertisesns.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import expertisesns.data.DataInfo;

public class MemberDao 
{
	public static MemberDao instance = null;
	
	//생성자 함수 
	public MemberDao(){}
	
	public static MemberDao getInstance()
	{
		if(instance == null)
		{
			synchronized(MemberDao.class)
			{
				instance = new MemberDao();
			}
		}
		return instance;
	}
	
	//회원 가입 아이디 존재 유무 확인후 db에 넣을지 안넣을지 확인
	public int getMember(MemberDto memberDto)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = 1;		//존재하지 않는 이메일 이므로 회원가입 성공
		
		String hashPassword = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select ID from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberDto.getEmail());
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				String id = rs.getString("ID");
				System.out.println("email : "+id);
				System.out.println("존재하는 이메일 입니다.");
				check = 0;
			}
			//존재하는 이메일일때 이메일 인증 하였는지 여부 
			if(check == 0)
			{
				System.out.println("이메일 인증 여부 확인 진행");
				query = "select EMAILCHECK from MEMBERS where ID = ?";
				pstmt.close();
				rs.close();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, memberDto.getEmail());
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					int emailcheck = rs.getInt("EMAILCHECK");
					//이메일 인증까지 완료된 계정 
					if(emailcheck == 1)
						check = 2;
					//이메일 인증 미완료 계정 
					else if(emailcheck == 0)
						check = 3;
				}
			}
			//존재하지 않는 이메일 이므로 회원 db에 저장
			if(check == 1)
			{
				System.out.println("회원가입 진행");
				query = "insert into MEMBERS(SEQ, NAME, ID, PASSWORD, BIRTH, SEX, FOLLOWER, FOLLOWING, BOARDNUM, EMAILCHECK) values(MEMBERS_SEQ.nextval,?,?,?,?,?,0,0,0,0)";
				pstmt.close();
				pstmt = conn.prepareStatement(query);
				
				hashPassword = BCrypt.hashpw(memberDto.getPassword(), BCrypt.gensalt());
				
				pstmt.setString(1, memberDto.getName());
				pstmt.setString(2, memberDto.getEmail());
				pstmt.setString(3, hashPassword);
				pstmt.setString(4, memberDto.getBirth());
				pstmt.setInt(5, memberDto.getSex());
				pstmt.executeUpdate();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("MemberDao loginCheck Function"+e);
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
	
	//이메일 인증 함수
	public void authenticateEmail(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "UPDATE MEMBERS SET EMAILCHECK=1 WHERE ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
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
	
	//ID/PASSWORD를 비교하여 결과를 정수 값으로 반환하는 메서드
	public int loginCheck(String email, String pass)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = -1;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select EMAILCHECK from MEMBERS where ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int emailcheck = rs.getInt("EMAILCHECK");
				if(emailcheck == 1)
				{
					query = "select PASSWORD from MEMBERS where ID=?";
					pstmt.close();
					rs.close();
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, email);
					rs = pstmt.executeQuery();
					if(rs.next())
					{
						String storedPassword = rs.getString("PASSWORD");
						if(BCrypt.checkpw(pass, storedPassword))
							check=1;			//아이디와 비밀번호가 같은 경우
						else
							check=2;			//비밀번호가 틀린 경우
					}
				}else
				{
					check = 0;					//인증되지 않은 계정의 경우
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
        {
            if(rs != null)
            {
                try{rs.close();}catch(SQLException sqle1){}
            }
            if(pstmt != null)
            {
                try{pstmt.close();}catch(SQLException sqle1){}
            }
            if(conn != null)
            {
                try{conn.close();}catch(SQLException sqle1){}
            }
        }
        return check;
	}
	
	//아이디로 이름 데이터 가져오기
	public String getName(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String name = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select NAME from MEMBERS where ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				name = rs.getString("NAME");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
	        {
	            try{rs.close();}catch(SQLException sqle1){}
	        }
	        if(pstmt != null)
	        {
	            try{pstmt.close();}catch(SQLException sqle1){}
	        }
	        if(conn != null)
	        {
	            try{conn.close();}catch(SQLException sqle1){}
	        }
		}
		return name;
	}
	
	//아이디로 팔로워 명수 가져오기
	public int getFollower(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int followerNum = -1;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select FOLLOWER from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				followerNum = rs.getInt("FOLLOWER");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
	        {
	            try{rs.close();}catch(SQLException sqle1){}
	        }
	        if(pstmt != null)
	        {
	            try{pstmt.close();}catch(SQLException sqle1){}
	        }
	        if(conn != null)
	        {
	            try{conn.close();}catch(SQLException sqle1){}
	        }
		}
		return followerNum;
	}
	
	//아이디로 팔로잉 명수 가져오기
	public int getFollowing(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int followingNum = -1;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select FOLLOWING from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				followingNum = rs.getInt("FOLLOWING");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
	        {
	            try{rs.close();}catch(SQLException sqle1){}
	        }
	        if(pstmt != null)
	        {
	            try{pstmt.close();}catch(SQLException sqle1){}
	        }
	        if(conn != null)
	        {
	            try{conn.close();}catch(SQLException sqle1){}
	        }
		}
		return followingNum;
	}
	
	//아이디로 게시판 글 갯수 가져오기
	public int getBoardNum(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int boardNum = -1;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select BOARDNUM from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				boardNum = rs.getInt("BOARDNUM");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
	        {
	            try{rs.close();}catch(SQLException sqle1){}
	        }
	        if(pstmt != null)
	        {
	            try{pstmt.close();}catch(SQLException sqle1){}
	        }
	        if(conn != null)
	        {
	            try{conn.close();}catch(SQLException sqle1){}
	        }
		}
		return boardNum;
	}
	
	//아이디로 사용자의 전문성 가져오기
	public String getExpertise(String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String ownexpertise = "none";
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select EXPERTISE from MEMBERS where ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				ownexpertise = rs.getString("EXPERTISE");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
	        {
	            try{rs.close();}catch(SQLException sqle1){}
	        }
	        if(pstmt != null)
	        {
	            try{pstmt.close();}catch(SQLException sqle1){}
	        }
	        if(conn != null)
	        {
	            try{conn.close();}catch(SQLException sqle1){}
	        }
		}
		return ownexpertise;
	}
	
	//프로필 이미지 작업 처리 함수
	public void updateProfile(String filename, String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "update MEMBERS set PROFILEIMAGE=? where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, filename);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
	        if(pstmt != null)
	        {
	            try{pstmt.close();}catch(SQLException sqle1){}
	        }
	        if(conn != null)
	        {
	            try{conn.close();}catch(SQLException sqle1){}
	        }
		}
	}
	
	//아이디로 프로필 이미지 가져오기 
	public String getProfile(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ownProfilePicture = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select PROFILEIMAGE from MEMBERS where ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				ownProfilePicture = rs.getString("PROFILEIMAGE");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		return ownProfilePicture;
	}
	
	//이름으로 사람 검색
	public List<SearchingPeopleDto> getSearchingPeopleInfo(String name)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		SearchingPeopleDto searchingpeopledto = null;
		List<SearchingPeopleDto> people_list = new ArrayList<SearchingPeopleDto>();
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select SEQ, ID, NAME, HIGHESTSCHOOLING, EXPERTISE, LASTCOMPANY from MEMBERS where NAME like ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+name+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				searchingpeopledto = new SearchingPeopleDto();
				
				searchingpeopledto.setSeq(rs.getInt("SEQ"));
				searchingpeopledto.setName(rs.getString("NAME"));
				searchingpeopledto.setHighestschooling(rs.getString("HIGHESTSCHOOLING"));
				searchingpeopledto.setExpertise(rs.getString("EXPERTISE"));
				searchingpeopledto.setLastcompany(rs.getString("LASTCOMPANY"));
				
				String profile_url = DataInfo.getProfileURL(rs.getString("ID"));
				searchingpeopledto.setProfileurl(profile_url);
				people_list.add(searchingpeopledto);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		
		return people_list;
	}
	
	//내가 이미 팔로잉을 했는지 여부 확인
	public int checkFollowing(int my_seq, int people_seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int check = 1;	//check=-1 : 이미 팔로잉 한 사람 / check=1: 팔로잉 안한 사람 
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select FOLLOWING_SEQ from FOLLOWINGDB where seq = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, my_seq);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int followingseq = rs.getInt("FOLLOWING_SEQ");
				if(followingseq == people_seq)
					check = -1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		return check;
	}
	
	//Members 테이블의 SEQ값 다 가져오기
	public List<Integer> getAllOfSeq()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Integer> seq_list = new ArrayList<Integer>();
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select seq from members";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int seq = rs.getInt("SEQ");
				seq_list.add(seq);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		return seq_list;
	}
	
	//Members 의 seq, name, highestschooling, expertise, lastcompany 값을 다 가져오기
	public List<PersonGrade> getMemberWithEssentialOption()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<PersonGrade> person_grade_list = new ArrayList<PersonGrade>();
		
		PersonGrade persongrade = null;
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select SEQ, ID, NAME, HIGHESTSCHOOLING, EXPERTISE, LASTCOMPANY, PROFILEIMAGE from members";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				persongrade = new PersonGrade();
				
				persongrade.setMember_seq(rs.getInt("SEQ"));
				persongrade.setName(rs.getString("NAME"));
				persongrade.setSchool(rs.getString("HIGHESTSCHOOLING"));
				persongrade.setExpertise(rs.getString("EXPERTISE"));
				persongrade.setCompany(rs.getString("LASTCOMPANY"));
				
				String profile_path = DataInfo.getProfileURL(rs.getString("ID"));
				persongrade.setProfilePath(profile_path);
				
				persongrade.setGrade(0);
				
				person_grade_list.add(persongrade);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		return person_grade_list;
	}
	
	//아이디 값으로 회원정보(seq, 이름, 전문분야, 학교, 회사, 프로필 경로) 가져오기
	public PeopleInfoDto getMyInfo(String id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PeopleInfoDto peopleinfodto = new PeopleInfoDto();
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select SEQ, ID, NAME, HIGHESTSCHOOLING, EXPERTISE, LASTCOMPANY from members where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				peopleinfodto.setSeq(rs.getInt("SEQ"));
				peopleinfodto.setName(rs.getString("NAME"));
				peopleinfodto.setSchool(rs.getString("HIGHESTSCHOOLING"));
				peopleinfodto.setExpertise(rs.getString("EXPERTISE"));
				peopleinfodto.setCompany(rs.getString("LASTCOMPANY"));
				
				String profileurl = DataInfo.getProfileURL(rs.getString("ID"));
				peopleinfodto.setProfile_path(profileurl);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		return peopleinfodto;
	}
	
	//seq데이터로 이름가져오기 
	public String getNameFromSeq(int seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String name = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select NAME from MEMBERS where SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				name = rs.getString("NAME");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		
		return name;
	}
	
	//seq데이터로 아이디 가져오기 
	public String getIdFromSeq(int seq)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String id = null;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select ID from MEMBERS where SEQ = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				id = rs.getString("ID");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs != null)
		    {
		        try{rs.close();}catch(SQLException sqle1){}
		    }
		    if(pstmt != null)
		    {
		        try{pstmt.close();}catch(SQLException sqle1){}
		    }
		    if(conn != null)
		    {
		        try{conn.close();}catch(SQLException sqle1){}
		    }
		}
		
		return id;
	}
	
	public int getEmail(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int check = -1;
		
		try
		{
			conn = ConnUtil.getConnection();
			String query = "select ID from MEMBERS where ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				check = 0;
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
	//패스워드 생성
	public String RandomPassword(String email){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String password = ""; 
		String hashPassword="";
			for(int i=0; i<8; i++){ 
				int rndVal = (int)(Math.random() * 62); 
				if(rndVal < 10) { password += rndVal; } 
				else if(rndVal > 35) { password += (char)(rndVal + 61); } 
				else { password += (char)(rndVal + 55); } }
			hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			try
			{
				conn = ConnUtil.getConnection();
				String query = "update MEMBERS set PASSWORD=? where ID = ?";
				pstmt = conn.prepareStatement(query);
				System.out.println(111);		
				pstmt.setString(1, hashPassword);
                pstmt.setString(2, email);
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
				if(rs != null)
					try{rs.close();}catch(SQLException sqle1){}
			}
		return password;
		}
}
