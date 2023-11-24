package util;

import javax.servlet.http.*;

public class CookieManager 
{	// ����� �̸� , ��, ���� �Ⱓ �������� ���ο� ��Ű ����
	public static void makeCookie(HttpServletResponse response, String cName,
			String cValue, int cTime) {
		Cookie cookie = new Cookie(cName, cValue);	// ��Ű ����
		cookie.setPath("/");						// ��� ����					
		cookie.setMaxAge(cTime);					// �����Ⱓ ����
		response.addCookie(cookie);					// ���� ��ü�� �߰�
	}
	
	public static String readCookie(HttpServletRequest request, String cName)
	{	// ����� �̸��� ��Ű�� ã�� �� ���� ��ȯ
		String cookieValue ="";	// ��ȯ��
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null)
		{
			for (Cookie c : cookies)
			{
				String cookieName = c.getName();
				if(cookieName.equals(cName))
				{
					cookieValue = c.getValue();	// ��ȯ�� ����
				}
			}
		}
		return cookieValue;
	}
	
	public static void deleteCookie(HttpServletResponse response, String cName)
	{	// ����� �̸��� ��Ű�� ����
		makeCookie(response, cName, "", 0);
	}
}
