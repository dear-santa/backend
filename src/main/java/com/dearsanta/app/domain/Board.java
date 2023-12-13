package com.dearsanta.app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Board {
    private String id;
    private String boardCategoryId;
    private String title;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private String memberId;
    private String imgUrl;
    private int likeCount;
    private int replyCount;
    private int viewCount;
    private int isDeleted;

    public void updateTitle (String title){
        this.title = title;
    }

    public void updateContent (String content){
        this.content = content;
    }

    public void updateImgUrl (String imgUrl){
        this.imgUrl = imgUrl;
    }

    public void update(Board updateBoard) {
        if (updateBoard.getTitle() != null) {
            updateTitle(updateBoard.getTitle());
        }
        if (updateBoard.getContent() != null) {
            updateContent(updateBoard.getContent());
        }
        if (updateBoard.getImgUrl() != null) {
            updateImgUrl(updateBoard.getImgUrl());
        }
    }
}