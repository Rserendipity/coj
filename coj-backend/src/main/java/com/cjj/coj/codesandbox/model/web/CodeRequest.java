package com.cjj.coj.codesandbox.model.web;

import lombok.Data;

import java.util.List;

@Data
public class CodeRequest {
    private String code;
    private String language;
    private List<String> JudgeCases;
}
