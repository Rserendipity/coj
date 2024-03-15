package com.cjj.coj.codesandbox.service.impl.nativecodebox;

import com.cjj.coj.codesandbox.model.ExecuteResult;

import java.io.Closeable;
import java.util.List;

public interface NativeCompileAndRun extends Closeable {
    Integer MAX_TIME_LIMIT = 5000;

    void compile(String code);
    ExecuteResult run(List<String> judgeCases);
}
