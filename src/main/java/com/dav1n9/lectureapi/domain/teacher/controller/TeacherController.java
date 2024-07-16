package com.dav1n9.lectureapi.domain.teacher.controller;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.domain.teacher.dto.TeacherRequest;
import com.dav1n9.lectureapi.domain.teacher.dto.TeacherResponse;
import com.dav1n9.lectureapi.domain.teacher.service.TeacherService;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Teacher", description = "강사 추가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "강사 추가 api")
    public ApiResponse<TeacherResponse> saveTeacher(@RequestBody TeacherRequest request) {
        return ApiUtils.SUCCESS(null, teacherService.save(request));
    }
}
