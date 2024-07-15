package com.dav1n9.lectureapi.exception;

import com.dav1n9.lectureapi.dto.ApiResponse;
import com.dav1n9.lectureapi.utils.ApiUtils;
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
