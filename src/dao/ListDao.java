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

	// ������ 
	public ListDao( SearchVo search )
	{
		System.out.printf("ListDao.java: ListDao() reached\r\n");
		
		this.search = search;
		// -> �Խ��� ���ÿ���
		// where pType = '�Խ����ڵ�' and 'search��ü�κ��� �˻�����'				// Ư�� �Խ��ǿ��� �˻�
		// where 'search ��ü�κ��� �˻�����'										// ��� �Խ��ǿ��� �˻�
		if( search.getType().equals("AL") )									// �Խ��� ���� ���� üũ
		{ this.where = ""; }												// ��� �Խ���
		else { this.where = " pType = '" + search.getType() + "' "; }		// Ư�� �Խ���
		// search�� �˻� �ܾ ������
		if( search.getKeyword().equals("") == false )
		{	// sql ������ and�� �߰��ؾߵǴ��� �Ǵ�
			if( !this.where.equals("") ) { this.where += " and "; } // Ư�� �Խ����̸� and�� �߰�
			// search ��ü�κ��� �˻� �������� sql ���� ����
			switch( search.getKind() )
			{
			case "T":
				this.where += " pTitle like '%" + search.getKeyword() + "%' "; break;
			case "C":
				this.where += " pContent like '%" + search.getKeyword() + "%' "; break;
			case "N":
				this.where += " uName like '%" + search.getKeyword() + "%' "; break;
			case "A":
				/* this.where +=" ( title���� �˻� or note���� �˻� ) "; */
				this.where += " ( ";
				this.where += " pTitle like '%" + search.getKeyword() + "%' ";
				this.where += " or ";
				this.where += " pContent like '%" + search.getKeyword() + "%' ";
				this.where += " ) ";
				break;
			}
		}
	}
	
	// ��ü �Խñ� ������ ��� �޼ҵ�
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
	
	// ȭ�鿡 ǥ���� �Խñ� ����Ʈ�� �޾ƿ��� �޼ҵ� - �Խñ��� �Ϻ� ������ �޾ƿ�
	public boolean getList( int curPage, int perPage)
	{
		return getList(curPage, perPage, "");
	}
	
	// ȭ�鿡 ǥ���� ����Ʈ�� �޾ƿ��� �޼ҵ� - �Խñ��� �Ϻ� ������ �޾ƿ�
	public boolean getList( int curPage, int perPage , String uNo )
	{
		this.list = null;
		if( this.DBOpen() == false ) return false;
		int offset = ( curPage - 1 ) * paging.perPage; // 1�������� 0 / 2�������� perPage * 1-> curPage -1 / 3�������� perPage * 2 -> curPage -1
		sql  = " select pNo, pTitle, date(pDate) as pDate, pType, pCnt, ";
		sql += " ( select uName from member where uNo = board.uNo ) as uName, ";		// �ۼ��� �̸�
		sql += " ( select count(rNo) from reply where pNo = board.pNo ) as rCnt, ";		// ��� ����
		sql += " ( select count(rcNo) from recommend where pNo = board.pNo ) as rc, ";	// ��õ��
		sql += " ( select count(rpNo) from report where pNo = board.pNo ) as rp, ";		// �Ű��
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
		// ����
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
		{	// boardVo vo�� �ְ�
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
			
			// ������ <, > ó��
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
	
	// ������ ����Ʈ�� ������ ��� �޼ҵ�
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// ����Ʈ���� �ε����� �Խñ� ������ ��� �޼ҵ�
	public BoardVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	
	// ������ ����Ʈ ��ü�� ��ȯ�ϴ� �޼ҵ�
	public ArrayList<BoardVo> getListArray()
	{
		return this.list;
	}
	
	public boolean getMyList( int curPage, int perPage , String uNo )
	{
		this.where = "uNo = " + uNo;
		
		this.list = null;
		if( this.DBOpen() == false ) return false;
		int offset = ( curPage - 1 ) * paging.perPage; // 1�������� 0 / 2�������� perPage * 1-> curPage -1 / 3�������� perPage * 2 -> curPage -1
		sql  = " select pNo, pTitle, date(pDate) as pDate, pType, pCnt, ";
		sql += " ( select uName from member where uNo = board.uNo ) as uName, ";	// �ۼ��� �̸�
		sql += " ( select count(rNo) from reply where pNo = board.pNo ) as rCnt, ";	// ��� ����
		sql += " ( select count(rcNo) from recommend where pNo = board.pNo ) as rc, ";	// ��õ��
		sql += " ( select count(rpNo) from report where pNo = board.pNo ) as rp, ";		// �Ű��
		sql += " ( select uLevel from member where uNo = board.uNo ) as uLevel ";
		sql += " from board ";
		sql += " where pNo in (select pNo from Board where uNo = " + uNo + ") ";
		
		System.out.printf("ListDao.java: getMyList(curPage = %d, perPage = %d, uNo = %s)\r\n", curPage, perPage, uNo);
		System.out.printf("ListDao.java: getMyList(): sql = [%s]\r\n", sql);
		
		
		System.out.printf("ListDao.java: getMyList(): sql after uNo Check = [%s]\r\n", sql);
		
		// ����
		sql += search.getOrder();
		
		System.out.printf("ListDao.java: getMyList(): sql after getOrder attach = [%s]\r\n", sql);
		
		
		// limit
		sql += " limit " + offset + ", " + perPage;
		if( this.isMonitoring ) { System.out.println("ListDao :: getList() / \n"+sql); }
		
		System.out.printf("ListDao.java: getMyList(): sql after limit = [%s]\r\n", sql);
		
		this.OpenQuery(sql);
		
		
		
		while( this.GetNext() == true )
		{	// boardVo vo�� �ְ�
			String pNo    = this.GetValue("pNo");
			String pTitle = this.GetValue("pTitle");
			String pDate  = this.GetValue("pDate");
			String pType  = this.GetValue("pType");
			String pCnt   = this.GetValue("pCnt");
			String uName  = this.GetValue("uName");
			String rCnt   = this.GetValue("rCnt");
			String rc   = this.GetValue("rc");
			String rp   = this.GetValue("rp");
			// ������ <, > ó��
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
			
			System.out.printf("ListDao.java: getMyList(): ========== �ϼ��� vo =============\r\n");
			vo.PrintInfo();
			System.out.printf("ListDao.java: getMyList(): ========== �ϼ��� vo =============\r\n");
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
