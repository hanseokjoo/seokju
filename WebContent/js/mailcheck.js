function namecheck()
{
	let result = "";
	let uName = document.querySelector("#uName");	// 메일 입력폼 객체
	let msg_areaa = document.querySelector(".msg_areaa");	// 메세지 출력 영역 객체

	// 이메일이 2자 이상 입력되어 있지 않으면 중복체크를 하지 않는다
	if( uName.value.length < 2 ) { return; }
	console.log(uName.value);
	$.ajax({
		type : "get",
		url	 : "./uNamecheck.jsp?uName=" + uName.value,
		dataType: "HTML",
		success : function(data)
		{
			data = data.trim();
			if( data == "ERROR" ) 
			{ 
				alert("중복검사 오류가 발생했습니다."); return data; 
			}
			if( data == "DUPLICATE1" )
			{	// 아이디가 중복됨
				$(".msg_areaa").html("닉네임이 중복되었습니다.");
				CheckState = "2";
			}
			if( data == "NOT_DUPLICATE1" )
			{
				$(".msg_areaa").html("사용할 수 있는 닉네임입니다.");
				CheckState = "1";
			}
		}
	});
}

function mailcheck()
{
	let result = "";
	let uMail = document.querySelector("#uMail");	// 메일 입력폼 객체
	let msg_area = document.querySelector(".msg_area");	// 메세지 출력 영역 객체

	// 이메일이 4자 이상 입력되어 있지 않으면 중복체크를 하지 않는다
	if( uMail.value.length < 4 ) { return; }
	console.log(uMail.value);
	$.ajax({
		type : "get",
		url	 : "./mailcheck.jsp?uMail=" + uMail.value,
		dataType: "HTML",
		success : function(data)
		{
			data = data.trim();
			if( data == "MAIL_ERROR" ) 
			{ 
				alert("중복검사 오류가 발생했습니다."); return data; 
			}
			if( data == "DUPLICATE" )
			{	// 아이디가 중복됨
				$(".msg_area").html("이메일이 중복되었습니다.");
				CheckState = "2";
			}
			if( data == "NOT_DUPLICATE" )
			{
				$(".msg_area").html("사용할 수 있는 이메일입니다.");
				CheckState = "1";
			}
		}
	});
}