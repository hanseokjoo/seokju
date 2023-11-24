<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/join.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%
	//파라메타 인코딩 설정
	request.setCharacterEncoding("UTF-8");

	// 파라메타로 넘어온 데이터 받기
	String uName  = request.getParameter("uName");
	String uPW    = request.getParameter("uPW");
	String uMail  = request.getParameter("uMail");
	String uInter = request.getParameter("uInter");
	String code	  = request.getParameter("code");
	// 파라메타에서 "code"를 받고
	// 데이터 유효성 체크 
	if( uMail == null || uMail.equals("") || uName == null || uName.equals("") || code == null || code.equals("") )
	{
		response.sendRedirect("join.jsp");
		return;
	}
	// 세션에서 authcode 키 이름으로 저장된 code를 받아 / 파라메타로 받은 code와 비교
	// -> 같지 않으면, 인증코드가 올바르지 않다고 메세지 출력하고 되돌려보냄
	// -> 같으면 가입 진행
	HttpSession hsession = request.getSession();
	String sCode = (String)session.getAttribute("authcode");
	
	 // 코드가 일치하는지 확인
    if (code != null && sCode != null && code.equals(sCode)) {
        // 코드가 일치하면 가입 프로세스를 진행하거나 로그인 처리를 수행
    } else {
    	%>
        <script>
            alert("인증 코드가 일치하지 않습니다.");
            location.href = "join.jsp";
        </script>
        <%
		return;
    }
	 
	MemberDao dao = new MemberDao(search);
	if(dao.IsDuplicate(uMail) == MemberDao.DUPLICATE)
	{
		%>
		<table style="width:70%; margin-left:40%; margin-right:50%;">
			<tr>
				<td>
				<br><br><br><br><br>
				중복된 이메일이 존재합니다.<br><br>
				</td>
			</tr>
			<tr>
				<td>
					<a href="join.jsp" class="btn btn-dark">되돌아가기</a>
					<a href="../board/index.jsp" class="btn btn-dark">메인으로</a>
				</td>
			</tr>
		</table>
		<%
		return;
	}
	
	// 중복검사 통과 => 가입 진행
	MemberVo vo = new MemberVo();
	// 파라메타로 받은 정보들로 vo에 정보를 채워넣음
	vo.setuName(uName);
	vo.setuMail(uMail);
	vo.setuPW(uPW);
	vo.setuInter(uInter);
	
	// 완성된 vo로 가입을 진행한다
	if(dao.Join(vo) == false)
	{
		%>
		<table style="width:70%; margin-left:40%; margin-right:50%;">
			<tr>
				<td>
				회원가입이 실패했습니다<br>
				</td>
				<td>
				<a href="join.jsp">되돌아가기</a>
				</td>
			</tr>
		</table>
		<%
		return;
	}
%>

<!-- section 영역 시작 --------------------------------------------------------------------------------- -->
<section>
	<div style="font-size:1.5rem; font-weight:bold; text-align: center; margin-top: 20px;">회원가입 완료</div>
	<table style="width:70%; margin-left:40%; margin-right:50%;">
		<tr>
			<td>이름 : 	<%= vo.getuName() %></td>
		</tr>
		<tr>
			<td>아이디 :	<%= vo.getuMail() %></td>
		</tr>
		<tr>
			<td>취미 :	<%= vo.getuInter() %></td>
		</tr>

		<tr>
			<td>
				<a href="../board/index.jsp" class="btn btn-dark">목록으로</a>
				<a href="../login/login.jsp" class="btn btn-dark">로그인하기</a>
			</td>
		</tr>
	</table>
<!-- section 영역 종료 --------------------------------------------------------------------------------- -->
</section>
<%@ include file="../include/footer.jsp" %>