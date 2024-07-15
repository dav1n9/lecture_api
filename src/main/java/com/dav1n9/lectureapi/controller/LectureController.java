package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.ApiResponse;
import com.dav1n9.lectureapi.dto.LectureRequest;
import com.dav1n9.lectureapi.dto.LectureResponse;
import com.dav1n9.lectureapi.entity.Category;
import com.dav1n9.lectureapi.entity.SortField;
import com.dav1n9.lectureapi.service.LectureService;
import com.dav1n9.lectureapi.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ApiResponse<LectureResponse> saveLecture(@RequestBody LectureRequest request) {
        return ApiUtils.SUCCESS(null, lectureService.save(request));
    }

    @GetMapping("/{lectureId}")
    public ApiResponse<LectureResponse> findLecture(@PathVariable Long lectureId) {
        return ApiUtils.SUCCESS(null, lectureService.findById(lectureId));
    }

    @GetMapping
    public ApiResponse<List<LectureResponse>> findLecture(@RequestParam Category category,
                                                          @RequestParam(required = false) SortField sort) {
        return ApiUtils.SUCCESS(null, lectureService.findByCategory(category, sort));
    }

}
