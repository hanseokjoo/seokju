package dao;

import java.util.ArrayList;
import dbms.DBManager;
import vo.*;

import java.io.File;
import java.util.UUID;

public class AttachDao extends DBManager
{
	// 첨부파일의 리스트
	private ArrayList<AttachVo> list;
	private String sql = "";
	
	// 첨부파일 vo, 게시글 번호를 받아서 DB에 넣는 메소드
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
	
	// 첨부파일 vo를 받아서 DB에 넣는 메소드
	public boolean Insert( AttachVo vo )
	{
		return this.Insert(vo, vo.getpNo());
	}
	
	// 첨부파일 번호를 받아서 DB에서 삭제하는 메소드
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
	
	// 게시글 번호를 받아서 DB에서 첨부파일을 일괄로 삭제하는 메소드
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
	
	// 게시글 번호를 받아서 DB에서 첨부파일 목록을 받아오는 메소드
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
	
	// 첨부 파일 번호로 DB에서 첨부파일 정보를 받아오는 메소드 
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
	
	// 첨부파일 목록의 원소 갯수를 반환하는 메소드
	public int GetListTotalCount()
	{
		if( this.list == null ) return 0;
		return this.list.size();
	}
	
	// 인덱스를 받아 첨부파일 목록에서 원소를 반환하는 메소드
	public AttachVo getItem( int index )
	{
		if( this.list == null ) return null;
		return this.list.get(index);
	}
	
	// 첨부파일 목록 자체를 반환하는 메소드
	public ArrayList<AttachVo> getArrayList()
	{
		return this.list;
	}
	
	// 첨부파일 저장폴더와, 원본파일명을 받아서, 물리파일명을 생성하고
	// 저장된 파일의 이름을 바꾸는 메소드
	public void setAttach( String upLoadPath, String fName, String pNo )
	{	// 경로 유효성 확인
		if( fName == null || fName.equals("") || 
			upLoadPath == null || upLoadPath.equals("") )
		{ return; }
		// vo를 저장할 리스트 확인
		if( this.list == null )
		{ this.list = new ArrayList<AttachVo>(); }
		// 실제 저장된 파일의 이름을 바꾸는 내용 =========================
		String newFName = UUID.randomUUID().toString();
		// 원본 파일명과, 새로운 파일명을 경로와 함께 저장
		String orgName = upLoadPath + "\\" + fName;
		String newName = upLoadPath + "\\" + newFName;
		// File 객체형으로, 원본파일을 지정하고, 새로운 이름을 갖는 File 객체를 생성한다
		File srcFile	= new File(orgName);
		File targetFile = new File(newName);
		// 원본 파일을 새로운 이름을 넣은 File 객체를 이용해서 rename한다
		try {
			srcFile.renameTo(targetFile);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		// 원본이름과, 새이름을 vo에 저장 ================================
		AttachVo vo = new AttachVo();
		vo.setfName(fName);
		vo.setuqName(newFName);
		vo.setpNo(pNo);
		// vo를 list에 저장 ==============================================
		this.list.add(vo);
	}

}
