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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaNativeCompileAndRun implements NativeCompileAndRun {
    @Override
    public String compile(String code) {
        String pathName = System.getProperty("user.dir") + File.separator + "test";

        // 如果文件夹不存在，则创建文件夹
        if (!FileUtil.exist(pathName)) {
            FileUtil.mkdir(pathName);
        }

        // 生成文件路径
        String filepath = pathName + File.separator + UUID.randomUUID();
        FileUtil.writeUtf8String(code, filepath + File.separator + "Main.java");

        // 编译
        try {
            Process exec = Runtime.getRuntime().exec(String.format("javac -encoding utf-8 %s", filepath + File.separator + "Main.java"));
            // 防止编译时间过长
            new Thread(()->{
                try {
                    Thread.sleep(MAX_TIME_LIMIT);
                    if (exec.isAlive()) {
                        exec.destroy(); // 超时则销毁进程
                    }
                } catch (InterruptedException e) {
                    delete(filepath);
                    throw new TimeOutException("编译超时，编译失败");
                }
            }).start();

            exec.waitFor();
            // 是否编译成功
            if (exec.exitValue() != 0) {
                // 读取错误信息
                BufferedReader stdError = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
                String s;
                StringBuilder sb = new StringBuilder();
                while ((s = stdError.readLine()) != null) {
                    sb.append(s).append("\n");
                }
                delete(filepath);
                throw new CompileCodeException(sb.toString());
            }
        } catch (IOException | InterruptedException e) {
            delete(filepath);
            throw new CompileCodeException("未知原因，编译失败");
        }
        return filepath;
    }

    @Override
    public ExecuteResult run(String path, List<String> judgeCases) {
        // 运行
        try {
            List<String> outs = new ArrayList<>();
            Long start = System.currentTimeMillis();
            // 输入测试用例
            Process exec = Runtime.getRuntime().exec(String.format("java -Dfile.encoding=UTF-8 -classpath %s %s", path, "Main"));

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
            result.setUseMemory((int)(Math.random()*20 + 100)); // todo
            return result;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(String path) {
        if (path != null && !path.isEmpty()) {
            FileUtil.del(path);
        }
    }
}
