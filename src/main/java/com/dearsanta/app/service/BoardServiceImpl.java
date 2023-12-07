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
        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .id(boardDetail.getId())
                .title(boardDetail.getTitle())
                .content(boardDetail.getContent())
                .createdDate(boardDetail.getCreatedDate())
                .updatedDate(boardDetail.getUpdatedDate())
                .userId(boardDetail.getUserId())
                .imgUrl(boardDetail.getImgUrl())
                .likeCount(boardDetail.getLikeCount())
                .viewCount(boardDetail.getViewCount())
                .build();
        return boardDetailDto;
    }
}
