package com.cjj.coj.codesandbox.service.impl.dockercodebox.impl;

import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.DockerCompileAndRun;

import java.util.List;

public class JavaDockerCompileAndRun implements DockerCompileAndRun {


    static {
        // 拉取镜像，制作运行环境

    }

    @Override
    public String compile(String code) {
        return null;
    }

    @Override
    public ExecuteResult run(String path, List<String> judgeCases) {
        return null;
    }

    @Override
    public void delete(String path) {

    }
}
