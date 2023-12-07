<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
${CLIENTID}
${clientId}
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