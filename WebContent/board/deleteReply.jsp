<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/header.jsp" %>
<%
// 로그인 정보가 없으면(권한이 없음) index.jsp로 보냄
if(LoginVo == null)
{
	response.sendRedirect("index.jsp");
	return;
}

ReplyDao dao = new ReplyDao();

// 삭제 대상 게시글의 게시글 번호를 파라메타로 받는다
String rNo = request.getParameter("rNo");
String pNo = request.getParameter("pNo");

// 유효성 체크
if(rNo == null || rNo.equals("") || pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("index.jsp");
	return;
}

if(LoginVo.getuLevel().equals("A"))
{
	dao.DeleteAllByPNo(pNo, rNo);
}else{
	dao.Delete(rNo, LoginVo.getuNo());
}
%>
<script>
	document.location = "view.jsp?no=<%= pNo %>&type=<%= type %>";
</script>