package com.dearsanta.app.service;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.BoardDetailDto;
import com.dearsanta.app.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardMapper boardMapper;

    @Override
    public BoardDetailDto getBoardDetail(String boardId) {
        Board boardDetail = boardMapper.getBoardDetail(boardId);
        BoardDetailDto boardDetailDto = boardDetail.toDTO();
        return boardDetailDto;
    }
}
