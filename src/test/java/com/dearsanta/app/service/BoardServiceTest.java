package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardListDto;
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
}