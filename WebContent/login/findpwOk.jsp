<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/index.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%
// 권한 확인
String uName  = request.getParameter("uName");
String uMail  = request.getParameter("uMail");

String authPW = (String)session.getAttribute("authPW");

System.out.println("uMail : "  + uMail);
System.out.println("uName : "  + uName);

// 유효성 체크를 한다
if( uMail == null || uMail.equals("") || uName == null || uName.equals("") )
{	// 유효하지 않은 접근
	response.sendRedirect("../board/index.jsp");
	return;
}
// 회원 정보를 담은 vo를 생성한다
MemberDao dao	= new MemberDao(search);	
MemberVo vo		= new MemberVo();	
vo.setuMail(uMail);
vo.setuName(uName);

// MemberDao 클래스의 인스턴스 생성
MemberDao memberDao = new MemberDao(search);

// 비정적인 UpdatePW 메소드 호출
boolean result = memberDao.UpdatePW(vo);

%>
<section style="background-color:white; height:700px" >
<!--<div class="container-sm" style="background-color:black; height:300px; width:400px; padding:50px"> -->
	<div class="container-sm" style="background-color:white; text-align: center;">
	<br><br><br>
	임시 비밀번호가 발송되었습니다.

	<br><br><br>
	<a href="../board/index.jsp" class="btn btn-dark">메인으로</a>
	</div>
</section>
<%@ include file="../include/footer.jsp"%>