package com.cjj.coj.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Test {
    @Async
    public void test() throws InterruptedException {
        System.out.println("test1");
        Thread.sleep(1000);
        System.out.println("test2");
    }
}
