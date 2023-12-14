package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardLikeDto;
import com.dearsanta.app.dto.BoardListDto;
import com.dearsanta.app.dto.BoardRequestDto;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardServiceTest {

    @Autowired
    @Qualifier("boardServiceImpl")
    private BoardService boardService;

    @DisplayName("대분류, 소분류로 카테고리 id값을 찾아옵니다.")
    @Test
    public void findBoardCategoryId() {
        String mainCategory = "PRESENT";
        String subCategory = "해리";

        String categoryId = boardService.findBoardCategoryId(mainCategory, subCategory);

        Assertions.assertNotNull(categoryId);
    }

    @DisplayName("존재하지 않는 대분류/소분류일 경우, 예외를 발생한다.")
    @Test
    public void findBoardCategoryId_InvalidParameter() {
        String mainCategory = "CAR";
        String subCategory = "";

        Assertions.assertThrows(NoSuchElementException.class,
                () -> boardService.findBoardCategoryId(mainCategory, subCategory));
    }

    @DisplayName("게시물을 대/소분류별로 페이징, 정렬하여 조회합니다. - 페이징 갯수")
    @Test
    public void getBoardListWithPaging_pageSize() {
        String mainCategory = "PRESENT";
        String subCategory = "해리";
        int pageNum = 1;
        int pageSize = 5;
        Sorted sorted = Sorted.LATEST;

        BoardListDto boards = boardService.getBoardListWithPaging(mainCategory, subCategory, pageNum, pageSize, sorted);
        List<BoardDto> boardList = boards.getBoardListDto();

        Assertions.assertEquals(boardList.size(), pageSize);
    }

    @DisplayName("게시물을 대/소분류별로 페이징, 정렬하여 조회합니다. - 정렬순")
    @Test
    public void getBoardListWithPaging_sort() {
        String mainCategory = "PRESENT";
        String subCategory = "해리";
        int pageNum = 1;
        int pageSize = 5;
        Sorted sorted = Sorted.LIKE_COUNT;

        BoardListDto boards = boardService.getBoardListWithPaging(mainCategory, subCategory, pageNum, pageSize, sorted);
        List<BoardDto> boardList = boards.getBoardListDto();
        BoardDto firstBoardDto = boardList.get(0);
        BoardDto secondBoardDto = boardList.get(0);

        Assertions.assertTrue(firstBoardDto.getLikeCount() >= secondBoardDto.getLikeCount());
    }

    @DisplayName("게시물을 생성합니다.")
    @Test
    public void createBoard() {
        String boardCategoryId = "2";
        String title = "제목";
        String content = "내용";
        String memberId = "test member";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .boardCategoryId(boardCategoryId)
                .title(title)
                .content(content)
                .memberId(memberId)
                .build();
        boardService.createBoard(boardRequestDto, null);
    }

    @DisplayName("게시물을 상세조회합니다. 게시글이 존재할 경우")
    @Test
    public void getBoard() {
        String boardId = "1";
        BoardDto boardDto = boardService.getBoard(boardId);
        Assertions.assertNotNull(boardDto);
    }

    @DisplayName("게시글을 수정합니다")
    @Test
    public void updateBoard() {
        String boardId = "1";
        String boardCategoryId = "4";
        String title = "제목";
        String content = "내용";
        String memberId = "member1";
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .boardCategoryId(boardCategoryId)
                .title(title)
                .content(content)
                .memberId(memberId)
                .build();
        boardService.updateBoard(boardId, boardRequestDto, null);
    }

    @DisplayName("게시글을 좋아요합니다.")
    @Test
    public void likeBoard() {
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .memberId("Test member 1")
                .boardId("54c57f8a-705b-4571-92d6-a909619bc006")
                .build();
        boardService.likeBoard(boardLikeDto);
    }

    @DisplayName("게시글을 좋아요 취소합니다.")
    @Test
    public void unlikeBoard() {
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .id("b70f52ee-f3d0-40fb-9799-4fea409fd9f3")
                .memberId("Test member 1")
                .boardId("54c57f8a-705b-4571-92d6-a909619bc006")
                .build();
        boardService.unlikeBoard(boardLikeDto);
    }
}