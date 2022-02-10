package com.example.backswim.pool.apiresult.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusEnum {

    NOT_FOUND("NOT_FOUND"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    BAD_REQUEST("BAD_REQEUST"),
    PARAMETER_TYPE_ERROR("PARAMETER_TYPE_ERROR"),
    OK("OK");


    private final String message;
}
