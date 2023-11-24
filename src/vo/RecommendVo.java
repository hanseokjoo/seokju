package vo;

public class RecommendVo 
{
	private String rcNo;	// 추천 관리번호
	private String pNo;		// 추천한 게시물
	private String uNo;		// 추천한 회원번호
	private String rc;		// 추천수
	private String rcDate;	// 추천일자
	
	public String getRcNo() {return rcNo;}
	public String getpNo() {return pNo;}
	public String getuNo() {return uNo;}
	public String getRc() {return rc;}
	public String getRcDate() {return rcDate;}
	
	public void setRcNo(String rcNo) {this.rcNo = rcNo;}
	public void setpNo(String pNo) {this.pNo = pNo;}
	public void setuNo(String uNo) {this.uNo = uNo;}
	public void setRc(String rc) {this.rc = rc;}
	public void setRcDate(String rcDate) {this.rcDate = rcDate;}
		
}
