package com.indinner.wallserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.indinner.wallserver"})
public class WallServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallServerApplication.class, args);
    }

}
