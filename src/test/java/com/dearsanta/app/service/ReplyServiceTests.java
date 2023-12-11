package com.dearsanta.app.service;

import com.dearsanta.app.dto.ReplyDto;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyServiceTests {

    @Autowired
    @Qualifier("replyServiceImpl")
    private ReplyService replyService;

    @DisplayName("댓글을 작성합니다.")
    @Test
    public void createReply() {
        String userId = "user1";
        String content = "댓글입니다.";
        String boardId = "0bbdfac6-9e69-4a52-9e9b-59431fdbaa50";
        ReplyDto replyDto = ReplyDto.builder()
                .userId(userId)
                .content(content)
                .boardId(boardId)
                .build();
        replyService.createReply(replyDto);
    }

    @DisplayName("댓글을 수정합니다.")
    @Test
    public void updateReply() {
        String replyId = "e83cd392-8940-42ac-be3c-3422e3d60d74";
        String content = "수정된 댓글입니다.";
        ReplyDto replyDto = ReplyDto.builder()
                .content(content)
                .build();
        replyService.updateReply(replyId, replyDto);
    }

    @DisplayName("댓글을 삭제합니다.")
    @Test
    public void deleteReply() {
        String replyId = "e83cd392-8940-42ac-be3c-3422e3d60d74";
        replyService.deleteReply(replyId);
    }
}
