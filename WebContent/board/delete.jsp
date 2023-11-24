<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/index.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%
// 삭제 대상 게시글의 게시글 번호를 파라메타로 받는다
String pNo = request.getParameter("no");
String pType = request.getParameter("type");
String pageNo = request.getParameter("page");

String[] noList = pNo.split(",");

// 로그인 정보를 확인하여 삭제 권한이 있는지 체크
if (LoginVo == null) {
    // 로그인하지 않은 경우 -> index 페이지로 리다이렉트
    response.sendRedirect("index.jsp");
} else {
    // 로그인한 경우
    for (int i = 0; i < noList.length; i++) {
        // 게시글 번호로 정보를 받아옴
        BoardDao dao = new BoardDao();
        BoardVo vo = dao.Read(noList[i], false);

        if (!LoginVo.getuLevel().equals("A") && !LoginVo.getuNo().equals(vo.getuNo())) {
            // 삭제 권한이 없는 경우 -> 게시글 목록 페이지로 리다이렉트
            response.sendRedirect("board.jsp?type=" + vo.getpType() + "&page=" + pageNo);
            return;
        }

        // 삭제 권한이 있는 경우
        // ScrapDao를 생성해서 스크랩을 삭제하는 메소드 호출
        dao.Delete(noList[i]);
    }
}

// 삭제가 완료되면 이 부분이 실행됨
%>
<section style="background-color:white; height:700px" >
    <div class="container-sm" style="background-color:white; text-align: center;">
        <br><br><br>
        삭제가 완료되었습니다.

        <br><br><br>
        <a href="board.jsp?type=<%= pType %>&page=<%= pageNo %>" class="btn btn-primary btn-sm">목록으로</a>
        <a href="write.jsp" class="btn btn-success btn-sm">새글쓰기</a>
    </div>
</section>
<%@ include file="../include/footer.jsp"%>