<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/myPost.css" type="text/css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<%@ page import="java.util.Enumeration" %>
<%
// 현재 요청에서 세션을 가져오기
HttpSession mySession = request.getSession();
// 세션에 저장된 모든 속성의 이름 가져오기
Enumeration<String> attrNames = session.getAttributeNames();
// 모든 세션 속성을 반복하기
while (attrNames.hasMoreElements()) 
{
	// 세션 속성의 이름을 가져오기
	String name = (String) attrNames.nextElement();
	// 해당 속성 값을 문자열로 변환
	String value = mySession.getAttribute(name).toString();
}
// 파라미터에서 모든 이름 가져오기
Enumeration<String> params = request.getParameterNames();
while(params.hasMoreElements()) {
  String name = (String) params.nextElement();
}
// 세션에서 로그인 정보를 가져와 MemberVo에 넣는다
MemberVo sessionVo = (MemberVo) session.getAttribute("Login");
// 회원 번호를 저장할 변수를 초기화
String uNo = "";
// 세션에 회원 번호가 비어 있지 않으면 세션에서 사용자 번호를 가져온다
if( sessionVo == null || sessionVo.getuNo() == null || sessionVo.getuNo().isEmpty() )
{	%>
	<script>
	alert("로그인이 해제되었습니다.");
	document.location = "../board/index.jsp";
	</script>
	<%
	return;
}else if (sessionVo.getuNo() != "") 
{
	uNo = sessionVo.getuNo();
}else 
{	// 아니면 request 객체에서 uNo 값을 가져와서 회원 번호로 사용
	uNo = request.getParameter("uNo");
}

System.out.printf("myPost.jsp: uNo = %s\r\n", uNo);
%>
<%
//페이징 객체 생성
search.setType("AL");
// 검색 조건 'search'를 전달해서 초기화
paging pager = new paging(search);

//파라메타로 받은 내용으로 화면에 찍을 게시글 목록을 만드는 dao 객체
//search.setType("AL");

// 게시물 목록을 가져오는 dao객체를 생성 search, uNo를 전달해서 초기화
MyListDao blist = new MyListDao(search, uNo); //search는 이미 request를 파라메터로 하여 만들어졌다.
// 전체 게시글 개수를 얻는다
int total = blist.getTotal();

// 현재 페이지 번호로 목록을 만든다
blist.getMyList(search.getCurPage(), 15, uNo);

