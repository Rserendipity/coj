package com.cjj.coj.codesandbox.service;

import com.cjj.coj.codesandbox.model.CodeRequest;
import com.cjj.coj.codesandbox.model.CodeResponse;

public interface CodeSandbox {
    CodeResponse executeCode(CodeRequest request);
}
