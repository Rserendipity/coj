package com.cjj.coj.codesandbox.service.impl.dockercodebox.impl;

import cn.hutool.core.io.FileUtil;
import com.cjj.coj.codesandbox.exception.CompileCodeException;
import com.cjj.coj.codesandbox.exception.TimeOutException;
import com.cjj.coj.codesandbox.model.ExecuteResult;
import com.cjj.coj.codesandbox.service.impl.dockercodebox.DockerCompileAndRun;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JavaDockerCompileAndRun implements DockerCompileAndRun {
    private static final String IMAGE_NAME = "openjdk:8-alpine";
    private static final DockerClient client;

    private String compileContainerId;
    private String runContainerId;
    private String filepath;

    static {
        // 拉取镜像，制作运行环境
        client = DockerClientBuilder.getInstance().build();
//        PullImageResultCallback callback = new PullImageResultCallback() {
//            @Override
//            public void onNext(PullResponseItem item) {
//                super.onNext(item);
//            }
//        };
//        try {
//            client.pullImageCmd(IMAGE_NAME).exec(callback).awaitCompletion();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

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

        HostConfig config = new HostConfig();
        config.withBinds(new Bind(filepath, new Volume("/data"))) // 绑定路径映射
                .withCpuCount(CPU_COUNT)
                .withMemory(MAX_MEMORY_LIMIT)
                .withMemorySwap(0L);

        // 创建容器并得到容器ID
        compileContainerId = client.createContainerCmd(IMAGE_NAME)
                .withHostConfig(config)
                .withNetworkDisabled(true)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withTty(true)
                .exec()
                .getId();

        // 启动容器
        client.startContainerCmd(compileContainerId).exec();

        // 预构建 执行编译命令
        // docker exec d8199caa4c0b javac /data/Main.java
        String[] cmd = new String[]{"javac", "/data/Main.java"};
        String runId = client.execCreateCmd(compileContainerId)
                .withCmd(cmd)
                .withTty(true)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .exec()
                .getId();

        // 保存编译错误
        StringBuilder sb = new StringBuilder();
        boolean[] timeout = {true};
        try {
            // 执行编译
            client.execStartCmd(runId)
                    .withTty(true)
                    .exec(new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            if (frame != null) {
                                sb.append(new String(frame.getPayload())).append('\n');
                            }
                        }

                        @Override
                        public void onComplete() {
                            timeout[0] = false;
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // handle error here
                            throwable.printStackTrace();
                        }
                    })
                    .awaitCompletion(MAX_TIME_LIMIT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new CompileCodeException("未知错误");
        }

        // 编译报错
        if (sb.length() != 0) {
            throw new CompileCodeException(sb.toString());
        }

        // 编译超时
        if (timeout[0]) {
            throw new TimeOutException("编译超时");
        }
    }


