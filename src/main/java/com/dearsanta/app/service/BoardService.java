package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardListDto;
import com.dearsanta.app.dto.BoardRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    void createBoard(BoardRequestDto boardRequestDto, MultipartFile boardImage);
    BoardDto getBoard(String boardId);
    void updateBoard(String boardId, BoardRequestDto boardRequestDto, MultipartFile boardImage);
    void deleteBoard(String boardId);
    String findBoardCategoryId(String mainCategory, String subCategory);
    BoardListDto getBoardListWithPaging(String mainCategory, String subCategory, int pageNum, int pageSize, Sorted sorted);
}
