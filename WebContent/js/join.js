$(document).ready(function(){
	$("#uName").focus();
});

let flag = false;
let isDuple = false;
let CheckState = "0";	// 0 :: 체크하지 않음  / 1 :: 사용가능 / 2 :: 중복

function DoSubmit()
{
	if( flag == true) { return; }
	
	// 1. 이름이 입력되어 있는가
	let uName = document.querySelector("#uName");
//	let uName = $("#userName");
	if( uName.value == "" )
	{
		alert("닉네임을 입력하세요");
		uName.focus();
		return false;
	}
	if( uName.value.length < 2 )
	{
		alert("닉네임을 2자 이상 입력하세요");
		uName.focus();
		return false;
	}
	// 닉네임 사용가능 상태 체크
	// 중복 검사를 하지 않음
	if( CheckState == "0" ) { namecheck(); return false; }
	// 중복 상태임
	if( CheckState == "2" )
	{
		document.querySelector(".msg_areaa").html("4자 이상 입력해주세요");
		uName.value = "";
		uName.focus();
		return false;
	}

	// 2. 비번이 입력되었는가
	let uPW  = document.getElementById("uPW");
	let uPWC = $("#pwc"); 
	if( uPW.value.length < 8 )
	{
		alert("비밀번호를 8자 이상 입력하세요");
		uPW.focus();
		return false;
	}

	// 2-1. 비번이 입력되어 있다면, 비번 확인이 입력되어 있는가
	if( uPWC.val() == "" )
	{
		alert("비밀번호 확인을 입력하세요");
		uPWC.focus();
		return false;
	}
	if( uPW.value != uPWC.val() )
	{
		alert("비밀번호가 일치하지 않습니다");
		uPW.value = "";
		uPWC.val("");
		uPW.focus();
		return false;
	}
	
	// 3. 이메일이 입력되었는가
	let uMail = document.querySelector("#uMail");
	if( uMail.value == "" )
	{
		alert("이메일을 입력하세요");
		uMail.focus();
		return false;
	}
	// 이메일 사용가능 상태 체크
	// 중복 검사를 하지 않음
	if( CheckState == "0" ) { mailcheck(); return false; }
	// 중복 상태임
	if( CheckState == "2" )
	{
		document.querySelector(".msg_area").html("4자 이상 입력해주세요");
		uMail.value = "";
		uMail.focus();
		return false;
	}
	// 여기로 내려오면 중복검사를 마쳤고, 사용 가능한 이메일 상태라는 의미
	
	console.log("필수 항목은 모두 입력되었습니다");
	alert("필수 항목 입력이 모두 확인되었습니다.");
	
	// 4. 자동가입 방지코드 확인
	let cCode = $("#Ccode").val();
	$.ajax({
		type : "get",
		url: "getSign.jsp",
		dataType: "html",
		success : function(data) 
		{
			if( data.trim() != cCode )
			{
				alert("자동가입 방지코드가 일치하지 않습니다.");
				return false;
			}else
			{
				if( confirm("회원 가입을 진행하시겠습니까?") == 1)
				{
					flag = true;
					$("#join").submit();
					return true;
				}
			}
		},
		error : function(xhr, status, error) { return false; }
	});
	return false;
}