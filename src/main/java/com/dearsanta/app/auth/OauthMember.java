package com.dearsanta.app.auth;

import com.dearsanta.app.domain.enumtype.OauthProvider;

public interface OauthMember {
    public String getEmail();
    OauthProvider getOauthProvider();
}
