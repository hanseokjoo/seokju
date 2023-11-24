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
	
	// ������ 
	public MemberDao( SearchVo search )
	{	
		this.search = search;
		if( search.getKeyword().equals("") == false )
		{	// sql ������ and�� �߰��ؾߵǴ��� �Ǵ�
			if( !this.where.equals("") ) { this.where += " and "; } // Ư�� �Խ����̸� and�� �߰�
			// search ��ü�κ��� �˻� �������� sql ���� ����
			switch( search.getKind() )
			{
			case "E":
				this.where += " uMail like '%" + search.getKeyword() + "%' "; break;
			case "N":
				this.where += " uName like '%" + search.getKeyword() + "%' "; break;
			}
		}
	}
	
	// ȸ�� ���̵� �ߺ� �˻�
		// ���ϰ� 0 / 1 / 2
		// MAIL_ERROR : ���� / DUPLICATE : �ߺ� / NOT_DUPLICATE : ��밡��
	public int IsDuplicate1( String uName )
	{
		//[1] DB����
//			this.DBOpen();
		if( this.DBOpen() == false )
		{
			return ERROR;
		}
		
		// id �ߺ��˻�� sql���� �ۼ� -> ����
		String sql = "";
		sql += " select uNo from member where uName = '" + uName + "' ";;
		
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: IsDuplicate() / \n"+sql);
		}
		
		this.OpenQuery(sql);
		// ����� ���� ���� / �ߺ� / ���� �� ��ȯ
		if( this.GetNext() == true )
		{	// ���̵� ����
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE1;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE1;
	}

	// ȸ�� ���̵� �ߺ� �˻�
	// ���ϰ� 0 / 1 / 2
	// MAIL_ERROR : ���� / DUPLICATE : �ߺ� / NOT_DUPLICATE : ��밡��
	public int IsDuplicate( String id )
	{
		//[1] DB����
//		this.DBOpen();
		if( this.DBOpen() == false )
		{
			return MAIL_ERROR;
		}
		
		// id �ߺ��˻�� sql���� �ۼ� -> ����
		String sql = "";
		sql += " select uNo from member where uMail = '" + id + "' ";;
		
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: IsDuplicate() / \n"+sql);
		}
		
		this.OpenQuery(sql);
		// ����� ���� ���� / �ߺ� / ���� �� ��ȯ
		if( this.GetNext() == true )
		{	// ���̵� ����
			this.CloseQuery();
			this.DBClose();
			return DUPLICATE;
		}
		this.CloseQuery();
		this.DBClose();
		return NOT_DUPLICATE;
	}

	// ȸ������
	public boolean Join( MemberVo vo )
	{
		// ���̵� �ߺ� üũ
		if( this.IsDuplicate(vo.getuMail()) == MemberDao.DUPLICATE )
		{ 
			return false; 
		}
		
		// DB����
		if( this.DBOpen() == false ) return false;
		
		// ȸ������ SQl���� �ۼ� -> ����
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
		
		// ��ϵ� ȸ����ȣ�� ��´� ===========================================
		sql  = " select last_insert_id() as uNo ";
		if( this.isMonitoring )
		{
			System.out.println("MemberDao :: Join() / \n"+sql);
		}
		this.OpenQuery(sql);
		this.GetNext();
		String uNo = this.GetValue("uNo");
		vo.setuNo(uNo);	// vo�� ��� ��ȣ�� �ִ´�
		this.CloseQuery();
		vo.PrintInfo();
		
		// DB ���� ����
		this.DBClose();
		return true;
	}

	// �α���
	public MemberVo Login( String id, String pw )
	{	// ����� ���� ��ü ����
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		// id, pw�� �̿��Ͽ� ����� ������ �޾ƿ´�
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
		// �α��� ����� vo�� ����
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
	
	// ȸ�� ������ �����ϴ� Update
	public boolean Update( MemberVo vo )
	{	// ȸ�������� ���� =======================================================
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
	
	// ȸ���� ��й�ȣ�� �����ϴ� UpdatePW
	public boolean UpdatePW( MemberVo vo )
	{	// ȸ�������� ���� =======================================================
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
	
	// ����� ��ȣ�� ��ȸ�ϴ� Read
	public MemberVo Read( String uNo )
	{
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// ���� ���� �ޱ� ================================
		sql += " select ";
		sql += " uNo, uMail, uName, uInter, joinDate ";
		sql += " from member where uNo = " + uNo;
		if( this.isMonitoring ){ System.out.println("MemberDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// ��ȸ���� ������ ����
		{ 
			this.CloseQuery(); this.DBClose(); return vo; 
		}
		// MemberVo ��ü ����
		vo = new MemberVo();
		
		// resultset���κ��� ������ �޾� vo�� ����
		vo.setuNo(this.GetValue("uNo"));
		vo.setuMail(this.GetValue("uMail"));
		vo.setuName(this.GetValue("uName"));
		vo.setuInter(this.GetValue("uInter"));
		vo.setJoinDate(this.GetValue("joinDate"));
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// ����� �̸��� �г������� ��ȸ�ϴ� mRead
	public MemberVo mRead( String uMail, String uName )
	{
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// ���� ���� �ޱ� ================================
		sql += " select * ";
		sql += " from member ";
		sql += " where uMail = '" + uMail + "' and uName = '" + uName + "' ";
		if( this.isMonitoring ){ System.out.println("MemberDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// ��ȸ���� ������ ����
		{ this.CloseQuery(); this.DBClose(); return vo; }
		// BoardVo ��ü ����
		vo = new MemberVo();
		// resultset���κ��� ������ �޾� vo�� ����
		vo.setuNo(this.GetValue("uNo"));
		vo.setuMail(this.GetValue("uMail"));
		vo.setuName(this.GetValue("uName"));
		vo.setuInter(this.GetValue("uInter"));
		vo.setJoinDate(this.GetValue("joinDate"));
		
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// ȸ���� ��й�ȣ�� ������ �� ��й�ȣ�� ��ġ�ϴ��� Ȯ���ϴ� pRead
	public MemberVo pRead( String curPW, String uNo )
	{
		MemberVo vo = null;
		if( this.DBOpen() == false ) return vo;
		
		// ���� ���� �ޱ� ================================
		sql += " select * ";
		sql += " from member ";
		sql += " where uPW = md5('" + curPW + "') and uNo = '" + uNo + "' ";
		if( this.isMonitoring ){ System.out.println("MemberDao :: Read() / \n"+sql); }
		this.OpenQuery(sql);
		if( this.GetNext() == false )	// ��ȸ���� ������ ����
		{ this.CloseQuery(); this.DBClose(); return vo; }
		// BoardVo ��ü ����
		vo = new MemberVo();
		// resultset���κ��� ������ �޾� vo�� ����
		vo.setuNo(this.GetValue("uNo"));
		vo.setuPW(this.GetValue("uPW"));		
		this.CloseQuery();
		this.DBClose();
		
		return vo;
	}
	
	// ȸ���� ��й�ȣ�� �����ϴ�  UpdateUPW
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
	
	// ȸ������ Ż���ϴ� RetireMember
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
	
	//�ӽ� ��й�ȣ ����
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
	
	//�ӽ� ��й�ȣ�� ���Ϸ� �߼��Ѵ�.
	public boolean SendMail( MemberVo vo )
	{
//		String code	= MailAuth.GetRandom(7);
		String code	= vo.getuPW();
		
		MailSender server = new MailSender("google");
		
		String from = "ezen@ezen.com";						// ������ �ּ�
		String ID   = "nalahan561@gmail.com";				// ����
		String PW   = "lodlsvnruhweongs";					// ��������
		
		// ���� ����
		String title = "�ӽ� ��й�ȣ �߼۸��� �Դϴ�";
		
		// ���� ����
		String message = "";
	    message = "�ӽ� ��й�ȣ�� [ "+ code +" ] �Դϴ�.<br>";
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
			System.out.println("���������� �̸����� �߼��Ͽ����ϴ�.");
			return true;
		}else
		{
			System.out.println("���� �߼� �����Դϴ�.");
			return false;
		}
	}
	
	// ��ü ȸ�� ���� ��� �޼ҵ�
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
	
	// ȭ�鿡 ǥ���� �Խñ� ����Ʈ�� �޾ƿ��� �޼ҵ� - �Խñ��� �Ϻ� ������ �޾ƿ�
	public boolean getList( int curPage, int perPage)
	{
		this.list = new ArrayList<MemberVo>();
		if( this.DBOpen() == false ) return false;
		int offset = ( curPage - 1 ) * paging.perPage; // 1�������� 0 / 2�������� perPage * 1-> curPage -1 / 3�������� perPage * 2 -> curPage -1
		sql  = " select uNo, uName, uMail, joinDate, uRetire ";	
		sql += " from member ";
		if( this.where.equals("") == false )
		{
			sql += " where " + this.where + " ";
		}
		// ����
		sql += search.getmOrder();
		// limit
		sql += " limit " + offset + ", " + perPage;
		if( this.isMonitoring ) { System.out.println("MemberDao :: getList() / \n"+sql); }
		
		this.OpenQuery(sql);
		while( this.GetNext() == true )
		{	// MemberVo vo�� �ְ�
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
	
	// ������ ����Ʈ�� ������ ��� �޼ҵ�
	public int getListSize()
	{
		if( this.list == null ) return 0;
		else return this.list.size();
	}
	
	// ����Ʈ���� �ε����� �Խñ� ������ ��� �޼ҵ�
	public MemberVo getItem( int index )
	{
		if( this.list == null ) return null;
		else return this.list.get(index);
	}
	
	// ������ ����Ʈ ��ü�� ��ȯ�ϴ� �޼ҵ�
	public ArrayList<MemberVo> getListArray()
	{
		return this.list;
	}
}
	

