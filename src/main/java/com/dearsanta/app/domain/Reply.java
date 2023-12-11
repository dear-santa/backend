package com.dearsanta.app.domain;

import lombok.Builder;

import java.util.Date;

@Builder
public class Reply {
    private String id;
    private String userId;
    private String boardId;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private int isDeleted;

    public void updateContent (String content) {
        this.content = content;
    }
}
