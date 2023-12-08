package com.dearsanta.app.controller;

import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/board")
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/new")
    public ResponseEntity<Void> createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpSession session) {
        // TODO: session에서 userId 가져오는 부분 service로 옮기기 (security 적용 후)
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        boardRequestDto.setUserId(userId.toString());
        boardService.createBoard(boardRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard (
            @PathVariable("boardId") String boardId
    ) {
        BoardDto board = boardService.getBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }
}
