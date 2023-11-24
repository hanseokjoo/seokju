package vo;

public class AttachVo
{
/*
	aNo int primary key auto_increment comment '파일번호',
	pNo int comment '게시글번호',
	fName varchar(255) comment '논리파일명',
	uqName varchar(255) comment '물리파일명',
*/
	private String aNo;			// 파일번호
	private String pNo;			// 게시글번호
	private String fName;		// 논리파일명
	private String uqName;		// 물리파일명
	
	public String getaNo()		 { return aNo;		 }
	public String getpNo()		 { return pNo;		 }
	public String getfName()	 { return fName;	 }
	public String getuqName()	 { return uqName;	 }
	
	public void setaNo(		 String aNo		 ) { this.aNo		 = aNo;		 }
	public void setpNo(		 String pNo		 ) { this.pNo		 = pNo;		 }
	public void setfName( 	 String fName 	 ) { this.fName	 	 = fName;    }
	public void setuqName(	 String uqName	 ) { this.uqName	 = uqName;	 }
	
	public void PrintInfo()
	{
		System.out.println("파일번호 : "   + this.getaNo()		 );
		System.out.println("게시글번호 : " + this.getpNo()		 );
		System.out.println("논리파일명 : " + this.getfName()	 );
		System.out.println("물리파일명 : " + this.getuqName()	 );
	}

}
