package com.sudongzhao.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.sudongzhao.server.mapper")
@EnableScheduling
public class JebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JebApplication.class,args);
    }
}
