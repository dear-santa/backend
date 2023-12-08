package com.dearsanta.app.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private String id;
    private String boardCategoryId;
    private String title;
    private String content;
    private String userId;
    private String imgUrl;
}
