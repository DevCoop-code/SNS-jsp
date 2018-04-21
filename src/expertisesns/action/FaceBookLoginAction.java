package expertisesns.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.model.FacebookMemberDao;
import expertisesns.model.MemberDao;

public class FaceBookLoginAction implements CommandAction
{
	FacebookMemberDao facebookmemberdao = null;
	
	MemberDao memberdao = null;
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		
		String f_name = request.getParameter("name");
		String f_id = request.getParameter("id");
		
		String facebook_id = "facebook"+f_id;
		
		facebookmemberdao = FacebookMemberDao.getInstance();
		memberdao = MemberDao.getInstance();
		
		int check = facebookmemberdao.checkFacebookMember(facebook_id);
		
		HttpSession session = request.getSession();
		
		//db에 계정이 존재하지 않으므로 db에 계정 정보를 추가
		if(check == -1)
		{
			//정보가 없는 경우 db에 추가
			facebookmemberdao.getFacebookMember(facebook_id, f_name);
			
			File imagefacebook_id[] = new File[51];
			
			//이미지를 담을 폴더를 생성
			imagefacebook_id[0] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/가");
			imagefacebook_id[1] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/나");
			imagefacebook_id[2] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/다");
			imagefacebook_id[3] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/라");
			imagefacebook_id[4] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/마");
			imagefacebook_id[5] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/바");
			imagefacebook_id[6] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/사");
			imagefacebook_id[7] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/아");
			imagefacebook_id[8] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/자");
			imagefacebook_id[9] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/차");
			imagefacebook_id[10] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/카");
			imagefacebook_id[11] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/타");
			imagefacebook_id[12] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/파");
			imagefacebook_id[13] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/하");
			imagefacebook_id[14] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/0");
			imagefacebook_id[15] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/1");
			imagefacebook_id[16] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/2");
			imagefacebook_id[17] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/3");
			imagefacebook_id[18] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/4");
			imagefacebook_id[19] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/5");
			imagefacebook_id[20] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/6");
			imagefacebook_id[21] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/7");
			imagefacebook_id[22] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/8");
			imagefacebook_id[23] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/9");
			imagefacebook_id[24] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/a");
			imagefacebook_id[25] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/b");
			imagefacebook_id[26] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/c");
			imagefacebook_id[27] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/d");
			imagefacebook_id[28] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/e");
			imagefacebook_id[29] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/f");
			imagefacebook_id[30] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/g");
			imagefacebook_id[31] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/h");
			imagefacebook_id[32] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/i");
			imagefacebook_id[33] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/j");
			imagefacebook_id[34] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/k");
			imagefacebook_id[35] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/l");
			imagefacebook_id[36] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/m");
			imagefacebook_id[37] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/n");
			imagefacebook_id[38] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/o");
			imagefacebook_id[39] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/p");
			imagefacebook_id[40] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/q");
			imagefacebook_id[41] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/r");
			imagefacebook_id[42] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/s");
			imagefacebook_id[43] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/t");
			imagefacebook_id[44] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/u");
			imagefacebook_id[45] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/v");
			imagefacebook_id[46] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/w");
			imagefacebook_id[47] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/x");
			imagefacebook_id[48] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/y");
			imagefacebook_id[49] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/z");
			imagefacebook_id[50] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+facebook_id+"/profile");
			
			for(int i=0; i<imagefacebook_id.length; i++)
			{
				if(!imagefacebook_id[i].exists())
					imagefacebook_id[i].mkdirs();
			}
			
			session.setAttribute("id", facebook_id);
			session.setAttribute("name", f_name);
			session.setAttribute("follower", 0);
			session.setAttribute("following", 0);
			session.setAttribute("boardnum", 0);
		}
		//db에 계정이 존재하므로 메인 페이지로 이동
		else if(check == 0)
		{
			int follower = memberdao.getFollower(facebook_id);
			int following = memberdao.getFollowing(facebook_id);
			int boardnum = memberdao.getBoardNum(facebook_id);
			
			session.setAttribute("id", facebook_id);
			session.setAttribute("name", f_name);
			session.setAttribute("follower", follower);
			session.setAttribute("following", following);
			session.setAttribute("boardnum", boardnum);
		}
		
		return "/refresh/HomeRefresh.jsp";
	}
}
