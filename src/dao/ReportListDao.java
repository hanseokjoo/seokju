package dao;

import dbms.DBManager;
import util.*;

import java.util.ArrayList;

import dao.*;
import vo.*;

public class ReportListDao extends DBManager
{
	private SearchVo search = null;
	private ArrayList<ReportVo> list;
	
	private String sql = ""; 
	private String where = "";
	private boolean isMonitoring = true;

	// 생성자 
	public ReportListDao( SearchVo search )
	{		
		this.search = search;
		// search에 검색 단어가 있으면
		if( search.getKeyword().equals("") == false )
		{	// sql 구문에 and를 추가해야되는지 판단
			if( !this.where.equals("") ) { this.where += " and "; } // 특정 게시판이면 and를 추가
			// search 객체로부터 검색 조건으로 sql 구문 생성
			switch( search.getKind() )
			{
			case "T":
				this.where += " (SELECT ptitle FROM board WHERE pNo = report.pNo) like '%" + search.getKeyword() + "%' "; break;
			case "C":
				this.where += " rpNote like '%" + search.getKeyword() + "%' "; break;
			case "A":
				/* this.where +=" ( title에서 검색 or note에서 검색 ) "; */
				this.where += " ( ";
				this.where += " (SELECT ptitle FROM board WHERE pNo = report.pNo) like '%" + search.getKeyword() + "%' ";
				this.where += " or ";
				this.where += " rpNote like '%" + search.getKeyword() + "%' ";
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
		this.sql  = " select count(*) as count from report ";
		if( this.where.equals("") == false )
		{
			this.sql += " where " + this.where;
		}
		if( this.isMonitoring ) { System.out.println("ReportListDao :: getTotal() / \n"+sql); }
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
		sql  = " select rpNo, date(rpDate) as rpDate, pNo, rpType, rp, uNo, rpNote, ";
		sql += " ( select uName from member where uNo = report.uNo ) as uName, ";		// 신고자 이름
		sql += " ( select pTitle from board where pNo = report.pNo ) as pTitle ";		// 신고 게시물 제목
		sql += " from report ";
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
		if(search.getSortCol().equals("rpNo"))
		{
			sql += search.getOrder();
		}else if(search.getSortCol().equals(""))
		{
			
		}
		// limit
		sql += " limit " + offset + ", " + perPage;
		if( this.isMonitoring ) { System.out.println("ReportListDao :: getList() / \n"+sql); }
		
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{	// reportVo vo에 넣고
			String rpNo    = this.GetValue("rpNo");
			String rpDate  = this.GetValue("rpDate");
			
			String pNo  = this.GetValue("pNo");
			String rpType = this.GetValue("rpType");
			String rp  = this.GetValue("rp");
			
			String uName   = this.GetValue("uName");
			String pTitle   = this.GetValue("pTitle");
			String rpNote   = this.GetValue("rpNote");
			
			// 제목의 <, > 처리
			pTitle = pTitle.replace("<","&lt;").replace(">","&gt;");
			if( this.list == null )
			{
				this.list = new ArrayList<ReportVo>();
			}
			ReportVo vo = new ReportVo();
			vo.setrpNo(rpNo);
			vo.setrpDate(rpDate);
			vo.setpNo(pNo);
			vo.setrpType(rpType);
			vo.setrp(uName);
			vo.setrpNote(rpNote);
			vo.setrp(rp);
			vo.setuName(uName);
			vo.setpTitle(pTitle);
			
			this.list.add(vo);
		}
		this.CloseQuery();
		this.DBClose();
		return true;
	}
	
	public boolean getList( String pNo )
	{
		this.list = null;
		if( this.DBOpen() == false ) return false; // 1페이지는 0 / 2페이지는 perPage * 1-> curPage -1 / 3페이지는 perPage * 2 -> curPage -1
		sql  = " select rpNo, rpType, rpNote, date(rpDate) as rpDate ";
		sql += " where pNo " + pNo;
		sql += " from report ";
		
		// 정렬
		if(search.getSortCol().equals("rpNo"))
		{
			sql += search.getOrder();
		}else if(search.getSortCol().equals(""))
		{
			
		}
		if( this.isMonitoring ) { System.out.println("ReportListDao :: getList() / \n"+sql); }
		
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{	// reportVo vo에 넣고
			String rpNo    = this.GetValue("rpNo");
			String rpDate  = this.GetValue("rpDate");
			
			String rpType = this.GetValue("rpType");
			String rpNote   = this.GetValue("rpNote");
			
			// 제목의 <, > 처리
			ReportVo vo = new ReportVo();
			vo.setrpNo(rpNo);
			vo.setrpDate(rpDate);
			vo.setpNo(pNo);
			vo.setrpType(rpType);
			vo.setrpNote(rpNote);
			
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
	public ReportVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	// 생성된 리스트 자체를 반환하는 메소드
	public ArrayList<ReportVo> getListArray()
	{
		return this.list;
	}

	
}
