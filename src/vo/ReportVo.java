package vo;

public class ReportVo 
{
	private String rpNo;	// 추천 관리번호
	private String rpDate;	// 신고 시각
	private String pNo;		// 신고 대상 게시물 번호
	private String rpType;	// 신고 분류
	private String rp;		// 신고 횟수	
	private String uNo;		// 신고한 회원 번호
	private String rpNote;	// 신고 내용
	private String pTitle;	// 신고 내용
	private String uName;	// 신고 내용
	
	public String getrpNo() 	{return rpNo;}
	public String getrpDate() 	{return rpDate;}
	public String getpNo() 		{return pNo;}
	public String getrpType() 	{return rpType;}
	public String getrp() 		{return rp;}
	public String getuNo()	 	{return uNo;}
	public String getrpNote() 	{return rpNote;}
	public String getpTitle() 	{return pTitle;}
	public String getuName() 	{return uName;}
	
	public void setrpNo(String rpNo) 	 {this.rpNo = rpNo;}
	public void setrpDate(String rpDate) {this.rpDate = rpDate;}
	public void setpNo(String pNo) 		 {this.pNo = pNo;}
	public void setrpType(String rpType) {this.rpType = rpType;}
	public void setrp(String rp) {this.rp = rp;}
	public void setuNo(String uNo) 		 {this.uNo = uNo;}
	public void setrpNote(String rpNote) {this.rpNote = rpNote;}
	public void setpTitle(String pTitle) {this.pTitle = pTitle;}
	public void setuName(String uName) {this.uName = uName;}
		
}
