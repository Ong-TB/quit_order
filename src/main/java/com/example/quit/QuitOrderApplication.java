package com.example.quit_order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class QuitOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuitOrderApplication.class, args);
        log.info("Launched QuitOrder");
    }

}
