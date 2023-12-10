package com.dearsanta.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardListDto {

    private List<BoardDto> boardListDto;
}