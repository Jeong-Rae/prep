package io.prep.infrastructure.exception;

import lombok.Getter;

@Getter
public class InfrastructureException extends RuntimeException {
    private final ErrorCode errorCode;

    public InfrastructureException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public InfrastructureException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
