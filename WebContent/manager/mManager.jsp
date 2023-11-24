<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%@ page import="util.*" %>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>

   <link href="../css/mManager.css" type="text/css" rel="stylesheet">
   <script>
	function pwmodi(uNo)
	   {
	       if (!confirm("사용자의 이메일로 임시 비밀번호를 전송하시겠습니까?")) {
	       alert("전송이 취소되었습니다.");
	       } else {
	       alert("임시 비밀번호가 전송되었습니다.");
	       }
	   }
	function id_del(uNo)
	{
		let urlString = "../deleteMember.jsp?uNO=" + uNo;
		console.log("urlString = " + urlString);
			
		alert("id_del 호출됨: " + uNo + "\n" + urlString +"|");
	    if ( !confirm("사용자의 계정을 삭제하시겠습니까?") )
	    {
	    	alert("삭제가 취소되었습니다.");
	    }else 
	    {
			$.ajax({
				    type: "get",
				    url: urlString,
				    dataType: "text",
				    success: function(data) 
				    {
				    	data = data.trim();
				    	data = data.split(":");
				    	code = data[0];
				    	msg  = data[1];
				     	if(code == "OK")
				     	{
				     		//탈퇴 처리됨
					    	alert(msg);
				     		document.location = "./mManager.jsp";
				     	}else
				     	{
					    	alert(msg);
				     		//document.location = "mManager.jsp";
				     	}
				    },
					error: function() 
					{
						alert("서버와 통신이 실패하였습니다.");
				    }
				
			});		
	    }
	}
	function id_stop(uNo)
	    {
			let urlString = "./stopMember.jsp?uNO=" + uNo;
			console.log("urlString = " + urlString);
		
			alert("id_stop 호출됨: " + uNo);
			
	        if (!confirm("사용자의 계정을 정지하시겠습니까?")) {
	        alert("정지가 취소되었습니다.");
	        } else {
				$.ajax({
				    type: "get",
				    url: urlString,
				    dataType: "text",
				    success: function(data) 
				    {
				    	data = data.trim();
				    	data = data.split(":");
				    	code = data[0];
				    	msg  = data[1];
				     	if(code == "OK")
				     	{
				     		//정지 처리됨
					    	alert(msg);
				     		document.location = "./mManager.jsp";
				     	}else
				     	{
					    	alert(msg);
				     		//document.location = "mManager.jsp";
				     	}
				    },
					error: function() 
					{
						alert("서버와 통신이 실패하였습니다.");
				    }
				
			});		
	        }
	    }
   </script>

<%

	 // 로그인된 상태인지 점검
	 MemberVo sLogin = (MemberVo) session.getAttribute("Login");
	 if (sLogin == null) { // login 하지 않은 경우 index.jsp 로 보낸다.
		System.out.printf("mManager.jsp: sessionLogin is null\r\n");
		response.sendRedirect("index.jsp");
	 }
	 else { // login 한 경우, 특별한 처리 없음
		System.out.printf("mManager.jsp: sessionLogin is not null\r\n"); 
		System.out.printf("mManager.jsp: sessionLogin = %s\r\n", sLogin.toString());
		sLogin.PrintInfo();
	 }
	 
	// 검색조건을 이미 가지고 있는지 점검
	SearchVo sSearch = (SearchVo) session.getAttribute("Search");
	
	if (sSearch == null) { // 현재 설정된 값이 없으므로 초기값을 만든다.
		
		System.out.printf("mManger.jsp: Why init? sSearch is null\r\n");
		sSearch = new SearchVo();
	
		search.setType("");
		search.setCurPage(1);
		search.setKind("M");
		search.setKeyword("");
		search.setSortCol("");
		search.setSortOrder("");
		
		session.setAttribute("Search", sSearch);	
	}
	else { // 이미 가지고 있는 경우, 이것을 현재의 request로 업데이트 한다.
		
		String tmpType = request.getParameter("type");
		String tmpPage = request.getParameter("page");
		String tmpSearchColumn = request.getParameter("searchColumn");
		String tmpSearchKeyword = request.getParameter("searchKeyword");
		String tmpSc = request.getParameter("sc");
		String tmpSo = request.getParameter("so");
		
		sSearch.setType(tmpType);
		sSearch.setCurPage(tmpPage);
		sSearch.setKind(tmpSearchColumn);
		sSearch.setKeyword(tmpSearchKeyword);
		sSearch.setSortCol(tmpSc);
		sSearch.setSortOrder(tmpSo);
		
		sSearch.Print_SearchVo("sSearch", 0);
	}

	// 리스트를 가지고 있는지 점검
	ArrayList<MemberVo> sList = (ArrayList<MemberVo>) session.getAttribute("List");
	ManagerDao sDao = (ManagerDao) session.getAttribute("Dao");
	Pager sPager = (Pager) session.getAttribute("Pager");
	
	// 검색조건이 바뀌었는지를 점검하여, 바뀌었으면 DB검색을 다시한다.
	String paramSearchHasChanged = request.getParameter("SearchHasChanged");

	sDao = new ManagerDao();
	
	if (paramSearchHasChanged != null && paramSearchHasChanged.equals("searchChanged")) {
		sList = sDao.getSearchedMemberList(sSearch);	
		sPager = new Pager(sList.size());
	}
	else {
		sList = sDao.getReportedMemberList();
		sPager = new Pager(sList.size());
	}

	//현재 페이지
	int curPage = sSearch.getCurPage();
	sPager.setCurPage(curPage);
	
	String strCurPage = String.valueOf(curPage);
	
	//현재 페이지에 첫번째 게시글에 붙일 번호를 계산
	int seqNo = sList.size() - sPager.getListIndex(curPage);
	
