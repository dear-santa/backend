package com.dearsanta.app.dto;

import com.dearsanta.app.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class BoardDto {
    private String id;
    private String title;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private String memberId;
    private String imgUrl;
    private int likeCount;
    private int viewCount;

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
                .build();
    }
}
