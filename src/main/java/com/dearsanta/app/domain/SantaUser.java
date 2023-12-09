package com.dearsanta.app.domain;

import com.dearsanta.app.dto.SantaUserDto;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SantaUser {


    private String id;
    private String email;
    private String nickname;
    private String role;
    private String imgUrl;
    private String platform;
    private Date createdDate;
    private Date updatedDate;
    private Integer isDeleted;

    public static SantaUser toEntity(SantaUserDto dto) {
        return SantaUser.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .role(dto.getRole())
                .imgUrl(dto.getImgUrl())
                .platform(dto.getPlatform())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .isDeleted(dto.getIsDeleted())
                .build();
    }

    public static SantaUserDto toDto(SantaUser entity) {
        return SantaUserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .role(entity.getRole())
                .imgUrl(entity.getImgUrl())
                .platform(entity.getPlatform())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .isDeleted(entity.getIsDeleted())
                .build();
    }
}
