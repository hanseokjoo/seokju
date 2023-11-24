<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
        <link href="../css/pw_modify.css" type="text/css" rel="stylesheet">
        <%@ include file="../include/header.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="../js/pwModify.js"></script>
<%	
//세션에 로그인 정보가 없으면 되돌려보낸다
if( LoginVo == null )
{	// 로그인 되어 있지 않으므로 돌려보냄
	%>
	<script>
		alert("유효하지 않은 접근입니다.");
		document.location = "../board/index.jsp";
	</script>
	<%
	return;
}
// 회원 정보 수정 JSP 
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
MemberVo  vo  = dao.Read(uNo);
if( vo == null )
{	// 유저 정보가 존재하지 않음
	%>
		<script>
   			alert("올바른 유저 정보가 아닙니다.");
   			document.location = "index.jsp";
		</script>
	<%
	return;
}
%>
<section>
    <div class="mtitle">비밀번호 변경</div>
    <form
		action="pwModifyOk.jsp"
		method="get"
		id="infoModify"
		enctype="multipart/form-data"
		onsubmit="return DoSubmit();">
	<input type="hidden" id="uNo" name="uNo" value="<%= uNo %>">
    <div class="my">
        <table class="check" border="1" style="width: 500px;">
          <tr>
              <td style="width:200px;"><p>email</p></td>
              <td>
                  <%= vo.getuMail() %>
              </td>
          </tr>
          <tr>
            <td style="width:200px;"><p>현재 비밀번호</p></td>
            <td>
                <input type="password" name="curPW" id="curPW" placeholder="현재 비밀번호">
            </td>
        </tr>
        <tr>
            <td style="width:200px;">
                <p>새 비밀번호</p>
                <p class="pc" style="font-size: 8px; color: #bcbcbc;">(영문/숫자 조합, 8자~16자)</p>
            </td>
            <td>
                <input type="password" name="newPW" id="newPW" placeholder="새 비밀번호">
            </td>
        </tr>
        <tr>
            <td style="width:200px;"><p>새 비밀번호 확인</p></td>
            <td>
                <input type="password" name="conPW" id="conPW" placeholder="새 비밀번호 확인">
            </td>
        </tr>
        </table>
      </div>
      <div class="btbtn">
      		<input type="submit" class="btn btn-outline-dark" value="등록">
      </div>
      </form>
</section>
<%@ include file="../include/footer.jsp"%>