package com.cjj.coj.codesandbox.service.impl.dockercodebox;

import com.cjj.coj.codesandbox.model.ExecuteResult;

import java.util.List;

public interface DockerCompileAndRun {
    String compile(String code);
    ExecuteResult run(String path, List<String> judgeCases);

    void delete(String path);
}
