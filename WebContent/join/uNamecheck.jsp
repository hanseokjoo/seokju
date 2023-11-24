<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%// 파라메타로 받은 아이디 유효성 체크
	String uName = request.getParameter("uName");
	
	if( uName == null || uName.equals("") )
	{
		out.println("ERROR"); return;
	}
	// 닉네임 중복 여부 검사
	MemberDao dao = new MemberDao(search);
	int code = dao.IsDuplicate1(uName);
	if( code == MemberDao.ERROR )
	{
		out.print("ERROR");
	}else if( code == MemberDao.NOT_DUPLICATE1 )
	{
		out.print("NOT_DUPLICATE1");
	}else if( code == MemberDao.DUPLICATE1 )
	{
		out.print("DUPLICATE1");
	}
%>