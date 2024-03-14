package com.cjj.coj.codesandbox.service.impl.dockercodebox;


import com.cjj.coj.codesandbox.service.impl.dockercodebox.impl.JavaDockerCompileAndRun;

public class DockerCompileFactory {
    // 静态工厂方法

    public static DockerCompileAndRun getCompileAndRun(String language) {
        switch (language) {
            case "java":
                return new JavaDockerCompileAndRun();
        }
        return new JavaDockerCompileAndRun();
    }
}
