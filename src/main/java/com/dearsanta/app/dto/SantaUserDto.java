package com.dearsanta.app.dto;


import com.dearsanta.app.domain.SantaUser;
import com.dearsanta.app.domain.enumtype.Nickname;
import com.dearsanta.app.domain.enumtype.OauthProvider;
import com.dearsanta.app.domain.enumtype.Role;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SantaUserDto {
    private String id;
    private String email;
    private String nickname;
    private String role;
    private String imgUrl;
    private String platform;
    private Date createdDate;
    private Date updatedDate;
    private Integer isDeleted;

    public SantaUser toEntity() {
        return SantaUser.builder()
                .id(UUID.randomUUID().toString())
                .email(this.getEmail())
                .nickname(Nickname.getRandomNickname())
                .role(Role.ROLE_USER.toString())
                .imgUrl(this.getImgUrl())
                .platform(OauthProvider.KAKAO.toString())
                .createdDate(this.getCreatedDate())
                .updatedDate(this.getUpdatedDate())
                .isDeleted(this.getIsDeleted())
                .build();
    }

    public SantaUser updateToEntity() {
        return SantaUser.builder()
                .id(this.getId())
                .nickname(this.getNickname())
                .imgUrl(this.getImgUrl())
                .updatedDate(this.getUpdatedDate())
                .build();
    }

    public SantaUser updateDeletedUserToEntity() {
        return SantaUser.builder()
                .id(this.getId())
                .isDeleted(this.getIsDeleted())
                .nickname(Nickname.getDeletedUserNickname())
                .updatedDate(this.getUpdatedDate())
                .build();
    }
}
