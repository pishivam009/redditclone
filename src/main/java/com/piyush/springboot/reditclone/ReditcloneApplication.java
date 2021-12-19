package com.piyush.springboot.reditclone;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReditcloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.piyush.springboot.reditclone.ReditcloneApplication.class, args);
    }

}
