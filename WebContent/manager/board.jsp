<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%
//페이징 객체 생성
paging pager = new paging(search);
//파라메타로 받은 내용으로 화면에 찍을 게시글 목록을 만드는 dao 객체
ReportListDao blist = new ReportListDao(search);

//전체 게시글 개수를 얻는다
int total = blist.getTotal();
//현재 페이지 번호로 목록을 만든다
blist.getList(search.getCurPage());

int size = blist.getListSize();
//현재 페이지에 첫번째 게시글에 붙일 번호를 계산
int seqNo = total - (search.getCurPage() - 1) * paging.perPage;
//페이징 객체를 생성한다
pager.setTotal(total, search.getCurPage());
%>
<%@ include file="../include/head.jsp" %>
<link href="../css/board.css" type="text/css" rel="stylesheet">
<style>
    a:visited {
      color: white;
    }
</style>
<%@ include file="../include/header.jsp" %>
<%
if(LoginVo == null || !LoginVo.getuLevel().equals("A"))
{
	%>
	<script>
	alert("접근 권한이 없습니다");
	document.location = "../board/index.jsp";
	</script>
	<%
}
%>
<section>
    <table class="table table-striped">
        <thead>
        	<tr>
        		<th>
        			<div style="text-align: center; font-size:2em;">
                    	신고 게시물 목록
                	</div>
             	     <div class="mbtn" style="text-align:center; margin-top:1%; margin-bottom:1%;">
				        <a href="mManager.jsp" class="btn btn-secondary btn-sm">회원 정보 관리</a>
				        <a href="board.jsp" class="btn btn-secondary btn-sm">신고 게시물 관리</a>
			    	</div>
        		</th>
        	</tr>
        </thead>
            <tfoot>
    	<tr>
    		<td>
    <form id="search_form" name="search_form" method="get" action="board.jsp">
    <input type="hidden" id="type" name="type" value="<%= type %>">
      </td>
      </tr>
      </tfoot>
         </table>
         <table class="table table-striped" style="text-align: center;">
             <thead>
                 <tr>
                     <th>번호</th>
                     <th>신고 게시물</th>
                     <th>신고자</th>
                     <th>신고날짜</th>
                     <th>신고내역</th>
                 </tr>
             <tbody class="list">
             <%
				for( int i = 0; i < size; i++ )
				{
					ReportVo vo = blist.getItem(i);
					// 제목의 <, > 처리
					vo.setpTitle(vo.getpTitle().replace("<","&lt;").replace(">","&gt;"));
					%>
					<tr>
					<td><%= seqNo-- %></td>
					<td style="text-align:left;" class="title">
						<a href="../board/view.jsp?<%= search.getViewLink(vo.getpNo()) %>"><%= vo.getpTitle() %>
						<span style="color:red">
						<%
							if(!vo.getrp().equals("0"))
							{
								%>(<%= vo.getrp() %>)<%
							}
						%>
						</span></a>
					</td>
						<td style="text-align:center"><%= vo.getuName() %></td>
						<td style="text-align:center"><%= vo.getrpDate() %></td>
						<td style="text-align:center"><%= vo.getrpNote() %></td>
					</tr>
				<% 
				}
				%>
             </tbody>
             <table>
             <tr>
                 <td>
                     <div class="search_2">
                         <select name="kind">
                             <option value="T" <%= search.getKind().equals("T") ? "selected" : "" %>>신고 글 제목</option>
                             <option value="C" <%= search.getKind().equals("C") ? "selected" : "" %>>신고 내역</option>
                         </select>
                         <input type="text" name="key" placeholder="검색" value="<%= search.getKeyword() %>">
                         <a href="javascript:document.search_form.submit();"><img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"></a>
                     </div>
                     </form>
                 </td>
             </tr>
         </table>
         </table>
         <ul class="pagination justify-content-center">
		<!-- 첫 페이지 블럭으로 이동하는 버튼 -> 1번 페이지로 이동하는 링크 -->
		<li class="page-item<%= (pager.getStartBlock() == 1) ? " disabled" : "" %>">
			<a class="page-link" <%= (pager.getStartBlock() != 1)
			? "href='board.jsp?"+pager.getPage(1)+"'"
			: "" %>>|&lang;</a>
		</li>
		<!-- 앞 페이지 블럭들로 이동하는 버튼 -->
		<li class="page-item<%= (pager.getStartBlock() == 1) ? " disabled" : "" %>">
			<a class="page-link" <%= (pager.getStartBlock() != 1)
			? "href='board.jsp?"+pager.getPrevBlock()+"'"
			: "" %>>&lang;&lang;</a>
		</li>
		<!-- 앞 페이지로 이동하는 버튼 -->
		<li class="page-item">
			<a class="page-link" href="board.jsp?<%= pager.getPage( pager.getCurPage() - 1 ) %>">&lang;</a>
		</li>
		<!-- 페이지 블럭들 -->
		<%	for( int i = pager.getStartBlock();  i <= pager.getEndBlock(); i++ )
				{	
					if( search.getCurPage() == i )
					{	%>
						<li class="page-item active">
							<a class="page-link" href="board.jsp?<%= pager.getPage(i) %>">
								<Strong style="color:black;"><%= i %></Strong>
							</a>
						</li>
		<%		}else{	%>
			<li class="page-item">
				<a class="page-link" href="board.jsp?<%= search.getPageLink(i) %>"><%= i %>
				</a>
			</li>
		<%		}
				}	%>
		<!-- 뒤 페이지로 이동하는 버튼 -->
		<li class="page-item">
			<a class="page-link" href="board.jsp?<%= pager.getPage( pager.getCurPage() + 1 ) %>">&rang;</a>
		</li>
		<!-- 뒤 페이지 블럭들로 이동하는 버튼 -->
		<li class="page-item<%= (pager.getEndBlock() == pager.getMaxPage()) ? " disabled" : "" %>">
			<a class="page-link" <%= (pager.getEndBlock() != pager.getMaxPage())
			? "href='board.jsp?"+pager.getNextBlock()+"'"
			: "" %>>&rang;&rang;</a>
		</li>
		<!-- 마지막 페이지 블럭으로 이동하는 버튼 -->
		<li class="page-item<%= (pager.getEndBlock() == pager.getMaxPage()) ? " disabled" : "" %>">
			<a class="page-link" <%= (pager.getEndBlock() != pager.getMaxPage())
			? "href='board.jsp?"+pager.getPage(pager.getMaxPage())+"'"
			: "" %>>&rang;|</a>
		</li>
	</ul>
    </section>
<%@ include file="../include/footer.jsp"%>