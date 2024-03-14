package com.cjj.coj.modle.dto.submit;

import lombok.Data;

@Data
public class SubmitUpload {
    private Long problemId;

    private Long userId;

    private String language;

    private String code;
}
