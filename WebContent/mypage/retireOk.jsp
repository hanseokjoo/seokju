<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
String uMail	= request.getParameter("useremail");
String uPW		= request.getParameter("userPW");
System.out.printf("retireOk.jsp: [%s] [%s]\r\n", uMail, uPW);

MemberDao dao = new MemberDao(search);
MemberVo vo = dao.Login(uMail, uPW);
// 로그인 성공
if( vo != null ) 
{	
	//탈퇴처리한다.
	dao.RetireMember(uMail, uPW);
	
	// 세션에 기록된 정보를 삭제
	session.removeAttribute("Login");
	
	// 세션에 기록된 정보를 null로 치환
	session.setAttribute("Login", null);
	
	out.println("OK:회원탈퇴가 완료되었습니다.");
} else 
{	
	out.println("ERROR:비밀번호가 일치하지 않아 탈퇴가 취소되었습니다.");
}
%>

