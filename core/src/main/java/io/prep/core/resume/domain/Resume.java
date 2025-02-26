package io.prep.core.resume.domain;

import io.prep.core.exception.CoreException;
import io.prep.core.exception.ErrorCode;
import io.prep.core.resume.FileType;
import io.prep.core.util.UniqueIdentityGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
public class Resume {
    private final UUID id;

    @NotBlank
    private final String fileName;

    @NotNull
    private final FileType fileType;

    @NotNull
    private final URL fileUrl;

    @NotNull
    private final LocalDateTime uploadedAt;


    @Builder(toBuilder = true)
    private Resume(
            final UUID id,
            final String fileName,
            final FileType fileType,
            final URL fileUrl,
            final LocalDateTime uploadedAt) {
        if (id == null) {
            this.id = UniqueIdentityGenerator.generate();
        } else {
            this.id = id;
        }
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.uploadedAt = uploadedAt;
    }

    public Resume updateFileUrl(final URL fileUrl) {
        if (fileUrl == null) {
            throw new CoreException(ErrorCode.INVALID_ARGUMENT);
        }

        if (this.fileUrl.sameFile(fileUrl)) {
            return this;
        }

        return Resume.builder().fileUrl(fileUrl).uploadedAt(LocalDateTime.now()).build();
    }

}
