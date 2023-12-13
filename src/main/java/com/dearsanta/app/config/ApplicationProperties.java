package com.dearsanta.app.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationProperties {

    private String FRONT_LOCAL_URL;
    private String DOMAIN;
    private String KAKAO_REDIRECT_URI;
    private String KAKAO_CLIENT_ID;
}