<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/mypage.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%
// 세션에 로그인 정보가 없으면 되돌려보낸다
if(LoginVo == null)
{	// 로그인 되어 있지 않으므로 돌려보냄
	%>
	<script>
		alert("유효하지 않은 접근입니다.");
		document.location = "../board/index.jsp";
	</script>
	<%
	return;
}

// 파라메타로 넘어온 사용자 번호 확인
String uNo = request.getParameter("uNo");
if( uNo == null || uNo.equals("") )
{	// 유효하지 않은 접근
	%>
	<script>
		alert("유효하지 않은 접근입니다.");
		document.location = "../board/index.jsp";
	</script>
	<%
	return;
}
// 파라메타로 넘어온 유저 번호와, 로그인한 유저의 유저번호가 같지 않으면 올바르지 않은 접근
if( uNo.equals(LoginVo.getuNo()) ) {}
else
{
	%>
	<script>
		alert("유효하지 않은 접근입니다.");
		document.location = "../board/index.jsp";
	</script>
	<%
	return;
}

MemberDao dao = new MemberDao(search);
MemberVo  vo  = dao.Read(uNo);
if( vo == null )
{	// 유저 정보가 존재하지 않음
	%>
		<script>
 		alert("올바른 회원 정보가 아닙니다.");
 		document.location = "../board/index.jsp";
		</script>
	<%
	return;
}

%>
<section>
    <div class="mtitle">마이 페이지</div>
    <div class="mbtn">
        <a href="mypage.jsp?uNo=<%= uNo %>&type=MI" class="btn btn-secondary btn-sm">내 정보 관리</a>
        <a href="scrapViewer.jsp?uNo=<%= uNo %>" class="btn btn-secondary btn-sm">스크랩 글 관리</a>
        <a href="myPost.jsp?uNo=<%= uNo %>" class="btn btn-secondary btn-sm">작성 글 보기</a>
    </div>
    <%
      	if(type.equals("MI"))
      	{
     	%>
    <div class="my">
        	내 정보
    </div>
    <div class="info">
        <dl>
            <dt>이메일</dt>
            <dd><%= vo.getuMail() %></dd>
            <dt>닉네임</dt>
            <dd><%= vo.getuName() %></dd>
            <dt>관심분야</dt>
            <dd><%= vo.getuInterString() %></dd>
            <dt>가입 날짜</dt>
            <dd><%= vo.getJoinDate() %></dd>
        </dl>
    </div>
    <a href="infoModify.jsp?uNo=<%= uNo %>" class="btn btn-outline-dark">회원 정보 수정</a>
    <a href="pwModify.jsp?uNo=<%= uNo %>" class="btn btn-outline-dark">비밀번호 재설정</a>
    <a href="retireCheck.jsp?uNo=<%= uNo %>" class="btn btn-outline-dark">탈퇴</a>
    	<%
      	}else if(type.equals("MS"))
      	{
      	%>
      	
      	<%
      	}
    %>
</section>
<%@ include file="../include/footer.jsp"%>