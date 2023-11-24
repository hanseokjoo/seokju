package dao;

import java.util.ArrayList;
import dbms.DBManager;
import vo.ReplyVo;

public class ReplyDao extends DBManager
{
	private boolean isMonitoring = true;
	private ArrayList<ReplyVo> list;
	
	// ��� ���
	public boolean Insert(ReplyVo vo)
	{
		if( this.DBOpen() == false ) return false;
		String sql = "";
		sql  = " insert into reply ( pNo, rContent, uNo ) ";
		sql += " values ( ";
		sql += " "	+ vo.getpNo()			 + ", ";
		sql += " '" + this._R(vo.getrContent()) + "', ";
		sql += " " + vo.getuNo()			 + " ) ";
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: Insert() / \n"+sql); }
		this.RunSQL(sql);
		// ��ϵ� ����� ��ȣ�� ��´�
		sql  = " select last_insert_id() as rNo ";
		this.OpenQuery(sql);
		this.GetNext();
		String rNo = this.GetValue("rNo");
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: Insert() / rNo \n" + rNo); }
//		vo.setrNo(rNo);
		this.CloseQuery();
		this.DBClose();
		vo = this.getReply(rNo);
		return true; 
	}
	
   public boolean DeleteAllByPNo(String pNo, String rNo) 
   {
	   if( this.DBOpen() == false ) return false;
	   String sql = "";
       sql = " DELETE FROM reply WHERE pNo = " + pNo;
       sql += " and rNo = " + rNo; 
       this.RunSQL(sql);
       this.DBClose();
       return true;
   }
	
	// ��� ���� // ��� ��ȣ��, ��� �ۼ��ڿ� ��ġ�ϴ��� Ȯ���ϰ� ����
	public boolean Delete(String rNo, String uNo)
	{
		if( this.DBOpen() == false ) return false;
		String sql = "";
		sql += " delete from reply ";
		sql += " where rNo = " + rNo + " ";
		sql += " and uNo = "   + uNo + " ";
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}

	// �Խñ� ��ȣ�� �޾Ƽ� ����� �ϰ� ����
	public boolean Delete(String pNo)
	{
		if( this.DBOpen() == false ) return false;
		String sql = "";
		sql += " delete from reply ";
		sql += " where pNo = " + pNo + " ";
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// ��� ����
	public boolean Update( ReplyVo vo, String uNo )
	{
		if( this.DBOpen() == false ) return false;
		String sql = "";
		sql += " update reply ";
		sql += " set ";
		sql += " rContent = '" + this._R(vo.getrContent()) + "', ";
		sql += " rDate = now() ";
		sql += " where rNo = " + vo.getrNo() + " ";
		sql += " and uNo = " + uNo + " ";
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: Update() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// ��� ��ȣ�� ����� �о���� �޼ҵ�
	public ReplyVo getReply( String rNo )
	{
		ReplyVo result = null;
		if( this.DBOpen() == false ) return result;
		String sql = "";
		sql += " select rNo, rContent, rDate, pNo, uNo, ";
		sql += " ( select uName from member where uNo = reply.uNo ) as uName, ";
		sql += " ( select uLevel from member where uNo = reply.uNo) as uLevel ";
		sql += " from reply ";
		sql += " where rNo = " + rNo;
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: getReply() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )
		{ this.CloseQuery(); this.DBClose(); return result; }
		result = new ReplyVo();
		result.setrNo(rNo);
		result.setrContent(this.GetValue("rContent"));
		result.setrDate(this.GetValue("rDate"));
		result.setpNo(this.GetValue("pNo"));
		result.setuNo(this.GetValue("uNo"));
		result.setuName(this.GetValue("uName"));
		result.setuLevel(this.GetValue("uLevel"));
		this.CloseQuery();
		this.DBClose();
		return result;
	}

	// �Խñ��� ��� ��� �ۼ�
	public ArrayList<ReplyVo> getList( String pNo )
	{
		this.list = null;
		
		if( this.DBOpen() == false ) return this.list;
		
/*		String sql = "";
		sql += " select rNo, rNote, rDate, pNo, uNo, ";
		sql += " ( select uName from member where uNo = reply.uNo ) as uName ";
		sql += " from reply ";
		sql += " where pNo = " + pNo;
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: getReply() / \n"+sql); }
		this.OpenQuery(sql);
		while( this.GetNext() == true)
		{
			ReplyVo vo = new ReplyVo();
			vo.setrNo(this.GetValue("rNo"));
			vo.setrNote(this.GetValue("rNote"));
			vo.setrDate(this.GetValue("rDate"));
			vo.setpNo(this.GetValue("pNo"));
			vo.setuNo(this.GetValue("uNo"));
			vo.setuName(this.GetValue("uName"));
			if(this.list==null)
			{
				this.list = new ArrayList<ReplyVo>();
			}
			this.list.add(vo);
		} */
		String sql = "";
		sql += " select rNo ";
		sql += " from reply ";
		sql += " where pNo = " + pNo;
		if( this.isMonitoring )
		{ System.out.println("ReplyDao :: getReply() / \n"+sql); }
		this.OpenQuery(sql);
		// ��۹�ȣ���� �ҷ���
		ArrayList<String> noList = null; // ��۹�ȣ ����Ʈ�� ����� ��̸���Ʈ
		while( this.GetNext() == true)
		{	// rNo ���ڿ��� ����Ʈ�� ����
			String rNo = this.GetValue("rNo"); // DB���� �о�� rNo
			if( noList == null ) { noList = new ArrayList<String>(); }
			noList.add(rNo); // noList�� rNo�� �ִ´�
		}
		this.CloseQuery(); this.DBClose();

		if( noList != null )
		{
			for( String rNo : noList )
			{	// rNo ���ڿ��� ����Ʈ�� ���� ����� �о��
				ReplyVo vo = this.getReply(rNo);
				if( this.list == null )
				{ this.list = new ArrayList<ReplyVo>(); }
				this.list.add(vo);
			}
		}
		return this.list;
	}
	
	// �Խñ��� ��ü ��� ����
	public int GetListTotalCount()
	{
		if( this.list == null ) return 0; 
		return this.list.size();
	}

	// ����Ʈ���� ���Ҹ� ������ �޼ҵ�
	public ReplyVo GetItem( int index )
	{
		if( this.list == null ) return null;
		return this.list.get(index);
	}
	
	// dao�� ������ ����Ʈ�� ��ȯ�ϴ� �޼ҵ�
	public ArrayList<ReplyVo> GetArrayList()
	{
		return this.list;
	}
}
