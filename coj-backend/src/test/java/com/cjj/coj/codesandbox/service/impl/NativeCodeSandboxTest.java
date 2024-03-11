package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.model.web.CodeRequest;
import com.cjj.coj.codesandbox.model.web.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NativeCodeSandboxTest {

    private final CodeSandbox codeSandbox = new NativeCodeSandbox();

    @Test
    void executeCode() {
        CodeRequest request = new CodeRequest();
        request.setCode("import java.util.Scanner;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int n = scanner.nextInt();\n" +
                "        for (int i = 0; i < n; i++) {\n" +
                "            int a = scanner.nextInt();\n" +
                "            int b = scanner.nextInt();\n" +
                "            System.out.println(a + b1);\n" +
                "        }\n" +
                "    }\n" +
                "}");
        request.setLanguage("java");
        request.setJudgeCases(Arrays.asList("1 2", "3 4"));
        CodeResponse codeResponse = codeSandbox.executeCode(request);
        System.out.println(codeResponse);
    }
}