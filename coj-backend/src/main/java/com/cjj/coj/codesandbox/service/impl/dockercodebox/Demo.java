package com.cjj.coj.codesandbox.service.impl.dockercodebox;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.AttachContainerResultCallback;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.StatsCmdImpl;

public class Demo {

    public static void main(String[] args) throws InterruptedException, IOException {
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        HostConfig config = new HostConfig()
                .withCpuCount(1L)
                .withBinds(new Bind("/home/a/code/coj/test/1d10c7df-eb3a-4d95-b0bd-d1cc53e1473a", new Volume("/data")))
                .withMemory(10L * 1024 * 1024); // 10 MB

        // Create a container
        CreateContainerResponse container = dockerClient.createContainerCmd("openjdk:8-alpine")
                .withCmd("java", "-classpath", "/data", "Main")
                .withHostConfig(config)
                .withTty(true)
                .exec();

        // Start the container
        dockerClient.startContainerCmd(container.getId()).exec();

        // Monitor the memory usage of the container
        StatsCmd statsCmd = dockerClient.statsCmd(container.getId());
        statsCmd.exec(new ResultCallback<Statistics>() {
            @Override
            public void onStart(Closeable closeable) {
            }

            @Override
            public void onNext(Statistics object) {
                Long memoryUsage = object.getMemoryStats().getUsage();
                // Convert to MB
                double memoryUsageInMb = memoryUsage / (1024.0 * 1024.0);
                System.out.printf("Memory usage: %.2f MB\n", memoryUsageInMb);

                // You can close the stats command if you no longer need to monitor the stats
                // closeable.close();
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

        // Your existing logic to interact with the container can go here
        Process exec = Runtime.getRuntime().exec("docker run --rm --cpus=1 --memory=10m -i -v /home/a/code/coj/test/1d10c7df-eb3a-4d95-b0bd-d1cc53e1473a:/data openjdk:8-alpine java -classpath /data Main");

        exec.getOutputStream().write("1\n1 3\n".getBytes());
        exec.getOutputStream().flush();
        exec.getOutputStream().close();
        exec.waitFor();
        System.out.println("--------------Error---------------");
        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getErrorStream()))) {
            String output;
            while ((output = stdInput.readLine()) != null) {
                System.out.println(output);
            }
        }
        System.out.println("--------------Error---------------");
        System.out.println();
        System.out.println("--------------Stdout---------------");
        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(exec.getInputStream()))) {
            String output;
            while ((output = stdInput.readLine()) != null) {
                System.out.println(output);
            }
        }
        System.out.println("--------------Stdout---------------");

        // Clean up
        dockerClient.stopContainerCmd(container.getId()).exec();
        dockerClient.removeContainerCmd(container.getId()).exec();
    }
}
