package dao;

import java.util.ArrayList;
import dbms.DBManager;
import vo.*;

import java.io.File;
import java.util.UUID;

public class AttachDao extends DBManager
{
	// ÷�������� ����Ʈ
	private ArrayList<AttachVo> list;
	private String sql = "";
	
	// ÷������ vo, �Խñ� ��ȣ�� �޾Ƽ� DB�� �ִ� �޼ҵ�
	public boolean Insert( AttachVo vo, String pNo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = " insert into attach ( pNo, fName, uqName )";
		sql += " values ";
		sql += " ( " + pNo				+ " , ";
		sql += " '"  + vo.getfName() + "', ";
		sql += " '"  + vo.getuqName()  + "' ) ";
		if( this.isMonitoring )
		{ System.out.println("AttachDao :: Insert() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// ÷������ vo�� �޾Ƽ� DB�� �ִ� �޼ҵ�
	public boolean Insert( AttachVo vo )
	{
		return this.Insert(vo, vo.getpNo());
	}
	
	// ÷������ ��ȣ�� �޾Ƽ� DB���� �����ϴ� �޼ҵ�
	public boolean Delete( String aNo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = " delete from attach where aNo = " + aNo;
		if( this.isMonitoring )
		{ System.out.println("AttachDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// �Խñ� ��ȣ�� �޾Ƽ� DB���� ÷�������� �ϰ��� �����ϴ� �޼ҵ�
	public boolean DeleteAll( String pNo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = " delete from attach where pNo = " + pNo;
		if( this.isMonitoring )
		{ System.out.println("AttachDao :: DeleteAll() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// �Խñ� ��ȣ�� �޾Ƽ� DB���� ÷������ ����� �޾ƿ��� �޼ҵ�
	public void getList( String pNo )
	{
		this.list = null;
		if( this.DBOpen() == false ) return;
		sql  = " select aNo, pNo, fName, uqName ";
		sql += " from attach where pNo = " + pNo;
		if( this.isMonitoring ) { System.out.println("AttachDao :: getList() / \n"+sql); }
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{
			AttachVo vo = new AttachVo();
			vo.setaNo(this.GetValue("aNo"));
			vo.setpNo(this.GetValue("pNo"));
			vo.setfName(this.GetValue("fName"));
			vo.setuqName(this.GetValue("uqName"));
			if( this.list == null )
			{ this.list = new ArrayList<AttachVo>(); }
			this.list.add(vo);
		}
		this.CloseQuery(); this.DBClose(); return;
	}
	
	// ÷�� ���� ��ȣ�� DB���� ÷������ ������ �޾ƿ��� �޼ҵ� 
	public AttachVo getItem( String aNo )
	{
		AttachVo vo = null;
		if( this.DBOpen() == false ) return vo;
		sql  = " select aNo, pNo, fName, uqName ";
		sql += " from attach where aNo = " + aNo;
		if( this.isMonitoring ) { System.out.println("AttachDao :: getItem() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == true )
		{
			vo = new AttachVo();
			vo.setaNo(this.GetValue("aNo"));
			vo.setpNo(this.GetValue("pNo"));
			vo.setfName(this.GetValue("fName"));
			vo.setuqName(this.GetValue("uqName"));
		}
		this.CloseQuery(); this.DBClose(); return vo;
	}
	
	// ÷������ ����� ���� ������ ��ȯ�ϴ� �޼ҵ�
	public int GetListTotalCount()
	{
		if( this.list == null ) return 0;
		return this.list.size();
	}
	
	// �ε����� �޾� ÷������ ��Ͽ��� ���Ҹ� ��ȯ�ϴ� �޼ҵ�
	public AttachVo getItem( int index )
	{
		if( this.list == null ) return null;
		return this.list.get(index);
	}
	
	// ÷������ ��� ��ü�� ��ȯ�ϴ� �޼ҵ�
	public ArrayList<AttachVo> getArrayList()
	{
		return this.list;
	}
	
	// ÷������ ����������, �������ϸ��� �޾Ƽ�, �������ϸ��� �����ϰ�
	// ����� ������ �̸��� �ٲٴ� �޼ҵ�
	public void setAttach( String upLoadPath, String fName, String pNo )
	{	// ��� ��ȿ�� Ȯ��
		if( fName == null || fName.equals("") || 
			upLoadPath == null || upLoadPath.equals("") )
		{ return; }
		// vo�� ������ ����Ʈ Ȯ��
		if( this.list == null )
		{ this.list = new ArrayList<AttachVo>(); }
		// ���� ����� ������ �̸��� �ٲٴ� ���� =========================
		String newFName = UUID.randomUUID().toString();
		// ���� ���ϸ��, ���ο� ���ϸ��� ��ο� �Բ� ����
		String orgName = upLoadPath + "\\" + fName;
		String newName = upLoadPath + "\\" + newFName;
		// File ��ü������, ���������� �����ϰ�, ���ο� �̸��� ���� File ��ü�� �����Ѵ�
		File srcFile	= new File(orgName);
		File targetFile = new File(newName);
		// ���� ������ ���ο� �̸��� ���� File ��ü�� �̿��ؼ� rename�Ѵ�
		try {
			srcFile.renameTo(targetFile);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		// �����̸���, ���̸��� vo�� ���� ================================
		AttachVo vo = new AttachVo();
		vo.setfName(fName);
		vo.setuqName(newFName);
		vo.setpNo(pNo);
		// vo�� list�� ���� ==============================================
		this.list.add(vo);
	}

}
