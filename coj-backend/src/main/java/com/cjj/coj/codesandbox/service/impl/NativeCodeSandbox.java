package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.exception.CompileException;
import com.cjj.coj.codesandbox.exception.TimeOutException;
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

        Thread taskThread = runUserCode(request, compileAndRun, codeResponse);

        // 主线程等待，超过三秒则中断
        try {
            taskThread.start();
            taskThread.join(5000);

            if (taskThread.isAlive()) {
                taskThread.interrupt();
                codeResponse.setState(0);
                codeResponse.setUseTime(5000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TimeOutException("Execution interrupted");
        }

        return codeResponse;
    }

    private static Thread runUserCode(CodeRequest request, CompileAndRun compileAndRun, CodeResponse codeResponse) {
        Runnable task = () -> {
            try {
                compileAndRun.compile(request.getCode());
                ExecuteResult run = compileAndRun.run(request.getJudgeCases());
                codeResponse.setState(0);
                codeResponse.setStderr(null);
                codeResponse.setUseTime(run.getUseTime());
                codeResponse.setUseMemory(123);
                codeResponse.setStdout(run.getOutput());
            } catch (CompileException e) {
                codeResponse.setState(1);
                codeResponse.setStderr("Compile Error");
            } catch (RuntimeException e) {
                codeResponse.setState(3);
                codeResponse.setStderr("System Error");
            }
        };

        return new Thread(task);
    }
}