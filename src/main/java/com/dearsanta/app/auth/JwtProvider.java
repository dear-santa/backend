package com.dearsanta.app.auth;

import com.dearsanta.app.config.ApplicationProperties;
import com.dearsanta.app.domain.SantaUser;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Log4j
@Setter
@Getter
@Component
public class JwtProvider {


    private ApplicationProperties applicationProperties;
    /*
     * 토큰 생성 메소드 jwt에 저장할 회원정보를 파라미터로 전달
     */
    public String createToken(String userId) {
        Date now = new Date(System.currentTimeMillis());
        Long expiration = 1000 * 60 * 60L; //만료기한 설정 시 사용

        /* 토큰이 보관할 회원ID */
        Claims claims = Jwts.claims();
        claims.put("memberId", userId); //비공개 클레임 등록

        return Jwts.builder().setHeaderParam("typ", "JWT") // 토큰 타입 지정
                .setSubject("accessToken") // 토큰 제목
                .setIssuedAt(now) // 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 만료기한
                .setClaims(claims) // 회원 아이디 저장(비공개 클레임)
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getSECRET_KEY()) //해싱알고리즘과 시크릿 키
                .compact(); //토큰 직렬화
    }

    /* 토큰 해석 메소드 */
    public String getSubject(String token) throws ExpiredJwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(applicationProperties.getSECRET_KEY()))
                .parseClaimsJwt(token) //토큰 파싱
                .getBody();
        log.debug("해독된 토큰:: " + claims.getSubject());
        return claims.getSubject();
    }

    /* 유효성 확인(해독된 jwt) */
    public boolean vaildToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary( applicationProperties.getSECRET_KEY()))
                    .parseClaimsJwt(jwt) //해독된 토큰 파싱
                    .getBody();
            return true;  //유효하다면 true 반환
        } catch (Exception e) {
            log.debug("유효하지 않은 토큰입니다.");
            return false; //유효하지 않다면 false 반환
        }
    }

}
