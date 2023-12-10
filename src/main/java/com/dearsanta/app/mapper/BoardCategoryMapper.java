package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.BoardCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardCategoryMapper {

    List<BoardCategory> getBoardCategories(String mainCategory);

    String getBoardCategoryId(@Param("mainCategory")String mainCategory, @Param("subCategory")String subCategory);
}