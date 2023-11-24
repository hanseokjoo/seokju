<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//파라메타 인코딩 설정
request.setCharacterEncoding("UTF-8");
%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../config/config.jsp" %>
<%
//request로 넘어온 파라메타를 받는다
String pNo   = request.getParameter("no");
String pType = request.getParameter("type");
String rpType = request.getParameter("rpType");
String rpNote = request.getParameter("rpNote");

//유효성 검사
if( LoginVo == null || LoginVo.getuNo().equals("") || pNo == null || pNo.equals("") )
{	// 메인 페이지로 보냄
	response.sendRedirect("view.jsp?no=" + pNo); return;
}

// 중복검사
BoardDao bDao = new BoardDao();
if(bDao.RpDuplicate(LoginVo.getuNo(), pNo) == bDao.DUPLICATE)
{
	%>
	<script>
		alert("이미 신고한 게시글입니다.")
		document.location = "view.jsp?no=<%= pNo %>&type=<%= pType %>";
	</script>
	<%
	return;
}

// 파라메타로 받은 정보들로 vo에 신고 정보를 채워넣음
ReportVo vo = new ReportVo();

vo.setuNo(LoginVo.getuNo());	// 신고자 회원번호
vo.setpNo(pNo);					// 신고 게시물 번호
vo.setrpType(rpType);
vo.setrpNote(rpNote);

// 신고 정보 vo를 DB에 업데이트한다
bDao.AddReport(vo);
%>

<script>
	alert("신고가 완료되었습니다.");
	document.location = "view.jsp?no=<%= pNo %>";
</script>