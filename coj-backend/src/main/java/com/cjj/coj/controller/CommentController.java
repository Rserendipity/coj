package com.cjj.coj.controller;

import com.cjj.coj.common.ResultBody;
import com.cjj.coj.modle.dto.comment.CommentDto;
import com.cjj.coj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/get")
    public ResultBody getComment(@RequestParam Long problemId) {
        return commentService.getCommentList(problemId);
    }

    @PostMapping("/add")
    public ResultBody addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }
}
