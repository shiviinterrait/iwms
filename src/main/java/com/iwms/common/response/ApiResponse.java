package com.iwms.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
}
//instead of every Api returning a different strutur .
//we have standard response format ..then emp,warehouse,product using same response structure