package vo;

import java.util.ArrayList;

public class MemberVo
{
/*
create table account
(
    uNo int primary key auto_increment comment 'ȸ����ȣ',
    uMail varchar(50) NOT NULL comment '���̵�',
    uPW varchar(100) NOT NULL comment '��й�ȣ',
    uName varchar(100) NOT NULL comment '�̸�',
    uGender varchar(2) comment '����',
    uInter varchar(2) comment '���',
    joinDate DATETIME NOT NULL default now() comment '��������',
    uRetire varchar(2) NOT NULL default 'U' comment 'Ż�𿩺�',
    uLevel varchar(2) NOT NULL default 'U' comment 'ȸ������'
) comment '�������' ;
*/
	private String uNo;			// ȸ����ȣ
	private String uMail;		// �̸���
	private String uPW;			// ��й�ȣ
	private String uName;		// �̸�
	private String uInter;		// ���ɻ�
	private String joinDate;	// ��������
	private String uRetire;		// Ż�𿩺�
	private String uLevel;		// ȸ������
	

	// getters & setters

	public String getuNo()		{ return uNo;		}
	public String getuMail()	{ return uMail;		}
	public String getuPW()		{ return uPW;		}
	public String getuName()	{ return uName;		}
	public String getuInter()	{ return uInter;	}
	public String getJoinDate()	{ return joinDate;	}
	public String getuRetire()	{ return uRetire;	}
	public String getuLevel()	{ return uLevel;	}
	public String getuInterString()
	{
		String result = "";
		switch( uInter )
		{
		case "IN": result = "���� �Խ���"; break;
		case "RE": result = "���� �Խ���"; break;
		case "HP": result = "���÷��̽�"; break;
		case "NO": result = "���� �Խ���";  break;
		default :  result = "���� �Խ���"; break;
		}
		return result;
	}
	
	public void setuNo(String uNo) { this.uNo = uNo; }
	public void setuMail(String uMail) { this.uMail = uMail; }
	public void setuPW(String uPW) { this.uPW = uPW; }
	public void setuName(String uName) { this.uName = uName; }
	public void setuInter(String uInter) { this.uInter = uInter; }
	public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
	public void setuRetire(String uRetire) { this.uRetire = uRetire; }
	public void setuLevel(String uLevel) { this.uLevel = uLevel; }
	
	public void PrintInfo()
	{
		System.out.println("ȸ����ȣ : " + uNo);
		System.out.println("���̵� : " + uMail);
		System.out.println("���� : " + uName);
		System.out.println("�������� : " + joinDate);
		System.out.println("Ż�𿩺� : " + uRetire);
		System.out.println("ȸ������ : " + uLevel);
	}
}