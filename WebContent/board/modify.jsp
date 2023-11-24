<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%
//대상 게시글의 게시글 번호를 파라메타로 받는다
String pNo = request.getParameter("no");
String pageNo = request.getParameter("page");

if(pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	response.sendRedirect("board.jsp");
	return;
}
//게시글을 조회한다
BoardDao dao = new BoardDao();
BoardVo vo = dao.Read(pNo, false);
if(vo == null)
{	// 게시글 번호로 게시글을 조회하지 못함
	%>
	<script>
	alert("게시글 정보가 올바르지 않습니다");
	document.location = "board.jsp";
	</script>
	<%
	return;
}
%>
        <link href="../css/write.css" type="text/css" rel="stylesheet">
        <%@ include file="../include/header.jsp" %>
        <%
		// 수정할 게시글의 권한을 검사한다
		// 게시글의 uID와 로그인 사용자의 uID가 일치하지 않으면
		if(LoginVo == null || (!LoginVo.getuLevel().equals("A") && !LoginVo.getuNo().equals(vo.getuNo())))
		{
			// -> 수정 권한이 없습니다 -> index
			%>
			<script>
				alert("게시글에 대한 수정 권한이 없습니다");
				document.location="view.jsp?type=<%= vo.getpType()%>&page=<%= pageNo %>";
			</script>
			<%
		}

		// 게시글의 정보를 input태그에 value 값으로 넣는다
		%>
        <section>
            <div style="font-size:1.5rem; font-weight:bold; text-align: center; margin-top: 20px; margin-bottom: 20px;">글 수정</div>
            <div class="bd" id="bd">
                <form action="modifyOk.jsp?type=<%= vo.getpType() %>" method="post" id="modify" enctype="multipart/form-data">
                    <input type="hidden" id="pNo" name="pNo" value="<%= pNo %>">
					<input type="hidden" id="uNo" name="uNo" value="<%= vo.getuNo() %>">
					<input type="hidden" id="page" name="page" value="<%= pageNo %>">
                    <table>
                        <tr>
                            <td colspan="2">
                                <select name="type" id="type">
                                    <option>게시판 선택</option>
                                    <option value="IN" value="IN" <%= (vo.getpType().equals("IN")) ? "selected" : "" %>>숙소게시판</option>
                                    <option value="RE" value="RE" <%= (vo.getpType().equals("RE")) ? "selected" : "" %>>맛집게시판</option>
                                    <option value="HP" value="HP" <%= (vo.getpType().equals("HP")) ? "selected" : "" %>>핫플레이스</option>
                                    <option value="FR" value="FR" <%= (vo.getpType().equals("FR")) ? "selected" : "" %>>자유게시판</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <input type="text" name="title" class="title" maxlength="40" value="<%= vo.getpTitle() %>">
                    <textarea name="note" id="note" cols="102" rows="18"><%= vo.getpContent() %></textarea>
                    <% 
						ArrayList<AttachVo> list = vo.getaList();
						if(list != null)
						{
							for(AttachVo item : list)
							{%>
							<%= item.getfName() %><br>
							<% }
							}else
							{ %> 첨부파일 없음<br><%
							}%>
					새 첨부파일 : <input type="file" name="attach" class="attach">
                        <table class="writebtn">
                            <tr>
                                <td colspan="2" style="text-align:center;">
                                    <a href="board.jsp?type=<%= vo.getpType()%>&no=<%= pNo%>&page=<%= pageNo %>" class="btn btn-dark">목록으로</a>
                                    <input type="submit"  class="btn btn-primary" value="글 수정 완료">
                                    <input type="reset" value="취소" class="btn btn-success">
                                    <a href="view.jsp?no=<%= pNo%>" class="btn btn-dark">되돌아가기</a>
                                </td>
                            </tr>
                        </table>
                </form>
            </div>
        </section>
<%@ include file="../include/footer.jsp"%>