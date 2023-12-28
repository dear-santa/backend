package com.dearsanta.app.security;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String bearerHeader;
    private String accessTokenHeader = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("JwtInterceptor - preHandle(): 로그인 시도");
        String accessToken = request.getHeader("Authorization");
        String memberId = getMemberIdByAccessToken(accessToken);

        // request에 memberId를 저장
        request.setAttribute("memberId", memberId);
        RequestContextHolder.currentRequestAttributes().setAttribute("memberId", memberId, RequestAttributes.SCOPE_REQUEST);
        return true;
    }

    public String getMemberIdByAccessToken(String accessToken) {
        log.info("getMemberIdByAccessToken() accessToken : " + accessToken);
        String token = jwtTokenProvider.resolveBearerToken(accessToken);
        log.info("resolveBearerToken() accessToken : " + token);
        Claims claims = jwtTokenProvider.getClaims(token);
        String memberId = claims.get("memberId").toString();
        log.info("claims : " + claims + " memberId : " + memberId);

        return memberId;
    }
}
