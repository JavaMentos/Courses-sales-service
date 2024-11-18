package ru.home.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoursesSalesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesSalesServiceApplication.class, args);
    }

}
