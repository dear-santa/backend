package com.dearsanta.app.auth;

import com.dearsanta.app.config.ApplicationProperties;
import com.dearsanta.app.domain.enumtype.OauthProvider;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Log4j
@Component
public class KakaoClient implements OauthClient{

    private static final String GRANT_TYPE = "authorization_code";

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public OauthProvider oauthProvider() {
        return OauthProvider.KAKAO;
    }

    @Override
    public String getOauthLoginToken(OauthParams oauthParams) {
        String url = applicationProperties.getKAKAO_TOKEN_URL();
        log.debug("전달할 code:: " + oauthParams.getAuthorizationCode());

        RestTemplate rt = new RestTemplate();
        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 바디 생성
        MultiValueMap<String, String> body = oauthParams.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", applicationProperties.getKAKAO_CLIENT_ID());
        body.add("redirect_uri", applicationProperties.getKAKAO_REDIRECT_URI());

        // 헤더와 바디 합체
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity(body, headers);
        log.debug("현재 httpEntity 상태:: " + tokenRequest);

        // 토큰 수신
        KakaoToken accessToken = rt.postForObject(url, tokenRequest, KakaoToken.class);
        log.debug("accessToken :: " + accessToken);

        return accessToken.getAccess_token();
    }

    public OauthMember getMemberInfo(String accessToken) {
        String url = applicationProperties.getKAKAO_USER_URL();

        log.debug("넘어온 토큰은:: " + accessToken);

        // 요청 객체 생성
        RestTemplate rt = new RestTemplate();

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + accessToken); // accessToken 정보 전달

        // 바디 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys",  "[\"kakao_account.email\"]");

        // 헤더 + 바디 조합
        HttpEntity<MultiValueMap<String, String>> infoRequest = new HttpEntity<>(body, headers);

        //요청 반환데이터를 메소드 리턴값으로 반환
        return rt.postForObject(url, infoRequest, KakaoMember.class);
    }
}
