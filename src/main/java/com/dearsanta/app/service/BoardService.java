package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    void createBoard(BoardCreateRequestDto boardCreateRequestDto, MultipartFile boardImage);
    BoardDto getBoard(String boardId);
    void updateBoard(String boardId, BoardRequestDto boardRequestDto, MultipartFile boardImage);
    void deleteBoard(String boardId);
    String findBoardCategoryId(String mainCategory, String subCategory);
    BoardListDto getBoardListWithPaging(String mainCategory, String subCategory, String keyword, int pageNum, int pageSize, Sorted sorted);
    BoardListDto getBoardListOfMyPageWithPaging(String memberId, int pageNum, int pageSize, Sorted sorted);

    void likeBoard(BoardLikeDto boardLikeDto);
    void unlikeBoard(BoardLikeDto boardLikeDto);
}
