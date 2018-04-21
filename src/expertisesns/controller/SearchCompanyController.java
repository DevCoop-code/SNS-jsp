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
import expertisesns.companydataprocess.CompanyInfo;
import expertisesns.companydataprocess.SearchCompany;

public class SearchCompanyController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<CompanyInfo> companyinfo = null;
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		int next = 0;
		
		String companyname = request.getParameter("companyname");
		System.out.println("companyname = " + companyname);
		
		PrintWriter out = response.getWriter();
		out.println("{");
		out.println("\"results\" : ");
		out.println("[");
		companyinfo = SearchCompany.getCompany();
		
		Iterator<CompanyInfo> companyiter = companyinfo.iterator();
		while(companyiter.hasNext())
		{
			CompanyInfo companyinformation = companyiter.next();
			if(companyname != null)
			{
				int result = new MatchingAlgorithm(companyinformation.getNameofCompany(), companyname).KMP();
				
				if(/*companyinformation.getNameofCompany().equals(companyname)*/ result >= 0)
				{
					if(next > 0)
						out.print(",");
					next++;
					out.println("{");
					String nameofcompany = companyinformation.getNameofCompany();
					out.println("\"CompanyName\" : " + "\"" +nameofcompany + "\",");
					String addressofcompany = companyinformation.getAddress();
					out.println("\"CompanyAddress\" : " + "\"" +addressofcompany + "\"");
					out.println("}");
				}	
			}
		}
		out.println("]");
		out.println("}");
	}
}
