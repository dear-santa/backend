package com.dearsanta.app.dto;

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
    private String userId;
    private String imgUrl;
    private int likeCount;
    private int viewCount;
}
