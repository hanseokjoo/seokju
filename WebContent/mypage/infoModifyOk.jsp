<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
<link href="../css/index.css" type="text/css" rel="stylesheet">
        <%@ include file="../include/header.jsp" %>
<%
	
	String uMail  = request.getParameter("uMail");
	String uName  = request.getParameter("uName");
	String uInter = request.getParameter("uInter");
	String uNo    = request.getParameter("uNo");  // 권한 확인
	
	System.out.println("uMail:"  + uMail);
	System.out.println("uName:"  + uName);
	System.out.println("uInter:"  + uInter);
	System.out.println("uNo:"  + uNo);

	
	// 유효성 체크를 한다
	if( uMail == null || uMail.equals("") || uName == null || uName.equals("") || 
		uInter == null || uInter.equals("") || uNo == null || uNo.equals(""))
	{	// 유효하지 않은 접근
		response.sendRedirect("../board/index.jsp");		
		return;
	}
	// 회원 정보를 담은 vo를 생성한다
	MemberDao dao	= new MemberDao(search);	
	MemberVo vo		= new MemberVo();
	vo.setuMail(uMail);
	vo.setuName(uName);
	vo.setuInter(uInter);
	vo.setuNo(uNo);


	// 회원의 vo를 db에 업데이트한다
	dao.Update(vo);
%>
        <section style="background-color:white; height:700px" >
			<!--<div class="container-sm" style="background-color:black; height:300px; width:400px; padding:50px"> -->
			<div class="container-sm" style="background-color:white; text-align: center;">
			<br><br><br>
			수정이 완료되었습니다
			
			<br><br><br>
				<a href="mypage.jsp?uNo=<%= uNo %>&type=MI" class="btn btn-dark">마이페이지</a>
				<a href="../board/index.jsp?uNo=<%= uNo %>" class="btn btn-dark">메인으로</a>
			</div>
        </section>
        <%@ include file="../include/footer.jsp"%>