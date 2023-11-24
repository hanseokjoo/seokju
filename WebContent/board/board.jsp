<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%
//페이징 객체 생성
paging pager = new paging(search);
//파라메타로 받은 내용으로 화면에 찍을 게시글 목록을 만드는 dao 객체
ListDao blist = new ListDao(search);

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
<%@ include file="../include/header.jsp" %>
<section>
    <table class="table table-striped">
        <thead>
        			<div style="text-align: center; font-size:2em; margin-top:0.5%; font-weight:bold;">
                    	<%
                    	if(type.equals("IN"))
                    	{
                    		%>숙소 게시판<%
                    	}else if(type.equals("RE"))
						{
							%>맛집 게시판<%
						}else if(type.equals("HP"))
						{
							%>핫플레이스<%
						}else if(type.equals("FR"))
						{
							%>자유 게시판<% 
						}
						else if(type.equals("NO"))
						{
							%>공지사항<% 
						}else if(type.equals("QA"))
						{
							%>QnA<% 
						}
                    	%>
                	</div>
                	<% 
                	if(type.equals("IN") || type.equals("RE") || type.equals("HP") || type.equals("FR"))
                	{
                	%>
                	<div style="text-align: center; color:gray;">
                    	인기 게시물
               	 	</div>
        </thead>
        <tbody>
        	<tr>
        	<th>
	        	<div class="picbox">
		           <% 
		           		BoardDao dao = new BoardDao();
						List<String> imageList = dao.getImagesFromBoard(type,4); // getImagesFromBoard 메소드를 호출하여 이미지 URL 목록을 가져옴
						int imageNumber = 1;
						
						if (imageList != null) {
						    // 이미지 목록이 비어 있지 않다면 이미지를 출력
						    for (String imageUrl : imageList) {
						    	System.out.println(imageUrl);
						    	String image_url = imageUrl.substring(0,36);
						    	String image_pNo = imageUrl.substring(36, imageUrl.length() -1 );
						    	image_pNo = image_pNo.split("`")[0];
						    	String image_pTitle = imageUrl.split("`")[1];
						    	System.out.println(image_url);
						    	System.out.println(image_pNo);
						    	System.out.println(image_pTitle);
						%>
						<div class="pic">
	<%-- 					    <a href="view.jsp"><img src="down.jsp?fname=<%= imageUrl %>"></a> --%>
						    <a href="view.jsp?no=<%= image_pNo %>"><img src="down.jsp?fname=<%= image_url %>"></a>
						    <p><%= imageNumber %>.  <%= image_pTitle %></p>
						</div>
						<%
							imageNumber++;
						    }
						}
					%>
				</div>
            </th>
            </tr>
        </tbody>
		        <%
		        }
		        %>
    <tfoot>
    	<tr>
    		<td>
    <form id="search_form" name="search_form" method="get" action="board.jsp">
    <input type="hidden" id="type" name="type" value="<%= type %>">
     <select name="sc" onchange="document.search_form.submit();">
     	<option value="pDate" <% if(search.getSortCol().equals("pDate")) out.print("selected"); %>>작성일 순</option>
     	<option value="rc" <% if(search.getSortCol().equals("rc")) out.print("selected"); %>>추천 순</option>
     	<option value="pCnt" <% if(search.getSortCol().equals("pCnt")) out.print("selected"); %>>조회수 순</option>
     </select>
          <select name="so" onchange="document.search_form.submit();">
		<option value="asc" <% if(search.getSortOrder().equals("asc")) out.print("selected"); %>>오름차순</option>
		<option value="desc" <% if(search.getSortOrder().equals("desc")) out.print("selected"); %>>내림차순</option>
	</select>
      </td>
      </tr>
      </tfoot>
         </table>
        <%
		if (LoginVo != null) 
		{
		    // 로그인 함
		    if (type.equals("NO") && LoginVo.getuLevel().equals("A")) 
		    {
			%>
		    <div class="bWrite">
		        <a href="write.jsp?type=<%= type %>" class="btn btn-secondary">글쓰기</a>
		    </div>
			<%
		    }
		    // 다른 타입의 게시판에서는 항상 버튼을 표시
		    else if (!type.equals("NO")) 
		    {
			%>
		    <div class="bWrite">
		        <a href="write.jsp?type=<%= type %>" class="btn btn-secondary">글쓰기</a>
		    </div>
			<%
		    }
		}
		%>
         <table class="table table-striped" style="text-align: center;">
             <thead>
                 <tr>
                     <th>번호</th>
                     <th>제목</th>
                     <th>작성자</th>
                     <th>날짜</th>
                     <th>조회수</th>
                     <th>추천</th>
                 </tr>
                 <% 
                 if(!type.equals("NO"))
                 {
                 %>
                 <% 
				    BoardDao dao = new BoardDao();
				    List<BoardVo> boardList = dao.ReadNo(); // ReadNo 메소드로 "NO" 타입의 게시물을 가져옴
				    
				    if (boardList != null) {
				        for (BoardVo board : boardList) {
				    %>
				    <tr>
				        <td style="background-color:#f2f5ff; font-weight:bold;">공지</td>
				        <div class="title">
					        <td style="background-color:#f2f5ff; text-align: left; font-weight:bold;">
					            <a href="view.jsp?pNo=<%= search.getViewLink(board.getpNo()) %>" style="color:black;"><%= board.getpTitle() %></a>
					        </td>
				        </div>
				        <td style="background-color:#f2f5ff; font-weight:bold;">
				        <%
                    		if(board.getuLevel().equals("A"))
                    		{
                    		%>
                    			관리자
                    		<%
                    		}else
                    		{
                    		%>
                    			<%= board.getuName() %>
                    		<%
                    		}
                    	%>
				        </td>
				        <td style="background-color:#f2f5ff; font-weight:bold;"><%= board.getpDate() %></td>
				        <td style="background-color:#f2f5ff; font-weight:bold;"><%= board.getpCnt() %></td>
				        <td style="background-color:#f2f5ff; font-weight:bold;"><%= board.getrc() %></td>
				    </tr>
				    <%
				        }
				    }
				 %>
             <%
             }
             %>
             <tbody class="list">
             <%
				for( int i = 0; i < size; i++ )
				{
					BoardVo vo = blist.getItem(i);
					// 게시물의 신고 횟수 확인
					String rp = vo.getrp();
					// 제목의 <, > 처리
					vo.setpTitle(vo.getpTitle().replace("<","&lt;").replace(">","&gt;"));
					%>
					<tr>
					<td><%= seqNo-- %></td>
					<td style="text-align:left;" class="title">
						<%
							// 게시물의 신고 횟수가 5회 이상인 경우
							if( rp != null && Integer.parseInt(rp) >= 5 )
							{ %>
								<span style="color:grey;">5회 이상 신고되어 블라인드 처리된 게시물입니다.</span>
						<%	}
							else
							{ %>
								<a href="view.jsp?<%= search.getViewLink(vo.getpNo()) %>"><%= vo.getpTitle() %>
						<%	}
						%>
						<span style="color:orange">
						<%
							if(!vo.getrCnt().equals("0"))
							{
								%>(<%= vo.getrCnt() %>)<%
							}
						%>
						</span></a>
					</td>
					<td style="text-align:center">
						<%
                    		if(vo.getuLevel() != null && vo.getuLevel().equals("A"))
                    		{
                    		%>
                    			관리자
                    		<%
                    		}else
                    		{
                    		%>
                    			<%= vo.getuName() %>
                    		<%
                    		}
                    	%>
					</td>
					<td style="text-align:center"><%= vo.getpDate() %></td>
					<td style="text-align:center"><%= vo.getpCnt() %></td>
					<td style="text-align:center"><%= vo.getrc() %></td>
					</tr>
				<% 
				}
				%>
             </tbody>
         </table>
         <table>
             <tr>
                 <td>
                     <div class="search_2">
                         <select name="kind">
                             <option value="A" <%= search.getKind().equals("A") ? "selected" : "" %>>제목+내용</option>
                             <option value="T" <%= search.getKind().equals("T") ? "selected" : "" %>>제목</option>
                             <option value="C" <%= search.getKind().equals("C") ? "selected" : "" %>>내용</option>
                         </select>
                         <input type="text" name="key" placeholder="검색" value="<%= search.getKeyword() %>">
                         <a href="javascript:document.search_form.submit();"><img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"></a>
                     </div>
                     </form>
                 </td>
             </tr>
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