package expertisesns.data;

import java.util.StringTokenizer;

import expertisesns.model.MemberDao;

public class DataInfo 
{
	static MemberDao memberdao = MemberDao.getInstance();
	
	public final static String univ_data_path = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/data/UniversityDataSet.xls";
	public final static String highschool_data_path = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/data/HighSchoolDataSet.xls";
	public final static String company_data_path = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/WebContent/data/companyList.txt";
	public final static String expertise_data[] = new String[]{"null", "기획,전략,경영", "홍보,PR,사보", "회계,재무,세무,IR", "비서,안내,수행원", 
			"사무보조,문서작성", "마케팅,광고,분석", "경리,출납,결산", "총무,법무,사무", "인사,교육,노무", "전시,컨벤션,세미나", "일반영업", "기술영업", "광고영업", "판매,매장관리", "TM,인바운드", 
			"QA,CS강사,수퍼바이저", "영업기획,관리,지원", "IT,솔루션영업", "금융,보험영업", "TM,아웃바운드", "고객센터,CS", "웹개발", "시스템개발", "ERP,시스템분석,설계", "퍼블리싱,UI개발", "하드웨어,소프트웨어", 
			"웹기획,PM", "인공지능,빅데이터", "응용프로그램개발", "서버,네트워크,보안", "데이터베이스,DBA", "통신,모바일", "웹마스터,QA,테스터", "게임,Game", "동영상,편집,코덱", "웹디자인", "그래픽디자인,CG", 
			"제품,산업디자인", "의류,패션,잡화디자인", "광고,시각디자인", "출판,편집디자인", "전시,공간디자인", "디자인기타", "보안,경호,안전", "AS,서비스,수리", "호텔,카지노,콘도", "레저,스포츠", 
			"요리,제빵사,영양사", "안내,도우미,나레이터", "주차,세차,주유", "외식,식음료", "여행,관광,항공", "미용,피부관리,애견", "가사,청소,육아", "경영분석,컨설턴트", "설문,통계,리서치", "외국어,번역,통역", 
			"세무,회계,cpa", "도서관사서", "문화,예술,종교", "증권,투자,펀드,외환", "헤드헌팅,노무,직업상담", "법률,특허,상표", "연구소,R&D", "임원,CEO", "의사,치과,한의사", "약사", "간호조무사", 
			"수의사", "의료기사", "생산,제조,포장,조립", "기계,기계설비", "비금속,요업,신소재", "반도체,디스플레이,LCD", "설계,CAD,CAM", "금속,금형", "전기,전자,제어", "화학,에너지", "바이오,제약,식품", 
			"안경,렌즈,과학", "토목,조경,도시,측량", "전기,소방,통신,설비", "부동산,개발,경매,분양", "건축,인테리어,설계", "환경,플랜트", "안전,품질,검사,관리", "방송연출,PD,감독", "진행,아나운서", "기자", 
			"공연,무대,스탭", "광고,카피,CF", "인쇄,출판,편집", "카메라,조명,미술", "작가,시나리오", "연예,엔터테인먼트", "음악,음향,사운드", "사진,포토그라퍼", "유치원,보육", "학습지,과외,방문", "대학교수,행정직", 
			"초등학교", "중학교", "고등학교", "외국어,어학원", "전문직업,IT강사", "교육기획,교재"};
	
	//인맥에서 kmeans 머신러닝을 위한 .arff 파일 위치 
	public final static String kmeans_path = "/Volumes/SAMSUNGUSB/JavaBasedWebProgramming/workspace/ExpertiseSNS/peopleWhoKnows/kmeansConnection.arff";
	
	public final static String ip = "192.168.219.117";
	
	public final static String domain_path = "http://"+ip+":8080/ExpertiseSNS";
	
	public final static String localhost_path = "http://"+ip+":8080";
	
	//프로필 사진 경로 탐색을 위한 id값 폴더 이름 리턴
	public static String getIDFolderName(String email)
	{
		StringTokenizer stk = new StringTokenizer(email, ".");
		
		String profile_folder = null;

		if(stk.hasMoreElements())
		{
			profile_folder = stk.nextToken();
		}
		
		return profile_folder;
	}
	
	//아이디값으로 프로필 사진 url 도출
	public static String getProfileURL(String id)
	{
		StringTokenizer stk = new StringTokenizer(id, ".");
		
		String profile_folder = null;

		if(stk.hasMoreElements())
		{
			profile_folder = stk.nextToken();
		}
		
		String profileimage_name = memberdao.getProfile(id);
		
		String profile_path = null;
		if(profileimage_name != null)
		{
			profile_path = domain_path + "/memberImageStorage/"+profile_folder+"/profile/"+profileimage_name;
		}else
		{
			profile_path = domain_path + "/data/peoplewho.png";
		}
		
		return profile_path;
	}
}
