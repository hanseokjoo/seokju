package vo;

public class ReplyVo
{
/*
    rNo int auto_increment primary key comment '��۹�ȣ',
    rNote TEXT comment '��۳���',
    rDate DATETIME default now() comment '����ۼ���',
    pNo int comment '���Խñ۹�ȣ',
    uNo int comment '����ۼ���',
*/
	private String rNo;			// ��۹�ȣ
	private String rContent;	// ��۳���
	private String rDate;		// ����ۼ���
	private String pNo;			// ���Խñ۹�ȣ
	private String uNo;			// ����ۼ��ڹ�ȣ
	private String uName;		// ����ۼ����̸�
	private String uLevel;		// ����ۼ��ڷ���
	
	public String getrNo()	 	{ return rNo;		}
	public String getrContent() { return rContent;	}
	public String getrDate() 	{ return rDate;		}
	public String getpNo()		{ return pNo;		}
	public String getuNo()	 	{ return uNo;		}
	public String getuName() 	{ return uName;		}
	public String getuLevel()	{ return uLevel;	}
	
	public void setrNo(	  	String rNo	 	) { this.rNo	  = rNo;		 }
	public void setrContent(String rContent	) { this.rContent = rContent;	 }
	public void setrDate(	String rDate	) { this.rDate	  = rDate;	 	 }
	public void setpNo(		String pNo	 	) { this.pNo	  = pNo;		 }
	public void setuNo(		String uNo	 	) { this.uNo	  = uNo;		 }
	public void setuName(	String uName	) { this.uName	  = uName;	 	 }
	public void setuLevel(  String uLevel   ) { this.uLevel   = uLevel;		 }
	
	public void PrintInfo()
	{
		System.out.println("��۹�ȣ : "		+ this.getrNo()	  );
		System.out.println("��۳��� :"		+ this.getrContent() );
		System.out.println("����ۼ��� : "		+ this.getrDate() );
		System.out.println("���Խñ۹�ȣ : "	+ this.getpNo()	  );
		System.out.println("����ۼ��ڹ�ȣ : "	+ this.getuNo()	  );
		System.out.println("����ۼ����̸� : "	+ this.getuName() );
	}
}
