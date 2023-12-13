package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.*;
import com.dearsanta.app.dto.criteria.BoardCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    void createBoard(BoardRequestDto boardRequestDto);
    BoardDto getBoard(String boardId);
    void updateBoard(Board boardRequestDto);
    void deleteBoard(String boardId);

    List<BoardDto> getBoardListAll(@Param("criteria") BoardCriteria criteria);
    List<BoardDto> getBoardListByMainCategory(@Param("criteria") BoardCriteria criteria);
    List<BoardDto> getBoardListByMainAndSubCategory(@Param("criteria") BoardCriteria criteria);
    List<BoardDto> getBoardListByMemberId(@Param("criteria") Criteria criteria);

    void increaseReplyCount(String boardId);
    void increaseViewCount(String boardId);
    void increaseLikeCount(String boardId);
    void decreaseLikeCount(String boardId);
    void boardLike(BoardLikeDto boardLikeDto);
    void boardUnlike(String likeId);
    String findLikeId(@Param("boardId") String boardId, @Param("userId") String userId);
    List<BoardDto> getBoardListWithPagingByKeyword(@Param("criteria") Criteria criteria);
}
