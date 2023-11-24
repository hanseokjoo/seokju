package util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender
{
	private String MailServer;
	private int    MailPort;

	public MailSender()
	{	// naver
		this.MailServer = "smtp.naver.com";
		this.MailPort   = 465;
		
	}
	
	public MailSender(String server)
	{
		// ���� �� ����
		if( server.equals("google"))
		{
			this.MailServer = "smtp.gmail.com";
			this.MailPort = 465;
		}
	}
	
	public void setMailServer( String mailserver )
	{
		this.MailServer = mailserver;
	}
	
	public void setMailPort( int mailPort )
	{
		this.MailPort = mailPort;
	}
	
	public boolean MailSend(String from,String to,String id,String pw,String title,String body)
	{
		
		try
		{  
			Properties clsProp = System.getProperties();
			
			// ���� ���� �ּ�
			clsProp.put( "mail.smtp.host", MailServer );
			
			// ���� ���� ��Ʈ ��ȣ
			clsProp.put( "mail.smtp.port", MailPort );			
			
			// ������ �ʿ��ϸ� �Ʒ��� ���� �����Ѵ�.
			clsProp.put("mail.smtp.auth", "true"); 
			clsProp.put("mail.smtp.ssl.enable", "true"); 
			clsProp.put("mail.smtp.ssl.trust", MailServer);			
			
			Session clsSession = Session.getInstance( clsProp, new Authenticator(){
					public PasswordAuthentication getPasswordAuthentication()
					{
						// ���� ���̵�/��й�ȣ�� �����Ѵ�.
						return new PasswordAuthentication( id, pw );
					}
				} );
			
			Message clsMessage = new MimeMessage( clsSession );
			
			// �߽��� �̸��� �ּҸ� �����Ѵ�.
			clsMessage.setFrom( new InternetAddress( from ) );
			
			// ������ �̸��� �ּҸ� �����Ѵ�.
			clsMessage.addRecipient( Message.RecipientType.TO, new InternetAddress( to ) );
						
			// ������ �����Ѵ�.
			clsMessage.setSubject( title );
			
			// ���� ������ �����Ѵ�. �ҽ� �ڵ带 euc-kr �� �ۼ��Ͽ��� charset �� euckr �� ������.
			clsMessage.setContent( body , "text/plain;charset=euckr" );
						
			// ���� ����
			Transport.send( clsMessage );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}		
		return true;
	}

}
