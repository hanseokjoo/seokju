 $(document).ready(function(){
			$("#userMail").focus();
		});
    	 // Dosubmit() 함수가 호출되었을때 아이디와 비번 입력값이 있는지 확인하고,값이 있으면 submit시킴
		function Dosubmit()
		{
    		
			//console.log("이메일 : "+$("#userMail").val());
			//console.log("비번 : "+$("#userPW").val());
			
			let uID = document.querySelector("#userMail");
			let uPW = document.querySelector("#userPW");
			if(uID.value=="")
				{
				alert("이메일을 입력하세요");
				uID.focus();
				return false;
				}else if(uPW.value=="")
				{
				alert("패스워드를 입력하세요");
				uPW.focus();
				return false;
				}
			// 값이 있으면 form을 submit시킴
			return true;
		}
 
	