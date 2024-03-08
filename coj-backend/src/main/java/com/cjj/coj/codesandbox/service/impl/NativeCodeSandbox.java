package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.model.CodeRequest;
import com.cjj.coj.codesandbox.model.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;
import org.springframework.stereotype.Service;

@Service
public class NativeCodeSandbox implements CodeSandbox {
    @Override
    public CodeResponse executeCode(CodeRequest request) {

        return null;
    }
}
