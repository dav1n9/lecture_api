package com.dav1n9.lectureapi.global.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final ResponseStatus status;
    private final String message;
    private final T data;
}
