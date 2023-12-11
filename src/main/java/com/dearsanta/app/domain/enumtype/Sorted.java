package com.dearsanta.app.domain.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sorted {
    LATEST("PK_BOARD_CREATED_DATE"),
    VIEW_COUNT("PK_BOARD_VIEW_COUNT"),
    REPLY_COUNT("PK_BOARD_REPLY_COUNT"),
    LIKE_COUNT("PK_BOARD_LIKE_COUNT"),
    REPLY_LATEST("PK_REPLY_CREATED_DATE");

    private final String indexColumn;
}