package com.cjj.coj.codesandbox.service.impl.nativecodebox.impl;

import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.impl.nativecodebox.NativeCompileAndRun;

import java.util.List;

public class CNativeCompileAndRun implements NativeCompileAndRun {

    CppNativeCompileAndRun cppCompileAndRun = new CppNativeCompileAndRun();

    @Override
    public String compile(String code) {
        return cppCompileAndRun.compile(code);
    }

    @Override
    public ExecuteResult run(String path, List<String> judgeCases) {
        return cppCompileAndRun.run(path, judgeCases);
    }

    @Override
    public void delete(String path) {
        cppCompileAndRun.delete(path);
    }
}
