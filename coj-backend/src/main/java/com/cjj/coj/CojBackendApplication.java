package com.cjj.coj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.cjj.coj.mapper")
public class CojBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CojBackendApplication.class, args);
    }

}
