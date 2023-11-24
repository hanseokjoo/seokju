package dao;

import dbms.DBManager;
import vo.*;
import java.util.ArrayList;


public class ManagerDao extends DBManager
{
	public ManagerDao()
	{
		
	}
	// 신고 당한 회원 목록을 불러와야 하는데 설계를 잘못한 getReportedMemberList
	public ArrayList<MemberVo> getReportedMemberList()
	{
		this.DBOpen();
		
		ArrayList<MemberVo> reportedMemberList = new ArrayList<MemberVo>();
		/*
		String sql = "";
		sql += " select uNo, uMail, uPW, uName, uInter, joinDate, uRetire, uLevel, uRetireDate \r\n";
	    sql += " from member where uNo in (select uNo from board where rpCnt <> 0) ";
	    sql += " order by uNo ";
	    */
	    
		String sql = "";
		sql += " select uNo, uMail, uPW, uName, uInter, joinDate, uRetire, uLevel, uRetireDate \r\n";
	    sql += " from member ";
	    sql += " order by uNo desc";
	    
	    System.out.printf("ManagerDao.java: getReportedMemberList: sql = [%s]\r\n", sql);
	    
	    this.DBOpen();
	    this.OpenQuery(sql);
	    
		while( this.GetNext() == true ) {	
			String uNo    = this.GetValue("uNo");
			String uMail    = this.GetValue("uMail");
			String uPW    = this.GetValue("uPW");
			String uName    = this.GetValue("uName");
			String uInter    = this.GetValue("uInter");
			String joinDate    = this.GetValue("joinDate");
			String uRetire    = this.GetValue("uRetire");
			String uLevel    = this.GetValue("uLevel");

			MemberVo vo = new MemberVo();
			
			vo.setuNo(uNo);
			vo.setuMail(uMail);
			vo.setuPW(uPW);
			vo.setuName(uName);
			vo.setuInter(uInter);
			vo.setJoinDate(joinDate);
			vo.setuRetire(uRetire);
			vo.setuLevel(uLevel);

			reportedMemberList.add(vo);
		}
		
		this.CloseQuery();
		this.DBClose();
		
		return reportedMemberList;
	}
	// 검색된 회원 목록을 가져오는 메소드
	public ArrayList<MemberVo> getSearchedMemberList(SearchVo search)
	{
	
		ArrayList<MemberVo> searchedMemberList = new ArrayList<MemberVo>();
		
		String sql = "";
		sql += " select uNo, uMail, uPW, uName, uInter, joinDate, uRetire, uLevel, uRetireDate \r\n";
	    sql += " from member \r\n";
	    
	    String searchColumn = search.getKind();
	    
	    if (searchColumn.contentEquals("M") || searchColumn.contentEquals("N")) {
	    	sql += " where ";
	    }
	    
	    System.out.printf("getSearchedMemberList: switch 앞: search.getKind() = %s\r\n", search.getKind());
	    
	    switch( search.getKind() )
		{
			case "M":
				sql += " uMail like '%" + search.getKeyword() + "%' "; 
				break;
			case "N":
				sql += " uName like '%" + search.getKeyword() + "%' "; 
				break;
		}
	    
	    System.out.printf("ManagerDao.java: getSearchedMemberList: sql = [%s]\r\n", sql);
	    
	    this.DBOpen();
	    this.OpenQuery(sql);
	    
		while( this.GetNext() == true ) {	
			String uNo    = this.GetValue("uNo");
			String uMail    = this.GetValue("uMail");
			String uPW    = this.GetValue("uPW");
			String uName    = this.GetValue("uName");
			String uInter    = this.GetValue("uInter");
			String joinDate    = this.GetValue("joinDate");
			String uRetire    = this.GetValue("uRetire");
			String uLevel    = this.GetValue("uLevel");

			MemberVo vo = new MemberVo();
			
			vo.setuNo(uNo);
			vo.setuMail(uMail);
			vo.setuPW(uPW);
			vo.setuName(uName);
			vo.setuInter(uInter);
			vo.setJoinDate(joinDate);
			vo.setuRetire(uRetire);
			vo.setuLevel(uLevel);

			searchedMemberList.add(vo);
		}
		
		this.CloseQuery();
		this.DBClose();
		
		return searchedMemberList;
	}
	
	// 회원에서 탈퇴하는 RetireMember
	public boolean RetireMember(String uNo)
	{
		System.out.printf("MemberDao.java: RetireMember(%s)", uNo);
		if( this.DBOpen() == false ) return false;
		String sql  = " update member set uRetire = 'R' where uNo = '" + uNo + "'";
		
		this.RunSQL(sql);
		this.DBClose();
		
		return true;
	}
	
	// 자격정지
	public boolean StopMember(String uNo)
	{
		System.out.printf("MemberDao.java: StopMember(%s)", uNo);
		if( this.DBOpen() == false ) return false;
		String sql  = " update member set uRetire = 'B' where uNo = '" + uNo + "'";
		
		this.RunSQL(sql);
		this.DBClose();
		
		return true;
	}
	// 디버깅 및 테스트용
	public void PrintList(ArrayList<MemberVo> list)
	{
		System.out.printf("list.size = %d\r\n", list.size());
		
		System.out.printf("\r\n++++++++++++++++++++ list begin ++++++++++++++++\r\n");
		for (int i = 0; i < list.size(); ++i)
		{
			System.out.printf("==================== list.get(%d) ================\r\n", i);
			
			System.out.printf("list.get(%d).uNo = %s\r\n", i, list.get(i).getuNo());
			System.out.printf("list.get(%d).uMail = %s\r\n", i, list.get(i).getuMail());
			System.out.printf("list.get(%d).uPW = %s\r\n", i, list.get(i).getuPW());
			System.out.printf("list.get(%d).uName = %s\r\n", i, list.get(i).getuName());
			System.out.printf("list.get(%d).uInter = %s\r\n", i, list.get(i).getuInter());
			System.out.printf("list.get(%d).joinDate = %s\r\n", i, list.get(i).getJoinDate());
			System.out.printf("list.get(%d).uRetire = %s\r\n", i, list.get(i).getuRetire());
			System.out.printf("list.get(%d).uLevel = %s\r\n", i, list.get(i).getuLevel());
			
		}
		System.out.printf("++++++++++++++++++++ list end ++++++++++++++++\r\n\r\n");
	}
}
