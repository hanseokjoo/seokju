<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//System.out.printf("logout.jsp: reached\r\n");

	// 세션에 기록된 정보를 삭제
	session.removeAttribute("Login");
	
	// 세션에 기록된 정보를 null로 치환
	session.setAttribute("Login", null);
	
	// 메인 페이지로 이동
	response.sendRedirect("../board/index.jsp");
%>