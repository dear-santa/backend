package com.dearsanta.app.domain.enumtype;

public enum Platform {
    GOOGLE("google-"), KAKAO("kakao-");

    private String header;

    Platform(String header) {
        this.header = header;
    }

    public String addOAuthTypeHeaderWithEmail(String email) {
        return this.header + email;
    }
}
