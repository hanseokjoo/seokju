<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/index.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%
//스크랩 대상 게시글의 정보를 파라메타로 받는다
String pNo = request.getParameter("no");
String pType = request.getParameter("type");

System.out.println(pNo);

//유효성 체크
if(pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("index.jsp");
	return;
}
//로그인 정보를 확인하여 스크랩 권한이 있는지 체크
if(LoginVo == null)
{
	// -> 스크랩 권한이 없습니다 -> index
	%>
	<script>
		alert("게시글에 대한 스크랩 권한이 없습니다");
		document.location = "view.jsp?no=" + <%= pNo %>;
	</script>
	<%
	return;
}

ScrapDao dao = new ScrapDao(search);
if(dao.IsDuplicate(LoginVo.getuNo(),pNo) == ScrapDao.DUPLICATE)
{
	dao.deleteScrap(LoginVo.getuNo(),pNo);
	%>
	<script>
	if(confirm("스크랩을 취소하시겠습니까?")==1)
		{
			alert("스크랩이 취소되었습니다.")
			document.location = "view.jsp?no="+ <%= pNo %>;
		}
	</script>
	<%
	return;
}

// 스크랩 진행
ScrapVo vo = new ScrapVo();
	
// 파라메타로 받은 정보들로 vo에 정보를 채워넣음
vo.setpNo(pNo);					// 해당 글 번호
vo.setuNo(LoginVo.getuNo());	// 스크랩 한 회원

// 스크랩글 vo를 DB에 업데이트 한다
dao.addScrap(vo);

%>
<script>
	alert("스크랩이 완료되었습니다.")
	document.location= "view.jsp?no=" + <%= pNo %>;
</script>
<%@ include file="../include/footer.jsp"%>