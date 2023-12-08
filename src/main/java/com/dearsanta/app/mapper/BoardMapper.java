package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;

public interface BoardMapper {
    void createBoard(BoardRequestDto boardRequestDto);
    BoardDto getBoard(String boardId);
    void updateBoard(Board boardRequestDto);
    void deleteBoard(String boardId);
}
