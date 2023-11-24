<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "util.CookieManager" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/findpw.css" type="text/css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="../js/login.js"></script>
<%
String loginEmail = CookieManager.readCookie(request, "loginEmail");

String cookieCheck = "";
if( !loginEmail.equals(""))
{
	cookieCheck = "checked";
}
%>
<%@ include file="../include/header.jsp" %>
      <section style="background-color:white; height: 800px;">
		<h3 style="text-align: center; margin-bottom: 3%; margin-top:2%">로그인</h3>
		<form id="login" action="loginOk.jsp" method="post" name="login" onsubmit="return Dosubmit(); && handleRememberMe();" onload="getLogin()">
          	<div id="loginInput" style="width:300px; margin:auto">
      			<table style="margin:auto">
					<tr>
						<td>
							e-mail
						</td>
						<td>
							<input type="email" name="userMail" id="userMail" placeholder="e-mail을 입력해주세요" value="<%= loginEmail%>">
						</td>
					</tr>
					<tr>
						<td>
							비밀번호
						</td>
						<td>
							<input type="password" name="userPW" id="userPW" placeholder="비밀번호를 입력해주세요">
						</td>
					</tr>
				</table>
          	</div>
			<br>
			<div style="margin:auto; text-align: center">
    				<input type="checkbox" name = "saveCheck" value="Y" id="flexCheckDefault" style="text-align: center; width: 20px;" <%= cookieCheck %>>
    				<label for="flexCheckDefault">
      				e-mail 저장
    				</label>
  			</div>
  			<br><br>
      		<div id="LoginButton" style="text-align:center;">
				<a href="join.jsp"><input type="button" class="btn btn-outline-dark" value="회원가입"></a>
				<input type="submit" class="btn btn-outline-dark" value="로그인하기">
				<a href="findpw.jsp" class="btn btn-outline-dark">비밀번호 찾기</a>
			</div>
		</form>
      </section>
<%@ include file="../include/footer.jsp"%>