<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
    <link href="../css/info_modify.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
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
   			document.location = "../board/index.jsp";
		</script>
	<%
	return;
}
%>
<section>
	<div class="mtitle">회원정보 수정</div>
	<form
		action="infoModifyOk.jsp"
		method="get"
		id="infoModify"
		enctype="multipart/form-data">
	<input type="hidden" id="uMail" name="uMail" value="<%= vo.getuMail() %>">	
	<input type="hidden" id="uNo" name="uNo" value="<%= uNo %>">
	<div class="my">
		<table class="check" border="1" style="width: 400px;">
               <tr>
                   <td style="width:100px;"><p>email</p></td>
                   <td>
                       <%= vo.getuMail() %>
                   </td>
               </tr>
               <tr>
                   <td><p>닉네임</p></td>
                   <td>
                       <input type="text" name="uName" id="uName" maxlength="12" value="<%= vo.getuName() %>" size="30px"> 
                   </td>
               </tr>
               <tr>
                   <td><p>관심분야</p></td>
                   <td>
                   	<label>
					    <input type="radio" name="uInter" value="FR" 
					    <% if("FR".equals(vo.getuInter())){%>checked<%}%>
					    style="width: 20px;">
						   	없음
					</label>
                       <label>
   						<input type="radio" name="uInter" value="IN"
   						<% if("IN".equals(vo.getuInter())){%>checked<%}%>
  							style="width: 20px;">
   						숙소 
					</label>
					<label>
   						<input type="radio" name="uInter" value="RE" 
   						<% if("RE".equals(vo.getuInter())){%>checked<%}%>
  						 	style="width: 20px;">
   						맛집 
					</label>
					<label>
 						  	<input type="radio" name="uInter" value="HP" 
 						  	<% if("HP".equals(vo.getuInter())){%>checked<%}%>
 							style="width: 20px;">
  							 핫플레이스
					</label>
                   </td>
               </tr>
               <tr>
               		<td>
               			<input type="hidden" id="uName" name="uName" value="<%= vo.getuName() %>">
		               	<input type="hidden" id="uInter" name="uInter" value="<%= vo.getuInter() %>">
               		</td>
               </tr>
             </table>
	</div>
	<div class="btbtn">
		<input type="submit" class="btn btn-outline-dark" value="수정 완료">
		<a href="mypage.jsp?uNo=<%= uNo %>&type=MI" class="btn btn-outline-dark">취소</a>
	</div>
	</form>
</section>
<%@ include file="../include/footer.jsp"%>