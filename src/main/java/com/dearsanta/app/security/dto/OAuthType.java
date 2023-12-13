package com.dearsanta.app.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuthType {
    APPLE, KAKAO;
}