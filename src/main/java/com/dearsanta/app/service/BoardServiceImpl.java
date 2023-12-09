package com.dearsanta.app.service;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.mapper.BoardMapper;
import com.dearsanta.app.util.AWSS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    AWSS3 aWSS3;

    @Override
    public void createBoard(BoardRequestDto boardRequestDto, MultipartFile boardImage) {
        boardRequestDto.setId(UUID.randomUUID().toString());
        if (boardImage != null) {
            String imgUrl= aWSS3.uploadImage(boardRequestDto.getId(), boardImage);
            boardRequestDto.setImgUrl(imgUrl);
        }
        boardMapper.createBoard(boardRequestDto);
    }

    @Override
    public BoardDto getBoard(String boardId) {
        BoardDto board = boardMapper.getBoard(boardId);
        if (board == null) {
            throw new NoSuchElementException("존재하지 않는 게시글입니다.");
        }
        return board;
    }

    @Override
    public void updateBoard(String boardId, BoardRequestDto boardRequestDto) {
        Board board = boardMapper.getBoard(boardId).toEntity();
        Board updateBoard = boardRequestDto.toEntity();
        board.update(updateBoard);
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(String boardId) {
        boardMapper.deleteBoard(boardId);
    }
}
