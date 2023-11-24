<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/index.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
String uNo		= request.getParameter("uNo");
String curPW	= request.getParameter("curPW");
String newPW	= request.getParameter("newPW");

MemberDao dao = new MemberDao(search);
MemberVo vo = dao.pRead(uNo, curPW);
if( uNo == null || uNo.equals("") || curPW == null || curPW.equals("") ) 
{ 
	System.out.println("메일값" + uNo);
	System.out.println("현재 비밀번호" + curPW);
	%>
	<script> alert("비밀번호가 일치하지 않습니다.");
	document.location ="pwModify.jsp?uNo=" + <%= uNo %>; </script> 
	<% 
	return; 
}else
{
	// 비밀번호를 수정한다
	dao.UpdateUPW(newPW, uNo);
	%>
	<script>
		alert("비밀번호가 변경되었습니다.");
		document.location = "mypage.jsp?uNo=" + <%= uNo %> + "&type=MI";
	</script>
	<%
	return;
}
%>