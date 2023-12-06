package com.dearsanta.app.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubCategoryDto {
    private String subCategory;
    private String imgUrl;
}