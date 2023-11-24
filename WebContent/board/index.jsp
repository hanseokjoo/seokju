<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%
//페이징 객체 생성
paging pager = new paging(search);
//파라메타로 받은 내용으로 화면에 찍을 게시글 목록을 만드는 dao 객체
ListDao blist = new ListDao(search);

//전체 게시글 개수를 얻는다
int total = blist.getTotal();

//현재 페이지에 표시할 게시글의 목록을 생성한다
blist.getList(search.getCurPage(),5);

int size = blist.getListSize();
%>
        <link href="../css/index.css" type="text/css" rel="stylesheet">
        <%@ include file="../include/header.jsp" %>
        <section style="height:800px;">
            <div class="button">
                <a href="index.jsp?type=IN" class="<%= type.equals("IN") ? "btn btn-primary active btn-sm" : "btn btn-primary btn-sm"%>">숙소</a>
                <a href="index.jsp?type=RE" class="<%= type.equals("RE") ? "btn btn-primary active btn-sm" : "btn btn-primary btn-sm"%>">맛집</a>
                <a href="index.jsp?type=HP" class="<%= type.equals("HP") ? "btn btn-primary active btn-sm" : "btn btn-primary btn-sm"%>">핫플</a>
                <a href="index.jsp?type=FR" class="<%= type.equals("FR") ? "btn btn-primary active btn-sm" : "btn btn-primary btn-sm"%>">자유</a>
            </div>
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
				%>		<div class="quick">인기 게시물</div>
					</div>
            <br><br>
            <p style="margin-left:300px; font-size:16px; color:gray">최신 게시물</p>
            <table class="table table-striped custom-table" style="text-align: center;" border="1">
            <input type="hidden" id="type" name="type" value="<%= type %>">
                <tbody class="list">
                    <tr>
                    	<%
						for( int i = 0; i < size; i++ )
						{
							BoardVo vo = blist.getItem(i);
							
							String rp = vo.getrp();
							// 제목의 <, > 처리
							vo.setpTitle(vo.getpTitle().replace("<","&lt;").replace(">","&gt;"));
						%>
                    	<td style="text-align:center"><%= vo.getpNo() %></td>
                    	<td style="text-align:left">
                    			<%
								// 게시물의 신고 횟수가 5회 이상인 경우
								if( rp != null && Integer.parseInt(rp) >= 5 )
								{ %>
									<span style="color:grey;">5회 이상 신고되어 블라인드 처리된 게시물입니다.</span>
								<%	}
								else
								{ %>
									<a href="view.jsp?no=<%= vo.getpNo() %>&type=<%= type%>" style="color:black;"><%= vo.getpTitle() %>
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
                    </tr>
					<% 
					}
					%>
                </tbody>
            </table>
        </section>
<%@ include file="../include/footer.jsp"%>