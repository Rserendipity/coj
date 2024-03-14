package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.service.CodeSandbox;
import org.junit.jupiter.api.Test;

class TestCodeSandboxTest {

    @Test
    void executeCode() throws InterruptedException {
        CodeSandbox codeSandbox = new TestCodeSandbox();
        System.out.println(codeSandbox.executeCode(null));
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        System.out.println(a + b);
    }
}