package com.dearsanta.app.mapper;

import com.dearsanta.app.dto.BoardDetailDto;

public interface BoardMapper {
    BoardDetailDto getBoardDetail(String boardId);
}
