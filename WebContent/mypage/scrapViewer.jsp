<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/scrapViewer.css" type="text/css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script>

function selectAll(selectAll) 
{
  const checkboxes = document.getElementsByName('selectedPosts');
  
  for (let i = 0; i < checkboxes.length; i++) 
  {
    checkboxes[i].checked = selectAll.checked;
  }
}

</script>
<%@ include file="../include/header.jsp" %>
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
MemberDao mDao = new MemberDao(search);
MemberVo  mvo  = mDao.Read(uNo);
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

//파라메타로 받은 내용으로 화면에 찍을 게시글 목록을 만드는 dao 객체
ScrapDao blist = new ScrapDao(search);
blist.getList(uNo);

ArrayList<ScrapVo> list = blist.getList();

int size = blist.getListSize();
%>
<script>

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
	document.location = "deleteScrap.jsp?no=" + Values;
}

</script>
<section>
    <table class="table table-striped">
        <thead>
        	<div style="text-align: center; font-size:2em;">
                 	스크랩 글 관리
        	</div>
            <div class="mbtn" style="text-align: center; margin-top: 10px;">
      			<a href="mypage.jsp?uNo=<%= uNo %>&type=MI" 	class="btn btn-secondary btn-sm">내 정보 관리</a>
    			<a href="scrapViewer.jsp?uNo=<%= uNo %>"class="btn btn-secondary btn-sm">스크랩 글 관리</a>
      			<a href="myPost.jsp?uNo=<%= uNo %>"		class="btn btn-secondary btn-sm">작성 글 보기</a>
  			</div>              
      </thead>
  </table>
  <form action="deleteScrap.jsp" method="post" id="deleteForm">
	  <a href="javascript:DeleteSelect();" class="btn btn-outline-dark btn-sm">선택 삭제</a>
	  <table class="table table-striped" style="text-align: center;  width: 100%;">
	      <thead>
	          <tr>
	          	  <th style="width:30px"  align="center">
	          	  	<input type=checkbox value='selectall' onclick='selectAll(this)'>
	          	  </th>
	              <th style="width:70px" >번호</th>
	              <th>제목</th>
	              <th>작성자</th>
	              <th>삭제</th>
	          </tr>
	      </thead>
	      <tbody class="list">
			 <%
				for( int i = 0; i < size; i++ )
				{
					ScrapVo vo = blist.getItem(i);
					%>
					<tr>
					<td align="center"><input type=checkbox id="selectedPosts" name="selectedPosts" value="<%= vo.getpNo() %>"></td>
					<td><%= vo.getsNo() %></td>
					<td style="text-align:left;">
					<a href="../board/view.jsp?<%= search.getViewLink(vo.getpNo()) %>" style="color:black;"><%= vo.getpTitle() %></a>
					</td>
					<td style="text-align:center"><%= vo.getuName() %></td>
					<td><a href="deleteScrap.jsp?uNo=<%= LoginVo.getuNo() %>&no=<%= vo.getpNo() %>" class="btn btn-dark btn-sm">삭제</a></td>
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
           <form id="search_form" name="search_form" method="get" action="scrapViewer.jsp">
              <div class="search_2">
                  <input type="hidden" id="uNo" name="uNo" value="<%= uNo%>">
                  <select name="kind">
                      <option value="T" <%= search.getKind().equals("T") ? "selected" : "" %>>제목</option>
                      <option value="N" <%= search.getKind().equals("N") ? "selected" : "" %>>작성자</option>
                  </select>
                  <input type="text" name="key" placeholder="검색" value="<%= search.getKeyword() %>">
                  <a href="javascript:document.search_form.submit();"><img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"  style="margin-top:1px;"></a>
              </div>
           </form>
          </td>
      </tr>
  </table>
</section>
<%@ include file="../include/footer.jsp"%>