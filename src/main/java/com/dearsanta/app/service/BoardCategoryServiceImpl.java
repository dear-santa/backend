package com.dearsanta.app.service;

import com.dearsanta.app.domain.BoardCategory;
import com.dearsanta.app.domain.enumtype.MainCategory;
import com.dearsanta.app.mapper.BoardCategoryMapper;
import com.dearsanta.app.dto.MainCategoryDto;
import com.dearsanta.app.dto.MainCategoriesDto;
import com.dearsanta.app.dto.SubCategoryDto;
import com.dearsanta.app.dto.SubCategoriesDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardCategoryServiceImpl implements BoardCategoryService {

    @Autowired
    private BoardCategoryMapper boardCategoryMapper;

    @Override
    public MainCategoriesDto getMainCategories() {
        List<MainCategoryDto> result = MainCategory.getCategoryVos();
        return new MainCategoriesDto(result);
    }

    @Override
    public SubCategoriesDto getSubCategories(String mainCategory) {
        List<BoardCategory> boardCategories = boardCategoryMapper.getBoardCategories(mainCategory);
        List<SubCategoryDto> subCategories = boardCategories.stream().map(
                c -> SubCategoryDto.builder()
                        .subCategory(c.getSubCategory())
                        .imgUrl(c.getBannerImgUrl())
                        .build())
                .collect(Collectors.toUnmodifiableList());

        return new SubCategoriesDto(subCategories);
    }
}