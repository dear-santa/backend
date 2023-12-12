package com.dearsanta.app.auth;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private OAuthService oauthService;

//    @PostMapping ("/oauth/kakao")
//    public ResponseEntity<String> handleKakaoLogin(@RequestBody KakaoParams kakaoParams) {
//
//        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());
//
//        //JWT 토큰 발급
//        String accessToken = oauthService.getMemberByOauthLogin(kakaoParams);
//        //응답 헤더 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("accessToken", accessToken);
//
//        return ResponseEntity.ok().headers(headers).body("Response with header using ResponseEntity");
//    }

    @GetMapping ("/oauth/kakao")
    public ResponseEntity<String> handleKakaoLogin(@RequestParam("code") String code) {

        log.debug("넘겨받은 Kakao 인증키 :: " + code);

        KakaoParams kakaoParams = new KakaoParams();
        kakaoParams.setAuthorizationCode(code);
        //JWT 토큰 발급
        String accessToken = oauthService.getMemberByOauthLogin(kakaoParams);

        //응답 헤더 생성
        HttpHeaders headers = new HttpHeaders();

        headers.set("accessToken", accessToken);

        return ResponseEntity.ok().headers(headers).body("반갑습니다");
    }



}
