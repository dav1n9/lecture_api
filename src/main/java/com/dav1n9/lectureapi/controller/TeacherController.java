package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.ApiResponse;
import com.dav1n9.lectureapi.dto.TeacherRequest;
import com.dav1n9.lectureapi.dto.TeacherResponse;
import com.dav1n9.lectureapi.service.TeacherService;
import com.dav1n9.lectureapi.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ApiResponse<TeacherResponse> saveTeacher(@RequestBody TeacherRequest request) {
        return ApiUtils.SUCCESS(null, teacherService.save(request));
    }
}
