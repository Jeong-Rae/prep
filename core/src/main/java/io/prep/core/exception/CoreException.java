package io.prep.core.exception;

import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {
    private final ErrorCode errorCode;

    public CoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CoreException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
