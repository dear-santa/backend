package com.dearsanta.app.domain;

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
}
