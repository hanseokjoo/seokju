<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/header.jsp" %>
<link href="../css/write.css" type="text/css" rel="stylesheet">
<%
// 로그인 정보가 없으면(권한이 없음) index.jsp로 보냄
if(LoginVo == null )
{
	response.sendRedirect("index.jsp");
	return;
}

// 수정 대상 댓글의 게시글 번호를 파라메타로 받는다
String rNo = request.getParameter("rNo");
String pNo = request.getParameter("pNo");

// 유효성 체크
if(rNo == null || rNo.equals("") || pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("index.jsp");
	return;
}
//댓글을 조회한다
ReplyDao dao = new ReplyDao();
ReplyVo vo = dao.getReply(rNo);
if(vo == null)
{	// 댓글 번호로 게시글을 조회하지 못함
	%>
	<script>
	alert("게시글 정보가 올바르지 않습니다");
	document.location = "board.jsp";
	</script>
	<%
	return;
}
if(!LoginVo.getuLevel().equals("A") && !LoginVo.getuNo().equals(vo.getuNo()))
{
	// -> 수정 권한이 없습니다 -> index
	%>
	<script>
		alert("댓글에 대한 수정 권한이 없습니다");
		document.location="index.jsp";
	</script>
	<%
}
%>
<section height="700px">
<form action="modifyReplyOk.jsp?pNo=<%= pNo%>&rNo=<%= rNo%>" method="post" id="modifyReply">
	<div style="font-size:1.2rem; font-weight:bold; text-align:center; margin-top:40px;">댓글 수정</div>
	<input type="hidden" id="uNo" name="uNo" value="<%= vo.getuNo() %>">
	<input type=text name="rContent" value="<%= vo.getrContent()%>" style="width:600px;  margin-top:30px; margin-left:350px;">
	<input type="submit" value="댓글 수정 완료" class="btn btn-primary btn-sm">
</form>
</section>
<%@ include file="../include/footer.jsp" %>