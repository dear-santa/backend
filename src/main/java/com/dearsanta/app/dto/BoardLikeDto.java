package com.dearsanta.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class BoardLikeDto {
    private String id;
    private String userId;
    private String boardId;
}
