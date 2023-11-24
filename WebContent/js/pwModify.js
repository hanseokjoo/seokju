$(document).ready(function(){
	$("#curPW").focus();
});

let flag = false;
let isDuple = false;
let CheckState = "0";	// 0 :: 체크하지 않음  / 1 :: 사용가능 / 2 :: 중복

function DoSubmit()
{
	if( flag == true) { return; }

	// 1. 비밀번호가 입력되었는가
	let curPW  = document.getElementById("curPW"); 
	if( curPW.value.length < 3 )
	{
		curPW.focus();
		return false;
	}
//	console.log(curPW.value)
	// 2. 새 비밀번호가 입력되었는가
	let newPW  = document.getElementById("newPW");
	let conPW = $("#conPW"); 
	if( newPW.value.length < 8 )
	{
		alert("새 비밀번호는 8자 이상 입력해주세요");
		newPW.focus();
		return false;
	}

	// 2-1. 비번이 입력되어 있다면, 비번 확인이 입력되어 있는가
	if( conPW.val() == "" )
	{
		alert("비밀번호 확인을 입력해주세요");
		conPW.focus();
		return false;
	}
	if( newPW.value != conPW.val() )
	{
		alert("비밀번호가 일치하지 않습니다");
		newPW.value = "";
		conPW.val("");
		newPW.focus();
		return false;
	}
	
	console.log("필수 항목이 모두 입력되었습니다");
	alert("비밀번호가 모두 입력되었습니다.");
}