package com.cesecsh.small_ics_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.cesecsh.small_ics_system.mapper")
@SpringBootApplication
public class SmallIcsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallIcsSystemApplication.class, args);
    }

}
