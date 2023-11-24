<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
    <link href="../css/view.css" type="text/css" rel="stylesheet">
     <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
     <script>
      function CheckReply()
  	{
  		if($("#rContent").val()=="")
  		{
  			alert("댓글 내용을 작성하세요");
  			$("#rContent").focus();
  			return false;
  		}
  		if(confirm("댓글을 작성하시겠습니까?")==1)
  		{
  			return true;
  		}
  		return false;
  	}
 	  
      function del(pNo, pType, pageNo) {
    	    if (confirm("글을 삭제하시겠습니까?")) {
    	        document.location =  "delete.jsp?type=" + pType + "&no=" + pNo + "&page=" + pageNo;
    	    }
    	    return false;
    	}
      
      function scrap()
      {
     	 if(confirm("스크랩 하시겠습니까?")==1)
   		{
   			document.location = "scrapOk.jsp?no=" + pNo;
   		}
      }
      
      function repo(pNo, pType){
          if (confirm("글을 신고 하시겠습니까?")) 
          {
              document.location = "report.jsp?no=" + pNo + "&type=" + pType;
          }else
          {
          alert("신고가 취소되었습니다.");
          }
          return false;
      }
      
      function DoDeleteReply(rNo, pNo, type)
  	{	// 댓글을 삭제할것인지 물어봄
  		if(confirm("댓글을 삭제하시겠습니까?")==1)
  		{
  			document.location = "deleteReply.jsp?rNo=" + rNo + "&pNo=" + pNo + "&type=" + pType;
  		}
  	}	
    </script>
<%
// 파라메타로 넘어온 게시글 번호 확인
String pNo = request.getParameter("no");
String pageNo = request.getParameter("page");
if(pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	%>
	<script>
	alert("유효하지 않은 접근입니다");
	document.location = "index.jsp";
	</script>
	<%
	return;
}

BoardDao dao = new BoardDao();
BoardVo vo = dao.Read(pNo, true);
if(vo == null)
{	// 게시글 번호가 존재하지 않음
	%>
	<script>
	alert("올바른 게시글 정보가 아닙니다");
	document.location = "index.jsp";
	</script>
	<%
	return;
}

//게시물의 신고 횟수 확인
String rp = vo.getrp();
AttachDao attachDao = new AttachDao();
attachDao.getList(pNo);
ArrayList<AttachVo> attachList = attachDao.getArrayList();

// write.jsp에서 제목과 본문을 입력받을때에 작은따옴표를 처리함
// <, >, \n, 등을 HTML에 맞게 처리를 해준다
vo.setpTitle(vo.getpTitle().replace("<", "&lt;").replace(">", "&gt;"));
vo.setpContent(vo.getpContent().replace("<", "&lt;").replace(">", "&gt;").replace("\n", "\n<br>"));

