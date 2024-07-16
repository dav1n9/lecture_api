package com.dav1n9.lectureapi.domain.teacher.dto;

import com.dav1n9.lectureapi.domain.teacher.entity.Teacher;
import lombok.Getter;

@Getter
public class TeacherRequest {
    private String name;
    private Integer career;
    private String company;
    private String phoneNumber;
    private String biography;

    public Teacher toEntity() {
        return Teacher.builder()
                .name(name)
                .career(career)
                .company(company)
                .phoneNumber(phoneNumber)
                .biography(biography)
                .build();
    }
}
