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
	
	public boolean Insert( BoardVo vo )	// 게시글을 등록하는 Insert
	{	// 게시글 정보를 DB에 등록함 ===========================================
		if( this.DBOpen() == false ) return false;
		sql  = "insert into board ( uNo, pType, pTitle, pContent )"; 
		sql += " values ( ";

		sql += "  " + vo.getuNo()	 + " , ";
		sql += " '" + vo.getpType()	 + "', ";
		sql += " '" + this._R(vo.getpTitle())	 + "', ";
		sql += " '" + this._R(vo.getpContent())	 + "') ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: Insert() / \n"+sql); }
		this.RunSQL(sql);
		// 게시글 번호를 받아옴 ================================================
		sql  = " select last_insert_id() as pNo ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: Insert() / \n"+sql); }
		this.OpenQuery(sql);
		this.GetNext();
		String pNo = this.GetValue("pNo");
		this.CloseQuery(); this.DBClose();
		// 첨부파일을 DB에 등록함 ==============================================
		vo.setpNo(pNo);
		ArrayList<AttachVo> aList =  vo.getaList();
		if(aList != null) 
		{
			// 첨부파일을 등록
			AttachDao aDao = new AttachDao();
			for( AttachVo item : aList ) { aDao.Insert(item, pNo); }
		}
		return true;
	}
	
	// 게시글을 삭제하는 Delete
	public boolean Delete( String pNo )
	{
		// 댓글 삭제 ===========================================================
/*		sql  = " delete from reply where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Delete() / \n"+sql); }
		this.RunSQL(sql); */
		ReplyDao rDao = new ReplyDao();
		rDao.Delete(pNo);
		
		// 첨부파일 삭제 =======================================================
/*		sql  = "  delete from attach where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);	*/
		AttachDao aDao = new AttachDao();
		aDao.DeleteAll(pNo);
		
		// 게시글 삭제 =========================================================
		if( this.DBOpen() == false ) return false;
		sql  = " delete from board where pNo = " + pNo;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Delete() / \n" + sql); }
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// 게시글을 수정하는 Update
	public boolean Update( BoardVo vo )
	{	// 게시글을 수정 =======================================================
		if( this.DBOpen() == false ) return false;
		sql  = " update board set ";
		sql += " pTitle = '" + this._R(vo.getpTitle()) + "', ";	
		sql += " pType  = '" + this._R(vo.getpType())  + "', ";
		sql += " pContent  = '" + this._R(vo.getpContent())  + "', ";
		sql += " pDate  = now() ";	// 작성일을 수정일로 갱신할 경우에
		sql += " where pNo = " + vo.getpNo() ;
		if( this.isMonitoring ){ System.out.println("BoardDao :: Update() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		// 첨부파일을 수정 =====================================================
		if( vo.getaList() != null )
		{	// 게시판 vo에 첨부파일 vo의 list가 있으면 수행
			AttachDao aDao = new AttachDao();
			// 기존 첨부파일을 DB에서 삭제
			aDao.DeleteAll(vo.getpNo());
			for( AttachVo item : vo.getaList() )
			{	// Update 상황에 BoardVO의 aList에 pNo가 셋팅이 되어있으면
				aDao.Insert(item); // 안되어있으면 aDao.Insert(item,vo.getpNo());
			}
		}
		return true;
	}
	
	// 게시글 번호로 조회하는 Read
	// 인자로 ispCnt -> t: 조회수 증가 / f: 조회수를 놔둠
	public BoardVo Read( String pNo, boolean ispCnt )
	{
		BoardVo vo = null;
		if( this.DBOpen() == false ) return vo;
		if( ispCnt == true )	// ispCnt에 따라 조회수 변경
		{
			sql = " update board set pCnt = pCnt + 1 where pNo = " + pNo;
			if( this.isMonitoring ){ System.out.println("BoardDao :: Read() / \n"+sql); }
			this.RunSQL(sql);
		}
		// 게시물 정보 받기 ===================================================
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
		if( this.GetNext() == false )	// 조회되지 않으면 종료
		{ this.CloseQuery(); this.DBClose(); return vo; }
		
		String rc = this.GetValue("rc");
		if (rc == null) {rc = "0";}
		// BoardVo 객체 생성
		vo = new BoardVo();
		// resultset으로부터 정보를 받아 vo에 셋팅
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
		
		// 첨부파일 정보 받기
		AttachDao aDao = new AttachDao();
		aDao.getList(pNo);
		if(aDao.getArrayList() != null )
		{ vo.setaList(aDao.getArrayList()); }
		
		// 댓글 정보 받기
		ReplyDao rDao = new ReplyDao();
		rDao.getList(pNo);
		if( rDao.GetArrayList() != null )
		{ vo.setrList(rDao.getList(pNo)); }
		
		return vo;
	}
	
	// 추천수를 증가시키는 addRecnd
	public boolean addRecnd( RecommendVo vo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = "insert into recommend ( pNo, uNo )"; 
		sql += " values ( ";
		sql += " " + vo.getpNo()	 + " , ";
		sql += " " + vo.getuNo()	 + " ) ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: addRecnd() / \n"+sql); }
		this.RunSQL(sql);
		// 추천수를 증가시킴 =================================================
		sql = " update recommend set rc = rc + 1 where pNo = " + vo.getpNo();
		if( this.isMonitoring ) { System.out.println("BoardDao :: addRecnd() / \n"+sql); }
		this.RunSQL(sql);
		// 게시글 번호를 받아옴 ================================================
		sql  = " select last_insert_id() as rcNo ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: addRecnd() / \n"+sql); }
		this.OpenQuery(sql);
		this.GetNext();
		String rcNo = this.GetValue("rcNo");
		this.CloseQuery(); 
		this.DBClose();
		
		return true;
	}
	
	// 좋아요 중복검사
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
		{	// rcNo가 존재함
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
	}
	
	// 신고 수를 증가시키는 AddReport
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
		// 신고수를 증가시킴 =================================================
		sql = " update report set rp = rp + 1 where pNo = " + vo.getpNo();
		if( this.isMonitoring ) { System.out.println("BoardDao :: AddReport() / \n"+sql); }
		this.RunSQL(sql);
		// 게시글 번호를 받아옴 ================================================
		sql  = " select last_insert_id() as rpNo ";
		if( this.isMonitoring ) { System.out.println("BoardDao :: AddReport() / \n"+sql); }
		this.OpenQuery(sql);
		this.GetNext();
		String rpNo = this.GetValue("rpNo");
		this.CloseQuery(); 
		this.DBClose();
		
		return true;
	}
	
	// 신고 수가 5회 이상이면 블라인드 처리하는 Blind
	/*
	 * public boolean Blind( BoardVo vo ) { // 게시물의 블라인드 여부를 수정
	 * ======================================================= if( this.DBOpen() ==
	 * false ) return false; sql = " IF : rpCnt >= 5 THEN "; sql +=
	 * " update board set "; sql += " pBlind = 'Y' "; sql += " where pNo = " +
	 * vo.getpNo(); sql += " END IF "; if( this.isMonitoring ){
	 * System.out.println("BoardDao :: Update() / \n"+sql); } this.RunSQL(sql);
	 * this.DBClose();
	 * 
	 * return true; }
	 */
	
	// 신고 중복검사
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
		{	// rpNo가 존재함
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
	}
	
	// 게시물에서 이미지를 가져오는 메소드
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
	
	// 공지사항을 불러오는 메소드
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
	// 게시글 목록을 생성하는 GetList
	// -> 게시판 종류, 페이지 번호 -> 검색 --> listDao 
}
