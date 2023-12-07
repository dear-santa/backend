package com.dearsanta.app.mapper;

import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    BoardDto getBoard(String boardId);

    List<BoardDto> getBoardListWithPaging(@Param("criteria")Criteria criteria);
}