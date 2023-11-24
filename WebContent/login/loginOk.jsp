<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>]
<%@ page import = "util.CookieManager" %>
<%@ include file="../config/config.jsp" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>

<%	   
String uMail	= request.getParameter("userMail");
String uPW		= request.getParameter("userPW");
String saveCheck = request.getParameter("saveCheck");

if( uMail == null || uMail.equals("") || uPW == null || uPW.equals("") ) { 
	%>
	<script> alert("아이디 또는 비번이 입력되지 않았거나 일치하지 않습니다.");
	document.location="login.jsp"; </script> 
	<% 
	return; 
}

// 파라메타로 아이디 비번이 넘어옴
MemberDao dao = new MemberDao(search);
MemberVo vo   = dao.Login(uMail, uPW);

if( vo != null ) {	
	//=====> 로그인 성공
	System.out.printf("loginOk.jsp: 로그인 성공");
	session.setAttribute("Login", vo);
	if(saveCheck != null && saveCheck.equals("Y"))
	{
		CookieManager.makeCookie(response, "loginEmail", uMail, 86400);
	}else{
		CookieManager.deleteCookie(response, "loginEmail");
	}
	%>
	<script>
		alert("환영합니다.");
		document.location = "../board/index.jsp";
	</script>
	<%
	return;
}else {	
	System.out.printf("loginOk.jsp: 로그인 실패"); 
	%> 
	<script> 
		alert("로그인이 실패했습니다."); 
		document.location = "login.jsp";
	</script> 
	<%
	return;
}
%>