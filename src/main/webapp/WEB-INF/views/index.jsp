<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>크리스마스의 소중한 추억을 나눠주세요</h1>
<h4>가장 기억에 남는 크리스마스 추억을 얘기해주세요.<br> 또, 올 크리스마스를 함께 할 음식, 장소, 선물을 공유해요.</h4>
<c:if test="${userId eq null}">
    <c:url var="kakaoAuthUrl" value="https://kauth.kakao.com/oauth/authorize">
        <c:param name="client_id" value="${clientId}" />
        <c:param name="redirect_uri" value="${redirectUri}" />
        <c:param name="response_type" value="code" />
    </c:url>
    <a href="${kakaoAuthUrl}">
        <img src="/resources/static/img/kakao_login_medium.png">
    </a>
</c:if>
<c:if test="${userId ne null}">
    <h1>로그인 성공입니다</h1>
    <input type="button" value="로그아웃" onclick="location.href='/logout'">
</c:if>
</body>
</html>
