<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp" %>
        <link href="../css/mypage.css" type="text/css" rel="stylesheet">
        <%@ include file="../include/header.jsp" %>
        <section>
            <div class="mtitle">마이 페이지</div>
            <div class="mbtn">
                <a href="mypage.jsp" class="btn btn-secondary btn-sm">내 정보 관리</a>
                <a href="scrapViewer.jsp" class="btn btn-secondary btn-sm">스크랩 글 관리</a>
                <a href="myPost.jsp" class="btn btn-secondary btn-sm">작성 글 보기</a>
            </div>
            <div class="my">
                내 정보
            </div>
            <div class="info">
                <dl>
                    <dt>이메일</dt>
                    <dd>email@email.net</dd>
                    <dt>닉네임</dt>
                    <dd>이젠</dd>
                    <dt>관심분야</dt>
                    <dd>숙소</dd>
                    <dt>가입 날짜</dt>
                    <dd>2023-09-05</dd>
                </dl>
            </div>
            <a href="mypage_check.jsp" class="btn btn-outline-dark">회원 정보 수정</a>
            <a href="pw_modify.jsp" class="btn btn-outline-dark">비밀번호 재설정</a>
            <a href="mypage_check2.jsp" class="btn btn-outline-dark">탈퇴</a>
        </section>
        <%@ include file="../include/footer.jsp"%>