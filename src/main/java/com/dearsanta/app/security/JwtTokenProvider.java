package com.dearsanta.app.security;

import com.dearsanta.app.security.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class JwtTokenProvider {

    private String jwtIssuer = "dearsanta";
    private String bearerHeader = "Bearer ";
    private String jwtSecretKey = "tm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpAtm4zrpa3tlZzqsJzrsJzsnpfvbnoperufgvjlsdnvkjhwfhDrpbzrp4zrk6TslrTqsID";
    private long accessTokenValidity = 10 * 24 * 60 * 60 * 1000;
    private long refreshTokenValidity = 10 * 24 * 60 * 60 * 1000;
    private SecretKey secretKey;
    private String MEMBER_ID = "memberId";

    public JwtTokenProvider() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public TokenDto createAccessAndRefreshTokenDto(String memberId) {
        String accessToken = createBearerTokenWithValidity(memberId, accessTokenValidity);
        String refreshToken = createBearerTokenWithValidity(memberId, refreshTokenValidity);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createBearerTokenWithValidity(String memberId, long tokenValidity){
        String accessToken = createTokenWithValidity(memberId, tokenValidity);
        return createBearerHeader(accessToken);
    }

    private String createTokenWithValidity(String memberId, long tokenValidity){
        Date now = new Date(System.currentTimeMillis());
        Date expirationAt = new Date(now.getTime() + accessTokenValidity);
        log.info("createTokenWithValidity() : " + expirationAt);

        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .setIssuer(jwtIssuer)
                .setIssuedAt(now)
                .setExpiration(expirationAt)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException e) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Claims getClaims(String token) {

        return Jwts.parser()
                .requireIssuer(jwtIssuer)
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String createBearerHeader (String token) {
        return bearerHeader + token;
    }

    public String resolveBearerToken(String token) {
        if (token != null && token.startsWith(bearerHeader)) {
            return token.substring(bearerHeader.length());
        }
        throw new NoSuchElementException("토큰 형식이 잘못되었습니다. " + token);
    }

    public Long getExpirationTime(String accessToken) {
        Date expiration = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        long now = new Date().getTime();
        return expiration.getTime() - now;
    }
}
