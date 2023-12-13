package com.dearsanta.app.dto;

import com.dearsanta.app.domain.Reply;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private String id;
    private String userId;
    private String userNickname;
    private String userImgUrl;
    private String boardId;
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private int isDeleted;

    public Reply toEntity() {
        return Reply.builder()
                .id(id)
                .userId(userId)
                .boardId(boardId)
                .content(content)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .isDeleted(isDeleted)
                .build();
    }
}
