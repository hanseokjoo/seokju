package util;

import java.util.Random;

public class MailAuth
{
	// 랜덤한 숫자를 얻는다.
	public static String GetRandom( int n )
	{
		String	key		= "0123456789abcdefghijklmnopqrstuvwxyz";
		int		len		= key.length();
		String	code	= "";
		
		for( int i = 0; i < n ; i++ )
		{
			int r	= (int)( Math.random() * len );
			code   += key.substring( r, r+1 );
		}
		return code;
		
	}
	
	// 인증 이메일을 발송한다
	public String sendAuthMail( String to )
	{
		String code	= MailAuth.GetRandom(5);
		
		MailSender server = new MailSender("google");
		
		String from = "ezen@ezen.com";						// 보내는 주소
		String ID   = "nalahan561@gmail.com";				// 계정
		String PW   = "lodlsvnruhweongs";					// 계정인증
		
		// 제목 생성
		String title = "[회원가입]인증번호 발송메일 입니다";
		
		// 본문 생성
		String message = "";
	    message = "[회원가입]인증번호는 [ "+ code +" ] 입니다.<br>";
	    //contents
	    String html_contents = "";
	    html_contents += "<html>";
	    html_contents += "<head>";
	    html_contents += "<meta charset='utf-8'>";
	    html_contents += "</head>";
	    html_contents += "<body>";
	    html_contents += "<font color='red'>"+message+"</font>";
	    html_contents += "</body>";
	    html_contents += "</html>";
		
		if( server.MailSend(from, to, ID, PW, title, html_contents) == true )
		{
			System.out.println("성공적으로 이메일을 발송하였습니다.");
			return code;
		}else
		{
			System.out.println("메일 발송 오류입니다.");
			return "ERROR";
		}
	}
	
	public static void main(String[] args)
	{
		// MailAuth ma = new MailAuth();
		// ma.sendAuthMail("");	// 받는 이메일주소를 입력하세요
	}
	
}
