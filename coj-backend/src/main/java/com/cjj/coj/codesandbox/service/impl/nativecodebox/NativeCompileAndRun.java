package com.cjj.coj.codesandbox.service.impl.nativecodebox;

import com.cjj.coj.codesandbox.model.ExecuteResult;

import java.util.List;

public interface NativeCompileAndRun {
    Integer MAX_TIME_LIMIT = 5000;

    String compile(String code);
    ExecuteResult run(String path, List<String> judgeCases);
    void delete(String path);
}
