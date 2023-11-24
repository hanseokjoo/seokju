<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../config/config.jsp" %>
<%@ include file="../include/head.jsp" %>
        <link href="../css/join.css" type="text/css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="../js/join.js"></script>
		<script src="../js/mailcheck.js"></script>
		<script src="../js/mailAuth.js"></script>
	    <%@ include file="../include/header.jsp" %>
        <section>
            <div style="font-size:1.5rem; font-weight:bold; text-align: center; margin-top: 20px;">회원가입</div>
            <form action="joinOk.jsp" method="post" id="join" name="join" onsubmit="return DoSubmit();">
                <table style="width:700px; border:0;">
                    <tr>
                        <td><p>닉네임</p></td>
                        <td>
                            <input type="text" name="uName" id="uName" onkeyup="namecheck();" placeholder="닉네임을 입력하세요" size="36px"> 
                            <span class="msg_areaa">2자 이상 입력해주세요</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p>비밀번호</p>
                            <p class="pc">(영문/숫자 조합, 8자~16자)</p>
                        </td>
                        <td>
                            <input type="password" name="uPW" id="uPW" placeholder="비밀번호를 입력하세요" size="36px">
                        </td>
                    </tr>
                    <tr>
                        <td><p>비밀번호 확인</p></td>
                        <td>
                            <input type="password" id="pwc" placeholder="비밀번호를 한 번 더 입력하세요" size="36px">
                        </td>
                    </tr>
                    <tr>
                        <td style="width:120px;"><p>e-mail</p></td>
                        <td>
                            <input type="email" name="uMail" id="uMail" onkeyup="mailcheck();" placeholder="e-mail을 입력하세요" size="36px">
                            <!-- <input type="button" id="check" value="중복체크" onclick="idcheck();"> -->
                            <input type="button" class="btn btn-dark btn-sm" value="인증번호 받기" onclick="Donumber();">
                            <div class="msg_area"></div>
                        </td>
                    </tr>
                    <tr>
                        <td><p>인증번호</p></td>
                        <td>
                            <input type="text" name="code" id="code" placeholder="인증코드를 입력하세요" size="36px">
                            <!-- <input type="button" id="check" value="중복체크" onclick="idcheck();"> -->
                        </td>
                    </tr>
                    <tr>
                        <td><p>관심분야</p></td>
                        <td>
                        	<label>
                            	<input type="radio" name="uInter" value="FR" checked>
                                 	없음
                            </label>
                            &nbsp;
                            <label>
                                <input type="radio" name="uInter" value="IN">
                                	숙소 
                            </label>
                            &nbsp;
                            <label>
                                <input type="radio" name="uInter" value="RE">
                                	맛집 
                            </label>
                            &nbsp;
                            <label>
                                <input type="radio" name="uInter" value="HP">
                                	핫플레이스
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td><p>자동가입방지</p></td>
					    <td>
                            <p><img src="Captcha.jsp"></p>
                            <input type="text" id="Ccode" placeholder="자동가입방지 코드를 입력하세요" size="36px">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="padding-left: 200px; padding-top: 20px;">
                            <input type="submit" class="btn btn-dark" value="가입하기">
                            <input type="reset" class="btn btn-dark" value="취소">
                        </td>
                    </tr>
                </table>
            </form>
        </section>
<%@ include file="../include/footer.jsp"%>