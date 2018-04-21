package expertisesns.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import expertisesns.alarm.UnCheckingAlarm;
import expertisesns.model.MemberDao;
import expertisesns.model.SearchingPeopleDto;

public class SearchPeopleAction implements CommandAction
{
	MemberDao memberdao = null;
	List<SearchingPeopleDto> searchpeople_list = null;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		String people = request.getParameter("people");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		if(people != null && id != null)
		{
			memberdao = MemberDao.getInstance();
			
			searchpeople_list = memberdao.getSearchingPeopleInfo(people);
			
			request.setAttribute("people", people);
			request.setAttribute("searchpeoplelist", searchpeople_list);	
			
			int alarm_num = UnCheckingAlarm.uncheckingAlarmNum(id);

			//알람 갯수를 jsp 페이지로 넘긴다 
			request.setAttribute("uncheckingalarm", alarm_num);
		}
		
		return "/main/SearchPeople.jsp";
	}
}
