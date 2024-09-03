package com.onix.customers.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse<T> {
    private String message;
    private T data;
}
