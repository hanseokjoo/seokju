<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//선택된 게시판 설정
String type = request.getParameter("type");
if(type == null || type.equals(""))
{	// 파라메타로 게시판 종류 선택을 받지 못했음
	type="IN";	// 숙소게시판을 기본값으로 세팅
}
%>
<%@ page import="vo.*" %>
<%
// 세션에서 로그인 정보가 있는지 받아온다
MemberVo LoginVo  = (MemberVo)session.getAttribute("Login");
%>
<script>
    // 스크롤 이벤트 핸들러 함수
    function handleScroll() {
        const nav = document.querySelector('nav');
        const scrollY = window.scrollY;

        // 스크롤 위치에 따라 배경색 변경
        if (scrollY > 100) {
            nav.style.backgroundColor = 'rgba(0, 0, 0, 0.8)'; // 스크롤 위치에 따라 원하는 배경색으로 변경
        } else {
            nav.style.backgroundColor = 'transparent'; // 일정 위치 이하로 스크롤하면 다시 투명 배경으로 변경
        }
    }

    // 스크롤 이벤트 리스너 등록
    window.addEventListener('scroll', handleScroll);
</script>
</head>
	<body>
	<header>
			<!-- nav 영역 시작 ------------------------------------ -->
            <nav>
                <span><a href="../board/index.jsp"><img src="../images/logo.png" width="100px" height="100px"></a></span>
                <ul>
                    <li id="nav"><a href="../board/board.jsp?type=IN" style="<%= type.equals("IN") ? "font-weight:bold" : "" %>">숙소게시판</a></li>
                    <li id="nav"><a href="../board/board.jsp?type=RE" style="<%= type.equals("RE") ? "font-weight:bold" : "" %>">맛집게시판</a></li>
                    <li id="nav"><a href="../board/board.jsp?type=HP" style="<%= type.equals("HP") ? "font-weight:bold" : "" %>">핫플레이스</a></li>
                    <li id="nav"><a href="../board/board.jsp?type=FR" style="<%= type.equals("FR") ? "font-weight:bold" : "" %>">자유게시판</a></li>
                    <li id="nav"><a href="../board/board.jsp?type=NO" style="<%= type.equals("NO") ? "font-weight:bold" : "" %>">공지사항</a></li>
                    <li id="nav"><a href="../board/board.jsp?type=QA" style="<%= type.equals("QA") ? "font-weight:bold" : "" %>">QnA</a></li>
                </ul>
                <div class="login">
					<% if (LoginVo == null) { %>
    					<a href="../join/join.jsp" class="btn btn-light">회원가입</a>&nbsp;
    					<a href="../login/login.jsp" class="btn btn-light">로그인</a>&nbsp;
					<% } else if ("A".equals(LoginVo.getuLevel())) { %>
    				<!-- 관리자 로그인 상태일 때의 링크 -->
	   				 	<a href="../manager/mManager.jsp" style="color: white;">[ <%= LoginVo.getuName() %> ] 관리자님 </a> &nbsp;&nbsp;
    					<a href="../login/logout.jsp" class="btn btn-light">로그아웃</a>&nbsp;
					<% } else if ("U".equals(LoginVo.getuLevel())) { %>
    				<!-- 일반 회원 로그인 상태일 때의 링크 -->
					    <a href="../mypage/mypage.jsp?uNo=<%= LoginVo.getuNo() %>&type=MI" style="color: white;">[ <%= LoginVo.getuName() %> ]님</a> &nbsp;&nbsp;
					    <a href="../login/logout.jsp" class="btn btn-light">로그아웃</a>&nbsp;
					<% } %>
                </div>
            </nav>
        </header>