%>


   <%@ include file="../include/header.jsp" %>
   
   <section>
       <table class="table table-striped">
           <thead>
                   <div style="text-align: center; font-size:2em;">
                       [일반회원관리]
                   </div>
                   <div class="mbtn" style="text-align:center; margin-top:1%; margin-bottom:1%;">
				        <a href="mManager.jsp" class="btn btn-secondary btn-sm">회원 정보 관리</a>
				        <a href="board.jsp" class="btn btn-secondary btn-sm">신고 게시물 관리</a>
			       </div>
           </thead>
       </table>
       <table class="table table-striped" style="text-align: center;">
           <thead>
               <tr>
               	   <th>항목번호</th>
                   <th>회원번호</th>
                   <th>이메일</th>
                   <th>닉네임</th>
                   <th>가입일자</th>
                   <th>탈퇴여부</th>
                   <th>회원탈퇴</th>
               </tr>
           <tbody class="list">
<%
           		int startIndex = sPager.getListIndex(curPage);
           		int size = sPager.getPageSize(curPage);
           		
				for(int i = 0; i < size; ++i)
				{
					MemberVo vo = sList.get(startIndex + i);
%>
					<tr>
					<td><%= seqNo-- %></td>
					<td style="text-align:center;"><%= vo.getuNo() %></td>
					<td style="text-align:center;"><%= vo.getuMail() %></td>
					<td style="text-align:center"><%= vo.getuName() %></td>
					<td style="text-align:center"><%= vo.getJoinDate() %></td>
					<td style="text-align:center"><%= vo.getuRetire() %></td>
						<td style="text-align:center">
							<a href="" class="btn btn-danger" onclick="id_del(<%= vo.getuNo() %>)">
								회원탈퇴
							</a>
						</td>
					
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
                   <script>
                   		function setHasChanged()
                   		{
                   			$("#SearchHasChanged").val("searchChanged");
                   		}
                   </script>
                   <form id="search_form" name="search_form" method="get" action="mManager.jsp">
                       <select name="searchColumn" onchange="setHasChanged();">
                           <option value="M" <%= search.getKind().equals("M") ? "selected" : "" %>>e-mail</option>
                           <option value="N" <%= search.getKind().equals("N") ? "selected" : "" %>>닉네임</option>
                       </select>
                       <input type="hidden" name="SearchHasChanged" id="SearchHasChanged" placeholder="ch" value="noChange">
                       <input type="text" name="searchKeyword" placeholder="검색" onchange="setHasChanged();" value="<%= search.getKeyword() %>">
                       <a href="javascript:document.search_form.submit();"><img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"></a>
	                </form>
                     
                   </div>
               </td>
           </tr>
       </table>
       <ul class="pagination justify-content-center">
       
		<!-- 뒤 페이지 블럭들로 이동하는 버튼 -->
		<li class="page-item<%= (sPager.getStartBlock(curPage) == 1) ? " disabled" : "" %>">
			<a class="page-link" <%= (sPager.getStartBlock(curPage) != 1)
			? "href='mManager.jsp?"+sPager.getPage(sPager.getStartBlock(curPage) - 1)+"'"
			: "" %>>&lang;&lang;</a>
		</li>   
        <li class="page-item">
			<a class="page-link" href="mManager.jsp?<%= sPager.getPage( sPager.getCurPage() - 1 ) %>">&lang;</a>
		</li>
		<!-- 페이지 블럭들 -->
<%	
		for( int i = sPager.getStartBlock(curPage);  i <= sPager.getStartBlock(curPage) + sPager.getFrameSize(curPage) - 1; i++ )
		{	
			if( search.getCurPage() == i )
			{	%>
				<li class="page-item active">
					<a class="page-link" href="mManager.jsp?<%= sPager.getPage(i) %>">
						<Strong style="color:black;"><%= i %></Strong>
					</a>
				</li>
		<%		}else{	%>
				<li class="page-item">
					<a class="page-link" href="mManager.jsp?<%= search.getPageLink(i) %>"><%= i %>
					</a>
				</li>
<%
				}
			}	
%>
 		<li class="page-item">
<%-- 			<a class="page-link" href="mManager.jsp?<%= sPager.getPage( sPager.getCurPage() + 1 ) %>">&rang;</a> --%>
			<a class="page-link" href="mManager.jsp?<%= sPager.getPage( search.getCurPage() + 1 ) %>">&rang;</a>
		</li>	
		<!-- 뒤 페이지 블럭들로 이동하는 버튼 -->
		<li class="page-item<%= (sPager.getEndBlock() == sPager.getMaxPage()) ? " disabled" : "" %>">
			<a class="page-link" <%= (sPager.getEndBlock() != sPager.getMaxPage())
/* 			? "href='mManager.jsp?"+sPager.getPage(sPager.getCurPage())+"'" */
			? "href='mManager.jsp?"+sPager.getPage(sPager.getNextBlock())+"'"
			: "" %>>&rang;&rang;</a>
		</li>		
	</ul>
   </section>
<%@ include file="../include/footer.jsp"%>