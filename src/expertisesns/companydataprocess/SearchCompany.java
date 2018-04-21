package expertisesns.companydataprocess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SearchCompany 
{
	public static List<CompanyInfo> getCompany()
	{
		List<CompanyInfo> company = new LinkedList<CompanyInfo>();
		
		CompanyInfo companyInfo = null;
		
		BufferedReader br = null;
		
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/data/companyList.txt"),"MS949"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line=br.readLine()) != null)
			{
				sb.append(line+"\n");
			}
			
			String companyList = sb.toString();
			
			StringTokenizer stk = new StringTokenizer(companyList, "\n");
			while(stk.hasMoreTokens())
			{
				StringTokenizer stk2 = new StringTokenizer(stk.nextToken(),"|");
				
				int i=0;
				
				companyInfo = new CompanyInfo();
				while(stk2.hasMoreTokens())
				{
					i++;
					String companydata = stk2.nextToken();
					if(companydata != null)
					{
						//회사이름
						if(i == 7)
						{
							companyInfo.setNameofCompany(companydata);
						}
						//회사주소
						if(i == 8)
						{
							companyInfo.setAddress(companydata);
						}
					}
				}
				company.add(companyInfo);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return company;
	}
}
