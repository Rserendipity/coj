package com.cjj.coj.modle.vo.comment;

import lombok.Data;

@Data
public class CommentsVo {
    private String id;
    private String account;
    private String nickname;
    private String content;
    private String createTime;
}
