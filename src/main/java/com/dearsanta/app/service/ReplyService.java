package com.dearsanta.app.service;

import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.ReplyDto;
import com.dearsanta.app.dto.ReplyListDto;

public interface ReplyService {
    void createReply(ReplyDto replyDto);
    ReplyListDto getReplyListWithPaging(String boardId, int pageNum, int pageSize, Sorted sorted);
    ReplyDto getReply(String replyId);
    void updateReply(String replyId, ReplyDto replyDto);
    void deleteReply(String replyId);
}
