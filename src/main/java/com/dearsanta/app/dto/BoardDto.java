package com.dearsanta.app.dto;

import com.dearsanta.app.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class BoardDto {
    private String id;
    private String title;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private String memberId;
    private String userNickname;
    private String userImgUrl;
    private int isMine;
    private String imgUrl;
    private int likeCount;
    private int viewCount;
    private int replyCount;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .memberId(memberId)
                .imgUrl(imgUrl)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .viewCount(replyCount)
                .build();
    }
}
