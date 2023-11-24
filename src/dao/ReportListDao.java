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

	// ������ 
	public ReportListDao( SearchVo search )
	{		
		this.search = search;
		// search�� �˻� �ܾ ������
		if( search.getKeyword().equals("") == false )
		{	// sql ������ and�� �߰��ؾߵǴ��� �Ǵ�
			if( !this.where.equals("") ) { this.where += " and "; } // Ư�� �Խ����̸� and�� �߰�
			// search ��ü�κ��� �˻� �������� sql ���� ����
			switch( search.getKind() )
			{
			case "T":
				this.where += " (SELECT ptitle FROM board WHERE pNo = report.pNo) like '%" + search.getKeyword() + "%' "; break;
			case "C":
				this.where += " rpNote like '%" + search.getKeyword() + "%' "; break;
			case "A":
				/* this.where +=" ( title���� �˻� or note���� �˻� ) "; */
				this.where += " ( ";
				this.where += " (SELECT ptitle FROM board WHERE pNo = report.pNo) like '%" + search.getKeyword() + "%' ";
				this.where += " or ";
				this.where += " rpNote like '%" + search.getKeyword() + "%' ";
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
		sql  = " select rpNo, date(rpDate) as rpDate, pNo, rpType, rp, uNo, rpNote, ";
		sql += " ( select uName from member where uNo = report.uNo ) as uName, ";		// �Ű��� �̸�
		sql += " ( select pTitle from board where pNo = report.pNo ) as pTitle ";		// �Ű� �Խù� ����
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
		// ����
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
		{	// reportVo vo�� �ְ�
			String rpNo    = this.GetValue("rpNo");
			String rpDate  = this.GetValue("rpDate");
			
			String pNo  = this.GetValue("pNo");
			String rpType = this.GetValue("rpType");
			String rp  = this.GetValue("rp");
			
			String uName   = this.GetValue("uName");
			String pTitle   = this.GetValue("pTitle");
			String rpNote   = this.GetValue("rpNote");
			
			// ������ <, > ó��
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
		if( this.DBOpen() == false ) return false; // 1�������� 0 / 2�������� perPage * 1-> curPage -1 / 3�������� perPage * 2 -> curPage -1
		sql  = " select rpNo, rpType, rpNote, date(rpDate) as rpDate ";
		sql += " where pNo " + pNo;
		sql += " from report ";
		
		// ����
		if(search.getSortCol().equals("rpNo"))
		{
			sql += search.getOrder();
		}else if(search.getSortCol().equals(""))
		{
			
		}
		if( this.isMonitoring ) { System.out.println("ReportListDao :: getList() / \n"+sql); }
		
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{	// reportVo vo�� �ְ�
			String rpNo    = this.GetValue("rpNo");
			String rpDate  = this.GetValue("rpDate");
			
			String rpType = this.GetValue("rpType");
			String rpNote   = this.GetValue("rpNote");
			
			// ������ <, > ó��
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
	
	// ������ ����Ʈ�� ������ ��� �޼ҵ�
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// ����Ʈ���� �ε����� �Խñ� ������ ��� �޼ҵ�
	public ReportVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	// ������ ����Ʈ ��ü�� ��ȯ�ϴ� �޼ҵ�
	public ArrayList<ReportVo> getListArray()
	{
		return this.list;
	}

	
}
