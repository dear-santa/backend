package com.dearsanta.app.service;

import com.dearsanta.app.domain.Reply;
import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.dto.BoardDto;
import com.dearsanta.app.dto.ReplyDto;
import com.dearsanta.app.dto.ReplyListDto;
import com.dearsanta.app.dto.criteria.UserCriteria;
import com.dearsanta.app.mapper.BoardMapper;
import com.dearsanta.app.mapper.ReplyMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Log4j
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    BoardMapper boardMapper;

    @Override
    public void createReply(ReplyDto replyDto) {
        BoardDto boardDto = boardMapper.getBoard(replyDto.getBoardId());
        if (boardDto == null) {
            throw new IllegalArgumentException("해당하는 게시글이 없습니다.");
        }
        replyDto.setId(UUID.randomUUID().toString());
        replyMapper.createReply(replyDto);
        boardMapper.increaseReplyCount(replyDto.getBoardId());
    }

    @Override
    public ReplyListDto getReplyListWithPaging(
            String userId, String boardId, int pageNum, int pageSize, Sorted sorted
    ) {
        UserCriteria criteria = UserCriteria.builder()
                .userId(userId)
                .selectId(boardId)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .sorted(sorted.getIndexColumn())
                .build();

        log.info("pageNum:" + pageNum + " pageSize: " + pageSize + " sort: " + sorted.getIndexColumn());

        List<ReplyDto> replyDtos = replyMapper.getReplyListWithPaging(criteria);
        return new ReplyListDto(replyDtos);
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
