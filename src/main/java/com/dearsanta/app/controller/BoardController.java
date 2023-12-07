package com.dearsanta.app.controller;

import com.dearsanta.app.dto.BoardDetailDto;
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
    public ResponseEntity<BoardDetailDto> getBoardDetail (
            @PathVariable("boardId") String boardId
    ) {
        BoardDetailDto boardDetail = boardService.getBoardDetail(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(boardDetail);
    }
}
