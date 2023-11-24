<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/header.jsp" %>
<%
//modifyReply에서 넘어온 파라메타를 받는다
String rContent = request.getParameter("rContent");

// 댓글 번호 확인용
String rNo = request.getParameter("rNo");
String pNo = request.getParameter("pNo");

// 권한 확인
String uNo = request.getParameter("uNo");

//유효성 체크
if(rNo == null || rNo.equals("") || pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("board.jsp");
	return;
}
ReplyDao rDao = new ReplyDao();
ReplyVo vo = new ReplyVo();
vo.setrContent(rContent);
vo.setrNo(rNo);
vo.setuNo(uNo);

// 댓글의 vo를 db에 업데이트 한다
rDao.Update(vo, uNo);
%>
<script>
	document.location = "view.jsp?no=<%= pNo%>&type=<%=type%>";
</script>