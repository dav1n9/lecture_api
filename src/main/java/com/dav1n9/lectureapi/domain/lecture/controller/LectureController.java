package com.dav1n9.lectureapi.domain.lecture.controller;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.domain.lecture.dto.LectureRequest;
import com.dav1n9.lectureapi.domain.lecture.dto.LectureResponse;
import com.dav1n9.lectureapi.domain.lecture.entity.Category;
import com.dav1n9.lectureapi.domain.lecture.entity.SortField;
import com.dav1n9.lectureapi.domain.lecture.service.LectureService;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lecture", description = "강의 추가, 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "강의 추가 api")
    public ApiResponse<LectureResponse> saveLecture(@RequestBody LectureRequest request) {
        return ApiUtils.SUCCESS(null, lectureService.save(request));
    }

    @GetMapping("/{lectureId}")
    @Operation(summary = "강의 단건조회 api")
    public ApiResponse<LectureResponse> findLecture(@PathVariable Long lectureId) {
        return ApiUtils.SUCCESS(null, lectureService.findById(lectureId));
    }

    @GetMapping
    @Operation(summary = "강의 카테고리별 조회 api, 정렬 기준 선택 가능")
    public ApiResponse<List<LectureResponse>> findLecture(@RequestParam Category category,
                                                          @RequestParam(required = false) SortField sort) {
        return ApiUtils.SUCCESS(null, lectureService.findByCategory(category, sort));
    }

}
