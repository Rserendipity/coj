package com.cjj.coj.codesandbox.service.impl.nativecodebox;

import com.cjj.coj.codesandbox.service.impl.nativecodebox.impl.CNativeCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.nativecodebox.impl.CppNativeCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.nativecodebox.impl.JavaNativeCompileAndRun;

public class NativeCompileFactory {
    public static NativeCompileAndRun getCompileAndRun(String language) {
        switch (language) {
            case "java":
                return new JavaNativeCompileAndRun();
            case "c":
                return new CNativeCompileAndRun();
            case "cpp":
                return new CppNativeCompileAndRun();
        }
        return new JavaNativeCompileAndRun();
    }
}
