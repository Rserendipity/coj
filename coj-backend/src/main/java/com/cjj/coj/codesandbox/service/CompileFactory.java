package com.cjj.coj.codesandbox.service;

import com.cjj.coj.codesandbox.service.impl.CCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.CppCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.JavaCompileAndRun;

public class CompileFactory {
    public static CompileAndRun getCompileAndRun(String language) {
        switch (language) {
            case "java":
                return new JavaCompileAndRun();
            case "c":
                return new CCompileAndRun();
            case "cpp":
                return new CppCompileAndRun();
        }
        return new JavaCompileAndRun();
    }
}
