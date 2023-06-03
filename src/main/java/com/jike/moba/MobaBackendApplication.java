package com.jike.moba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author JIke
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.jike.moba.mapper")
public class MobaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobaBackendApplication.class, args);
    }
}
