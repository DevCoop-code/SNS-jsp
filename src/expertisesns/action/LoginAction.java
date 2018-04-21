package expertisesns.action;

import java.io.File;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.model.MemberDao;
import expertisesns.model.MemberDto;

public class LoginAction implements CommandAction
{
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		MemberDto memberDto = new MemberDto();
		MemberDao memberDao = new MemberDao();
		
		int check = -1;
		try
		{
			request.setCharacterEncoding("utf-8");
			
			String familyname = request.getParameter("familyname");
			String name = request.getParameter("username");
			memberDto.setName(familyname+name);
			
			String email = request.getParameter("email");
			memberDto.setEmail(email);
			
			String password = request.getParameter("password");
			memberDto.setPassword(password);
			
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String days = request.getParameter("days");
			memberDto.setBirth(year+"-"+month+"-"+days);
			
			int sex = Integer.parseInt(request.getParameter("sex"));
			memberDto.setSex(sex);
			
			//아이디 존재 유무 확인
			check = memberDao.getMember(memberDto);
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		//db에 이메일이 존재하지 않아 db에 이메일 등록 후 
		//이메일 인증 시작
		if(check == 1)
		{
			String email = memberDto.getEmail();
			String folder = null;
			request.setAttribute("sender", email);
			File imageFolder[] = new File[51];
			
			StringTokenizer foldername = new StringTokenizer(email, ".");
			if(foldername.hasMoreTokens())
			{
				folder = foldername.nextToken();
			}
			
			imageFolder[0] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/other");
			imageFolder[14] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/0");
			imageFolder[15] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/1");
			imageFolder[16] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/2");
			imageFolder[17] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/3");
			imageFolder[18] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/4");
			imageFolder[19] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/5");
			imageFolder[20] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/6");
			imageFolder[21] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/7");
			imageFolder[22] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/8");
			imageFolder[23] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/9");
			imageFolder[24] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/a");
			imageFolder[25] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/b");
			imageFolder[26] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/c");
			imageFolder[27] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/d");
			imageFolder[28] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/e");
			imageFolder[29] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/f");
			imageFolder[30] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/g");
			imageFolder[31] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/h");
			imageFolder[32] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/i");
			imageFolder[33] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/j");
			imageFolder[34] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/k");
			imageFolder[35] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/l");
			imageFolder[36] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/m");
			imageFolder[37] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/n");
			imageFolder[38] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/o");
			imageFolder[39] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/p");
			imageFolder[40] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/q");
			imageFolder[41] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/r");
			imageFolder[42] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/s");
			imageFolder[43] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/t");
			imageFolder[44] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/u");
			imageFolder[45] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/v");
			imageFolder[46] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/w");
			imageFolder[47] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/x");
			imageFolder[48] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/y");
			imageFolder[49] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/z");
			imageFolder[50] = new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/memberImageStorage/"+folder+"/profile");
			
			for(int i=0; i<imageFolder.length; i++)
			{
				if(!imageFolder[i].exists())
					imageFolder[i].mkdirs();
			}
			return "/baconnetwork/emailauthentication.do";
		}
		//db에 이메일 존재하고 이메일 인증까지 완료된 계정
		//존재하는 이메일
		else if(check == 2)
		{
			request.setAttribute("check", "exists");
			return "/sign/MainEntrance.jsp";
		}
		//db에 이메일 존재하지만 이메일 인증이 미완료된 계정
		else if(check == 3)
		{
			return "/sign/AuthenticateEmail.jsp";
		}
		else
		{
			return "/sign/MainEntrance.jsp";
		}
	}
}
