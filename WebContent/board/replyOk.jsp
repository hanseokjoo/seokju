<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/header.jsp" %>
<%
request.setCharacterEncoding("UTF-8");

String pNo = request.getParameter("pNo"); // 댓글을 달을 게시글 번호
// String uID = request.getParameter("LoginID"); // 댓글을 작성한 작성자 아이디
String rContent = request.getParameter("rContent");	// 댓글 내용
String pType = request.getParameter("type");
// 유효성 검사
if(pNo == null || pNo.equals("") || LoginVo == null || LoginVo.getuNo().equals("") || rContent == null || rContent.equals(""))
{	// 메인페이지로 보냄
	response.sendRedirect("index.jsp");
	return;
}

// ReplyVo를 생성해서 댓글 객체를 셋팅
ReplyVo vo = new ReplyVo();
vo.setpNo(pNo);
vo.setrContent(rContent);
vo.setuNo(LoginVo.getuNo());

// ReplyDao를 생성해서 댓글 등록
ReplyDao dao = new ReplyDao();
dao.Insert(vo);

%>

<script>
	alert("댓글이 작성되었습니다");
	document.location = "view.jsp?type=<%= pType %>&no=<%= pNo%>";
</script>