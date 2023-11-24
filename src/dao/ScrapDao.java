package dao;

import java.util.ArrayList;

import dbms.DBManager;
import util.paging;
import vo.*;

public class ScrapDao extends DBManager
{
	private SearchVo search = null;
	private ArrayList<ScrapVo> list;

	private String sql = "";
	private String where = "";
	private boolean isMonitoring = true;
	
	public final static int SCRAP_ERROR		= 0;
	public final static int DUPLICATE		= 1;
	public final static int NOT_DUPLICATE	= 2;
	
	// 생성자 
	public ScrapDao(SearchVo search) {
	    this.search = search;
	    this.where = ""; // 초기화

	    if (!search.getKeyword().isEmpty()) { // 검색어가 비어있지 않은 경우
	        this.where = ""; // WHERE 절 추가

	        // search 객체로부터 검색 조건으로 SQL 구문 생성
	        switch (search.getKind()) {
	            case "T":
	                this.where += "b.pTitle LIKE '%" + search.getKeyword() + "%'";
	                break;
	            case "N":
	                this.where += "m.uName LIKE '%" + search.getKeyword() + "%'";
	                break;
	        }
	    }
	}
			
	
	// 화면에 표시할 스크랩 리스트를 받아오는 메소드 - 게시글의 일부 정보만 받아옴
	public boolean getList(String uNo) {
	    this.list = null;
	    if (this.DBOpen() == false) return false;
	    int offset = (search.getCurPage() - 1) * paging.perPage;
	    sql  = " SELECT s.sNo AS 'sNo', b.pNo AS 'pNo', b.pTitle AS 'pTitle', m.uName AS 'uName', ";
	    sql += " ( select count(rpNo) from report where pNo = s.pNo ) as rp ";			// 신고수
	    sql += " FROM Scrap s ";
	    sql += " INNER JOIN board b ON s.pNo = b.pNo ";
	    sql += " INNER JOIN member m ON b.uNo = m.uNo ";
	    sql += " WHERE s.uno = " + uNo;

	    if (!this.where.isEmpty()) {
	        sql += " AND " + this.where;
	    }

	    sql += " ORDER BY sNo DESC ";
	    sql += "LIMIT " + offset + ", " + paging.perPage;

	    if (this.isMonitoring) {
	        System.out.println("ScrapDao :: getList() / \n" + sql);
	    }
	    this.OpenQuery(sql);
	    while (this.GetNext() == true) {
	        // boardVo vo에 넣고
	        if (this.list == null) {
	            this.list = new ArrayList<ScrapVo>();
	        }
	        ScrapVo vo = new ScrapVo();
	        vo.setsNo(this.GetValue("sNo"));
	        vo.setpNo(this.GetValue("pNo"));
	        vo.setrp(this.GetValue("rp"));
	        vo.setpTitle(this.GetValue("pTitle").replace("<", "&lt;").replace(">", "&gt;"));
	        vo.setuName(this.GetValue("uName")); // 게시글 작성자 이름
	        vo.setuNo(uNo); // 스크랩한 유저 번호
	        // 제목의 <, > 처리
	        this.list.add(vo);
	    }
	    this.CloseQuery();
	    this.DBClose();
	    return true;
	}
	
	// 중복검사
	public int IsDuplicate(String uNo, String pNo)
	{
		if( this.DBOpen() == false ) return SCRAP_ERROR;
		sql = " select sNo from scrap where uNo = " + uNo + " and pNo = " + pNo;
		if( this.isMonitoring )
		{
			System.out.println("ScrapDao :: IsDuplicate() / \n"+sql);
		}
		this.OpenQuery(sql);
		if(this.GetNext() == true)
		{	// sNo가 존재함
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
		
	}
	
	// 글을 스크랩하는 addScrap
	public boolean addScrap(ScrapVo vo)
	{	// scrap 테이블에 글 정보를 넣는다
		if( this.DBOpen()==false ) return false;
		sql  = " insert into scrap ";
		sql += " ( uNo, pNo ) ";
		sql += " values ( ";
		sql += " " + vo.getuNo() + " , ";
		sql += " " + vo.getpNo() + " ) ";
		if(this.isMonitoring)
		{
			System.out.println("ScrapDao :: addScrap() / \n"+sql);
		}
		this.RunSQL(sql);
		// ======== 스크랩 번호를 얻는다
		sql = " select last_insert_id() as sNo ";
		if(this.isMonitoring)
		{
			System.out.println("ScrapDao :: addScrap() / \n"+sql);
		}
		this.OpenQuery(sql);
		this.GetNext();
		String sNo = this.GetValue("sNo");
		vo.setsNo(sNo);	// vo에 등록된 스크랩 번호를 넣는다
		this.CloseQuery();
		vo.PrintInfo();
		
		// DB 연결 종료
		this.DBClose();
		return true;
	}
	
	// 스크랩한 글을 삭제하는 deleteScrap
	public boolean deleteScrap(String uNo, String pNo)
	{
		if( this.DBOpen() == false ) return false;
		sql = " delete from scrap where pNo = " + pNo + " and uNo = " + uNo;
		if(this.isMonitoring)
		{
			System.out.println("ScrapDao :: deleteScrap() / \n"+sql);
		}
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// uNo로 조회하는 Read
	public ScrapVo Read(String uNo)
	{
		ScrapVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// 게시물 정보 받기 ===================================================
		sql  = " select pNo, ";
		sql += " pTitle, ";
		sql += " (select uName from member where uNo = board.uNo) as uName";
		sql += " from board ";
		sql += " where pNo in ( select pNo from Scrap where uNo = " + uNo;
		sql += " ) ";
		if( this.isMonitoring ){ System.out.println("ScrapDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// 조회되지 않으면 종료
		{ this.CloseQuery(); this.DBClose(); return vo; }
		// ScrapVo 객체 생성
		vo = new ScrapVo();
		// resultset으로부터 정보를 받아 vo에 셋팅
		vo.setpNo(this.GetValue("pNo"));
		vo.setuNo(this.GetValue("uNo"));
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// 생성된 리스트의 개수를 얻는 메소드
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// 리스트에서 인덱스로 게시글 정보를 얻는 메소드
	public ScrapVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	public ArrayList<ScrapVo> getList()
	{
		return this.list;
	}
}
