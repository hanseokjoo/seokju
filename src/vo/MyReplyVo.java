package vo;

import java.util.ArrayList;

public class MyReplyVo {

	private String rNo;
	private String pNo;
	private String uNo;
	private String rContent;
	private String rDate;
	private String rDel;
	private String rDelDate;
	
	public String getrNo()			{ return rNo; }
	public String getpNo()			{ return pNo; }
	public String getuNo()			{ return uNo; }
	public String getrContent()		{ return rContent; }
	public String getrDate()		{ return rDate; }
	public String getrDel()			{ return rDel; }
	public String getrDelDate()		{ return rDelDate; }
	
	public void setrNo( String rNo ) 			{ this.rNo = rNo; }
	public void setpNo( String pNo ) 			{ this.pNo = pNo; }
	public void setuNo( String uNo ) 			{ this.uNo = uNo; }
	public void setrContent( String rContent ) 	{ this.rContent = rContent; }
	public void setrDate( String rDate ) 		{ this.rDate = rDate; }
	public void setrDel( String rDel ) 			{ this.rDel = rDel; }
	public void setrDelDate( String rDelDate ) 	{ this.rDelDate = rDelDate; }
	

	public void PrintMyReplyVo()
	{
		System.out.printf("MyReplyVo.java: PrintMyReplyVo(): (rNo, pNo, uNo, rDate, rDel, rDelDate) = (%s,%s,%s,%s,%s,%s)\r\n",
				rNo, pNo, uNo, rDate, rDel, rDelDate);
	}
	
	
}
