package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.dto.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    void createBoard(BoardRequestDto boardRequestDto);
    BoardDto getBoard(String boardId);
    void updateBoard(Board boardRequestDto);
    void deleteBoard(String boardId);
    List<BoardDto> getBoardListWithPaging(@Param("criteria")Criteria criteria);

    void increaseReplyCount(String boardId);
}
