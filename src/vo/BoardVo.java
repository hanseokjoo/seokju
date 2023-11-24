package vo;

import java.util.ArrayList;

public class BoardVo
{
/*
	pNo int auto_increment primary key comment '�Խñ۹�ȣ',
	pType varchar(2) comment '�Խ�������',
	pTitle varchar(200) comment '����',
	pContent TEXT comment '����',
	pDate DATETIME default now() comment '�ۼ���',
	pCnt int default 0 comment '��ȸ��',
	uNo int comment '�Խñ��ۼ���',
*/
	private String pNo;		// �Խñ۹�ȣ
	private String pType;	// �Խ�������
	private String pTitle;	// ����
	private String pContent;// ����
	private String pDate;	// �ۼ���
	private String pCnt;	// ��ȸ��
	private String uNo;		// �Խñ��ۼ���
	private String uName;	// �Խñ��ۼ����̸�
	private String uLevel;	// �Խñ� �ۼ��� ����
	private String rCnt;	// ��� ����
	private String rc;		// ��õ ��
	private String rp;		// �Ű� ��
	private String pBlind;	// ����ε� ����
	
	// ÷������ ����Ʈ
	private ArrayList<AttachVo> aList;
	
	// ��� ����Ʈ
	private ArrayList<ReplyVo> rList;

	public String getpNo()		 { return pNo;		 }
	public String getpType()	 { return pType;	 }
	public String getpTitle()	 { return pTitle;	 }
	public String getpContent()  { return pContent;	 }
	public String getpDate()	 { return pDate;	 }
	public String getpCnt()		 { return pCnt;		 }
	public String getuNo()		 { return uNo;		 }
	public String getuName()	 { return uName;	 }
	public String getrCnt()		 { return rCnt;		 }
	public String getrc()		 { return rc;		 }
	public String getrp()		 { return rp;		 }
	public String getpBlind()	 { return pBlind;	 }
	public String getuLevel()	 { return uLevel;	 }
	public ArrayList<AttachVo> getaList() { return aList; }
	public ArrayList<ReplyVo>  getrList() { return rList; }
	public String getpTypeString()
	{
		String result = "";
		switch( pType )
		{
		case "IN": result = "���� �Խ���"; break;
		case "RE": result = "���� �Խ���"; break;
		case "HP": result = "���÷��̽�"; break;
		case "FR": result = "���� �Խ���";  break;
		case "NO": result = "��������";  break;
		case "QA": result = "QnA";  break;
		default :  result = "���� �Խ���"; break;
		}
		return result;
	}

	public void setpNo(		String pNo		) { this.pNo		= pNo;		}
	public void setpType(	String pType	) { this.pType		= pType;	}
	public void setpTitle(	String pTitle	) { this.pTitle		= pTitle;	}
	public void setpContent(String pContent	) { this.pContent	= pContent; }
	public void setpDate(	String pDate	) { this.pDate		= pDate;	}
	public void setpCnt(	String pCnt		) { this.pCnt		= pCnt;		}
	public void setuNo(		String uNo		) { this.uNo		= uNo;		}
	public void setuName(	String uName	) { this.uName		= uName;	}
	public void setrCnt(	String rCnt		) { this.rCnt		= rCnt;		}
	public void setrc(		String rc		) { this.rc			= rc;		}
	public void setrp(		String rp		) { this.rp			= rp;		}
	public void setpBlind (	String pBlind 	) { this.pBlind		= pBlind;	}
	public void setuLevel (	String uLevel 	) { this.uLevel		= uLevel;	}
	public void setaList(ArrayList<AttachVo> aList) { this.aList = aList;	}
	public void addAttach(	AttachVo file	)
	{
		if( this.aList == null )
		{ this.aList = new ArrayList<AttachVo>(); }
		this.aList.add(file);
	}
	public void setrList(ArrayList<ReplyVo>  rList) { this.rList = rList; }
	public void addReply(ReplyVo reply)
	{
		if( this.rList == null )
		{ this.rList = new ArrayList<ReplyVo>(); }
		this.rList.add(reply);
	}
	
	public void PrintInfo()
	{
		System.out.println("�Խñ۹�ȣ : "		+ this.getpNo()	  );
		System.out.println("�Խ��������ڵ� : "	+ this.getpType() );
		System.out.println("�Խ��������̸� : "	+ this.getpTypeString() );
		System.out.println("���� : "			+ this.getpTitle());
		System.out.println("���� : "			+ this.getpContent() );
		System.out.println("�ۼ��� : "			+ this.getpDate() );
		System.out.println("��ȸ�� : "			+ this.getpCnt()	  );
		System.out.println("��õ �� : "  		+ this.getrc()	  );
		System.out.println("�ۼ����̸� : "  	+ this.getuName()	  );
		System.out.println("�Խñ��ۼ��� : "	+ this.getuNo()	  );
		System.out.println("�ۼ����̸� : "  	+ this.getuName()	  );
		System.out.println("����ε� ���� : "  	+ this.getpBlind()	  );
		if( aList != null )
		{
			for( AttachVo item : aList )
			{ item.PrintInfo(); }
		}
		if( rList != null )
		{
			for( ReplyVo item : rList )
			{ item.PrintInfo(); }
		}
	}
}
