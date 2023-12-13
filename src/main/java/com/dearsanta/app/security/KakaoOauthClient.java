package com.dearsanta.app.security;

import com.dearsanta.app.config.ApplicationProperties;
import com.dearsanta.app.security.dto.KakaoOauthTokenDto;
import com.dearsanta.app.security.dto.KakaoUserInfoDto;
import com.dearsanta.app.security.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Log4j
@Service
@RequiredArgsConstructor
public class KakaoOauthClient {

    private final String REQUEST_URI = "https://kapi.kakao.com/v2/user/me";
    private final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private final String BEARER_HEADER = "Bearer ";
    private final String ACCESSTOKEN_HEADER = "Authorization";

    @Autowired
    private ApplicationProperties properties;
    private RestTemplate restTemplate = new RestTemplate();

    public String getEmail(LoginRequestDto loginRequestDto) {
        String authorizationCode = loginRequestDto.getToken();
        KakaoOauthTokenDto dto = getKakaoAccessTokenByAuthorazationCode(authorizationCode);
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfoByKakaoAccessToken(dto.getAccessToken());

        String email =  kakaoUserInfoDto.getEmail();
        if (email == null) {
            throw new NoSuchElementException("해당하는 사용자가 존재하지 않습니다.");
        }
        return email;
    }

    private KakaoOauthTokenDto getKakaoAccessTokenByAuthorazationCode(String authorizeCode) {
        log.info("getKakaoOauthToken() - 프론트에서 받은 인가 코드로 accessToken 발급받기");
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.getKAKAO_CLIENT_ID());
        body.add("redirect_uri", properties.getKAKAO_REDIRECT_URI());
        body.add("code", authorizeCode);

        HttpEntity<?> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(TOKEN_URI, request, KakaoOauthTokenDto.class);
    }

    // 2. 토큰으로 사용자 정보 조회하기
    private KakaoUserInfoDto getKakaoUserInfoByKakaoAccessToken(String accessToken) {
        log.info("getKakaoUserInfoByKakaoAccessToken() - 인가 코드로 토큰 발급받은 이후 사용자 정보 조회하기");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(ACCESSTOKEN_HEADER, BEARER_HEADER + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        return restTemplate.postForObject(REQUEST_URI, request, KakaoUserInfoDto.class);
    }
}