<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/write.css" type="text/css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script>
	function CheckSubmit()
	{	
		if( $("#type").val() == "게시판 선택" )
		{
			alert("게시판을 선택해주세요");
			$("#type").focus();
			return false;
		}
		return true;
	}
	
 	window.onload = function ()
	{
 		/*
		// 이미지 인풋 객체
		const inputImage = document.getElementById("input-multiple-image");
		// 이벤트 리스너
		inputImage.onchange = function() {
			// 이미지 객체 변경 함수 호출
			readMultipleImage(inputImage);
		};
		*/
		$("input:file").change(function(){
			readMultipleImage(this);
		});
	} 
	// 이미지 변경 함수
	function readMultipleImage(input) 
	{
    	const multipleContainer = document.getElementById("multiple-container")
	    // 인풋 태그에 파일들이 있는 경우
	    if(input.files) 
	    {
	        // 이미지 파일 검사 (생략)
	        console.log(input.files)
	        // 유사배열을 배열로 변환 (forEach문으로 처리하기 위해)
	        const fileArr = Array.from(input.files)
	        const $colDiv1 = document.createElement("div")
	        const $colDiv2 = document.createElement("div")
	        $colDiv1.classList.add("column")
	        $colDiv2.classList.add("column")
	        fileArr.forEach((file, index) => {
	            const reader = new FileReader()
	            const $imgDiv = document.createElement("div")   
	            const $img = document.createElement("img")
	            $img.classList.add("image")
	            const $label = document.createElement("label")
	            $label.classList.add("image-label")
	            $imgDiv.appendChild($img)
	            $imgDiv.appendChild($label)
	            reader.onload = e => {
	                $img.src = e.target.result
	                
	            }
	            
	            console.log(file.name)
	            if(index % 2 == 0) 
	            {
	                $colDiv1.appendChild($imgDiv)
	            } else {
	                $colDiv2.appendChild($imgDiv)
	            }
	            
	            reader.readAsDataURL(file)
	        })
	        multipleContainer.appendChild($colDiv1)
	        multipleContainer.appendChild($colDiv2)
	    }
	}
		
</script>
<%@ include file="../include/header.jsp" %>
<%
if(LoginVo == null)
{
	%>
	<script>
		alert("접근 권한이 없습니다")
		document.location="index.jsp";
	</script>
	<%
}
%>
  <section>
      <div style="font-size:1.5rem; font-weight:bold; text-align: center; margin-top: 20px; margin-bottom: 20px;">글쓰기</div>
      <div class="bd" id="bd">
          <form action="writeOk.jsp" method="post" id="write" onsubmit="return CheckSubmit();" enctype="multipart/form-data">
              <table>
                  <tr>
                      <td colspan="2">
                          <select name="type" id="type">
                              <option>게시판 선택</option>
                              <option value="IN" <%= (search.getType().equals("IN")) ? "selected" : "" %>>숙소게시판</option>
                              <option value="RE" <%= (search.getType().equals("RE")) ? "selected" : "" %>>맛집게시판</option>
                              <option value="HP" <%= (search.getType().equals("HP")) ? "selected" : "" %>>핫플레이스</option>
                              <option value="FR" <%= (search.getType().equals("FR")) ? "selected" : "" %>>자유게시판</option>
                              <option value="QA" <%= (search.getType().equals("QA")) ? "selected" : "" %>>QnA</option>
                          	<% if (LoginVo != null && LoginVo.getuLevel().equals("A")) 
                          		{
                          		%>
						    	<option value="NO" <%= (search.getType().equals("NO")) ? "selected" : "" %>>공지사항</option>
						    	<%
						    	} 
						    %>
                          </select>
                      </td>
                  </tr>
              </table>
              <input type="text" name="title" class="title" maxlength="100" placeholder="제목을 입력하세요">
              <!--  <img style="width: 100px; margin-left:8px;" id="preview-image" src="https://dummyimage.com/500x500/ffffff/000000.png&text=preview+image"> -->
              <div id="multiple-container"></div>
              <textarea style="width: 98%;" name="note" id="note" rows="18" placeholder="내용을 입력하세요"></textarea>
              <!-- 
              <input type="file" id="input-multiple-image" name="attach" class="attach" accept=".gif, .jpg, .png" multiple enctype="multipart/form-data"">
               -->
              <input type="file" id="input-multiple-image" name="attach1" class="attach" accept=".gif, .jpg, .png" enctype="multipart/form-data">
              <br>
              <input type="file" id="input-multiple-image" name="attach2" class="attach" accept=".gif, .jpg, .png" enctype="multipart/form-data">
              <br>
              <input type="file" id="input-multiple-image" name="attach3" class="attach" accept=".gif, .jpg, .png" enctype="multipart/form-data">
              <br>
              <input type="file" id="input-multiple-image" name="attach4" class="attach" accept=".gif, .jpg, .png" enctype="multipart/form-data">
              <br>
              <input type="file" id="input-multiple-image" name="attach5" class="attach" accept=".gif, .jpg, .png" enctype="multipart/form-data">                                          
              <p style="font-size:12px; margin-left:12px;">gif, jpg, png 확장자의 파일만 업로드 할 수 있습니다.</p>
                  <table class="writebtn">
                      <tr>
                          <td colspan="2" style="text-align:center;">
                              <a href="board.jsp" class="btn btn-dark">목록으로</a>
                              <input type="submit" value="글쓰기 완료" class="btn btn-primary">
                              <input type="reset" value="취소" class="btn btn-success">
                          </td>
                      </tr>
                  </table>
          </form>
      </div>
  </section>
  <%@ include file="../include/footer.jsp"%>
