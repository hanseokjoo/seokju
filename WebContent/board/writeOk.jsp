<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp" %>
<link href="../css/write.css" type="text/css" rel="stylesheet">
<%@ include file="../include/header.jsp" %>
<%@ include file="../config/config.jsp" %>
<section style="background-color:white; height:700px;">
		<div class="container-sm" style="background-color:white; text-align: center;">
		<br><br><br>
<%
MultipartRequest multi = new MultipartRequest(
		request,
		uploadpath, /* 파일을 저장할 경로 */
		fileSize, /* 파일의 최대 크기 설정 */
		"UTF-8", /* form에서 넘어온 데이터의 인코딩 */
		new DefaultFileRenamePolicy()	/* 파일명 변경 기본 정책 */ );


//multipart로 넘어온 파라메타를 받는다
String Ptitle	 = multi.getParameter("title");
String pType = multi.getParameter("type");
String pContent	 = multi.getParameter("note");

System.out.printf("writeOk.jsp: title = [%s]\r\n", Ptitle);
System.out.printf("writeOk.jsp: type = [%s]\r\n", pType);
System.out.printf("writeOk.jsp: note = [%s]\r\n", pContent);

//유효성 검사
if( LoginVo   == null || LoginVo.getuNo().equals("")   ||	// 로그인 여부 확인
	Ptitle	  == null || Ptitle.equals("")	  ||			// 제목   입력 확인
	pType	  == null || pType.equals("") ||				// 게시판 선택 확인
	pContent  == null || pContent.equals("")	)			// 내용   입력 확인
{	// 글쓰기 페이지로 보냄
	response.sendRedirect("write.jsp?type=" + type); return;
}


//게시글 정보를 담을 vo와, DB에 보낼 dao를 생성
BoardDao bDao = new BoardDao();
BoardVo vo = new BoardVo();

vo.setuNo(LoginVo.getuNo());			// 작성자 번호

vo.setpTitle(Ptitle);					// 제목
vo.setpType(pType);						// 게시판 종류
vo.setpContent(pContent);				// 게시글 내용

//파일에 사용할 변수 설정
String newFileName = "";				// 변경된 파일 이름
String fileName = "";					// 원본 파일 이름

Enumeration files = multi.getFileNames();

AttachDao aDao = new AttachDao();

while(files.hasMoreElements()) {
	String file = (String) files.nextElement();
	String file_name = multi.getFilesystemName(file);
	//중복된 파일을 업로드할 경우 파일명이 바뀐다.
	String ori_file_name = multi.getOriginalFileName(file);
	out.print("<br> 업로드된 파일명: " + file_name);
	out.print("<br> 원본 파일명: " + ori_file_name);
	out.print("<hr>");
	if(file_name != null)
	{
		aDao.setAttach(uploadpath, file_name, "");
		// 새로운 첨부 파일을 게시글 vo에 추가한다
			
	}
}

vo.setaList(aDao.getArrayList());

/*
//multipart로 파일이 넘어왔다면, 파일의 이름을 받아온다
fileName = (String)multi.getFilesystemName("attach");
if( fileName != null)
{	// 새로운 파일명으로 사용할 문자열을 생성
	// 원본파일명으로, AttachVo에 물리 파일명을 셋팅한다
			AttachDao aDao = new AttachDao();
			aDao.setAttach(uploadpath, fileName, "");
			// 새로운 첨부 파일을 게시글 vo에 추가한다
			vo.setaList(aDao.getArrayList());
}
*/

//게시글 vo를 DB에 업데이트한다
boolean ret_insert = bDao.Insert(vo);
%>


<script>
	alert("게시글이 작성되었습니다");
	document.location = "view.jsp?no=<%= vo.getpNo()%>&type=<%= vo.getpType() %>";
</script>

