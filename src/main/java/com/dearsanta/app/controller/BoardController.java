package com.dearsanta.app.controller;

import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.BoardRequestDto;
import com.dearsanta.app.service.BoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RequestMapping("/board")
@RestController
@Log4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping(value="/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createBoard(
            @RequestPart("boardRequestDto") BoardRequestDto boardRequestDto,
            @RequestPart("boardImage") MultipartFile boardImage,
            HttpSession session) {
        log.info(boardRequestDto.getTitle() +  boardRequestDto.getContent() + boardImage);
        // TODO: session에서 userId 가져오는 부분 service로 옮기기 (security 적용 후)
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        if (boardRequestDto.getTitle().length() > 100) {
            throw new IllegalArgumentException("제목은 100자 이하로 입력해주세요.");
        }
        if (boardRequestDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("내용은 1000자 이하로 입력해주세요.");
        }
        boardRequestDto.setUserId(userId.toString());
        boardService.createBoard(boardRequestDto, boardImage);
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

    @PatchMapping(value="/{boardId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateBoard (
            @PathVariable("boardId") String boardId,
            @RequestPart("boardRequestDto") BoardRequestDto boardRequestDto,
            @RequestPart("boardImage") MultipartFile boardImage,
            HttpSession session) {
        // TODO: session에서 userId 가져와 비교하는 부분 service로 옮기기 (security 적용 후)
        Object userId = session.getAttribute("userId");
        String boardUserId = boardService.getBoard(boardId).getUserId();
        if (!userId.toString().equals(boardUserId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        if (boardRequestDto.getTitle().length() > 100) {
            throw new IllegalArgumentException("제목은 100자 이하로 입력해주세요.");
        }
        if (boardRequestDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("내용은 1000자 이하로 입력해주세요.");
        }
        boardService.updateBoard(boardId, boardRequestDto, boardImage);
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
