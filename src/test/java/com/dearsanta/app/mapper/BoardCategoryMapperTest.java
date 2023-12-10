package com.dearsanta.app.mapper;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardCategoryMapperTest {

    @Autowired
    private BoardCategoryMapper categoryMapper;

    @DisplayName("대분류, 소분류를 기준으로 category id를 조회합니다.")
    @Test
    public void getBoardListWithPaging() {
        String mainCategory = "PRESENT";
        String subCategory = "해리";

        String categoryId = categoryMapper.getBoardCategoryId(mainCategory, subCategory);
        Assertions.assertNotNull(categoryId);
    }
}