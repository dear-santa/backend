package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.BoardCategory;

import java.util.List;

public interface BoardCategoryMapper {

    List<BoardCategory> getBoardCategories(String mainCategory);
}