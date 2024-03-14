package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.model.web.CodeRequest;
import com.cjj.coj.codesandbox.model.web.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class NativeCodeSandboxTest {

    private final CodeSandbox codeSandbox = new NativeCodeSandbox();

    @Test
    void executeCode() {
        CodeRequest request = new CodeRequest();
        request.setCode("public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        try{\n" +
                "            Thread.sleep(10000);\n" +
                "        } catch (InterruptedException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "    }\n" +
                "}");
        request.setLanguage("java");
        request.setJudgeCases(Arrays.asList("1 2", "3 4"));
        CodeResponse codeResponse = codeSandbox.executeCode(request);
        System.out.println(codeResponse);
    }
}