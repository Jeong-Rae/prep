package io.prep.infrastructure.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FAILED_SAVE("ERR101", "저장에 실패"),
    FAILED_MAPPING("ERR102", "맵핑에 실패"),
    FAILED_CALL_LLM_API("ERR103", "LLM API 호출에 실패"),
    ;


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
