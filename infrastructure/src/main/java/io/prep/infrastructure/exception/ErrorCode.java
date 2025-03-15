package io.prep.infrastructure.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FAILED_SAVE("ERR101", "저장에 실패"),
    FAILED_MAPPING("ERR102", "맵핑에 실패"),
    FAILED_UPLOAD_FILE_TO_LLM("ERR103", "LLM에 파일 업로드에 실패"),
    FAILED_CREATE_THREAD("ERR104", "스레드 생성에 실패"),
    FAILED_RUN_ASSISTANT("ERR105", "Assistant 실행에 실패"),
    FAILED_EXTRACT_VALUE("ERR106", "값 추출에 실패");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
