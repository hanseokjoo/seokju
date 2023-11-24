<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="utf-8"%>
<%@ include file="../config/config.jsp" %>    
<%@ page import="util.*" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

//1. 닉네임과 메일주소를 받는다.
String uName = request.getParameter("uName");	// 닉네임
String uMail = request.getParameter("uMail");	// 받을 주소
if(uName == null || uMail == null)
{
	out.println("닉네임 또는 메일주소가 누락되었습니다.");
	return;
}

//2. DB에서 닉네임과 메일주소가 일치하는지 검사한다.
MemberDao dao = new MemberDao(search);
MemberVo  vo  = dao.mRead(uMail,uName);
if(vo == null)
{
	out.println("등록된 닉네임 또는 메일주소가 아닙니다.");
	return;
}
//3. 만약 일치하면 임시 비밀번호를 생성한다.
vo.setuPW(MemberDao.GetRandomPW());

//4. 임시 비밀번호를 DB에 업데이트 한다.
dao.UpdatePW(vo);

//5. 임시 비밀번호를 메일로 발송한다.
dao.SendMail(vo);

out.println("임시 비밀번호를 메일로 발송하였습니다. \n * 메일 도착까지 시간이 소요될 수 있습니다. *");
/*
String from = "ezen@ezen.com";				// 보내는 주소
String tto = request.getParameter("tto");	// 받을 주소
System.out.println("받을 메일 주소 : " + tto);

//String ID = "아이디";						// 계정
//String PW = "비번";							// 계정인증
String ID = "nalahan561@gmail.com";			// 계정
String PW = "lodlsvnruhweongs";				// 계정인증
String title = request.getParameter("title");
String note  = request.getParameter("note");

MailAuth2 ma = new MailAuth2();
// out.print(ma.sendAuthMail(to));
String code = ma.AuthPWMail(tto);
// 이메일로 발송한 코드를 세션으로 받기
session.setAttribute("authPW", code);
*/
%>