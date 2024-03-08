package com.cjj.coj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 开启异步调用
@MapperScan(value = "com.cjj.coj.mapper")
public class CojBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CojBackendApplication.class, args);
    }

}
