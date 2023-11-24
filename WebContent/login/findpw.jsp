<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/findpw.css" type="text/css" rel="stylesheet">
<link
 	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
 	rel="stylesheet"
 	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
 	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="../js/mailAuthPW.js"></script>
<%@ include file="../include/header.jsp" %>
<script>
	window.onload=function()
	{
		$('#uName').focus();
	};
</script>
   	<section style="background-color:white; height: 800px;">
		<h3 style="text-align: center; margin-bottom: 1%; margin-top:2%">비밀번호 찾기</h3>
		<p style="color: black; text-align: center; font-size: 1em;">비밀번호는 닉네임, 이메일을 통해 찾으실 수 있습니다.</p>
   		<form action="findpw.jsp" method="post" id="findpw" enctype="multipart/form-data" onsubmit="return false;">
   		<div id="loginInput" style="width:300px; margin:auto">
		<!-- <table class="table table-bordered" style="border:1px solid black;margin-left:auto;margin-right:auto;">  -->
			<table style="margin:auto">
				<tr>
					<td>
						닉네임
					</td>
					<td>
						<input type="text" name="uName" id="uName" placeholder="닉네임을 입력해주세요">
					</td>
				</tr>			
				<tr>
					<td>
						e-mail
					</td>
					<td>
						<input type="email" name="uMail" id="uMail" placeholder="e-mail을 입력하세요">
						<div class="msg_area"></div>
					</td>
				</tr>
			</table>
			</div>
				<br>
				<br>
			<div id="LoginButton" style="text-align:center;">
			<input type="button" class="btn btn-outline-dark" value="임시 비밀번호 전송" onclick="Domail();">
		</div>
		</form>
</section>
<%@ include file="../include/footer.jsp"%>