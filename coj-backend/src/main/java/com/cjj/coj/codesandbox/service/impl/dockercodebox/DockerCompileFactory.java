package com.cjj.coj.codesandbox.service.impl.dockercodebox;


import com.cjj.coj.codesandbox.service.impl.dockercodebox.impl.CDockerCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.impl.CppDockerCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.impl.JavaDockerCompileAndRun;

public class DockerCompileFactory {
    // 静态工厂方法

    public static DockerCompileAndRun getCompileAndRun(String language) {
        switch (language) {
            case "java":
                return new JavaDockerCompileAndRun();
            case "cpp":
                return new CppDockerCompileAndRun();
            case "c":
                return new CDockerCompileAndRun();
        }

        return new JavaDockerCompileAndRun();
    }
}