//    @Override
//    public ExecuteResult run(List<String> judgeCases) {
//        ExecuteResult result = new ExecuteResult();
//        result.setUseMemory(0);
//        result.setUseTime(0);
//
//        HostConfig config = new HostConfig()
//                .withCpuCount(1L)
//                .withBinds(new Bind(String.format("%s", filepath), new Volume("/data")))
//                .withMemory(50L * 1024 * 1024); // 50 MB
//
//        // Create a container
//        CreateContainerResponse container = client.createContainerCmd(IMAGE_NAME)
//                .withCmd("java", "-classpath", "/data", "Main")
//                .withHostConfig(config)
//                .withTty(true)
//                .exec();
//
//        runContainerId = container.getId();
//        // Start the container
//        client.startContainerCmd(container.getId()).exec();
//
//        // Monitor the memory usage of the container
//        StatsCmd statsCmd = client.statsCmd(container.getId());
//        statsCmd.exec(new ResultCallback<Statistics>() {
//            @Override
//            public void onStart(Closeable closeable) {
//            }
//
//            @Override
//            public void onNext(Statistics object) {
//                Long memoryUsage = object.getMemoryStats().getUsage();
//                result.setUseMemory(Math.max(result.getUseMemory(), memoryUsage.intValue()) / 1000);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.err.println("Error while fetching stats: " + throwable.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("Stats command completed");
//            }
//
//            @Override
//            public void close() throws IOException {
//            }
//        });
//
//        // Your existing logic to interact with the container can go here
//        try {
//
//            Process exec = Runtime.getRuntime().exec(String.format("docker run --rm --memory=100m -i -v %s:/data %s java -classpath /data Main", filepath, IMAGE_NAME));
//
//            // 防止超时
//            new Thread(() -> {
//                try {
//                    Thread.sleep(5000L);
//                    if (exec.isAlive()) {
//                        exec.destroy();
//                        throw new TimeOutException("运行超时");
//                    }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }).start();
//
//            // input cases to string
//            StringBuilder in = new StringBuilder();
//            if (judgeCases != null) {
//                in.append(judgeCases.size()).append('\n');
//                judgeCases.forEach((judge) -> {
//                    in.append(judge).append('\n');
//                });
//            }
//
//            Long begin = System.currentTimeMillis();
//            exec.getOutputStream().write(in.toString().getBytes());
//            exec.getOutputStream().flush();
//            exec.getOutputStream().close();
//            exec.waitFor();
//            Long end = System.currentTimeMillis();
//            result.setUseTime(Math.toIntExact(end - begin));
//
//            // if run code error.
//            StringBuilder error = new StringBuilder();
//            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getErrorStream()))) {
//                String output;
//                while ((output = stdInput.readLine()) != null) {
//                    error.append(output).append('\n');
//                }
//            }
//            if (error.length() != 0) {
//                throw new RunCodeException(error.toString());
//            }
//
//            // run success.
//            List<String> outs = new ArrayList<>();
//            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getInputStream()))) {
//                String output;
//                while ((output = stdInput.readLine()) != null) {
//                    outs.add(output);
//                }
//            }
//            result.setOutput(outs);
//            exec.destroy();
//
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        return result;
//    }

    @Override
    public ExecuteResult run(List<String> judgeCases) {
        ExecuteResult result = new ExecuteResult();
        result.setUseMemory(0);
        result.setUseTime(0);

        HostConfig config = new HostConfig()
                .withCpuCount(1L)
                .withBinds(new Bind(String.format("%s", filepath), new Volume("/data")))
                .withMemory(50L * 1024 * 1024); // 50 MB

        // Create a container
        CreateContainerResponse container = client.createContainerCmd(IMAGE_NAME)
                .withCmd("java", "-classpath", "/data", "Main")
                .withHostConfig(config)
                .withTty(true)
                .withNetworkDisabled(true)
                .withStdinOpen(true)
                .withAttachStdout(true)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .exec();

        runContainerId = container.getId();
        // Start the container
        client.startContainerCmd(container.getId()).exec();

        // Monitor the memory usage of the container
        StatsCmd statsCmd = client.statsCmd(container.getId());
        statsCmd.exec(new ResultCallback<Statistics>() {
            @Override
            public void onStart(Closeable closeable) {
            }

            @Override
            public void onNext(Statistics object) {
                Long memoryUsage = object.getMemoryStats().getUsage();
                result.setUseMemory(Math.max(result.getUseMemory(), memoryUsage.intValue()) / 1000);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error while fetching stats: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Stats command completed");
            }

            @Override
            public void close() throws IOException {
            }
        });


        try {
            StringBuilder in = new StringBuilder();
            if (judgeCases != null) {
                in.append(judgeCases.size()).append('\n');
                judgeCases.forEach((judge) -> {
                    in.append(judge).append('\n');
                });
            }
            InputStream inputStream = new ByteArrayInputStream(in.toString().getBytes());

            client.execStartCmd(runContainerId)
//                    .withStdIn(inputStream)
                    .withTty(true)
                    .exec(new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            if (frame != null) {
//                                sb.append(new String(frame.getPayload())).append('\n');
                                System.out.println(new String(frame.getPayload()));
                            }
                        }

                        @Override
                        public void onComplete() {
//                            timeout[0] = false;
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // handle error here
                            throwable.printStackTrace();
                        }
                    })
                    .awaitCompletion();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void close() {
        if (filepath != null && !filepath.isEmpty()) {
            FileUtil.del(filepath);
        }

        if (compileContainerId != null && !compileContainerId.isEmpty()) {
            client.removeContainerCmd(compileContainerId).withForce(true).exec();
        }
        if (runContainerId != null && !runContainerId.isEmpty()) {
            client.removeContainerCmd(runContainerId).withForce(true).exec();
        }
    }

    public static void main(String[] args) {
        JavaDockerCompileAndRun run = new JavaDockerCompileAndRun();
        run.compile("import java.util.Scanner;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int n = scanner.nextInt();\n" +
                "        for (int i = 0; i < n; i++) {\n" +
                "            int a = scanner.nextInt();\n" +
                "            int b = scanner.nextInt();\n" +
                "            System.out.println(a + b);\n" +
                "        }\n" +
                "    }\n" +
                "}");

        ExecuteResult result = run.run(Arrays.asList("1 3", "2 4"));
        System.out.println("----------------------");
        System.out.println(result);
        System.out.println("----------------------");
    }

}
