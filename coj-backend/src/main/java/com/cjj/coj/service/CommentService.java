package com.cjj.coj.service;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.modle.dto.comment.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    ResultBody getCommentList(Long problemId);

    ResultBody addComment(CommentDto commentDto);
}
