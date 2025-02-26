package io.prep.infrastructure.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FAILED_SAVE("ERR101", "저장에 실패");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
