package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardListDto;
import com.dearsanta.app.dto.Criteria;
import com.dearsanta.app.mapper.BoardCategoryMapper;
import com.dearsanta.app.mapper.BoardMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private BoardCategoryMapper boardCategoryMapper;

    @Override
    public BoardListDto getBoardListWithPaging(
            String mainCategory, String subCategory, int pageNum, int pageSize, Sorted sorted
    ) {
        String categoryId = findBoardCategoryId(mainCategory, subCategory);
        Criteria criteria = new Criteria(categoryId, pageNum, pageSize, sorted.getIndexColumn());
        log.info("pageNum:" + pageNum + " pageSize: " + pageSize + " sort: " + sorted.getIndexColumn());

        List<BoardDto> boardDtos = boardMapper.getBoardListWithPaging(criteria);
        return new BoardListDto(boardDtos);
    }

    @Override
    public String findBoardCategoryId(String mainCategory, String subCategory) {
        String categoryId = boardCategoryMapper.getBoardCategoryId(mainCategory, subCategory);
        log.info("{mainCategory: " + mainCategory + ", subCategory: " + subCategory + "} => categoryId=" + categoryId);

        if (categoryId == null) {
            throw new NoSuchElementException("해당하는 게시판이 존재하지 않습니다.");
        }
        return categoryId;
    }

    @Override
    public BoardDto getBoard(String boardId) {
        BoardDto board = boardMapper.getBoard(boardId);
        return board;
    }
}