int size = blist.getListSize();
// 현재 페이지에 첫번째 게시글에 붙일 번호를 계산한다
int seqNo = total - (search.getCurPage() - 1) * paging.perPage;
// 페이징 객체에 전체 게시물 개수와 현재 페이지 번호를 설정한다
pager.setTotal(total, search.getCurPage());
%>
         <script>
            function selectAll(selectAll) 
            {
              const checkboxes = document.getElementsByName('selectedPosts');
              
              for (let i = 0; i < checkboxes.length; i++) 
              {
                checkboxes[i].checked = selectAll.checked;
              }
            }
            
            function DeleteSelect()
            {
            	var Values = "";
            	
            	$("input:checkbox[name=selectedPosts]:checked").each(function()
            	{
            		if(Values != "") Values += ",";
            		Values += $(this).val();
            	});
            	
            	if(Values == "")
            	{
            		alert("삭제할 게시물을 선택하세요.");
            		return;
            	}
            	if( confirm("삭제 할래요?") != 1) return;
            	document.location = "../board/delete.jsp?no=" + Values;
            }
        </script>
        <%@ include file="../include/header.jsp" %>
        <section>
            <table class="table table-striped">
                <thead>
                        <div style="text-align: center; font-size:2em;">
                            	내가 작성한 글 보기
                        </div>
                        <div class="mbtn" style="text-align: center; margin-top: 10px;">
                            <a href="mypage.jsp?uNo=<%= uNo %>&type=MI" 	class="btn btn-secondary btn-sm">내 정보 관리</a>
			    			<a href="scrapViewer.jsp?uNo=<%= uNo %>"class="btn btn-secondary btn-sm">스크랩 글 관리</a>
			      			<a href="myPost.jsp?uNo=<%= uNo %>"		class="btn btn-secondary btn-sm">작성 글 보기</a>
                        </div>
                        <tr>
                            <td width="50">
                                	정렬
                            </td>
                            <td>
                            	<form id="order_form" name="order_form" method="get" action="myPost.jsp">
	                            	
	                                <select name="sc" onchange="document.order_form.submit();">
								     	<option value="pDate" <% if(search.getSortCol().equals("pDate")) out.print("selected"); %>>작성일 순</option>
								     	<option value="rc" <% if(search.getSortCol().equals("rc")) out.print("selected"); %>>추천 순</option>
								     	<option value="pCnt" <% if(search.getSortCol().equals("pCnt")) out.print("selected"); %>>조회수 순</option>
	                                </select>
	                                
								    <select name="so" onchange="document.order_form.submit();">
										<option value="asc" <% if(search.getSortOrder().equals("asc")) out.print("selected"); %>>오름차순</option>
										<option value="desc" <% if(search.getSortOrder().equals("desc")) out.print("selected"); %>>내림차순</option>
									</select>
								
                                </form>
                            </td>
                        </tr>
                </thead>
            </table>
            <form action="../board/delete.jsp" method="post" id="delete">
            <table class="table table-striped" style="text-align: center; width: 100%; margin-bottom: 30px;">
                <thead>
                    <tr>
                    	<th style="width:30px"  align="center">
	          	  			<input type=checkbox value='selectall' onclick='selectAll(this)'>
	          	  		</th>
                        <th width="120"><a href="javascript:DeleteSelect();" class="btn btn-secondary btn-sm">선택 삭제</a></th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>날짜</th>
                        <th>조회수</th>
                        <th>추천</th>
                    </tr>
                </thead>
                <tbody class="list">
<%				// 게시물 목록을 반복해서 출력한다
				for( int i = 0; i < size; i++ )
				{
					BoardVo vo = blist.getItem(i);
					//blist.Print_ListDao("리스트 화면 직전");
					// 제목의 <, > 처리
					vo.setpTitle(vo.getpTitle().replace("<","&lt;").replace(">","&gt;"));
%>
					<tr>
						<td align="center">
							<input type=checkbox id="selectedPosts" name="selectedPosts" value="<%= vo.getpNo() %>">
						</td>
						<td><%= seqNo-- %></td>
						<td style="text-align:left;">
							<!--  게시물의 제목을 클릭하면 게시물로 이동한다 -->
							<a href="../board/view.jsp?<%= search.getViewLink(vo.getpNo()) %>" style="color:black;"><%= vo.getpTitle() %>
								<span style="color:orange">
							<% 	// 댓글 개수가 0이 아니면, 댓글 수를 표시한다
								if(!vo.getrCnt().equals("0"))
								{ %>
									(<%= vo.getrCnt() %>)
							<%	}
								%>
								</span>
							</a>
						</td>
						<td style="text-align:center"><%= vo.getuName() %></td>
						<td style="text-align:center"><%= vo.getpDate() %></td>
						<td style="text-align:center"><%= vo.getpCnt() %></td>
						<td style="text-align:center"><%= vo.getrc() %></td>
					</tr>
<% 
				}
