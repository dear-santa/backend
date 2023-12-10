package com.dearsanta.app.service;

import com.dearsanta.app.dto.ReplyDto;

public interface ReplyService {
    void createReply(ReplyDto replyDto);
    ReplyDto getReply(String replyId);
    void updateReply(String replyId, ReplyDto replyDto);
    void deleteReply(String replyId);
}
