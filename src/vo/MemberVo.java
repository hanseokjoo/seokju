package vo;

import java.util.ArrayList;

public class MemberVo
{
/*
create table account
(
    uNo int primary key auto_increment comment '회원번호',
    uMail varchar(50) NOT NULL comment '아이디',
    uPW varchar(100) NOT NULL comment '비밀번호',
    uName varchar(100) NOT NULL comment '이름',
    uGender varchar(2) comment '성별',
    uInter varchar(2) comment '취미',
    joinDate DATETIME NOT NULL default now() comment '가입일자',
    uRetire varchar(2) NOT NULL default 'U' comment '탈퇴여부',
    uLevel varchar(2) NOT NULL default 'U' comment '회원레벨'
) comment '계정목록' ;
*/
	private String uNo;			// 회원번호
	private String uMail;		// 이메일
	private String uPW;			// 비밀번호
	private String uName;		// 이름
	private String uInter;		// 관심사
	private String joinDate;	// 가입일자
	private String uRetire;		// 탈퇴여부
	private String uLevel;		// 회원레벨
	

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
		case "IN": result = "숙소 게시판"; break;
		case "RE": result = "맛집 게시판"; break;
		case "HP": result = "핫플레이스"; break;
		case "NO": result = "자유 게시판";  break;
		default :  result = "숙소 게시판"; break;
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
		System.out.println("회원번호 : " + uNo);
		System.out.println("아이디 : " + uMail);
		System.out.println("성명 : " + uName);
		System.out.println("가입일자 : " + joinDate);
		System.out.println("탈퇴여부 : " + uRetire);
		System.out.println("회원레벨 : " + uLevel);
	}
}