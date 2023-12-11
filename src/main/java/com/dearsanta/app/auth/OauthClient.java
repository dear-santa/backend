package com.dearsanta.app.auth;

import com.dearsanta.app.domain.enumtype.OauthProvider;

// 로그인 형태에 따른 동작을 위한 인터 페이스
public interface OauthClient {
    public OauthProvider oauthProvider();
    public String getOauthLoginToken(OauthParams oauthParams);
    public OauthMember getMemberInfo(String accessToken);
}
