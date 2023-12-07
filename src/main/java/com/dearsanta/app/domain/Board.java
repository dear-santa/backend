package com.dearsanta.app.domain;

import com.dearsanta.app.dto.BoardDetailDto;
import lombok.Getter;

import java.util.Date;

@Getter
public class Board {
    private String id;
    private String boardCategoryId;
    private String title;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private String userId;
    private String imgUrl;
    private int likeCount;
    private int replyCount;
    private int viewCount;
    private int isDeleted;

    public BoardDetailDto toDTO() {
        return BoardDetailDto.builder()
                .id(this.id)
                .title(this.getTitle())
                .content(this.getContent())
                .createdDate(this.getCreatedDate())
                .updatedDate(this.getUpdatedDate())
                .userId(this.getUserId())
                .imgUrl(this.getImgUrl())
                .likeCount(this.getLikeCount())
                .viewCount(this.getViewCount())
                .build();
    }
}
