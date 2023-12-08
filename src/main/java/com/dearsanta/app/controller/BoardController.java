package com.dearsanta.app.controller;

import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.service.BoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/board")
@RestController
@Log4j
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
        log.info("createBoard");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard (
            @PathVariable("boardId") String boardId
    ) {
        BoardDto board = boardService.getBoard(boardId);
        log.info("getBoard: " + boardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard (
            @PathVariable("boardId") String boardId,
            @RequestBody BoardRequestDto boardRequestDto,
            HttpSession session
    ) {
        // TODO: session에서 userId 가져와 비교하는 부분 service로 옮기기 (security 적용 후)
        Object userId = session.getAttribute("userId");
        String boardUserId = boardService.getBoard(boardId).getUserId();
        if (!userId.toString().equals(boardUserId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        boardService.updateBoard(boardId, boardRequestDto);
        log.info("updateBoard: " + boardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard (
            @PathVariable("boardId") String boardId,
            HttpSession session
    ) {
        // TODO: session에서 userId 가져와 비교하는 부분 service로 옮기기 (security 적용 후)
        Object userId = session.getAttribute("userId");
        String boardUserId = boardService.getBoard(boardId).getUserId();
        if (!userId.toString().equals(boardUserId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        boardService.deleteBoard(boardId);
        log.info("deleteBoard: " + boardId);
        return ResponseEntity.ok().build();
    }
}
