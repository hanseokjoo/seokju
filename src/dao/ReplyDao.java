package dao;

import java.util.ArrayList;
import dbms.DBManager;
import vo.ReplyVo;

public class ReplyDao extends DBManager
{
	private boolean isMonitoring = true;
	private ArrayList<ReplyVo> list;
	
	// 댓글 등록
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
		// 등록된 댓글의 번호를 얻는다
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
	
	// 댓글 삭제 // 댓글 번호와, 댓글 작성자와 일치하는지 확인하고 삭제
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

	// 게시글 번호를 받아서 댓글을 일괄 삭제
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
	
	// 댓글 수정
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
	
	// 댓글 번호로 댓글을 읽어오는 메소드
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

	// 게시글의 댓글 목록 작성
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
		// 댓글번호들을 불러옴
		ArrayList<String> noList = null; // 댓글번호 리스트로 사용할 어레이리스트
		while( this.GetNext() == true)
		{	// rNo 문자열의 리스트를 생성
			String rNo = this.GetValue("rNo"); // DB에서 읽어온 rNo
			if( noList == null ) { noList = new ArrayList<String>(); }
			noList.add(rNo); // noList에 rNo를 넣는다
		}
		this.CloseQuery(); this.DBClose();

		if( noList != null )
		{
			for( String rNo : noList )
			{	// rNo 문자열의 리스트를 돌며 댓글을 읽어옴
				ReplyVo vo = this.getReply(rNo);
				if( this.list == null )
				{ this.list = new ArrayList<ReplyVo>(); }
				this.list.add(vo);
			}
		}
		return this.list;
	}
	
	// 게시글의 전체 댓글 갯수
	public int GetListTotalCount()
	{
		if( this.list == null ) return 0; 
		return this.list.size();
	}

	// 리스트에서 원소를 꺼내는 메소드
	public ReplyVo GetItem( int index )
	{
		if( this.list == null ) return null;
		return this.list.get(index);
	}
	
	// dao에 생성된 리스트를 반환하는 메소드
	public ArrayList<ReplyVo> GetArrayList()
	{
		return this.list;
	}
}
