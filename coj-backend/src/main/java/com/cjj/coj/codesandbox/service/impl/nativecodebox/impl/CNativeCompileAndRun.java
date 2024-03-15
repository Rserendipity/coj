package com.cjj.coj.codesandbox.service.impl.nativecodebox.impl;

import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.impl.nativecodebox.NativeCompileAndRun;

import java.io.IOException;
import java.util.List;

public class CNativeCompileAndRun implements NativeCompileAndRun {

    CppNativeCompileAndRun cppCompileAndRun = new CppNativeCompileAndRun();

    @Override
    public void compile(String code) {
        cppCompileAndRun.compile(code);
    }

    @Override
    public ExecuteResult run(List<String> judgeCases) {
        return cppCompileAndRun.run(judgeCases);
    }

    @Override
    public void close() {
        cppCompileAndRun.close();
    }
}
