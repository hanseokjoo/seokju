package vo;

public class AttachVo
{
/*
	aNo int primary key auto_increment comment '���Ϲ�ȣ',
	pNo int comment '�Խñ۹�ȣ',
	fName varchar(255) comment '�����ϸ�',
	uqName varchar(255) comment '�������ϸ�',
*/
	private String aNo;			// ���Ϲ�ȣ
	private String pNo;			// �Խñ۹�ȣ
	private String fName;		// �����ϸ�
	private String uqName;		// �������ϸ�
	
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
		System.out.println("���Ϲ�ȣ : "   + this.getaNo()		 );
		System.out.println("�Խñ۹�ȣ : " + this.getpNo()		 );
		System.out.println("�����ϸ� : " + this.getfName()	 );
		System.out.println("�������ϸ� : " + this.getuqName()	 );
	}

}
