package com.dearsanta.app.controller;

import com.dearsanta.app.domain.enumtype.MainCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BoardCategoryControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("게시판 대분류 목록을 조회합니다.")
    @Test
    public void getMainCategories() throws Exception {

        mockMvc.perform(get("/categories/main-categories"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.mainCategoriesDto", notNullValue()))
                .andDo(print());
    }

    @DisplayName("게시판 소분류 목록을 조회합니다.")
    @Test
    public void getSubCategories() throws Exception {
        String mainCategory = MainCategory.values()[0].toString();

        mockMvc.perform(get("/categories/main-categories/{mainCategories}/sub-categories", mainCategory))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.subCategoriesDto", notNullValue()))
                .andDo(print());
    }
}