%>
        <%@ include file="../include/header.jsp" %>
        <section>
            <table class="table table-striped">
                <thead>
                        <br>
                        <div style="text-align: center; font-size:2em;">
                            <%= vo.getpTypeString() %>
                        </div>
                        <br><br><br>
                        <tr>
                            <td style="text-align: left; font-size:2em; width: 80%;">
                                <%= vo.getpTitle() %>
                            </td>
                            <td style="text-align: right; font-size:2em; width: 50%;">
                                	조회수 <%= vo.getpCnt() %>
                            </td>
                        </tr>
                </thead>
            </table>
            <table class="table table-bordered">
                <tr>
                    <td style="background-color: #b3b3b3; text-align: left; font-size:2em; width: 50%;">
                    	<%
                    		if(vo.getuLevel().equals("A"))
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
                    <td style="background-color: #b3b3b3; text-align: right; font-size:2em; width: 50%;">
                        <%= vo.getpDate() %>
                    </td>
                </tr>
                <tr>
                    <td style="text-align:left; font-size:2em;" colspan="2">
                    	<% if (attachList != null) {%>
					        <% for (AttachVo attach : attachList) { %>
					            <img src="down.jsp?fname=<%= attach.getuqName() %>" style="width:450px; margin-bottom:10px;"><br>
					        <% } %>
				        <% } %>
				        <br>
                       	<%= vo.getpContent() %>
                    </td>
                </tr>
            </table>
            <br>
                <div style="text-align:center;">
                	<a href="recndOk.jsp?type=<%= vo.getpType() %>&no=<%= pNo %>">
                    	<img src="../images/good.png" style="width:80px; height:80px;">
                    </a>
                </div>
                <div class="number">
                    <span style="margin-left: -4.5%; color: #6ec17d;">
                    	<%= vo.getrc() %>
                    </span>
                </div>
            <br>
            <div style="text-align:center;">
            	<% if(LoginVo != null)
					{	if( rp != null && Integer.parseInt(rp) >= 5 )
						{%>
							<span style="color:red;">[ 5회 이상 신고되어 수정/삭제가 금지된 게시물입니다. ]</span>
					<%	}
               		 } %>
            </div>
            <br>
            <div style="text-align:center;">
            	<input type="hidden" name="pNo" value="<%= pNo%>">
                <a href="board.jsp?type=<%= vo.getpType() %>&page=<%= pageNo %>" class="btn btn-dark">글목록</a>
                <% if(LoginVo != null)
					{	if( rp != null && Integer.parseInt(rp) >= 5 )
						{%>
					<%	}else if(LoginVo.getuNo().equals(vo.getuNo()) || LoginVo.getuLevel().equals("A") )	
						{
						%>
	                	<a href="modify.jsp?type=<%= vo.getpType() %>&no=<%= pNo %>&page=<%= pageNo %>" class="btn btn-dark">글수정</a>
	                	<a href="javascript:;" class="btn btn-dark" onclick="del('<%= pNo %>', '<%= vo.getpType() %>', '<%= pageNo %>')">글삭제</a>
	                	<%
	                	}
	                	%>
	                <a href="scrapOk.jsp?no=<%=vo.getpNo() %>&type=<%=vo.getpType()%>&page=<%=pageNo%>" class="btn btn-secondary">스크랩하기</a>
	                <a href="javascript:;" class="btn btn-light" onclick="repo('<%= pNo %>', '<%= vo.getpType() %>')">신고하기</a>
	                <%
                }
                %>
            </div>
            <br>
            <!-- 댓글 영역 -->
            <form action="replyOk.jsp" method="post" id="reply" onsubmit="return CheckReply();">
	            <table class="table table-bordered">
	            <% if(LoginVo != null)
	            	{
	            		// 게시판 타입이 "QA"이고 uLevel이 "A"인 경우에만 댓글 작성 폼 표시
	                	if (vo.getpType().equals("QA") && LoginVo.getuLevel().equals("A")) 
	                	{
	           	 		%>
		                <tr>
		                    <td style="width:120px;">
		                    	<%
		                    	if(LoginVo.getuLevel().equals("A"))
	                    		{
	                    		%>
	                    			관리자
	                    		<%
	                    		}else
	                    		{
	                    			LoginVo.getuName();
		                    	}
		                    	%>
		                    </td>
		                    <td colspan="2">
		                        <input type="hidden" name="pNo" value="<%= pNo%>">
		                        <input type="hidden" name="type" value="<%= vo.getpType()%>">
		                        <input type="text" size="100em" name="rContent" id="rContent">
		                        <input type="submit" value="작성완료" class="btn btn-primary btn-sm">
		                    </td>
		                </tr>
	            		<%
	                	}else if(!type.equals("QA"))
	                	{
	                	%>
	                	<tr>
		                    <td style="width:120px;"><%= LoginVo.getuName() %></td>
		                    <td colspan="2">
		                        <input type="hidden" name="pNo" value="<%= pNo %>">
		                        <input type="hidden" name="type" value="<%= vo.getpType() %>">
		                        <input type="text" size="100em" name="rContent" id="rContent">
		                        <input type="submit" value="작성완료" class="btn btn-primary btn-sm">
		                    </td>
		                </tr>
	                	<%	
	                	}
	            	}	
	            	%>
					<!------------->
					<%
					// 댓글 목록을 불러와 화면에 표시
					if( vo.getrList() != null)
						for(ReplyVo item : vo.getrList())
						{
						%>
						<tr>
							<td style="width:120px;">
								<%
		                    		if(item.getuLevel().equals("A"))
		                    		{
		                    		%>
		                    			관리자
		                    		<%
		                    		}else
		                    		{
		                    		%>
		                    			<%= item.getuName() %>
		                    		<%
		                    		}
		                    	%>
							</td>
							<td>
								<%= item.getrContent() %>
							</td>
							<td>
								<%= item.getrDate() %>
									<!-- LoginNo == replyUserNo 댓글 작성자 -> 댓글 삭제 버튼 -->
									<%
									if( LoginVo != null && item.getuNo().equals(LoginVo.getuNo()) )
									{	%>
										<a href="deleteReply.jsp?rNo=<%= item.getrNo() %>&pNo=<%= pNo %>&type=<%= vo.getpType() %>" class="btn btn-danger btn-sm">삭제</a>
										<a href="modifyReply.jsp?rNo=<%= item.getrNo() %>&pNo=<%=pNo %>" class="btn btn-success btn-sm">수정</a>
								<%	}else if( LoginVo != null && LoginVo.getuLevel().equals("A") )
									{	%>
										<a href="deleteReply.jsp?rNo=<%= item.getrNo() %>&pNo=<%= pNo %>&type=<%= vo.getpType() %>" class="btn btn-danger btn-sm">삭제</a>
										<a href="modifyReply.jsp?rNo=<%= item.getrNo() %>&pNo=<%=pNo %>" class="btn btn-success btn-sm">수정</a>
								<%	}
									%>
							</td>
						</tr>
						<% 
						}
						%>
	            </table>
           </form>
        </section>
<%@ include file="../include/footer.jsp"%>