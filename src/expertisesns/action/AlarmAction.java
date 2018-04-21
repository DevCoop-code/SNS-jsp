package expertisesns.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.data.DataInfo;
import expertisesns.eachfollow.SearchEachFollow;
import expertisesns.model.AlarmDao;
import expertisesns.model.AlarmDto;
import expertisesns.model.AlarmInfoDto;
import expertisesns.model.FollowDto;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class AlarmAction implements CommandAction
{
	AlarmDao alarmdao = null;
	MemberDao memberdao = null;
	ProfileDao profiledao = null;
	
	List<AlarmDto> alarminfo_list = null;
	AlarmDto alarmdto = null;
	
	AlarmInfoDto alarm_info = null;
	List<AlarmInfoDto> alarm_info_list = null;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		//session으로 부터 회원 아이디 값 가져오기(receiver email)
		String email = (String)session.getAttribute("id");
		
		if(email != null)
		{
			alarmdao = AlarmDao.getInstance();
			memberdao = MemberDao.getInstance();
			profiledao = ProfileDao.getInstance();
			
			int receiver_seq = profiledao.getSeq(email);
			
			alarminfo_list = alarmdao.getUncheckAlarmInfo(receiver_seq);
			alarm_info_list = new ArrayList<AlarmInfoDto>();
			
			Iterator<AlarmDto> alarminfo_iter = alarminfo_list.iterator();
			while(alarminfo_iter.hasNext())
			{
				alarm_info = new AlarmInfoDto();
				
				alarmdto = alarminfo_iter.next();
				
				//알람의 seq값을 설정
				alarm_info.setSeq(alarmdto.getSeq());
				
				//알람을 보낸 사람의 이름을 설정
				int sender_seq = alarmdto.getSender_seq();
				String sender_name = memberdao.getNameFromSeq(sender_seq);
				alarm_info.setName(sender_name);
				
				//알람을 보낸 사람의 이미지 url을 설정
				String sender_id = memberdao.getIdFromSeq(sender_seq);
				String profilepath = DataInfo.getProfileURL(sender_id);
				alarm_info.setProfile_path(profilepath);
				
				//알람의 내용을 설정
				String type = alarmdto.getType();
				String message = null;
				switch(type)
				{
				case "A":
					message = "팔로우를 신청하셨습니다.";
				break;
				case "B":
					message = "게시물에 좋아요를 하셨습니다.";	
				break;
				case "C":
					message = "게시물에 댓글을 다셨습니다.";
				break;
				case "D":
					message = "댓글에 답글을 다셨습니다.";
				break;
				}
				alarm_info.setMessage(message);
				
				alarm_info_list.add(alarm_info);
			}
			request.setAttribute("alarminfo", alarm_info_list);
			
			int uncheckingAlarmNum = alarmdao.getNotCheckedAlarmNum(receiver_seq);

			request.setAttribute("uncheckingalarm", uncheckingAlarmNum);	
			
			//맞팔로우 데이터를 jsp 페이지로 넘긴다.
			List<FollowDto> Follow_data_list = SearchEachFollow.searchFollow(email);
			request.setAttribute("follow_data", Follow_data_list);
		}

		return "/main/Alarming.jsp";
	}
}
