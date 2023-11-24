package vo;

public class ScrapVo 
{	// 스크랩 목록
	private String sNo;		// 스크랩 번호
	private String uNo;		// 스크랩 한 회원
	private String pNo;		// 스크랩 한 게시글
	private String pTitle;	// 스크랩 한 게시글 제목
	private String uName;	// 스크랩 한 게시글 작성자 이름
	private String rp;		// 신고 수
	
	
	public String getsNo() {return sNo;}
	public String getuNo() {return uNo;}
	public String getpNo() {return pNo;}
	public String getrp()  {return rp;}
	
	public void setsNo(String sNo) {this.sNo = sNo;}
	public void setuNo(String uNo) {this.uNo = uNo;}
	public void setpNo(String pNo) {this.pNo = pNo;}
	public void setrp(String rp)   {this.rp  = rp;}
	
	
	
	public String getpTitle() {
		return pTitle;
	}
	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public void PrintInfo()
	{
		System.out.println("스크랩 번호 : " + sNo);
		System.out.println("회원번호 : "   + uNo);
		System.out.println("게시글 번호 : " + pNo);
		System.out.println("게시글 번호 : " + pTitle);
		System.out.println("게시글 번호 : " + uName);
		System.out.println("신고수 : " + rp);		
		System.out.println("------------------- ");
	}
}
