package com.cjj.coj;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootTest
@EnableAsync
public class AsyncTests {

    @Async
    public void task() {
        System.out.println("test1...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test2...");
    }

    @Test
    @Async
    public void testAsync() {
        System.out.println("start");
        task();
        System.out.println("end");
    }

}
