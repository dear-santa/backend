package com.dearsanta.app.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationProperties {

    private String FRONT_LOCAL_URL;
    private String DOMAIN;

    private String SECRET_KEY;
    private String KAKAO_CLIENT_ID;
    private String KAKAO_REDIRECT_URI;
    private String KAKAO_TOKEN_URL;
    private String KAKAO_USER_URL;
}