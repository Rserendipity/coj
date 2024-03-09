package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.exception.CompileException;
import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.model.web.CodeRequest;
import com.cjj.coj.codesandbox.model.web.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;
import com.cjj.coj.codesandbox.service.CompileAndRun;
import com.cjj.coj.codesandbox.service.CompileFactory;
import org.springframework.stereotype.Service;

@Service
public class NativeCodeSandbox implements CodeSandbox {
    @Override
    public CodeResponse executeCode(CodeRequest request) {
        CompileAndRun compileAndRun = CompileFactory.getCompileAndRun(request.getLanguage());
        CodeResponse codeResponse = new CodeResponse();

        try {
            compileAndRun.compile(request.getCode());
            ExecuteResult run = compileAndRun.run(request.getJudgeCases());

            codeResponse.setState(0);
            codeResponse.setStderr(null);
            codeResponse.setUseTime(run.getUseTime());
            codeResponse.setUseMemory(123);
            codeResponse.setStdout(run.getOutput());
            return codeResponse;
        } catch (CompileException e) {
            codeResponse.setState(1);
            codeResponse.setStderr("Compile Error");
            return codeResponse;
        } catch (RuntimeException e) {
            codeResponse.setState(3);
            codeResponse.setStderr("System Error");
            return codeResponse;
        }
    }
}
