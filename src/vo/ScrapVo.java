package vo;

public class ScrapVo 
{	// ��ũ�� ���
	private String sNo;		// ��ũ�� ��ȣ
	private String uNo;		// ��ũ�� �� ȸ��
	private String pNo;		// ��ũ�� �� �Խñ�
	private String pTitle;	// ��ũ�� �� �Խñ� ����
	private String uName;	// ��ũ�� �� �Խñ� �ۼ��� �̸�
	private String rp;		// �Ű� ��
	
	
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
		System.out.println("��ũ�� ��ȣ : " + sNo);
		System.out.println("ȸ����ȣ : "   + uNo);
		System.out.println("�Խñ� ��ȣ : " + pNo);
		System.out.println("�Խñ� ��ȣ : " + pTitle);
		System.out.println("�Խñ� ��ȣ : " + uName);
		System.out.println("�Ű�� : " + rp);		
		System.out.println("------------------- ");
	}
}
