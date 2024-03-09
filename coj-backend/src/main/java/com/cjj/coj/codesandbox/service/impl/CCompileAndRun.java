package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.CompileAndRun;

import java.util.List;

public class CCompileAndRun implements CompileAndRun {

    CppCompileAndRun cppCompileAndRun = new CppCompileAndRun();

    @Override
    public void compile(String code) {
        cppCompileAndRun.compile(code);
    }

    @Override
    public ExecuteResult run(List<String> judgeCases) {
        return cppCompileAndRun.run(judgeCases);
    }
}
