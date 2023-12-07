package com.dearsanta.app.mapper;

import com.dearsanta.app.dto.BoardDto;

public interface BoardMapper {
    BoardDto getBoard(String boardId);
}
