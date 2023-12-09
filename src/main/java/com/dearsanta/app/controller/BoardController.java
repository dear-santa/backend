package com.dearsanta.app.controller;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardListDto;
import com.dearsanta.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board")
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;
    
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard (
            @PathVariable("boardId") String boardId
    ) {
        BoardDto board = boardService.getBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @GetMapping("/{mainCategory}/{subCategory}")
    public ResponseEntity<BoardListDto> getBoardListWithPaging(
            @PathVariable(value = "mainCategory") String mainCategory,
            @PathVariable(value = "subCategory") String subCategory,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sorted", defaultValue = "LATEST") Sorted sorted
    ) {
        BoardListDto boards = boardService.getBoardListWithPaging(mainCategory, subCategory, pageNum, pageSize, sorted);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
}