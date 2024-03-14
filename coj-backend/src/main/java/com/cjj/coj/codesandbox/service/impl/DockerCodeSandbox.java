package com.cjj.coj.codesandbox.service.impl;

import com.cjj.coj.codesandbox.exception.CompileCodeException;
import com.cjj.coj.codesandbox.exception.RunCodeException;
import com.cjj.coj.codesandbox.exception.TimeOutException;
import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.model.web.CodeRequest;
import com.cjj.coj.codesandbox.model.web.CodeResponse;
import com.cjj.coj.codesandbox.service.CodeSandbox;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.DockerCompileAndRun;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.DockerCompileFactory;
import com.cjj.coj.codesandbox.service.impl.nativecodebox.NativeCompileAndRun;

import java.util.List;

public class DockerCodeSandbox implements CodeSandbox {
    @Override
    public CodeResponse executeCode(CodeRequest request) {
        String code = request.getCode();
        String language = request.getLanguage();
        List<String> judgeCases = request.getJudgeCases();
        CodeResponse codeResponse = new CodeResponse();

        DockerCompileAndRun compileAndRun = DockerCompileFactory.getCompileAndRun(language);

        ExecuteResult result;
        String path = null;
        try {
            // 编译运行代码
            path = compileAndRun.compile(code);
            result = compileAndRun.run(path, judgeCases);
        } catch (CompileCodeException e) {
            // 编译异常
            codeResponse.setState(1);
            codeResponse.setStderr(e.getMessage());
            codeResponse.setStdout(null);
            codeResponse.setUseTime(null);
            codeResponse.setUseMemory(null);
            return codeResponse;
        } catch (RunCodeException e) {
            // 运行异常
            codeResponse.setState(2);
            codeResponse.setStderr(e.getMessage());
            codeResponse.setStdout(null);
            codeResponse.setUseTime(null);
            codeResponse.setUseMemory(null);
            return codeResponse;
        } catch (TimeOutException e) {
            // 超时异常
            codeResponse.setState(0);
            codeResponse.setStderr(e.getMessage());
            codeResponse.setStdout(null);
            codeResponse.setUseTime(NativeCompileAndRun.MAX_TIME_LIMIT);
            codeResponse.setUseMemory(null);
            return codeResponse;
        } finally {
            // 删除临时文件
            if (path != null) {
                compileAndRun.delete(path);
            }
        }

        // 没有发生 编译 / 运行 异常，返回正常结果
        codeResponse.setState(0);
        codeResponse.setStderr(null);
        codeResponse.setStdout(result.getOutput());
        codeResponse.setUseTime(result.getUseTime());
        codeResponse.setUseMemory(result.getUseMemory());
        return codeResponse;
    }
}
