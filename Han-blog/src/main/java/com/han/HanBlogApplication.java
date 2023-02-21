package com.han;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.han.mapper")
@EnableScheduling
public class HanBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(HanBlogApplication.class,args);
    }
}
