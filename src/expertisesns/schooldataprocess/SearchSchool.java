package expertisesns.schooldataprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SearchSchool 
{
	public static List<UnivInfo> getSchool(String school_kind)
	{
		List<UnivInfo> school = new LinkedList<UnivInfo>();
		
		UnivInfo univInfo = null;
		
		int rowindex = 0;
		int columnindex = 0;
		
		FileInputStream fis = null;
		try
		{
			if(school_kind.equals("university"))
			{
				System.out.println("University Data Search...");
				fis = new FileInputStream(new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/data/UniversityDataSet.xls"));	
			}
			else if(school_kind.equals("highschool"))
			{
				System.out.println("HighSchool Data Search...");
				fis = new FileInputStream(new File("/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/data/HighSchoolDataSet.xls"));	
			}
			else
			{
				System.out.println("No Data Search...");
				fis = null;
			}
			
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			
			//몇번째 시트를 읽는 지를 지정 ex) 첫번째 시트를 읽는다면 0을 넣어주면 됨
			//각 시트마다 읽고 싶다면 반문을 통해 읽어주면 됨
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			//행의 수
			int rows = sheet.getPhysicalNumberOfRows();
		
			for(rowindex=1; rowindex<rows; rowindex++)
			{
				//행을 읽는다
				HSSFRow row = sheet.getRow(rowindex);
				
				univInfo = new UnivInfo();
				
				if(row != null)
				{
					//열의 수 (셀의 수)
					int cells = row.getPhysicalNumberOfCells();
					
					for(columnindex=0; columnindex<cells; columnindex++)
					{
						//셀값을 읽음 
						HSSFCell cell = row.getCell(columnindex);
						
						if(cell == null)
							continue;
						else if(cells >= 12)
						{
							if(school_kind.equals("university"))
							{
								switch(columnindex)
								{
								//학교 종류
								case 1:
									univInfo.setKindofUniv(cell.getStringCellValue());
									break;
								//학교명
								case 3:
									univInfo.setNameofUniv(cell.getStringCellValue());
									break;
								//학교 본교 분교
								case 4:
									univInfo.setBranchSchoolOrNot(cell.getStringCellValue());
									break;
								case 6:
									univInfo.setEstablishment(cell.getStringCellValue());
									break;
								case 8:
									univInfo.setAddress(cell.getStringCellValue());
									break;
								default:
									break;
								}	
							}
							else if(school_kind.equals("highschool"))
							{
								switch(columnindex)
								{
								//학교 종류
								case 2:
									univInfo.setKindofUniv(cell.getStringCellValue());
									break;
								//학교명
								case 5:
									univInfo.setNameofUniv(cell.getStringCellValue());
									break;
								//학교 본교 분교
								case 6:
									univInfo.setBranchSchoolOrNot(cell.getStringCellValue());
									break;
								//설립
								case 8:
									univInfo.setEstablishment(cell.getStringCellValue());
									break;
								//주소
								case 12:
									univInfo.setAddress(cell.getStringCellValue());
									break;
								default:
									break;
								}
							}
							else
							{
								univInfo = null;
							}
						}else
						{
							continue;
						}
					}
					school.add(univInfo);
				}
			}
		}catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return school;
	}
}
