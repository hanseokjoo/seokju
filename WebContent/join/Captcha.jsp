<%@ page language="java" contentType="image/jpeg" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="nl.captcha.Captcha" %>
<%@ page import="nl.captcha.Captcha.Builder" %>
<%@ page import="nl.captcha.backgrounds.FlatColorBackgroundProducer" %>
<%@ page import="java.awt.Color" %>
<%
// 자동 등록 방지 코드를 생성하고 이미지로 출력한다
Captcha mcaptcha;
Builder mBuilder;
String mAnswer;	// 코드

// 코드 이미지 생성을 위한 빌더 셋팅
mBuilder = new Captcha.Builder(303,50);
mBuilder.addText();
mBuilder.addBackground(new FlatColorBackgroundProducer(Color.WHITE));
mBuilder.addBorder();
mBuilder.addNoise();

// 빌더로부터 캡챠 객체 생성
mcaptcha = mBuilder.build();
mAnswer = mcaptcha.getAnswer();

// 이미지 스트림 생성
out.clear();
try
{
	OutputStream mOut = response.getOutputStream();
	ImageIO.write(mcaptcha.getImage(),"jpg",mOut);
	mOut.close();
	// 해당 이미지의 코드는 세션에 저장한다
	request.getSession().setAttribute("sign", mAnswer);
}catch(Exception e)
{
	e.printStackTrace();
}

%>
