package com.dearsanta.app.service;
  
import com.dearsanta.app.domain.enumtype.Sorted;
import com.dearsanta.app.domain.Board;
import com.dearsanta.app.dto.*;
import com.dearsanta.app.dto.criteria.BoardCriteria;
import com.dearsanta.app.mapper.BoardCategoryMapper;
import com.dearsanta.app.mapper.BoardMapper;
import com.dearsanta.app.mapper.UserMapper;
import com.dearsanta.app.util.AWSS3;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.UUID;

import java.util.List;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private BoardCategoryMapper boardCategoryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    AWSS3 aWSS3;

    @Override
    public BoardListDto getBoardListWithPaging(
            String mainCategory, String subCategory, int pageNum, int pageSize, Sorted sorted
    ) {
        log.info("mainCategory :" + mainCategory + " subCategory :" + subCategory);
        log.info("pageNum:" + pageNum + " pageSize: " + pageSize + " sort: " + sorted.getIndexColumn());
        List<BoardDto> boardDtos = getBoardListDtoByCriteria(mainCategory, subCategory, pageNum, pageSize, sorted);

        return new BoardListDto(boardDtos);
    }

    private List<BoardDto> getBoardListDtoByCriteria(String mainCategory, String subCategory, int pageNum, int pageSize, Sorted sorted) {

        BoardCriteria criteria = BoardCriteria.builder()
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .sorted(sorted.getIndexColumn())
                .build();

        if (mainCategory.equals("HOME")) {
            log.info("getBoardListAll");
            return boardMapper.getBoardListAll(criteria);
        }
        if (subCategory.equals("NONE")) {
            log.info("getBoardListByMainCategory");
            return boardMapper.getBoardListByMainCategory(criteria);
        }
        log.info("getBoardListByMainAndSubCategory");
        return boardMapper.getBoardListByMainAndSubCategory(criteria);
    }

    @Override
    public String findBoardCategoryId(String mainCategory, String subCategory) {
        String categoryId = boardCategoryMapper.getBoardCategoryId(mainCategory, subCategory);
        log.info("{mainCategory: " + mainCategory + ", subCategory: " + subCategory + "} => categoryId=" + categoryId);

        if (categoryId == null) {
            throw new NoSuchElementException("해당하는 게시판이 존재하지 않습니다.");
        }
        return categoryId;
    }

    @Override
    public void createBoard(BoardRequestDto boardRequestDto, MultipartFile boardImage) {
        boardRequestDto.setId(UUID.randomUUID().toString());
        if (boardImage != null) {
            String imgUrl = aWSS3.uploadImage(boardRequestDto.getId(), boardImage);
            boardRequestDto.setImgUrl(imgUrl);
        }
        boardMapper.createBoard(boardRequestDto);
    }

    @Override
    public BoardDto getBoard(String boardId) {
        BoardDto board = boardMapper.getBoard(boardId);
        if (board == null) {
            throw new NoSuchElementException("존재하지 않는 게시글입니다.");
        }
        boardMapper.increaseViewCount(boardId);
        return board;
    }

    @Override
    public void updateBoard(String boardId, BoardRequestDto boardRequestDto, MultipartFile boardImage) {
        Board board = boardMapper.getBoard(boardId).toEntity();
        Board updateBoard = boardRequestDto.toEntity();
        if (boardImage != null) {
            // TODO: S3에 있는 기존 이미지 삭제 (지금은 그냥 덮어씌움)
            String imgUrl = aWSS3.uploadImage(boardId, boardImage);
            updateBoard.setImgUrl(imgUrl);
        }
        board.update(updateBoard);
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(String boardId) {
        boardMapper.deleteBoard(boardId);
    }

    @Override
    public void likeBoard(BoardLikeDto boardLikeDto) {
        BoardDto board = boardMapper.getBoard(boardLikeDto.getBoardId());
        if (board == null) {
            throw new NoSuchElementException("존재하지 않는 게시글입니다.");
        }
        boardLikeDto.setId(UUID.randomUUID().toString());
        boardMapper.boardLike(boardLikeDto);
        boardMapper.increaseLikeCount(boardLikeDto.getBoardId());
    }

    @Override
    public void unlikeBoard(BoardLikeDto boardLikeDto) {
        BoardDto board = boardMapper.getBoard(boardLikeDto.getBoardId());
        if (board == null) {
            throw new NoSuchElementException("존재하지 않는 게시글입니다.");
        }
        String likeId = boardMapper.findLikeId(board.getId(), boardLikeDto.getUserId());
        boardMapper.boardUnlike(likeId);
        boardMapper.decreaseLikeCount(board.getId());
    }
}
