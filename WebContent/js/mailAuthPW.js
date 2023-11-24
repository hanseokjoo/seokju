function val(email) {
    // 이메일 규격을 정의한 정규 표현식
    var ept = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    // 정규 표현식과 매치되는지 검사
    return ept.test(email);
}

function Domail() 
{
	if( $("#uName").val() == "")
	{
		alert("닉네임을 입력하세요.");
		$("#uName").focus();
		return;
	}
	if( $("#uMail").val() == "")
	{
		alert("이메일 주소를 입력하세요.");
		$("#uMail").focus();
		return;
	}
    
    // AJAX 요청 보내기
    $.ajax({
        type: "get",
        url: "findOk.jsp",
        data: "uName="+ $("#uName").val() + "&uMail=" + $("#uMail").val(),
        success: function(response) 
        {
        	response = response.trim();
        	alert(response);
        	/*
        	if (response == "ERROR")
        	{
        		// 발송 안되었음
        	}else{
        		// 서버에서 받은 인증번호를 처리하거나 화면에 표시
        		// alert("인증번호: " + response);
        		alert("임시 비밀번호가 발송되었습니다. \n * 메일 도착까지는 시간이 조금 소요될 수 있습니다. *");
        	}
        	*/
        },
        error: function(xhr, status, error) 
        {
            // 오류 처리
            console.error(error);
        }
    });
    return false;
}