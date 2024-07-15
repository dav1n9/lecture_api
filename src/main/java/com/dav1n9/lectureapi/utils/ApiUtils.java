package com.dav1n9.lectureapi.utils;

import com.dav1n9.lectureapi.dto.ApiResponse;

public class ApiUtils {
    public static <T> ApiResponse<T> SUCCESS (String message, T data) {
        return new ApiResponse<>(ResponseStatus.SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> ERROR (String message, T data) {
        return new ApiResponse<>(ResponseStatus.ERROR, message, data);
    }
}