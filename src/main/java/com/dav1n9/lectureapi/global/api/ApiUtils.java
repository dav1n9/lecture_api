package com.dav1n9.lectureapi.global.api;

public class ApiUtils {
    public static <T> ApiResponse<T> SUCCESS (String message, T data) {
        return new ApiResponse<>(ResponseStatus.SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> ERROR (String message, T data) {
        return new ApiResponse<>(ResponseStatus.ERROR, message, data);
    }
}