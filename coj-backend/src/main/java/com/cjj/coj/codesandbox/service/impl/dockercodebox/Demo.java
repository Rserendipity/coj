package com.cjj.coj.codesandbox.service.impl.dockercodebox;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        DockerClient client = DockerClientBuilder.getInstance().build();
        PullImageResultCallback callback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                System.out.println(item.getStatus());
                super.onNext(item);
            }
        };
        client.pullImageCmd("openjdk:8-alpine").exec(callback).awaitCompletion();
    }
}
