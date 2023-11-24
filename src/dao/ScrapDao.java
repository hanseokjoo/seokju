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
	
	// ������ 
	public ScrapDao(SearchVo search) {
	    this.search = search;
	    this.where = ""; // �ʱ�ȭ

	    if (!search.getKeyword().isEmpty()) { // �˻�� ������� ���� ���
	        this.where = ""; // WHERE �� �߰�

	        // search ��ü�κ��� �˻� �������� SQL ���� ����
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
			
	
	// ȭ�鿡 ǥ���� ��ũ�� ����Ʈ�� �޾ƿ��� �޼ҵ� - �Խñ��� �Ϻ� ������ �޾ƿ�
	public boolean getList(String uNo) {
	    this.list = null;
	    if (this.DBOpen() == false) return false;
	    int offset = (search.getCurPage() - 1) * paging.perPage;
	    sql  = " SELECT s.sNo AS 'sNo', b.pNo AS 'pNo', b.pTitle AS 'pTitle', m.uName AS 'uName', ";
	    sql += " ( select count(rpNo) from report where pNo = s.pNo ) as rp ";			// �Ű��
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
	        // boardVo vo�� �ְ�
	        if (this.list == null) {
	            this.list = new ArrayList<ScrapVo>();
	        }
	        ScrapVo vo = new ScrapVo();
	        vo.setsNo(this.GetValue("sNo"));
	        vo.setpNo(this.GetValue("pNo"));
	        vo.setrp(this.GetValue("rp"));
	        vo.setpTitle(this.GetValue("pTitle").replace("<", "&lt;").replace(">", "&gt;"));
	        vo.setuName(this.GetValue("uName")); // �Խñ� �ۼ��� �̸�
	        vo.setuNo(uNo); // ��ũ���� ���� ��ȣ
	        // ������ <, > ó��
	        this.list.add(vo);
	    }
	    this.CloseQuery();
	    this.DBClose();
	    return true;
	}
	
	// �ߺ��˻�
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
		{	// sNo�� ������
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
		
	}
	
	// ���� ��ũ���ϴ� addScrap
	public boolean addScrap(ScrapVo vo)
	{	// scrap ���̺� �� ������ �ִ´�
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
		// ======== ��ũ�� ��ȣ�� ��´�
		sql = " select last_insert_id() as sNo ";
		if(this.isMonitoring)
		{
			System.out.println("ScrapDao :: addScrap() / \n"+sql);
		}
		this.OpenQuery(sql);
		this.GetNext();
		String sNo = this.GetValue("sNo");
		vo.setsNo(sNo);	// vo�� ��ϵ� ��ũ�� ��ȣ�� �ִ´�
		this.CloseQuery();
		vo.PrintInfo();
		
		// DB ���� ����
		this.DBClose();
		return true;
	}
	
	// ��ũ���� ���� �����ϴ� deleteScrap
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
	
	// uNo�� ��ȸ�ϴ� Read
	public ScrapVo Read(String uNo)
	{
		ScrapVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// �Խù� ���� �ޱ� ===================================================
		sql  = " select pNo, ";
		sql += " pTitle, ";
		sql += " (select uName from member where uNo = board.uNo) as uName";
		sql += " from board ";
		sql += " where pNo in ( select pNo from Scrap where uNo = " + uNo;
		sql += " ) ";
		if( this.isMonitoring ){ System.out.println("ScrapDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// ��ȸ���� ������ ����
		{ this.CloseQuery(); this.DBClose(); return vo; }
		// ScrapVo ��ü ����
		vo = new ScrapVo();
		// resultset���κ��� ������ �޾� vo�� ����
		vo.setpNo(this.GetValue("pNo"));
		vo.setuNo(this.GetValue("uNo"));
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// ������ ����Ʈ�� ������ ��� �޼ҵ�
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// ����Ʈ���� �ε����� �Խñ� ������ ��� �޼ҵ�
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
