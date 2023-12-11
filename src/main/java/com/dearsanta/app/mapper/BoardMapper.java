package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    void createBoard(BoardRequestDto boardRequestDto);
    BoardDto getBoard(String boardId);
    void updateBoard(Board boardRequestDto);
    void deleteBoard(String boardId);
    List<BoardDto> getBoardListWithPaging(@Param("criteria")Criteria criteria);

    void increaseReplyCount(String boardId);
    void increaseViewCount(String boardId);
    void increaseLikeCount(String boardId);
    void decreaseLikeCount(String boardId);
    void boardLike(BoardLikeDto boardLikeDto);
    void boardUnlike(String likeId);
    String findLikeId(@Param("boardId") String boardId, @Param("userId") String userId);
}
