package com.dearsanta.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Criteria {
    private String selectId;
    private int pageNum;
    private int pageSize;
    private String sorted;
}