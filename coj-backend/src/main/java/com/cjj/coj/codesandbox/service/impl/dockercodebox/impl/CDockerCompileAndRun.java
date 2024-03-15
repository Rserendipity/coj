package com.cjj.coj.codesandbox.service.impl.dockercodebox.impl;

import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.DockerCompileAndRun;

import java.io.IOException;
import java.util.List;

public class CDockerCompileAndRun implements DockerCompileAndRun {
    CppDockerCompileAndRun cppDockerCompileAndRun = new CppDockerCompileAndRun();

    @Override
    public void compile(String code) {
        cppDockerCompileAndRun.compile(code);
    }

    @Override
    public ExecuteResult run(List<String> judgeCases) {
        return cppDockerCompileAndRun.run(judgeCases);
    }

    @Override
    public void close() {
        cppDockerCompileAndRun.close();
    }
}
