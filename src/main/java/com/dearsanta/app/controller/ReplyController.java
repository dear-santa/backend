package com.dearsanta.app.controller;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.ReplyDto;
import com.dearsanta.app.dto.ReplyListDto;
import com.dearsanta.app.service.ReplyService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;

@RequestMapping("/api/v1")
@Log4j
@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    @PostMapping("/auth/board/{boardId}/reply/new")
    public ResponseEntity<Void> createReply(
            @PathVariable("boardId") String boardId,
            @RequestBody ReplyDto replyDto) {
        String userId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        log.info(userId);
        if (userId == null) {
            throw new RuntimeException("댓글 작성 권한이 없습니다.");
        }
        if (replyDto.getContent().length() == 0) {
            throw new IllegalArgumentException("댓글을 입력해주세요.");
        }
        else if (replyDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("댓글을 1000자 이하로 입력해주세요.");
        }
        replyDto.setUserId(userId);
        replyDto.setBoardId(boardId);
        replyService.createReply(replyDto);
        log.info("create reply...");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/board/{boardId}/reply")
    public ResponseEntity<ReplyListDto> getReplyListWithPaging(
            @PathVariable(value="boardId") String boardId,
            @RequestParam(value="pageNum", defaultValue="1") int pageNum,
            @RequestParam(value="pageSize", defaultValue="100") int pageSize,
            @RequestParam(value="sorted", defaultValue="REPLY_LATEST") Sorted sorted
    ) {
        String userId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        ReplyListDto replies = replyService.getReplyListWithPaging(userId, boardId, pageNum, pageSize, sorted);
        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }

    @PatchMapping("/auth/reply/{replyId}")
    public ResponseEntity<Void> updateReply(
            @PathVariable("replyId") String replyId,
            @RequestBody ReplyDto replyDto) {
        String userId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        String replyUserId = replyService.getReply(replyId).getUserId();
        if (!userId.equals(replyUserId)) {
            throw new RuntimeException("댓글 수정 권한이 없습니다.");
        }
        if (replyDto.getContent().length() == 0) {
            throw new IllegalArgumentException("댓글을 입력해주세요.");
        }
        else if (replyDto.getContent().length() > 1000) {
            throw new IllegalArgumentException("댓글을 1000자 이하로 입력해주세요.");
        }
        replyService.updateReply(replyId, replyDto);
        log.info("update reply... replyId: " + replyId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/auth/reply/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable("replyId") String replyId) {
        String userId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);
        String replyUserId = replyService.getReply(replyId).getUserId();
        if (!userId.equals(replyUserId)) {
            throw new RuntimeException("댓글 삭제 권한이 없습니다.");
        }
        replyService.deleteReply(replyId);
        log.info("delete reply... replyId: " + replyId);
        return ResponseEntity.ok().build();
    }
}
