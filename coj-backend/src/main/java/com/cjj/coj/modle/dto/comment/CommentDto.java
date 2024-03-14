package com.cjj.coj.modle.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
    private Long userId;
    private Long problemId;
    private String content;
}
