package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Board;
import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardLikeDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.dto.Criteria;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private BoardCategoryMapper categoryMapper;

    @DisplayName("대분류, 소분류, 페이지 번호, 페이지 갯수, 정렬방식을 기준으로 게시물을 조회합니다.")
    @Test
    public void getBoardListWithPaging() {
        String mainCategory = "PRESENT";
        String subCategory = "해리";
        int pageNum = 1;
        int pageSize = 5;
        Sorted sorted = Sorted.LIKE_COUNT;
        String categoryId = categoryMapper.getBoardCategoryId(mainCategory, subCategory);
        Criteria criteria = new Criteria(categoryId, pageNum, pageSize, sorted.getIndexColumn());

        List<BoardDto> boards = boardMapper.getBoardListWithPaging(criteria);
        Assertions.assertEquals(boards.size(), pageSize);
        Assertions.assertTrue(boards.get(0).getLikeCount() >= boards.get(1).getLikeCount());
    }

    @DisplayName("게시물을 등록합니다.")
    @Test
    public void createBoard() {
        String id = "will be set as a random id";
        String boardCategoryId = "3";
        String title = "테스트 게시물 제목";
        String content = "테스트 게시물 내용";
        String userId = "test user";
        String imgUrl = "img url";
        BoardRequestDto boardRequestDto = new BoardRequestDto(id, boardCategoryId, title, content, userId, imgUrl);

        boardMapper.createBoard(boardRequestDto);
    }

    @DisplayName("게시물을 조회합니다.")
    @Test
    public void getBoard() {
        String boardId = "bd4d1fd6-a33b-4e69-9f89-b36f2d18a91d";
        BoardDto board = boardMapper.getBoard(boardId);
        Assertions.assertNotNull(board);
    }

    @DisplayName("게시물을 수정합니다.")
    @Test
    public void updateBoard() {
        String id = "bd4d1fd6-a33b-4e69-9f89-b36f2d18a91d";
        String boardCategoryId = "3";
        String title = "테스트 게시물 제목 수정";
        String content = "테스트 게시물 내용 수정";
        String userId = "test user";
        String imgUrl = "img url";
        BoardRequestDto boardRequestDto = new BoardRequestDto(id, boardCategoryId, title, content, userId, imgUrl);
        Board board = boardRequestDto.toEntity();

        boardMapper.updateBoard(board);
    }

    @DisplayName("게시물을 삭제합니다.")
    @Test
    public void deleteBoard() {
        // 삭제할 게시글이 있는지 확인 후 테스트
        String boardId = "bd4d1fd6-a33b-4e69-9f89-b36f2d18a91d";
        boardMapper.deleteBoard(boardId);
    }

    @DisplayName("게시물의 좋아요 수를 증가시킵니다.")
    @Test
    public void increaseLikeCount() {
        String boardId = "bd4d1fd6-a33b-4e69-9f89-b36f2d18a91d";
        boardMapper.increaseLikeCount(boardId);
    }

    @DisplayName("게시물의 좋아요 수를 감소시킵니다.")
    @Test
    public void decreaseLikeCount() {
        String boardId = "bd4d1fd6-a33b-4e69-9f89-b36f2d18a91d";
        boardMapper.decreaseLikeCount(boardId);
    }

    @DisplayName("게시물의 좋아요를 누릅니다.")
    @Test
    public void boardLike() {
        String id = "will be set as a random id";
        String userId = "test user";
        String boardId = "bd4d1fd6-a33b-4e69-9f89-b36f2d18a91d";
        BoardLikeDto boardLikeDto = new BoardLikeDto(id, userId, boardId);

        boardMapper.boardLike(boardLikeDto);
    }

    @DisplayName("게시물의 좋아요를 취소합니다.")
    @Test
    public void boardUnlike() {
        String likeId = "will be set as a random id";
        boardMapper.boardUnlike(likeId);
    }
}