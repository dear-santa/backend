package com.dearsanta.app.domain;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private String id;
    private String email;
    private String nickname;
    private String role;
    private String imgUrl;
    private String platform;
    private String refreshToken;
    private Date createdDate;
    private Date updatedDate;
    private Integer isDeleted;

    public Member toDto() {
        return Member.builder()
                .id(getId())
                .email(getEmail())
                .nickname(getNickname())
                .role(getRole())
                .imgUrl(getImgUrl())
                .platform(getPlatform())
                .createdDate(getCreatedDate())
                .updatedDate(getUpdatedDate())
                .isDeleted(getIsDeleted())
                .build();
    }
}