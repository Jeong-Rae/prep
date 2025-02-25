package io.prep.core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_ARGUMENT("ERR001", "올바르지 않은 인자가 전달");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
