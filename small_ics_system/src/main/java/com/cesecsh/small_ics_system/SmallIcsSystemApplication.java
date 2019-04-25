package com.cesecsh.small_ics_system;

import com.cesecsh.small_ics_system.socket.Reactor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@MapperScan(basePackages = "com.cesecsh.small_ics_system.mapper")
@SpringBootApplication
public class SmallIcsSystemApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SmallIcsSystemApplication.class, args);
        Reactor temp = new Reactor(20008);
        temp.run();
    }

}
