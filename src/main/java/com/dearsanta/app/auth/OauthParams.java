package com.dearsanta.app.auth;

import com.dearsanta.app.domain.enumtype.OauthProvider;
import org.springframework.util.MultiValueMap;

public interface OauthParams {
    public OauthProvider oauthProvider();
    public String getAuthorizationCode();
    public MultiValueMap<String, String> makeBody();
}
