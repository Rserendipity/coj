package com.cjj.coj.codesandbox.service.impl.dockercodebox;

import com.cjj.coj.codesandbox.model.ExecuteResult;

import java.io.Closeable;
import java.util.List;

public interface DockerCompileAndRun extends Closeable {
    Long CPU_COUNT = 1L;
    Long MAX_MEMORY_LIMIT = 100 * 1024 * 1024L; // 100mb
    Long MAX_TIME_LIMIT = 5000L; // 最多5秒

    void compile(String code);
    ExecuteResult run(List<String> judgeCases);
}
