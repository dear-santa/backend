package com.dearsanta.app.dto;

import com.dearsanta.app.domain.MainCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainCategoryDto {

    private MainCategory mainCategory;
    private String korean;
    private String subtitle;
    private String emoji;
}