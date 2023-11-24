package vo;

import java.util.ArrayList;

public class BoardVo
{
/*
	pNo int auto_increment primary key comment '게시글번호',
	pType varchar(2) comment '게시판종류',
	pTitle varchar(200) comment '제목',
	pContent TEXT comment '내용',
	pDate DATETIME default now() comment '작성일',
	pCnt int default 0 comment '조회수',
	uNo int comment '게시글작성자',
*/
	private String pNo;		// 게시글번호
	private String pType;	// 게시판종류
	private String pTitle;	// 제목
	private String pContent;// 내용
	private String pDate;	// 작성일
	private String pCnt;	// 조회수
	private String uNo;		// 게시글작성자
	private String uName;	// 게시글작성자이름
	private String uLevel;	// 게시글 작성자 레벨
	private String rCnt;	// 댓글 개수
	private String rc;		// 추천 수
	private String rp;		// 신고 수
	private String pBlind;	// 블라인드 여부
	
	// 첨부파일 리스트
	private ArrayList<AttachVo> aList;
	
	// 댓글 리스트
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
		case "IN": result = "숙소 게시판"; break;
		case "RE": result = "맛집 게시판"; break;
		case "HP": result = "핫플레이스"; break;
		case "FR": result = "자유 게시판";  break;
		case "NO": result = "공지사항";  break;
		case "QA": result = "QnA";  break;
		default :  result = "숙소 게시판"; break;
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
		System.out.println("게시글번호 : "		+ this.getpNo()	  );
		System.out.println("게시판종류코드 : "	+ this.getpType() );
		System.out.println("게시판종류이름 : "	+ this.getpTypeString() );
		System.out.println("제목 : "			+ this.getpTitle());
		System.out.println("내용 : "			+ this.getpContent() );
		System.out.println("작성일 : "			+ this.getpDate() );
		System.out.println("조회수 : "			+ this.getpCnt()	  );
		System.out.println("추천 수 : "  		+ this.getrc()	  );
		System.out.println("작성자이름 : "  	+ this.getuName()	  );
		System.out.println("게시글작성자 : "	+ this.getuNo()	  );
		System.out.println("작성자이름 : "  	+ this.getuName()	  );
		System.out.println("블라인드 여부 : "  	+ this.getpBlind()	  );
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
