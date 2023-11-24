package dao;

import dbms.DBManager;
import util.*;

import java.util.ArrayList;

import dao.*;
import vo.*;

public class ListDao extends DBManager
{
	private SearchVo search = null;
	private ArrayList<BoardVo> list;
	
	private String sql = ""; 
	private String where = "";
	private boolean isMonitoring = true;

	// 생성자 
	public ListDao( SearchVo search )
	{
		System.out.printf("ListDao.java: ListDao() reached\r\n");
		
		this.search = search;
		// -> 게시판 선택여부
		// where pType = '게시판코드' and 'search객체로부터 검색조건'				// 특정 게시판에서 검색
		// where 'search 객체로부터 검색조건'										// 모든 게시판에서 검색
		if( search.getType().equals("AL") )									// 게시판 선택 여부 체크
		{ this.where = ""; }												// 모든 게시판
		else { this.where = " pType = '" + search.getType() + "' "; }		// 특정 게시판
		// search에 검색 단어가 있으면
		if( search.getKeyword().equals("") == false )
		{	// sql 구문에 and를 추가해야되는지 판단
			if( !this.where.equals("") ) { this.where += " and "; } // 특정 게시판이면 and를 추가
			// search 객체로부터 검색 조건으로 sql 구문 생성
			switch( search.getKind() )
			{
			case "T":
				this.where += " pTitle like '%" + search.getKeyword() + "%' "; break;
			case "C":
				this.where += " pContent like '%" + search.getKeyword() + "%' "; break;
			case "N":
				this.where += " uName like '%" + search.getKeyword() + "%' "; break;
			case "A":
				/* this.where +=" ( title에서 검색 or note에서 검색 ) "; */
				this.where += " ( ";
				this.where += " pTitle like '%" + search.getKeyword() + "%' ";
				this.where += " or ";
				this.where += " pContent like '%" + search.getKeyword() + "%' ";
				this.where += " ) ";
				break;
			}
		}
	}
	
	// 전체 게시글 개수를 얻는 메소드
	public int getTotal()
	{
		int total = 0;
		if( this.DBOpen() == false ) return 0;
		this.sql  = " select count(*) as count from board ";
		if( this.where.equals("") == false )
		{
			this.sql += " where " + this.where;
		}
		if( this.isMonitoring ) { System.out.println("ListDao :: getTotal() / \n"+sql); }
		this.OpenQuery(this.sql);
		this.GetNext();
		total = this.GetInt("count");
		this.CloseQuery();
		this.DBClose();
		return total;
	}
	
	public boolean getList( int curPage) {
		return this.getList(curPage, paging.perPage);
	}
	
	// 화면에 표시할 게시글 리스트를 받아오는 메소드 - 게시글의 일부 정보만 받아옴
	public boolean getList( int curPage, int perPage)
	{
		return getList(curPage, perPage, "");
	}
	
	// 화면에 표시할 리스트를 받아오는 메소드 - 게시글의 일부 정보만 받아옴
	public boolean getList( int curPage, int perPage , String uNo )
	{
		this.list = null;
		if( this.DBOpen() == false ) return false;
		int offset = ( curPage - 1 ) * paging.perPage; // 1페이지는 0 / 2페이지는 perPage * 1-> curPage -1 / 3페이지는 perPage * 2 -> curPage -1
		sql  = " select pNo, pTitle, date(pDate) as pDate, pType, pCnt, ";
		sql += " ( select uName from member where uNo = board.uNo ) as uName, ";		// 작성자 이름
		sql += " ( select count(rNo) from reply where pNo = board.pNo ) as rCnt, ";		// 댓글 개수
		sql += " ( select count(rcNo) from recommend where pNo = board.pNo ) as rc, ";	// 추천수
		sql += " ( select count(rpNo) from report where pNo = board.pNo ) as rp, ";		// 신고수
		sql += " ( select uLevel from member where uNo = board.uNo ) as uLevel ";
		sql += " from board ";
		if(!uNo.equals(""))
		{
			sql += " where pNo in (select pNo from Scrap where uNo = " + uNo + ") ";
			if( this.where.equals("") == false )
			{
				sql += " and " + this.where + " ";
			}
		}else {
			if( this.where.equals("") == false )
			{
				sql += " where " + this.where + " ";
			}
		}
		// 정렬
		if(search.getSortCol().equals("rc"))
		{
			sql += search.getOrder();
		}else if(search.getSortCol().equals("pDate") || search.getSortCol().equals("pCnt"))
		{
			sql += search.getOrder("board");
		}
		// limit
		sql += " limit " + offset + ", " + perPage;
		if( this.isMonitoring ) { System.out.println("ListDao :: getList() / \n"+sql); }
		
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{	// boardVo vo에 넣고
			String pNo    = this.GetValue("pNo");
			String pType  = this.GetValue("pType");
			
			String uName  = this.GetValue("uName");
			String pTitle = this.GetValue("pTitle");
			String pDate  = this.GetValue("pDate");
			
			String pCnt   = this.GetValue("pCnt");
			String rCnt   = this.GetValue("rCnt");
			String rc   = this.GetValue("rc");
			String rp   = this.GetValue("rp");
			String uLevel = this.GetValue("uLevel");
			
			// 제목의 <, > 처리
			pTitle = pTitle.replace("<","&lt;").replace(">","&gt;");
			if (rc == null) {rc = "0";}
			if( this.list == null )
			{
				this.list = new ArrayList<BoardVo>();
			}
			BoardVo vo = new BoardVo();
			vo.setpNo(pNo);
			vo.setpTitle(pTitle);
			vo.setpDate(pDate);
			vo.setpType(pType);
			vo.setpCnt(pCnt);
			vo.setuName(uName);
			vo.setrCnt(rCnt);
			vo.setrc(rc);
			vo.setrp(rp);
			vo.setuLevel(uLevel);
			
			this.list.add(vo);
		}
		this.CloseQuery();
		this.DBClose();
		return true;
	}
	
