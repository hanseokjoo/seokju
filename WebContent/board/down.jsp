<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ include file="../config/config.jsp" %>    
<%@ page import="java.net.URLEncoder" %>
<%
String phyName = request.getParameter("fname");

// 파일이 저장되어있는 경로
String fullPath = uploadpath + "\\" + phyName;
String fileName = URLEncoder.encode(phyName, "UTF-8").replaceAll("\\+", "%20");

// 응답하는 내용의 형태를 선언
response.setContentType("application/octet-stream; charset=EUC-KR");
// 파일을 원래 이름으로 받을 수 있게 원본 파일명을 셋팅
response.setHeader("Content-Disposition","attachment; filename=" + fileName);

// 다운로드 시킬 rename된 원본파일
File file = new File(fullPath);
FileInputStream fileIn = new FileInputStream(file);
ServletOutputStream ostream = response.getOutputStream();
byte[] outputByte = new byte[4096];
while(fileIn.read(outputByte,0,4096)!= -1)
{
	ostream.write(outputByte,0,4096);
}
fileIn.close();
ostream.flush();
ostream.close();
%>