%>
                </tbody>
            </table>
            </form>
            <table>
                <tr>
                    <td>
	                     <div class="search_2">
							<form id="search_form" name="search_form" method="get" action="myPost.jsp">
	                         	<select name="kind">
		                             <option value="A" <%= search.getKind().equals("A") ? "selected" : "" %>>제목+내용</option>
		                             <option value="T" <%= search.getKind().equals("T") ? "selected" : "" %>>제목</option>
		                             <option value="C" <%= search.getKind().equals("C") ? "selected" : "" %>>내용</option>
		                         </select>
	                         <input type="text" name="key" placeholder="검색" value="<%= search.getKeyword() %>">
	                         <a href="javascript:document.search_form.submit();"><img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"></a>
	                         </form>
	                     </div>
                    </td>
                </tr>
            </table>
            <ul class="pagination justify-content-center">
			<!-- 첫 페이지 블럭으로 이동하는 버튼 -> 1번 페이지로 이동하는 링크 -->
				<li class="page-item<%= (pager.getStartBlock() == 1) ? " disabled" : "" %>">
					<a class="page-link" <%= (pager.getStartBlock() != 1)
					? "href='myPost.jsp?"+"uNo=" + uNo + "&" + pager.getPage(1)+"'"
					: "" %>>|&lang;</a>
				</li>		
			<!-- 앞 페이지 블럭들로 이동하는 버튼 -->
				<li class="page-item<%= (pager.getStartBlock() == 1) ? " disabled" : "" %>">
					<a class="page-link" <%= (pager.getStartBlock() != 1)
					? "href='myPost.jsp?" + "uNo=" + uNo + "&" + pager.getPrevBlock()+"'"
					: "" %>>&lang;&lang;</a>
				</li>	
			<!-- 앞 페이지로 이동하는 버튼 -->
				<li class="page-item">
					<a class="page-link" href="myPost.jsp?<%= "uNo=" + uNo + "&" + pager.getPage( pager.getCurPage() - 1 ) %>">&lang;</a>
				</li>
			<!-- 페이지 블럭들 -->
<%	
			String pageLink = "";
			for( int i = pager.getStartBlock();  i <= pager.getEndBlock(); i++ ) {
				pageLink = "uNo=" + uNo + "&" + pager.getPage(i);
				
				if( search.getCurPage() == i ) {
					/*
							i, pager.getStartBlock(), pager.getEndBlock(), pager.getPage(i));
					*/
%>
					<li class="page-item active">
						<a class="page-link" href="myPost.jsp?<%= pageLink %>">
							<Strong style="color:black;"><%= i %></Strong>
						</a>
					</li>				
<%		
				}
				else {
					/*
							i, pager.getStartBlock(), pager.getEndBlock(), pager.getPage(i));
					*/
%>
					<li class="page-item">
						<a class="page-link" href="myPost.jsp?<%= pageLink %>"><%= i %>
						</a>
					</li>
<%
				}
			}
%>
			<!-- 뒤 페이지로 이동하는 버튼 -->
				<li class="page-item">
					<a class="page-link" href="myPost.jsp?<%= "uNo=" + uNo + "&" + pager.getPage( pager.getCurPage() + 1 ) %>">&rang;</a>
				</li>
			<!-- 뒤 페이지 블럭들로 이동하는 버튼 -->
<%
				pageLink = "uNo=" + uNo + "&" + pager.getNextBlock();
%>
				<li class="page-item<%= (pager.getEndBlock() == pager.getMaxPage()) ? " disabled" : "" %>">
					<a class="page-link" 
						<%= (pager.getEndBlock() != pager.getMaxPage()) ? "href='myPost.jsp?" + pageLink + "'" : "" %>>
						&rang;&rang;
					</a>
				</li>
			<!-- 마지막 페이지 블럭으로 이동하는 버튼 -->
<%
				pageLink = "uNo=" + uNo + "&" + pager.getPage(pager.getMaxPage());
%>
				<li class="page-item<%= (pager.getEndBlock() == pager.getMaxPage()) ? " disabled" : "" %>">
					<a class="page-link" <%= (pager.getEndBlock() != pager.getMaxPage()) 
						? 
						"href='myPost.jsp?"+ pageLink +"'"
						: 
						"" %>>
						&rang;|
					</a>
				</li>
			</ul>
        </section>
        <%@ include file="../include/footer.jsp"%>