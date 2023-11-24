<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
	String uNo	= request.getParameter("uNO");
	System.out.printf(" *************************** deleteMember.jsp: begin uNo = %s\r\n", uNo);
	ManagerDao dao = new ManagerDao();
	dao.RetireMember(uNo);
	out.println("OK:회원탈퇴가 완료되었습니다.");
%>