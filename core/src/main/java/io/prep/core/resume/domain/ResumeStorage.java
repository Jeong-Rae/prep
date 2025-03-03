package io.prep.core.resume.domain;

import io.prep.core.exception.CoreException;
import io.prep.core.exception.ErrorCode;
import io.prep.core.resume.FileType;
import io.prep.core.util.UniqueIdentityGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@ToString()
public class ResumeStorage {
    private final UUID id;

    @NotBlank
    private final String filename;

    @NotNull
    private final FileType fileType;

    @NotNull
    private final URL fileUrl;

    @NotNull
    private final LocalDateTime uploadedAt;


    @Builder(toBuilder = true)
    private ResumeStorage(
            final UUID id,
            final String filename,
            final FileType fileType,
            final URL fileUrl,
            final LocalDateTime uploadedAt) {
        if (id == null) {
            this.id = UniqueIdentityGenerator.generate();
        } else {
            this.id = id;
        }
        this.filename = filename;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.uploadedAt = uploadedAt;

    }

    public ResumeStorage updateFileUrl(final URL fileUrl) {
        if (fileUrl == null) {
            throw new CoreException(ErrorCode.INVALID_ARGUMENT);
        }

        if (this.fileUrl.sameFile(fileUrl)) {
            return this;
        }

        return ResumeStorage.builder().fileUrl(fileUrl).uploadedAt(LocalDateTime.now()).build();
    }

}
