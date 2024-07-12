package com.dav1n9.lectureapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LectureApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LectureApiApplication.class, args);
    }

}
