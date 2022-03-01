package com.example.backswim.common.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusEnum {

    NOT_FOUND("NOT_FOUND"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    BAD_REQUEST("BAD_REQEUST"),
    PARAMETER_TYPE_ERROR("PARAMETER_TYPE_ERROR"),
    OK("OK"),
    TIME_OUT("TIME_OUT"),
    ALREADY_AUTH("ALREADY_AUTH"),
    AUTH_FIRST("EMAIL_AUTH_FIRST"),
    WRONG_PASSWORD("WRONG_PASSWORD");


    private final String message;
}
