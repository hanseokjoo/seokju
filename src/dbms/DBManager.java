package dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBManager
{
	// DB ���ӿ� ������
	private String host;	// ���� �ּ�/��Ʈ/DB�̸�/���õ�
	private String uMail;	// DB ����
	private String uPW;		// DB ���� ���
	
	// DB ���� ��ü��
	private Connection	conn	= null;		// DB ���� ��ü
	private Statement	stmt	= null;		// SQL ���� ��ü
	private ResultSet	result	= null;		// SQL ��� ��ü
	
	protected boolean isMonitoring = false;
	// ������
	public DBManager()
	{
		this.host = "jdbc:mysql://192.168.0.62:3306/twk";
//		this.host = "jdbc:mysql://127.0.0.1:3306/twk";
		host += "?useUnicode=true";
		host += "&characterEncoding=utf-8";
		host += "&serverTimezone=UTC";
//		host += "&useSSL=false";	// SSL �ɼ� ����
		
		this.uMail = "Bteam";
		this.uPW = "1234";
//		this.uMail = "root";
//		this.uPW = "ezen";
	}
	
	// ID, PW ���� / host ���� ���� ���͸� Ȱ���Ͽ� ������ �� ����
	public void setUMail(String userMail) { this.uMail = userMail; }
	public void setUPW(String userPW) { this.uPW = userPW; }
	public Connection getConn() { return conn; }
	public void setMonitoring(boolean value) { isMonitoring = value; }
	// DB ���� �޼ҵ�
	public boolean DBOpen()
	{
		try
		{	// JDBC ����̹��� �ε��Ѵ�
			Class.forName("com.mysql.cj.jdbc.Driver");
			// �����ͺ��̽��� �����Ѵ�
			this.conn = DriverManager.getConnection(this.host, this.uMail, this.uPW);
			return true;
		}catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// DB ���� �޼ҵ�
	public boolean DBClose()
	{
		try
		{	// DB ���� ���� �õ�
			this.stmt.close();
			this.conn.close();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// SQL ���� �޼ҵ� / Insert Update Delete SQl ���� ó��
	public boolean RunSQL( String SQL )
	{
		try
		{	// ���� ��ü�� �Ҵ����
			this.stmt = this.conn.createStatement();
			// ���ڷ� ���� SQL ������ �����Ѵ�
			int result = this.stmt.executeUpdate(SQL);
			if( result == 0 )
			{
				System.out.println("����� ����� 0�Դϴ�");
			}
			// ���� ��ü�� ����
			this.stmt.close();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// ���� ���� �޼ҵ� - result / stmt -> open
	public boolean OpenQuery( String SQL )
	{
		try
		{	// ���� ��ü �Ҵ�
			this.stmt = this.conn.createStatement();
			// SQL ������ �����Ͽ� ����� �޴´�
			this.result = stmt.executeQuery(SQL);
			return true;
		}catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// ���� ���� �޼ҵ�	- result / stmt	-> close
	public boolean CloseQuery()
	{
		try
		{
			this.result.close();
			this.stmt.close();
			return true;
		}catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// getNext
	public boolean GetNext()
	{
		try
		{
			return this.result.next();
		}catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// getValue
	public String GetValue( String colName )
	{
		try
		{	// �÷� �̸����� �÷��� ���� ������
			return this.result.getString(colName);
		}catch( Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}
	
	// getValue
	public int GetInt( String colName )
	{
		try
		{	// �÷� �̸����� �÷��� ���� ������
			return this.result.getInt(colName);
		}catch( Exception e )
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	// ��������ǥ ó��	�ڹٿ��� ���ڿ��� ""�� ���ΰ� ���ڿ� ���ο� ���� SQL ��������
	// ���ڿ��� ''�� ���ΰ� �Ǵµ�, �� ������(���ڿ�)�� '�� ���Ե� ��쿡�� '�� ó������� ��
	// SQL ������ �� �ش��ϴ� ������ �߰� �� �� ����
	public String _R( String value )
	{
		return value.replace("'", "''");
	}
}
