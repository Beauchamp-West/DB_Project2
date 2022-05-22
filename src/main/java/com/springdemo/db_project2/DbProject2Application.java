package com.springdemo.db_project2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.springdemo.db_project2.dao")
public class DbProject2Application {

    public static void main(String[] args) {
        SpringApplication.run(DbProject2Application.class, args);
    }

}
