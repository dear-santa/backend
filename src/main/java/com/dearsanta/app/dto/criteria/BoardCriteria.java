package com.dearsanta.app.dto.criteria;

import com.dearsanta.app.dto.Criteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class BoardCriteria extends Criteria {
    private String mainCategory;
    private String subCategory;
}