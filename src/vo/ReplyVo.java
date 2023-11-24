package vo;

public class ReplyVo
{
/*
    rNo int auto_increment primary key comment '댓글번호',
    rNote TEXT comment '댓글내용',
    rDate DATETIME default now() comment '댓글작성일',
    pNo int comment '대상게시글번호',
    uNo int comment '댓글작성자',
*/
	private String rNo;			// 댓글번호
	private String rContent;	// 댓글내용
	private String rDate;		// 댓글작성일
	private String pNo;			// 대상게시글번호
	private String uNo;			// 댓글작성자번호
	private String uName;		// 댓글작성자이름
	private String uLevel;		// 댓글작성자레벨
	
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
		System.out.println("댓글번호 : "		+ this.getrNo()	  );
		System.out.println("댓글내용 :"		+ this.getrContent() );
		System.out.println("댓글작성일 : "		+ this.getrDate() );
		System.out.println("대상게시글번호 : "	+ this.getpNo()	  );
		System.out.println("댓글작성자번호 : "	+ this.getuNo()	  );
		System.out.println("댓글작성자이름 : "	+ this.getuName() );
	}
}
