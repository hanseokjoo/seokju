<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/index.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%
//삭제 대상 게시글의 게시글 번호를 파라메타로 받는다
String pNo = request.getParameter("no");
String pType = request.getParameter("type");
String pageNo = request.getParameter("page");
//유효성 체크
if(pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("index.jsp");
	return;
}
//게시글 번호로 정보를 받아옴
BoardDao dao = new BoardDao();
BoardVo vo = dao.Read(pNo, false);
//로그인 정보를 확인하여 수정 권한이 있는지 체크
if(LoginVo == null || vo.getuNo().equals(LoginVo.getuNo()) == false)
{
	// -> 삭제 권한이 없습니다 -> index
	%>
	<script>
		alert("게시글에 대한 삭제 권한이 없습니다");
		document.location="index.jsp";

	</script>
	<%
	return;
}
dao.Delete(pNo);
%>
<section style="background-color:white; margin:auto; height:800px;">
	<div class="box" style="background-color:white; text-align: center;">
	    <br><br><br>
	          글 삭제가 완료되었습니다
		<br><br><br>
		<a href="index.jsp" class="btn btn-primary btn-sm">목록으로</a>
    </div>
</section>
<%@ include file="../include/footer.jsp"%>