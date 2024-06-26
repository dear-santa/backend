package com.dearsanta.app.controller;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.*;
import com.dearsanta.app.service.BoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RequestMapping("/api/v1")
@RestController
@Log4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping(value="auth/board/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createBoard(
            @RequestPart("boardCreateRequestDto") BoardCreateRequestDto boardCreateRequestDto,
            @RequestPart("boardImage") MultipartFile boardImage) {
        log.info(boardCreateRequestDto.getTitle() +  boardCreateRequestDto.getContent() + boardImage);
        String memberId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        if (memberId == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        if (boardCreateRequestDto.getTitle().length() > 100) {
            throw new IllegalArgumentException("제목은 100자 이하로 입력해주세요.");
        }
        if (boardCreateRequestDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("내용은 1000자 이하로 입력해주세요.");
        }
        String boardCategoryId = boardService.findBoardCategoryId(boardCreateRequestDto.getMainCategory(), boardCreateRequestDto.getSubCategory());
        boardCreateRequestDto.setMemberId(memberId);
        boardCreateRequestDto.setBoardCategoryId(boardCategoryId);
        boardService.createBoard(boardCreateRequestDto, boardImage);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/board/{boardId}")
    public ResponseEntity<BoardDto> getBoard (
            @PathVariable("boardId") String boardId
    ) {
        String memberId = (String) RequestContextHolder
            .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        BoardDto board = boardService.getBoard(boardId);
        log.info("getBoard: " + boardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }
  
    @PatchMapping(value="/auth/board/{boardId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateBoard (
            @PathVariable("boardId") String boardId,
            @RequestPart("boardRequestDto") BoardRequestDto boardRequestDto,
            @RequestPart("boardImage") MultipartFile boardImage) {
        String memberId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        String boardmemberId = boardService.getBoard(boardId).getMemberId();
        if (!memberId.equals(boardmemberId)) {
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

    @DeleteMapping("/auth/board/{boardId}")
    public ResponseEntity<Void> deleteBoard (
            @PathVariable("boardId") String boardId) {
        String memberId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        String boardmemberId = boardService.getBoard(boardId).getMemberId();
        if (!memberId.equals(boardmemberId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        boardService.deleteBoard(boardId);
        log.info("deleteBoard: " + boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/board/{boardId}/like")
    public ResponseEntity<Void> likeBoard (
            @PathVariable("boardId") String boardId) {
        String memberId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        if (memberId == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .memberId(memberId)
                .boardId(boardId)
                .build();
        boardService.likeBoard(boardLikeDto);
        log.info("like board: " + boardId);
        return null;
    }

    @PostMapping("/auth/board/{boardId}/unlike")
    public ResponseEntity<Void> unlikeBoard (
            @PathVariable("boardId") String boardId) {
        String memberId = (String) RequestContextHolder
            .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        if (memberId == null) {
            throw new RuntimeException("권한이 없습니다.");
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .memberId(memberId)
                .boardId(boardId)
                .build();
        boardService.unlikeBoard(boardLikeDto);
        log.info("unlike board: " + boardId);
        return null;
    }

    @GetMapping("/board/category")
    public ResponseEntity<BoardListDto> getBoardListWithPaging(
            @RequestParam(value = "mainCategory", defaultValue = "HOME") String mainCategory,
            @RequestParam(value = "subCategory", defaultValue = "NONE") String subCategory,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sorted", defaultValue = "LATEST") Sorted sorted
    ) {
        BoardListDto boards = boardService.getBoardListWithPaging(mainCategory, subCategory, keyword, pageNum, pageSize, sorted);
        log.info("getBoardListWithPaging " + LocalDateTime.now());
        log.info("mainCategory : " + mainCategory + " subCategory : " + subCategory);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping("/auth/board")
    public ResponseEntity<BoardListDto> getBoardByMyPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sorted", defaultValue = "LATEST") Sorted sorted
    ) {
        String memberId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);

        log.info("getBoardByMyPage() memberId : " + memberId);
        BoardListDto boards = boardService.getBoardListOfMyPageWithPaging(memberId, pageNum, pageSize, sorted);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
}