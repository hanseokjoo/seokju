<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<%
	MultipartRequest multi =
	new MultipartRequest(
		request,
		uploadpath,		// 파일을 저장할 경로
		fileSize,			// 파일의 최대 크기 설정
		"UTF-8",		// form에서 넘어온 데이터의 인코딩
		new DefaultFileRenamePolicy()	// 파일 저장시, 파일명 변경에 대한 기본 정책
		);
	// multipart로 넘어온 파라메타를 받는다
	String pTitle    = multi.getParameter("title");
	String pType     = multi.getParameter("type");
	String pContent  = multi.getParameter("note");
	String pageNo    = multi.getParameter("page");
	String pNo       = multi.getParameter("pNo");  // 게시글 번호 확인용	
	String uNo       = multi.getParameter("uNo");  // 권한 확인
	/*
	System.out.println("pTitle:"  + pTitle);
	System.out.println("pType:"  + pType);
	System.out.println("pContent:"  + pContent);
	System.out.println("pageNo:"  + pageNo);
	System.out.println("pNo:"  + pNo);
	System.out.println("uNo:"  + uNo);
	*/
	// 유효성 체크를 한다
	if( pTitle==null || pTitle.equals("") || pType==null || pType.equals("") || pContent==null || pContent.equals("") || pNo==null || pNo.equals("") || uNo==null || uNo.equals(""))
	{	// 유효하지 않은 접근
		//response.sendRedirect("board.jsp");		
		return;
	}
	// 게시글 정보를 담은 vo를 생성한다
	BoardDao bDao = new BoardDao();	
	BoardVo vo = new BoardVo();
	vo.setpTitle(pTitle);
	vo.setpType(pType);
	vo.setpContent(pContent);
	vo.setpNo(pNo);
	vo.setuNo(uNo);

	// 첨부파일 바꾸기
	String newFileName = "";
	String fileName    = "";

	// multipart로 파일이 넘어왔다면 파일의 이름을 받아온다
	fileName = (String)multi.getFilesystemName("attach");
	if(fileName != null)
	{
		// 새로운 파일명으로 사용할 문자열을 생성
		AttachDao aDao = new AttachDao();
		aDao.setAttach(uploadpath, fileName, pNo);
		// DB에서 기존 첨부파일을 삭제한다
		aDao.DeleteAll(pNo);
		// 새로운 첨부 파일을 게시글 vo에 추가한다
		vo.setaList(aDao.getArrayList());
	}
	// 게시글을 vo를 db에 업데이트한다
	bDao.Update(vo);
%>
        <link href="../css/index.css" type="text/css" rel="stylesheet">
        <%@ include file="../include/header.jsp" %>
        <section style="background-color:white; height:700px" >
			<!--<div class="container-sm" style="background-color:black; height:300px; width:400px; padding:50px"> -->
			<div class="container-sm" style="background-color:white; text-align: center;">
			<br><br><br>
			수정이 완료되었습니다
			
			<br><br><br>
			
				<a href="board.jsp" class="btn btn-primary btn-sm">목록으로</a>
				<a href="write.jsp" class="btn btn-success btn-sm">새글쓰기</a>
				<a href="view.jsp?no=<%=pNo %>" class="btn btn-success btn-sm">글보기</a>
			</div>
        </section>
<%@ include file="../include/footer.jsp"%>