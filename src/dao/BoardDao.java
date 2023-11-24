package dao;

import dbms.DBManager;
import vo.*;
import dao.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDao extends DBManager
{
	private String sql = "";
	private boolean isMonitoring = true;
	
	public final static int RCN_ERROR		= 0;
	public final static int DUPLICATE		= 1;
	public final static int NOT_DUPLICATE	= 2;
	
	public boolean Insert( BoardVo vo )	// �Խñ��� ����ϴ� Insert
	{	// �Խñ� ������ DB�� ����� ===========================================
		if( this.DBOpen() == false ) return false;
		sql  = "insert into board ( uNo, pType, pTitle, pContent )"; 
		sql += " values ( ";

		sql += "  " + vo.getuNo()	 + " , ";
		sql += " '" + vo.getpType()	 + "', ";
		sql += " '" + this._R(vo.getpTitle())	 + "', ";
		sql += " '" + this._R(vo.getpContent())	 + "') ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: Insert() / \n"+sql); }
		this.RunSQL(sql);
		// �Խñ� ��ȣ�� �޾ƿ� ================================================
		sql  = " select last_insert_id() as pNo ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: Insert() / \n"+sql); }
		this.OpenQuery(sql);
		this.GetNext();
		String pNo = this.GetValue("pNo");
		this.CloseQuery(); this.DBClose();
		// ÷�������� DB�� ����� ==============================================
		vo.setpNo(pNo);
		ArrayList<AttachVo> aList =  vo.getaList();
		if(aList != null) 
		{
			// ÷�������� ���
			AttachDao aDao = new AttachDao();
			for( AttachVo item : aList ) { aDao.Insert(item, pNo); }
		}
		return true;
	}
	
	// �Խñ��� �����ϴ� Delete
	public boolean Delete( String pNo )
	{
		// ��� ���� ===========================================================
/*		sql  = " delete from reply where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Delete() / \n"+sql); }
		this.RunSQL(sql); */
		ReplyDao rDao = new ReplyDao();
		rDao.Delete(pNo);
		
		// ÷������ ���� =======================================================
/*		sql  = "  delete from attach where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);	*/
		AttachDao aDao = new AttachDao();
		aDao.DeleteAll(pNo);
		
		// �Խñ� ���� =========================================================
		if( this.DBOpen() == false ) return false;
		sql  = " delete from board where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Delete() / \n" + sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// �Խñ��� �����ϴ� Update
	public boolean Update( BoardVo vo )
	{	// �Խñ��� ���� =======================================================
		if( this.DBOpen() == false ) return false;
		sql  = " update board set ";
		sql += " pTitle = '" + this._R(vo.getpTitle()) + "', ";	
		sql += " pType  = '" + this._R(vo.getpType())  + "', ";
		sql += " pContent  = '" + this._R(vo.getpContent())  + "', ";
		sql += " pDate  = now() ";	// �ۼ����� �����Ϸ� ������ ��쿡
		sql += " where pNo = " + vo.getpNo() ;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Update() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		// ÷�������� ���� =====================================================
		if( vo.getaList() != null )
		{	// �Խ��� vo�� ÷������ vo�� list�� ������ ����
			AttachDao aDao = new AttachDao();
			// ���� ÷�������� DB���� ����
			aDao.DeleteAll(vo.getpNo());
			for( AttachVo item : vo.getaList() )
			{	// Update ��Ȳ�� BoardVO�� aList�� pNo�� ������ �Ǿ�������
				aDao.Insert(item); // �ȵǾ������� aDao.Insert(item,vo.getpNo());
			}
		}
		return true;
	}
	
	// �Խñ� ��ȣ�� ��ȸ�ϴ� Read
	// ���ڷ� ispCnt -> t: ��ȸ�� ���� / f: ��ȸ���� ����
	public BoardVo Read( String pNo, boolean ispCnt )
	{
		BoardVo vo = null;
		if( this.DBOpen() == false ) return vo;
		if( ispCnt == true )	// ispCnt�� ���� ��ȸ�� ����
		{
			sql = " update board set pCnt = pCnt + 1 where pNo = " + pNo;
			if( this.isMonitoring ){ System.out.println("BoardDao :: Read() / \n"+sql); }
			this.RunSQL(sql);
		}
		// �Խù� ���� �ޱ� ===================================================
		sql  = " select ";
		sql += " pNo, uNo, pType, pTitle, pContent, ";
		sql += " date(pDate) as pDate , pCnt, ";
		sql += " ( select uName from member ";
		sql += " where uNo = board.uNo ) as uName, ";
		sql += " ifnull((select count(rcNo) from recommend ";
		sql += " where pNo = board.pNo ),0) as rc, ";
		sql += " ( select uLevel from member ";
		sql += " where uNo = board.uNo ) as uLevel";
		sql += " from board where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// ��ȸ���� ������ ����
		{ this.CloseQuery(); this.DBClose(); return vo; }
		
		String rc = this.GetValue("rc");
		if (rc == null) {rc = "0";}
		// BoardVo ��ü ����
		vo = new BoardVo();
		// resultset���κ��� ������ �޾� vo�� ����
		vo.setpNo(this.GetValue("pNo"));
		vo.setuNo(this.GetValue("uNo"));
		vo.setpType(this.GetValue("pType"));
		vo.setpTitle(this.GetValue("pTitle"));
		vo.setpContent(this.GetValue("pContent"));
		vo.setpDate(this.GetValue("pDate"));
		vo.setpCnt(this.GetValue("pCnt"));
		vo.setrc(this.GetValue("rc"));
		vo.setuName(this.GetValue("uName"));
		vo.setuLevel(this.GetValue("uLevel"));
		this.CloseQuery();
		this.DBClose();
		
		// ÷������ ���� �ޱ�
		AttachDao aDao = new AttachDao();
		aDao.getList(pNo);
		if(aDao.getArrayList() != null )
		{ vo.setaList(aDao.getArrayList()); }
		
		// ��� ���� �ޱ�
		ReplyDao rDao = new ReplyDao();
		rDao.getList(pNo);
		if( rDao.GetArrayList() != null )
		{ vo.setrList(rDao.getList(pNo)); }
		
		return vo;
	}
	
	// ��õ���� ������Ű�� addRecnd
	public boolean addRecnd( RecommendVo vo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = "insert into recommend ( pNo, uNo )"; 
		sql += " values ( ";
		sql += " " + vo.getpNo()	 + " , ";
		sql += " " + vo.getuNo()	 + " ) ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: addRecnd() / \n"+sql); }
		this.RunSQL(sql);
		// ��õ���� ������Ŵ =================================================
		sql = " update recommend set rc = rc + 1 where pNo = " + vo.getpNo();
		if( this.isMonitoring ) { System.out.println("BoardDao :: addRecnd() / \n"+sql); }
		this.RunSQL(sql);
		// �Խñ� ��ȣ�� �޾ƿ� ================================================
		sql  = " select last_insert_id() as rcNo ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: addRecnd() / \n"+sql); }
		this.OpenQuery(sql);
		this.GetNext();
		String rcNo = this.GetValue("rcNo");
		this.CloseQuery(); 
		this.DBClose();
		
		return true;
	}
	
	// ���ƿ� �ߺ��˻�
	public int IsDuplicate(String uNo, String pNo)
	{
		if( this.DBOpen() == false ) return RCN_ERROR;
		sql = " select rcNo from recommend where uNo = " + uNo + " and pNo = " + pNo;
		if( this.isMonitoring )
		{
			System.out.println("ScrapDao :: IsDuplicate() / \n"+sql);
		}
		this.OpenQuery(sql);
		if(this.GetNext() == true)
		{	// rcNo�� ������
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
	}
	
	// �Ű� ���� ������Ű�� AddReport
	public boolean AddReport( ReportVo vo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = "insert into report ( pNo, uNo, rpType, rpNote )"; 
		sql += " values ( ";
		sql += " " + vo.getpNo()	 + " , ";
		sql += " " + vo.getuNo()	 + " , ";
		sql += " " + vo.getrpType()	 + " , ";
		sql += " '" + vo.getrpNote() + "' ) ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: AddReport() / \n"+sql); }
		this.RunSQL(sql);
		// �Ű���� ������Ŵ =================================================
		sql = " update report set rp = rp + 1 where pNo = " + vo.getpNo();
		if( this.isMonitoring ) { System.out.println("BoardDao :: AddReport() / \n"+sql); }
		this.RunSQL(sql);
		// �Խñ� ��ȣ�� �޾ƿ� ================================================
		sql  = " select last_insert_id() as rpNo ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: AddReport() / \n"+sql); }
		this.OpenQuery(sql);
		this.GetNext();
		String rpNo = this.GetValue("rpNo");
		this.CloseQuery(); 
		this.DBClose();
		
		return true;
	}
	
	// �Ű� ���� 5ȸ �̻��̸� ����ε� ó���ϴ� Blind
	/*
	 * public boolean Blind( BoardVo vo ) { // �Խù��� ����ε� ���θ� ����
	 * ======================================================= if( this.DBOpen() ==
	 * false ) return false; sql = " IF : rpCnt >= 5 THEN "; sql +=
	 * " update board set "; sql += " pBlind = 'Y' "; sql += " where pNo = " +
	 * vo.getpNo(); sql += " END IF "; if( this.isMonitoring ){
	 * System.out.println("BoardDao :: Update() / \n"+sql); } this.RunSQL(sql);
	 * this.DBClose();
	 * 
	 * return true; }
	 */
	
	// �Ű� �ߺ��˻�
	public int RpDuplicate(String uNo, String pNo)
	{
		if( this.DBOpen() == false ) return RCN_ERROR;
		sql = " select rpNo from report where uNo = " + uNo + " and pNo = " + pNo;
		if( this.isMonitoring )
		{
			System.out.println("ReportDao :: RpDuplicate() / \n"+sql);
		}
		this.OpenQuery(sql);
		if(this.GetNext() == true)
		{	// rpNo�� ������
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
	}
	
	// �Խù����� �̹����� �������� �޼ҵ�
	public List<String> getImagesFromBoard(String pType, int limit) {
	    List<String> imageList = new ArrayList<>();
	    if (this.DBOpen() == false) return imageList;
	    sql = " SELECT b.pNo AS pNo, b.pTitle AS pTitle, ";
	    sql += " ( SELECT a.uqName FROM attach AS a WHERE a.pNo = b.pNo ";
	    sql += " ORDER BY a.aNo ASC LIMIT 1 ) AS uqName ";
	    sql += " FROM board AS b WHERE b.pType = '"+ pType +"' AND EXISTS ( SELECT 1 FROM recommend AS r WHERE r.pNo = b.pNo) ";
	    sql += " ORDER BY ( SELECT COUNT(rcNo) FROM recommend AS r WHERE r.pNo = b.pNo ) DESC ";
	    sql += " LIMIT " + limit;
	    if( this.isMonitoring ) { System.out.println("BoardDao :: getImagesFromBoard() / \n"+sql); }
	    this.OpenQuery(sql);
	    while (this.GetNext()) 
	    {
//	        String uqName = this.GetValue("uqName");
//	        String pNo = this.GetValue("pNo");
//	        String pTitle = this.GetValue("pTitle");
	        String uqName = this.GetValue("uqName") + this.GetValue("pNo") + "`" + this.GetValue("pTitle");
	        if (uqName != null) {imageList.add(uqName);}
	    }
	    this.CloseQuery();
	    this.DBClose();
	    return imageList;
	}
	
	// ���������� �ҷ����� �޼ҵ�
	public List<BoardVo> ReadNo() {
		List<BoardVo> boardList = new ArrayList<>();
		if (this.DBOpen() == false) return boardList;
		sql = "SELECT pNo, uNo, pTitle, pContent, pType, ";
	    sql += "date(pDate) as pDate , pCnt, ";
	    sql += "(select uName from member ";
	    sql += "where uNo = board.uNo) as uName, ";
	    sql += "ifnull((select count(rcNo) from recommend ";
	    sql += "where pNo = board.pNo),0) as rc, ";
	    sql += "(select uLevel from member ";
	    sql += "where uNo = board.uNo) as uLevel ";
	    sql += "FROM board WHERE pType = 'NO' ORDER BY pDate DESC LIMIT 3 ";
		if( this.isMonitoring ){ System.out.println("BoardDao :: ReadNo() / \n"+sql); }
		this.OpenQuery(sql);
		while (this.GetNext()) 
		{
	        BoardVo vo = new BoardVo();
	        vo.setpNo(this.GetValue("pNo"));
	        vo.setuNo(this.GetValue("uNo"));
	        vo.setpType(this.GetValue("pType"));
	        vo.setpTitle(this.GetValue("pTitle"));
	        vo.setpContent(this.GetValue("pContent"));
	        vo.setpDate(this.GetValue("pDate"));
	        vo.setpCnt(this.GetValue("pCnt"));
	        vo.setrc(this.GetValue("rc"));
	        vo.setuName(this.GetValue("uName"));
	        vo.setuLevel(this.GetValue("uLevel"));
	        boardList.add(vo);
	    }
		
		this.CloseQuery();
	    this.DBClose();
		
		return boardList;
	}
	// �Խñ� ����� �����ϴ� GetList
	// -> �Խ��� ����, ������ ��ȣ -> �˻� --> listDao 
}
