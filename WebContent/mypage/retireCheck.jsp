<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp" %>
<%@ include file="../include/header.jsp" %>
        <link href="../css/mypage_check.css" type="text/css" rel="stylesheet">
<%
	// LoginVo가 없는 사용자는 retireCheck.jsp에 들어올수 없다.
	if (LoginVo == null) {	
		//System.out.printf("retireCheck.jsp: LoginVo 가 null 일때 진입금지\r\n");
		response.sendRedirect("../board/index.jsp");
		return;
	}
%>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>

<script>
window.onload = function()
{
	$("#userPW").focus();
}

function checkLogin()
{
	if( $("#userPW").val() == "" )
	{
		alert("비밀번호를 입력하세요.");
		$("#userPW").focus();
		return;				
	}
	// 호출되면 탈퇴 절차를 실행하고 그 결과를 data로 보낸다.
	// 이미 탈퇴한 회원이 화면을 여러개 띄워 놓았기 때문에 다른 화면에서 비밀번호 재확인화면의 확인을 클릭할 경우
	// 이미 탈퇴한 회원이라는 정보를 data에 포함해야 한다.
	$.ajax({
	    type: "get",
	    url: "retireOk.jsp?useremail=" + $("#useremail").val() + "&userPW=" + $("#userPW").val() + "",
	    dataType: "text",
	    success: function(data) 
	    {
	    	data = data.trim();
	    	data = data.split(":");
	    	code = data[0];
	    	msg  = data[1];
	    	alert(msg);
	     	if(code == "OK")
	     	{
	     		//탈퇴 처리됨
	     		document.location = "../board/index.jsp";
	     	}else
	     	{
	     		$("#userPW").focus();	
	     	}
	    },
		error: function() 
		{
			alert("서버와 통신이 실패하였습니다.");
	    },
	  });			
	
}
</script>
      <section>
          <div class="mtitle">비밀번호 재확인</div>
          <p>정보를 안전하게 보호하기 위해 비밀번호를 재확인 합니다.</p>
          <div class="my" style="text-align:center;">
            <table class="check" border="1">
              <tr>
                  <td style="width:100px;"><p>email</p></td>
                  <td>
                      <input type="email" name="useremail" id="useremail" value="<%= LoginVo.getuMail() %>">
                  </td>
              </tr>
              <tr>
                  <td><p>PW</p></td>
                  <td>
                      <input type="password" name="userPW" id="userPW" placeholder="비밀번호를 입력하세요">
                  </td>
              </tr>
            </table>
            <br>
            <input type="button" class="btn btn-dark" value="확인" onclick="checkLogin();">
          </div>
 
      </section>
<%@ include file="../include/footer.jsp"%>