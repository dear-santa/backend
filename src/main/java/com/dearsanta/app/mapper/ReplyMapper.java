package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Reply;
import com.dearsanta.app.dto.ReplyDto;

public interface ReplyMapper {
    void createReply(ReplyDto replyDto);
    ReplyDto getReply(String replyId);
    void updateReply(Reply reply);
    void deleteReply(String replyId);
}
