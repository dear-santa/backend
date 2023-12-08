package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardListDto;

public interface BoardService {

    BoardDto getBoard(String boardId);

    String findBoardCategoryId(String mainCategory, String subCategory);

    BoardListDto getBoardListWithPaging(String mainCategory, String subCategory, int pageNum, int pageSize, Sorted sorted);
}