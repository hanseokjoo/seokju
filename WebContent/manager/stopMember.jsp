<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
	String uNo	= request.getParameter("uNO");
	System.out.printf(" *************************** stopMember.jsp: begin uNo = %s\r\n", uNo);
	ManagerDao dao = new ManagerDao();
	dao.StopMember(uNo);
	out.println("OK:자격정지가 완료되었습니다.");
%>