package io.prep.core.resume.domain;

import io.prep.core.exception.CoreException;
import io.prep.core.exception.ErrorCode;
import io.prep.core.util.UniqueIdentityGenerator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder(toBuilder = true)
public class Resume {
    private final UUID id = UniqueIdentityGenerator.generate();
    private final String s3Url;
    private final LocalDateTime uploadedAt;

    public Resume updateS3Url(String s3Url) {
        if (s3Url == null || s3Url.isBlank()) {
            throw new CoreException(ErrorCode.INVALID_ARGUMENT);
        }

        if (this.s3Url.equals(s3Url)) return this;


        return this.toBuilder()
                .s3Url(s3Url)
                .uploadedAt(LocalDateTime.now())
                .build();
    }
}
