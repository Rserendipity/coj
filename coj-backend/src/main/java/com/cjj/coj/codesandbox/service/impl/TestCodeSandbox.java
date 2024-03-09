package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.model.web.CodeRequest;
import com.cjj.coj.codesandbox.model.web.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;

import java.util.ArrayList;
import java.util.List;

//@Service
public class TestCodeSandbox implements CodeSandbox {
    @Override
    public CodeResponse executeCode(CodeRequest request){
        // 仅用于测试流程，不实际执行代码
        System.out.println(request);
        CodeResponse codeResponse = new CodeResponse();
        codeResponse.setState(0);
        codeResponse.setStderr(null);
        codeResponse.setUseTime(6815);
        codeResponse.setUseMemory(123);
        List<String> stdout = new ArrayList<>();
        stdout.add("1");
        stdout.add("2");
        stdout.add("3");
        stdout.add("4");
        stdout.add("5");
        stdout.add("6");
        stdout.add("7");
        codeResponse.setStdout(stdout);
        return codeResponse;
    }
}
