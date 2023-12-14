package com.dearsanta.app.dto;

import com.dearsanta.app.domain.Board;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequestDto {
    private String id;
    private String boardCategoryId;
    private String title;
    private String content;
    private String memberId;
    private String imgUrl;
    private String mainCategory;
    private String subCategory;
    public Board toEntity() {
        return Board.builder()
                .id(id)
                .boardCategoryId(boardCategoryId)
                .title(title)
                .content(content)
                .memberId(memberId)
                .imgUrl(imgUrl)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .build();
    }
}
