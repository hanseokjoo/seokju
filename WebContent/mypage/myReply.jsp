<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%
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
// 유저 정보를 조회한다
MemberDao dao = new MemberDao(search);
MemberVo  mvo  = dao.Read(uNo);
if( mvo == null )
{	// 유저 정보가 존재하지 않음
	%>
	<script>
	alert("올바른 유저 정보가 아닙니다.");
	document.location = "../board/index.jsp";
	</script>
	<%
	return;
}
%>
        <link href="css/myReply.css" type="text/css" rel="stylesheet">
         <script>
         window.onload = function() {
             let num = 32;
             let list = document.querySelector(".list");
             for( let i = 0; i < 13; i++ )
             {
                 let item = document.createElement("tr");
                 item.innerHTML = `<td style="text-align:center"><label>\${num-i}  <input type="checkbox"></label></td>
                 <td style="text-align:left;"><a href="view.html">트립 월드 킹 댓글</a></td>
                 <td style="text-align:center">2023. 09. 03</td>
                 <td style="text-align:center"><a href="" class="btn btn-secondary">삭제</a></td>`;
                 list.appendChild(item);
             }
         }
         function del()
         {
             if (!confirm("작성하신 댓글을 모두 삭제하시겠습니까?")) {
                 alert("삭제가 취소되었습니다.");
             } else {
                 alert("작성하신 댓글이 모두 삭제되었습니다.");
             }
         }
        </script>
        <%@ include file="../include/header.jsp" %>
        <section>
            <table class="table table-striped">
                <thead>
                        <div style="text-align: center; font-size:2em;">
                           	 내 댓글 관리
                        </div>
                        <div class="mbtn" style="text-align: center; margin-top: 10px;">
                            <a href="mypage.jsp?uNo=<%= uNo %>&type=MI" class="btn btn-secondary btn-sm">내 정보 관리</a>
                            <a href="scrapViewer.jsp?uNo=<%= uNo %>" class="btn btn-secondary btn-sm">스크랩 글 관리</a>
                            <a href="myPost.jsp?uNo=<%= uNo %>" class="btn btn-secondary btn-sm">작성 글 보기</a>
                            <a href="myReply.jsp?uNo=<%= uNo %>" class="btn btn-secondary btn-sm">작성 댓글 보기</a>
                        </div>
                </thead>
            </table>
            <table class="table table-striped" style="text-align: center; width: 100%; margin-bottom: 30px;">
                <thead>
                    <tr>
                        <th width="120"><a href="" class="btn btn-secondary" onclick="del()">일괄 삭제</a></th>
                        <th>댓글</th>
                        <th>날짜</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody class="list">
                <!-- 게시물 목록 자리 -->
                </tbody>
            </table>
            <table>
                <tr>
                    <td>
                        <div class="search_2">
                            <select name="searchOption">
                                <option value="1">전체</option>
                                <option value="2">내용</option>
                            </select>
                            <input type="text" placeholder="검색">
                            <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png">
                        </div>
                    </td>
                </tr>
            </table>
            <ul class="pagination justify-content-center">
                <li class="page-item"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;&laquo;</span></a></li>
                <li class="page-item"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">4</a></li>
                <li class="page-item"><a class="page-link" href="#">5</a></li>
                <li class="page-item"><a class="page-link" href="#">6</a></li>
                <li class="page-item"><a class="page-link" href="#">7</a></li>
                <li class="page-item"><a class="page-link" href="#">8</a></li>
                <li class="page-item"><a class="page-link" href="#">9</a></li>
                <li class="page-item"><a class="page-link" href="#">10</a></li>
                <li class="page-item"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
                <li class="page-item"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;&raquo;</span></a></li>
            </ul>
        </section>
        <%@ include file="../include/footer.jsp"%>