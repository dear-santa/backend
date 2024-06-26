package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.Reply;
import com.dearsanta.app.dto.ReplyDto;
import com.dearsanta.app.dto.criteria.UserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {
    void createReply(ReplyDto replyDto);
    ReplyDto getReply(String replyId);
    List<ReplyDto> getReplyListWithPaging(@Param("criteria") UserCriteria criteria);
    void updateReply(Reply reply);
    void deleteReply(String replyId);
}
