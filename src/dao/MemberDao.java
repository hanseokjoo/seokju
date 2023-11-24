package dao;

import dbms.DBManager;
import util.MailAuth;
import util.MailSender;
import util.paging;
import vo.*;
import dao.*;
import java.util.ArrayList;

public class MemberDao extends DBManager
{
	public final static int MAIL_ERROR		= 0;
	public final static int DUPLICATE		= 1;
	public final static int NOT_DUPLICATE	= 2;
	public final static int ERROR			= 0;
	public final static int DUPLICATE1		= 1;
	public final static int NOT_DUPLICATE1	= 2;
	
	private String sql = "";
	private String where = "";
	private boolean isMonitoring = true;
	
	private SearchVo search = null;
	private ArrayList<MemberVo> list;
	
	// 생성자 
	public MemberDao( SearchVo search )
	{	
		this.search = search;
		if( search.getKeyword().equals("") == false )
		{	// sql 구문에 and를 추가해야되는지 판단
			if( !this.where.equals("") ) { this.where += " and "; } // 특정 게시판이면 and를 추가
			// search 객체로부터 검색 조건으로 sql 구문 생성
			switch( search.getKind() )
			{
			case "E":
				this.where += " uMail like '%" + search.getKeyword() + "%' "; break;
			case "N":
				this.where += " uName like '%" + search.getKeyword() + "%' "; break;
			}
		}
	}
	
	// 회원 아이디 중복 검사
		// 리턴값 0 / 1 / 2
		// MAIL_ERROR : 오류 / DUPLICATE : 중복 / NOT_DUPLICATE : 사용가능
	public int IsDuplicate1( String uName )
	{
		//[1] DB연결
//			this.DBOpen();
		if( this.DBOpen() == false )
		{
			return ERROR;
		}
		
		// id 중복검사용 sql문을 작성 -> 실행
		String sql = "";
		sql += " select uNo from member where uName = '" + uName + "' ";;
		
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: IsDuplicate() / \n"+sql);
		}
		
		this.OpenQuery(sql);
		// 결과에 따라 오류 / 중복 / 가능 을 반환
		if( this.GetNext() == true )
		{	// 아이디가 있음
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE1;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE1;
	}

	// 회원 아이디 중복 검사
	// 리턴값 0 / 1 / 2
	// MAIL_ERROR : 오류 / DUPLICATE : 중복 / NOT_DUPLICATE : 사용가능
	public int IsDuplicate( String id )
	{
		//[1] DB연결
//		this.DBOpen();
		if( this.DBOpen() == false )
		{
			return MAIL_ERROR;
		}
		
		// id 중복검사용 sql문을 작성 -> 실행
		String sql = "";
		sql += " select uNo from member where uMail = '" + id + "' ";;
		
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: IsDuplicate() / \n"+sql);
		}
		
		this.OpenQuery(sql);
		// 결과에 따라 오류 / 중복 / 가능 을 반환
		if( this.GetNext() == true )
		{	// 아이디가 있음
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
	}

	// 회원가입
	public boolean Join( MemberVo vo )
	{
		// 아이디 중복 체크
		if( this.IsDuplicate(vo.getuMail()) == MemberDao.DUPLICATE )
		{ 
			return false; 
		}
		
		// DB연결
		if( this.DBOpen() == false ) return false;
		
		// 회원가입 SQl문을 작성 -> 실행
		sql += " insert into member ";
		sql += " ( uMail, uPW, uName, uInter ) ";
		sql += " values ( ";
		sql += " '" + vo.getuMail() + "', ";
		sql += " md5('" + vo.getuPW() + "'), ";
		sql += " '" + vo.getuName()  +"', ";
		sql += " '" + vo.getuInter() + "') ";
		
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: Join() / \n"+sql);
		}
		try
		{
			System.out.println(this.RunSQL(sql));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// 등록된 회원번호를 얻는다 ===========================================
		sql  = " select last_insert_id() as uNo ";
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: Join() / \n"+sql);
		}
		this.OpenQuery(sql);
		this.GetNext();
		String uNo = this.GetValue("uNo");
		vo.setuNo(uNo);	// vo에 등록 번호를 넣는다
		this.CloseQuery();
		vo.PrintInfo();
		
