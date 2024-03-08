package com.cjj.coj.service.impl;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.mapper.CommentMapper;
import com.cjj.coj.modle.dto.comment.CommentDto;
import com.cjj.coj.modle.entity.Comments;
import com.cjj.coj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public ResultBody getCommentList(Long problemId) {
        return ResultBody.success(commentMapper.getComment(problemId));
    }

    @Override
    public ResultBody addComment(CommentDto commentDto) {
        Comments comments = new Comments();
        comments.setUserId(commentDto.getUserId());
        comments.setProblemId(commentDto.getProblemId());
        comments.setContent(commentDto.getContent());
        comments.setCreateTime(new Date());
        System.out.println(comments);

        commentMapper.insert(comments);
        return ResultBody.success();
    }


}
