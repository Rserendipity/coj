package com.cjj.coj.codesandbox.utils;

import org.junit.jupiter.api.Test;

class CompileUtilTest {

    @Test
    void getCommand() {
        System.out.println(CompileUtil.getCommand("cpp").getCompile());
    }
}