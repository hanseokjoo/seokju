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

	// ������ 
	public MyListDao( SearchVo search, String uNo)
	{
		this.search = search;
		search.setType("AL");
		// ��� �Խ��ǿ��� ȸ�� ��ȣ�� ã��
		this.where = " uNo = '" + uNo +  "' ";
		
		if( search.getKeyword().equals("") == false ) 
		{	// ������ ������ search�� �ִ��� �˻�
			if( !this.where.equals("") ) 
			{ // ���� where�� �̹� ������ �Ѱ��� �ִٸ� and�� �߰��ϰ�, search�� Ű���带 �ٿ��� �Ѵ�.
				this.where += " and "; 
			} 
			
			// Ư�� �Խ����̸� and�� �߰�
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
		
		this.sql += " where " + this.where; // 
		
		
		this.OpenQuery(this.sql);
		
		this.GetNext();
		
		total = this.GetInt("count");
		
		this.CloseQuery();
		this.DBClose();
		
		return total;
	}
	

	// ������ ����Ʈ�� ������ ��� �޼ҵ�
	public int getListSize()
	{
		if( this.list == null ) {
			return 0;
		}
		else {
			return this.list.size();
		}
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
	
	public boolean getMyList( int curPage) // ���� ������ ��ȣ�� �ָ�, paging
	{
		return this.getMyList(curPage, paging.perPage);
	}
	
	// ȭ�鿡 ǥ���� �Խñ� ����Ʈ�� �޾ƿ��� �޼ҵ� - �Խñ��� �Ϻ� ������ �޾ƿ�
	public boolean getMyList( int curPage, int perPage)
	{
		return getMyList(curPage, perPage,  "");
	}
	
	public boolean getMyList( int curPage, int perPage , String uNo )
	{
		this.list = null;
		if( this.DBOpen() == false ) return false;
		
		// 1�������� 0
		// 2�������� perPage * 1-> curPage -1
		// 3�������� perPage * 2 -> curPage -1
		int offset = ( curPage - 1 ) * paging.perPage; 

		sql  = " select pNo, pTitle, date(pDate) as pDate, pType, pCnt, ";
		sql += " ( select uName from member where uNo = board.uNo ) as uName, ";	// �ۼ��� �̸�
		sql += " ( select count(rNo) from reply where pNo = board.pNo ) as rCnt, ";	// ��� ����
		sql += " (select sum(rc) from recommend where pNo = board.pNo ) as rc ";	// ��õ��
		sql += " from board ";
		sql += " where pNo in (select pNo from Board where uNo = " + uNo + ")";
		sql += " and " + this.where;
	
		// ����
		sql += search.getOrder();
		
		
		// limit
		sql += " limit " + offset + ", " + perPage;
		
		//this.Print_ListDao("OpenQuery(); ����");	
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
			
			this.list.add(vo);
		}
		this.CloseQuery();
		this.DBClose();
		return true;
	}

}