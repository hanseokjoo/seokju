package util;

import java.util.Random;

public class MailAuth
{
	// ������ ���ڸ� ��´�.
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
	
	// ���� �̸����� �߼��Ѵ�
	public String sendAuthMail( String to )
	{
		String code	= MailAuth.GetRandom(5);
		
		MailSender server = new MailSender("google");
		
		String from = "ezen@ezen.com";						// ������ �ּ�
		String ID   = "nalahan561@gmail.com";				// ����
		String PW   = "lodlsvnruhweongs";					// ��������
		
		// ���� ����
		String title = "[ȸ������]������ȣ �߼۸��� �Դϴ�";
		
		// ���� ����
		String message = "";
	    message = "[ȸ������]������ȣ�� [ "+ code +" ] �Դϴ�.<br>";
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
			System.out.println("���������� �̸����� �߼��Ͽ����ϴ�.");
			return code;
		}else
		{
			System.out.println("���� �߼� �����Դϴ�.");
			return "ERROR";
		}
	}
	
	public static void main(String[] args)
	{
		// MailAuth ma = new MailAuth();
		// ma.sendAuthMail("");	// �޴� �̸����ּҸ� �Է��ϼ���
	}
	
}
