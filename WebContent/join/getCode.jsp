<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%
out.print(request.getSession().getAttribute("authcode"));
%>