package ru.home.courses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class CoursesSalesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesSalesServiceApplication.class, args);
        log.info("STARTING APP");
    }

}
