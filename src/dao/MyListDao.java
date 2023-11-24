package dao;

import dbms.DBManager;
import util.*;

import java.util.ArrayList;

import dao.*;
import vo.*;

public class MyListDao extends DBManager
{
	private SearchVo search = null;
	private ArrayList<BoardVo> list;
	
	private String sql = ""; 
	private String where = "";
	private boolean isMonitoring = false;

	// 생성자 
	public MyListDao( SearchVo search, String uNo)
	{
		this.search = search;
		search.setType("AL");
		// 모든 게시판에서 회원 번호로 찾기
		this.where = " uNo = '" + uNo +  "' ";
		
		if( search.getKeyword().equals("") == false ) 
		{	// 덧붙일 조건이 search에 있는지 검사
			if( !this.where.equals("") ) 
			{ // 여기 where에 이미 조건이 한개라도 있다면 and를 추가하고, search의 키워드를 붙여야 한다.
				this.where += " and "; 
			} 
			
			// 특정 게시판이면 and를 추가
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
		
		this.sql += " where " + this.where; // 
		
		
		this.OpenQuery(this.sql);
		
		this.GetNext();
		
		total = this.GetInt("count");
		
		this.CloseQuery();
		this.DBClose();
		
		return total;
	}
	

	// 생성된 리스트의 개수를 얻는 메소드
	public int getListSize()
	{
		if( this.list == null ) {
			return 0;
		}
		else {
			return this.list.size();
		}
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
	
	public boolean getMyList( int curPage) // 현재 페이지 번호만 주면, paging
	{
		return this.getMyList(curPage, paging.perPage);
	}
	
	// 화면에 표시할 게시글 리스트를 받아오는 메소드 - 게시글의 일부 정보만 받아옴
	public boolean getMyList( int curPage, int perPage)
	{
		return getMyList(curPage, perPage,  "");
	}
	
	public boolean getMyList( int curPage, int perPage , String uNo )
	{
		this.list = null;
		if( this.DBOpen() == false ) return false;
		
		// 1페이지는 0
		// 2페이지는 perPage * 1-> curPage -1
		// 3페이지는 perPage * 2 -> curPage -1
		int offset = ( curPage - 1 ) * paging.perPage; 

		sql  = " select pNo, pTitle, date(pDate) as pDate, pType, pCnt, ";
		sql += " ( select uName from member where uNo = board.uNo ) as uName, ";	// 작성자 이름
		sql += " ( select count(rNo) from reply where pNo = board.pNo ) as rCnt, ";	// 댓글 개수
		sql += " (select sum(rc) from recommend where pNo = board.pNo ) as rc ";	// 추천수
		sql += " from board ";
		sql += " where pNo in (select pNo from Board where uNo = " + uNo + ")";
		sql += " and " + this.where;
	
		// 정렬
		sql += search.getOrder();
		
		
		// limit
		sql += " limit " + offset + ", " + perPage;
		
		//this.Print_ListDao("OpenQuery(); 직전");	
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
			
			this.list.add(vo);
		}
		this.CloseQuery();
		this.DBClose();
		return true;
	}

}