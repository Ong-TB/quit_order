package com.example.quit;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author Wang Zhiming
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.example.quit.mapper")
@ServletComponentScan
public class QuitOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuitOrderApplication.class, args);
        log.info("Launched QuitOrder");
    }

}
