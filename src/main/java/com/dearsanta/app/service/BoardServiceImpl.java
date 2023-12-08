package com.dearsanta.app.service;

import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardMapper boardMapper;

    @Override
    public void createBoard(BoardRequestDto boardRequestDto) {
        boardRequestDto.setId(UUID.randomUUID().toString());
        boardMapper.createBoard(boardRequestDto);
    }

    @Override
    public BoardDto getBoard(String boardId) {
        BoardDto board = boardMapper.getBoard(boardId);
        return board;
    }
}
