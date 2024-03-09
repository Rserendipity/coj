package com.cjj.coj.codesandbox.service;

import com.cjj.coj.codesandbox.model.ExecuteResult;

import java.util.List;

public interface CompileAndRun {

    void compile(String code);
    ExecuteResult run(List<String> judgeCases);
}
