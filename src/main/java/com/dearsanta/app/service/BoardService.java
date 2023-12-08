package com.dearsanta.app.service;

import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;

public interface BoardService {
    void createBoard(BoardRequestDto boardRequestDto);
    BoardDto getBoard(String boardId);
}
