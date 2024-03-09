package com.cjj.coj.codesandbox.service.impl;

import cn.hutool.core.io.FileUtil;
import com.cjj.coj.codesandbox.exception.CompileException;
import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.CompileAndRun;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaCompileAndRun implements CompileAndRun {
    private String filepath;

    @Override
    public void compile(String code) {

        String pathName = System.getProperty("user.dir") + File.separator + "test";

        // 如果文件夹不存在，则创建文件夹
        if (!FileUtil.exist(pathName)) {
            FileUtil.mkdir(pathName);
        }

        // 生成文件路径
        filepath = pathName + File.separator + UUID.randomUUID();
        FileUtil.writeUtf8String(code, filepath + File.separator + "Main.java");

        // 编译
        try {
            Process exec = Runtime.getRuntime().exec(String.format("javac %s", filepath + File.separator + "Main.java"));
            exec.waitFor();
            // 是否编译成功
            if (exec.exitValue() != 0) {
                throw new CompileException("Compile Error");
            }
        } catch (IOException | InterruptedException e) {
            // 删除文件
            FileUtil.del(filepath);
            throw new RuntimeException();
        }
    }

    @Override
    public ExecuteResult run(List<String> judgeCases) {
        // 运行
        try {
            List<String> outs = new ArrayList<>();
            Long start = System.currentTimeMillis();
            // 输入测试用例
            Process exec = Runtime.getRuntime().exec(String.format("java -classpath %s %s", filepath, "Main"));

            exec.getOutputStream().write((judgeCases.size() + "\n").getBytes());
            exec.getOutputStream().flush();

            StringBuilder sb = new StringBuilder();
            for (String judgeCase : judgeCases) {
                sb.append(judgeCase).append("\n");
            }
            exec.getOutputStream().write(sb.toString().getBytes());
            exec.getOutputStream().flush();
            exec.getOutputStream().close();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String output;
            while ((output = stdInput.readLine()) != null) {
                outs.add(output);
            }

            Long end = System.currentTimeMillis();
            ExecuteResult result = new ExecuteResult();
            result.setOutput(outs);
            result.setUseTime(Math.toIntExact(end - start));
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            // 删除文件
            FileUtil.del(filepath);
        }
    }
}
