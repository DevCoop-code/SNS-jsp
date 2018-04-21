package expertisesns.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.KMP.MatchingAlgorithm;
import expertisesns.schooldataprocess.SearchSchool;
import expertisesns.schooldataprocess.UnivInfo;

public class SearchSchoolController extends HttpServlet
{	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		int next = 0;
		
		PrintWriter out = response.getWriter();
		out.println("{");
		out.println("\"results\" : ");
		out.println("[");
		List<UnivInfo> schoolInfoList = null;
		
		//고졸 = highschool
		//대졸이상 = university
		String lastschool = request.getParameter("education");
		System.out.println("SchoolKind = "+lastschool);
		
		//입력한 학교이름
		String schoolname = request.getParameter("schoolname");
		System.out.println("School Name = "+schoolname);
		
		//학교 리스트 가져오기
		schoolInfoList = SearchSchool.getSchool(lastschool);
		
		Iterator<UnivInfo> schooliter = schoolInfoList.iterator();
		
		do
		{
			UnivInfo univinfo = schooliter.next();
			String univname = univinfo.getNameofUniv();
			
			if(univname != null)
			{
				int result = new MatchingAlgorithm(univname, schoolname).KMP();
				
				if(/*schoolname.equals(univname.trim())*/ result >= 0)
				{
					if(next > 0)
						out.print(",");
					
					next++;
					
					out.println("{");
					
					String kindofUniv = univinfo.getKindofUniv();
					out.println("\"kindofUniv\" : "+"\""+kindofUniv+"\",");
					
					String nameofUniv = univinfo.getNameofUniv();
					out.println("\"nameofUniv\" : "+"\""+nameofUniv+"\",");
					
					String branchSchoolOrNot = univinfo.getBranchSchoolOrNot();
					out.println("\"branchSchoolOrNot\" : "+"\""+branchSchoolOrNot+"\",");
					
					String establishment = univinfo.getEstablishment();
					out.println("\"establishment\" : "+"\""+establishment+"\",");
					
					String address = univinfo.getAddress();
					out.println("\"address\" : "+"\""+address+"\"");
					
					out.println("}");
				}
			}
		}
		while(schooliter.hasNext());
		
		out.println("]");
		out.println("}");
		
		/*
		 * {
		 * 		results:
		 * 		[
		 * 			{
		 * 				"kindofUniv": " ",
		 * 				"nameofUniv": " ",
		 * 				"branchSchoolOrNot": " ",
		 * 				"establishment": " ",
		 * 				"address": " "
		 * 			},
		 * 		]
		 * }
		 */
	}
}