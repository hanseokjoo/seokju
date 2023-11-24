<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
// 업로드 경로 설정
String uploadpath = "C:\\Users\\pc\\Documents\\team\\trip_world_king\\WebContent\\upload";

// 업로드 파일 크기 설정
int fileSize= 10*1024*1024;		// 10MB
// 파라메타 인코딩 설정
request.setCharacterEncoding("UTF-8");
// 파라메타로 받는 키 / 값들을 일괄처리하는 search 객체 생성
SearchVo search = new SearchVo(request);
%>