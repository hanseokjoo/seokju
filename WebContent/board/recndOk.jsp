<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp" %>
<link href="css/write.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%@ include file="../config/config.jsp" %>
<section style="background-color:white; height:700px;">
		<div class="container-sm" style="background-color:white; text-align: center;">
		<br><br><br>
<%
//request로 넘어온 파라메타를 받는다
String pNo = request.getParameter("no");
String pType = request.getParameter("type");

//유효성 검사
if( LoginVo == null || LoginVo.getuNo().equals("") || pNo == null || pNo.equals("") )
{	// 메인 페이지로 보냄
	response.sendRedirect("view.jsp?no="+pNo); return;
}

// 중복검사
BoardDao bDao = new BoardDao();
if(bDao.IsDuplicate(LoginVo.getuNo(), pNo) == bDao.DUPLICATE)
{
	%>
	<script>
		alert("이미 추천한 게시글입니다.")
		document.location = "view.jsp?no=<%= pNo%>&type=<%= pType %>";
	</script>
	<%
	return;
}

// 파라메타로 받은 정보들로 vo에 추천 정보를 채워넣음
RecommendVo vo = new RecommendVo();

vo.setuNo(LoginVo.getuNo());	// 추천인 회원번호
vo.setpNo(pNo);					// 추천 글번호
vo.setRc(vo.getRc());

// 추천정보 vo를 DB에 업데이트한다
bDao.addRecnd(vo);
%>


<script>
	alert("추천이 완료되었습니다");
	document.location = "view.jsp?no=<%= pNo%>&type=<%= pType %>";
</script>

