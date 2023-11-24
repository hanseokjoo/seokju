<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
        <link href="../css/join.css" type="text/css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="../js/join.js"></script>
		<script src="../js/mailcheck.js"></script>
		<script src="../js/mailAuth.js"></script>
	    <%@ include file="../include/header.jsp" %>
<%
// 세션에 로그인 정보가 없으면 되돌려보낸다
if(LoginVo == null)
{	// 로그인 되어 있지 않으므로 돌려보냄
	%>
	<script>
		alert("유효하지 않은 접근입니다.");
		document.location = "index.jsp";
	</script>
	<%
	return;
}

String pNo = request.getParameter("no");
String pType = request.getParameter("type");

if(pNo == null || pNo.equals(""))
{	// 유효하지 않은 접근
	%>
	<script>
	alert("유효하지 않은 접근입니다.");
	document.location = "index.jsp";
	</script>
	<%
	return;
}

//중복검사
BoardDao bDao = new BoardDao();
if(bDao.RpDuplicate(LoginVo.getuNo(), pNo) == bDao.DUPLICATE)
{
	%>
	<script>
		alert("이미 신고한 게시글입니다.")
		document.location = "view.jsp?no=<%= pNo %>&type=<%= pType %>";
	</script>
	<%
	return;
}

MemberDao dao = new MemberDao(search);
MemberVo  vo  = dao.Read(LoginVo.getuNo());
if( vo == null )
{	// 유저 정보가 존재하지 않음
	%>
		<script>
 		alert("올바른 회원 정보가 아닙니다.");
 		document.location = "index.jsp";
		</script>
	<%
	return;
}
%>
<section>
    <div style="font-size:1.5rem; font-weight:bold; text-align: center; margin-top: 20px;">신고</div>
    <form action="reportOk.jsp?no=<%= pNo %>" method="post" id="join" name="join">
        <table style="width:700px; border:0;">
            <tr>
                <td><p>신고 타입</p></td>
                <td>
                	<label for="rpType">:</label>
					<select name="rpType" id="rpType">
			  		<option value="0">스팸</option>
						<option value="1">음란물</option>
						<option value="2">성격에 맞지 않는 글</option>
						<option value="3">과도한 욕설</option>        						
						<option value="4">광고</option>        						
						<option value="5">사회 분란 글</option>        						
					</select>
               	 </td>
            </tr>
     		<tr>
        		<td>
        			<label for="rpNote">신고 내용:</label>
			</td>
        		<td>
        			<textarea name="rpNote" id="rpNote" rows="4" cols="37"></textarea>
        		</td>
        	</tr>
            <tr>
                <td><p>자동신고방지</p></td>
 				<td>
                    <p><img src="../join/Captcha.jsp"></p>
                    <input type="text" id="Ccode" placeholder="자동신고방지 코드를 입력하세요" size="36px">
                </td>
            </tr>
            <tr>
            	<td colspan="2" style="padding-left: 150px; padding-top: 20px;">
            		<p>* 허위 신고 시 신고자를 처벌합니다. *</p>
            	</td>
            </tr>
            <tr>
                <td colspan="2" style="padding-left: 200px; padding-top: 20px;">
                    <input type="submit" class="btn btn-dark" value="신고하기">
                    <input type="reset" class="btn btn-dark" value="취소">
                </td>
            </tr>
        </table>
    </form>
</section>
<%@ include file="../include/footer.jsp"%>