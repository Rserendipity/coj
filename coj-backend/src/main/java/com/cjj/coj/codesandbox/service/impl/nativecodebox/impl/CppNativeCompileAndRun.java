package com.cjj.coj.codesandbox.service.impl.nativecodebox.impl;

import cn.hutool.core.io.FileUtil;
import com.cjj.coj.codesandbox.exception.CompileCodeException;
import com.cjj.coj.codesandbox.exception.TimeOutException;
import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.impl.nativecodebox.NativeCompileAndRun;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CppNativeCompileAndRun implements NativeCompileAndRun {
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
        FileUtil.writeUtf8String(code, filepath + File.separator + "main.cpp");

        // 编译
        try {
            String fullName = filepath + File.separator;
            String s = String.format("g++ %s -o %s -O2", fullName + "main.cpp", fullName + "main");

            Process exec = Runtime.getRuntime().exec(s);

            // 防止编译时间过长
            new Thread(() -> {
                try {
                    Thread.sleep(MAX_TIME_LIMIT);
                    if (exec.isAlive()) {
                        exec.destroy(); // 超时则销毁进程
                    }
                } catch (InterruptedException e) {
                    throw new TimeOutException("编译超时，编译失败");
                }
            }).start();

            exec.waitFor();
            // 是否编译成功
            if (exec.exitValue() != 0) {
                // 读取错误信息
                BufferedReader stdError = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                while ((s = stdError.readLine()) != null) {
                    sb.append(s).append("\n");
                }
                throw new CompileCodeException(sb.toString());
            }
        } catch (IOException | InterruptedException e) {
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
            String s = String.format("%s%s", filepath + File.separator, "main");
            Process exec = Runtime.getRuntime().exec(s);

            // 防止运行时间过长
            new Thread(()->{
                try {
                    Thread.sleep(MAX_TIME_LIMIT);
                    if (exec.isAlive()) {
                        exec.destroy(); // 超时则销毁进程
                    }
                } catch (InterruptedException e) {
                    throw new TimeOutException("运行超时，运行失败");
                }
            }).start();

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
            result.setUseMemory((int) (Math.random() * 20 + 2)); // todo
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            FileUtil.del(filepath);
        }
    }

    @Override
    public void close() {
        if (filepath != null && !filepath.isEmpty()) {
            FileUtil.del(filepath);
        }
    }
}
