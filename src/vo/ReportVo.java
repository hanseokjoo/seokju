package vo;

public class ReportVo 
{
	private String rpNo;	// ��õ ������ȣ
	private String rpDate;	// �Ű� �ð�
	private String pNo;		// �Ű� ��� �Խù� ��ȣ
	private String rpType;	// �Ű� �з�
	private String rp;		// �Ű� Ƚ��	
	private String uNo;		// �Ű��� ȸ�� ��ȣ
	private String rpNote;	// �Ű� ����
	private String pTitle;	// �Ű� ����
	private String uName;	// �Ű� ����
	
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
