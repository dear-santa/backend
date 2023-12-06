package com.dearsanta.app.service;

import com.dearsanta.app.dto.MainCategoriesDto;
import com.dearsanta.app.dto.SubCategoriesDto;

public interface BoardCategoryService {

    MainCategoriesDto getMainCategories();

    SubCategoriesDto getSubCategories(String mainCategory);
}