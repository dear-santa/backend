package com.dearsanta.app.security;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class JwtResolver {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String getMemberIdByAccessToken(String accessToken) {
        String token = jwtTokenProvider.resolveBearerToken(accessToken);
        Claims claims = jwtTokenProvider.getClaims(token);
        String memberId = claims.get("memberId").toString();
        log.info("claims : " + claims + " memberId : " + memberId);

        return memberId;
    }
}