		// DB 연결 종료
		this.DBClose();
		return true;
	}

	// 로그인
	public MemberVo Login( String id, String pw )
	{	// 결과를 담을 객체 생성
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		// id, pw를 이용하여 사용자 정보를 받아온다
		sql += " select uNo, uName,  uMail, uInter, uRetire, uLevel ";
		sql += " from member where uMail like '" + id + "' ";
		sql += " and uPW like md5('" + pw + "') ";
		sql += " and uRetire = 'U' ";
		if( this.isMonitoring ) { System.out.println("MemberDao :: Login() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )
		{ 
			this.CloseQuery(); this.DBClose(); return vo; 
		}
		// 로그인 사용자 vo를 생성
		vo = new MemberVo();
		
		vo.setuNo(this.GetValue("uNo"));
		vo.setuMail(this.GetValue("uMail"));
		vo.setuName(this.GetValue("uName"));
		vo.setuInter(this.GetValue("uInter"));
		vo.setuRetire(this.GetValue("uRetire"));
		vo.setuLevel(this.GetValue("uLevel"));
		this.CloseQuery(); this.DBClose();
		return vo;
	}
	
	// 회원 정보를 수정하는 Update
	public boolean Update( MemberVo vo )
	{	// 회원정보를 수정 =======================================================
		if( this.DBOpen() == false ) return false;
		sql  = " update member set ";
		sql += " uName  = '" + this._R(vo.getuName())  + "', ";
		sql += " uInter  = '" + this._R(vo.getuInter())  + "' ";
		sql += " where uNo = " + vo.getuNo() ;
		if( this.isMonitoring )
		{ 
			System.out.println("MemberDao :: Update() / \n"+sql); 
		}
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// 회원의 비밀번호를 수정하는 UpdatePW
	public boolean UpdatePW( MemberVo vo )
	{	// 회원정보를 수정 =======================================================
		if( this.DBOpen() == false ) return false;
		sql  = " update member set ";
		sql += " uPW  = md5('" + vo.getuPW()  + "') ";
		sql += " where uMail = '" + vo.getuMail() + "' " ;
		sql += " and uName = '" + vo.getuName() + "' " ;		
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: Update() / \n"+sql); 
		}
		vo.PrintInfo();
		this.RunSQL(sql);
		this.DBClose();
		return true;
	}
	
	// 사용자 번호로 조회하는 Read
	public MemberVo Read( String uNo )
	{
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// 유저 정보 받기 ================================
		sql += " select ";
		sql += " uNo, uMail, uName, uInter, joinDate ";
		sql += " from member where uNo = " + uNo;
		if( this.isMonitoring ){ System.out.println("MemberDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// 조회되지 않으면 종료
		{ 
			this.CloseQuery(); this.DBClose(); return vo; 
		}
		// MemberVo 객체 생성
		vo = new MemberVo();
		
		// resultset으로부터 정보를 받아 vo에 셋팅
		vo.setuNo(this.GetValue("uNo"));
		vo.setuMail(this.GetValue("uMail"));
		vo.setuName(this.GetValue("uName"));
		vo.setuInter(this.GetValue("uInter"));
		vo.setJoinDate(this.GetValue("joinDate"));
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// 사용자 이름과 닉네임으로 조회하는 mRead
	public MemberVo mRead( String uMail, String uName )
	{
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// 유저 정보 받기 ================================
		sql += " select * ";
		sql += " from member ";
		sql += " where uMail = '" + uMail + "' and uName = '" + uName + "' ";
		if( this.isMonitoring ){ System.out.println("MemberDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// 조회되지 않으면 종료
		{ this.CloseQuery(); this.DBClose(); return vo; }
		// BoardVo 객체 생성
		vo = new MemberVo();
		// resultset으로부터 정보를 받아 vo에 셋팅
		vo.setuNo(this.GetValue("uNo"));
		vo.setuMail(this.GetValue("uMail"));
		vo.setuName(this.GetValue("uName"));
		vo.setuInter(this.GetValue("uInter"));
		vo.setJoinDate(this.GetValue("joinDate"));
		
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// 회원이 비밀번호를 수정할 때 비밀번호가 일치하는지 확인하는 pRead
	public MemberVo pRead( String curPW, String uNo )
	{
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// 유저 정보 받기 ================================
		sql += " select * ";
		sql += " from member ";
		sql += " where uPW = md5('" + curPW + "') and uNo = '" + uNo + "' ";
		if( this.isMonitoring ){ System.out.println("MemberDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// 조회되지 않으면 종료
		{ this.CloseQuery(); this.DBClose(); return vo; }
		// BoardVo 객체 생성
		vo = new MemberVo();
		// resultset으로부터 정보를 받아 vo에 셋팅
		vo.setuNo(this.GetValue("uNo"));
		vo.setuPW(this.GetValue("uPW"));		
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// 회원이 비밀번호를 수정하는  UpdateUPW
	public boolean UpdateUPW( String newPW, String uNo )
	{
		if( this.DBOpen() == false ) return false;
		sql  = " update member set ";
		sql += " uPW  = md5('" + newPW  + "') ";
		sql += " where uNo = '" + uNo + "' " ;
		
		if( this.isMonitoring )
		{ System.out.println("MemberDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		
		return true;
	}
	
	// 회원에서 탈퇴하는 RetireMember
	public boolean RetireMember(String uMail, String uPW)
	{
		System.out.printf("MemberDao.java: RetireMember(%s,%s)", uMail, uPW);
		if( this.DBOpen() == false ) return false;
		String sql  = " update member set uRetire = 'R' where uMail = '" + uMail + "' and uPW = '" + uPW + "'";
		
		if( this.isMonitoring )
		{ System.out.println("MemberDao :: Delete() / \n"+sql); }
		this.RunSQL(sql);
		this.DBClose();
		
		System.out.printf("MemberDao.java: RetireMember(%s,%s)", uMail, uPW);
		return true;
	}
	
	//임시 비밀번호 생성
	public static String GetRandomPW()
	{
		String	key		= "0123456789abcdefghijklmnopqrstuvwxyz";
		int		len		= key.length();
		String	code	= "";
		
		for( int i = 0; i < 7 ; i++ )
		{
			int r	= (int)( Math.random() * len );
			code   += key.substring( r, r + 1 );
		}
		return code;
	}
	
	//임시 비밀번호를 메일로 발송한다.
	public boolean SendMail( MemberVo vo )
	{
//		String code	= MailAuth.GetRandom(7);
		String code	= vo.getuPW();
		
		MailSender server = new MailSender("google");
		
		String from = "ezen@ezen.com";						// 보내는 주소
		String ID   = "nalahan561@gmail.com";				// 계정
		String PW   = "lodlsvnruhweongs";					// 계정인증
		
		// 제목 생성
		String title = "임시 비밀번호 발송메일 입니다";
		
		// 본문 생성
		String message = "";
	    message = "임시 비밀번호는 [ "+ code +" ] 입니다.<br>";
	    //contents
	    String html_contents = "";
	    html_contents += "<html>";
	    html_contents += "<head>";
	    html_contents += "<meta charset='utf-8'>";
	    html_contents += "</head>";
	    html_contents += "<body>";
	    html_contents += "<font color='red'>"+message+"</font>";
	    html_contents += "</body>";
	    html_contents += "</html>";
		
		if( server.MailSend(from, vo.getuMail(), ID, PW, title, html_contents) == true )
		{
			System.out.println("성공적으로 이메일을 발송하였습니다.");
			return true;
		}else
		{
			System.out.println("메일 발송 오류입니다.");
			return false;
		}
	}
	
	// 전체 회원 수를 얻는 메소드
	public int getTotal()
	{
		int total = 0;
		if( this.DBOpen() == false ) return 0;
		this.sql  = " select count(*) as count from member ";
		if( this.where.equals("") == false )
		{
			this.sql += " where " + this.where;
		}
		if( this.isMonitoring ) { System.out.println("MemberDao :: getTotal() / \n"+sql); }
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
	
	// 화면에 표시할 게시글 리스트를 받아오는 메소드 - 게시글의 일부 정보만 받아옴
	public boolean getList( int curPage, int perPage)
	{
		this.list = new ArrayList<MemberVo>();
		if( this.DBOpen() == false ) return false;
		int offset = ( curPage - 1 ) * paging.perPage; // 1페이지는 0 / 2페이지는 perPage * 1-> curPage -1 / 3페이지는 perPage * 2 -> curPage -1
		sql  = " select uNo, uName, uMail, joinDate, uRetire ";	
		sql += " from member ";
		if( this.where.equals("") == false )
		{
			sql += " where " + this.where + " ";
		}
		// 정렬
		sql += search.getmOrder();
		// limit
		sql += " limit " + offset + ", " + perPage;
		if( this.isMonitoring ) { System.out.println("MemberDao :: getList() / \n"+sql); }
		
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{	// MemberVo vo에 넣고
			String uNo    = this.GetValue("uNo");
			String uName  = this.GetValue("uName");
			String uMail  = this.GetValue("uMail");
			
			String joinDate  = this.GetValue("joinDate");
			String uRetire = this.GetValue("uRetire");
			
			MemberVo vo = new MemberVo();
			vo.setuNo(uNo);
			vo.setuName(uName);
			vo.setuMail(uMail);
			vo.setJoinDate(joinDate);
			vo.setuRetire(uRetire);
			
			this.list.add(vo);
		}
		this.CloseQuery();
		this.DBClose();
		return true;
	}
	
	// 생성된 리스트의 개수를 얻는 메소드
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// 리스트에서 인덱스로 게시글 정보를 얻는 메소드
	public MemberVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	// 생성된 리스트 자체를 반환하는 메소드
	public ArrayList<MemberVo> getListArray()
	{
		return this.list;
	}
}
	

