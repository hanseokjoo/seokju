<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/header.jsp" %>
<%
// 로그인 정보가 없으면(권한이 없음) index.jsp로 보냄
if(LoginVo == null)
{
	response.sendRedirect("../board/index.jsp");
	return;
}

//삭제 대상 게시글의 게시글 번호를 파라메타로 받는다
String pNo = request.getParameter("no");

//유효성 체크
if(LoginVo.getuNo() == null || LoginVo.getuNo().equals("") || pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("../board/index.jsp");
	return;
}

String [] NoList = pNo.split(",");
for(int i = 0 ; i < NoList.length; i++)
{
	//ScrapDao를 생성해서 스크랩을 삭제하는 메소드 호출
	ScrapDao dao = new ScrapDao(search);
	dao.deleteScrap(LoginVo.getuNo(), NoList[i]);	
}
%>
<script>
	document.location = "scrapViewer.jsp?uNo=" + <%= LoginVo.getuNo() %>;
</script>