	// 생성된 리스트의 개수를 얻는 메소드
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// 리스트에서 인덱스로 게시글 정보를 얻는 메소드
	public BoardVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	
	// 생성된 리스트 자체를 반환하는 메소드
	public ArrayList<BoardVo> getListArray()
	{
		return this.list;
	}
	
	public boolean getMyList( int curPage, int perPage , String uNo )
	{
		this.where = "uNo = " + uNo;
		
		this.list = null;
		if( this.DBOpen() == false ) return false;
		int offset = ( curPage - 1 ) * paging.perPage; // 1페이지는 0 / 2페이지는 perPage * 1-> curPage -1 / 3페이지는 perPage * 2 -> curPage -1
		sql  = " select pNo, pTitle, date(pDate) as pDate, pType, pCnt, ";
		sql += " ( select uName from member where uNo = board.uNo ) as uName, ";	// 작성자 이름
		sql += " ( select count(rNo) from reply where pNo = board.pNo ) as rCnt, ";	// 댓글 개수
		sql += " ( select count(rcNo) from recommend where pNo = board.pNo ) as rc, ";	// 추천수
		sql += " ( select count(rpNo) from report where pNo = board.pNo ) as rp, ";		// 신고수
		sql += " ( select uLevel from member where uNo = board.uNo ) as uLevel ";
		sql += " from board ";
		sql += " where pNo in (select pNo from Board where uNo = " + uNo + ") ";
		
		System.out.printf("ListDao.java: getMyList(curPage = %d, perPage = %d, uNo = %s)\r\n", curPage, perPage, uNo);
		System.out.printf("ListDao.java: getMyList(): sql = [%s]\r\n", sql);
		
		
		System.out.printf("ListDao.java: getMyList(): sql after uNo Check = [%s]\r\n", sql);
		
		// 정렬
		sql += search.getOrder();
		
		System.out.printf("ListDao.java: getMyList(): sql after getOrder attach = [%s]\r\n", sql);
		
		
		// limit
		sql += " limit " + offset + ", " + perPage;
		if( this.isMonitoring ) { System.out.println("ListDao :: getList() / \n"+sql); }
		
		System.out.printf("ListDao.java: getMyList(): sql after limit = [%s]\r\n", sql);
		
		this.OpenQuery(sql);
		
		
		
		while( this.GetNext() == true )
		{	// boardVo vo에 넣고
			String pNo    = this.GetValue("pNo");
			String pTitle = this.GetValue("pTitle");
			String pDate  = this.GetValue("pDate");
			String pType  = this.GetValue("pType");
			String pCnt   = this.GetValue("pCnt");
			String uName  = this.GetValue("uName");
			String rCnt   = this.GetValue("rCnt");
			String rc   = this.GetValue("rc");
			String rp   = this.GetValue("rp");
			// 제목의 <, > 처리
			pTitle = pTitle.replace("<","&lt;").replace(">","&gt;");
			if( this.list == null )
			{
				this.list = new ArrayList<BoardVo>();
			}
			BoardVo vo = new BoardVo();
			vo.setpNo(pNo);
			vo.setpTitle(pTitle);
			vo.setpDate(pDate);
			vo.setpType(pType);
			vo.setpCnt(pCnt);
			vo.setuName(uName);
			vo.setrCnt(rCnt);
			vo.setrc(rc);
			vo.setrp(rp);
			this.list.add(vo);
			
			System.out.printf("ListDao.java: getMyList(): ========== 완성된 vo =============\r\n");
			vo.PrintInfo();
			System.out.printf("ListDao.java: getMyList(): ========== 완성된 vo =============\r\n");
		}
		this.CloseQuery();
		this.DBClose();
		
		
		return true;
	}
	
	public void Print_ListDao(String msg)
	{
		System.out.printf("--------------- %s begin ---------------------\r\n", msg);
		System.out.printf("ListDao.java: sql : [%s]\r\n", sql);
		System.out.printf("ListDao.java: where : [%s]\r\n", where);
		if (list != null) 
		{
			System.out.printf("ListDao.java: list.size() : [%d]\r\n", list.size());
		}else 
		{
			System.out.printf("ListDao.java: list.size() : list is null\r\n");
		}
		System.out.printf("--------------- %s end ---------------------\r\n", msg);
	}
	
}
