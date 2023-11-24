<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="util.*" %>
<%
request.setCharacterEncoding("utf-8");

String from = "ezen@ezen.com";				// 보내는 주소
String to = request.getParameter("to");		// 받을 주소
System.out.println("받을 메일 주소 : " + to);

//String ID = "아이디";						// 계정
//String PW = "비번";							// 계정인증
String ID = "nalahan561@gmail.com";			// 계정
String PW = "lodlsvnruhweongs";				// 계정인증
String title = request.getParameter("title");
String note  = request.getParameter("note");

MailAuth ma = new MailAuth();
// out.print(ma.sendAuthMail(to));
String code = ma.sendAuthMail(to);
// 이메일로 발송한 코드를 세션으로 저장
session.setAttribute("authcode", code);
%>