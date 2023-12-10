package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
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
}