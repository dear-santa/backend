package com.dearsanta.app.service;

import com.dearsanta.app.domain.Reply;
import com.dearsanta.app.dto.ReplyDto;
import com.dearsanta.app.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper replyMapper;

    @Override
    public void createReply(ReplyDto replyDto) {
        replyDto.setId(UUID.randomUUID().toString());
        replyMapper.createReply(replyDto);
    }

    @Override
    public ReplyDto getReply(String replyId) {
        ReplyDto reply = replyMapper.getReply(replyId);
        if (reply == null) {
            throw new NoSuchElementException("존재하지 않는 댓글입니다.");
        }
        return reply;
    }

    @Override
    public void updateReply(String replyId, ReplyDto replyDto) {
        Reply reply = replyMapper.getReply(replyId).toEntity();
        reply.updateContent(replyDto.getContent());
        replyMapper.updateReply(reply);
    }

    @Override
    public void deleteReply(String replyId) {
        replyMapper.deleteReply(replyId);
    }
}
