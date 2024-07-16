package com.dav1n9.lectureapi.global.exception;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<ErrorResponse> handleIllegalArgumentException (IllegalArgumentException ex) {
        return ApiUtils.ERROR(ex.getMessage(), null);
    }
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<ErrorResponse> handleNullPointerException (NullPointerException ex) {
        return ApiUtils.ERROR(ex.getMessage(), null);
    }
}
