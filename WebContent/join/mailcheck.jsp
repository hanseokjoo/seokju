<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%// 파라메타로 받은 아이디 유효성 체크
	String uMail = request.getParameter("uMail");
	
	if( uMail == null || uMail.equals("") )
	{
		out.println("ERROR"); return;
	}
	// 이메일 중복 여부 검사
	MemberDao dao = new MemberDao(search);
	int code = dao.IsDuplicate(uMail);
	if( code == MemberDao.MAIL_ERROR )
	{
		out.print("ERROR");
	}else if( code == MemberDao.NOT_DUPLICATE )
	{
		out.print("NOT_DUPLICATE");
	}else if( code == MemberDao.DUPLICATE )
	{
		out.print("DUPLICATE");
	}
%>