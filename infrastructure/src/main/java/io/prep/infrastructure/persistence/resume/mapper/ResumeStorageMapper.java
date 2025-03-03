package io.prep.infrastructure.persistence.resume.mapper;

import io.prep.core.resume.domain.ResumeStorage;
import io.prep.infrastructure.exception.ErrorCode;
import io.prep.infrastructure.exception.InfrastructureException;
import io.prep.infrastructure.persistence.resume.entity.ResumeStorageEntity;

import java.net.MalformedURLException;
import java.net.URL;

public class ResumeStorageMapper {
    public static ResumeStorageEntity toEntity(final ResumeStorage domain) {
        return new ResumeStorageEntity(
                domain.getId(),
                domain.getFilename(),
                domain.getFileType(),
                domain.getFileUrl(),
                domain.getUploadedAt()
        );
    }

    public static ResumeStorage toDomain(final ResumeStorageEntity entity) {
        try {
            return ResumeStorage.builder()
                                .id(entity.getId())
                                .filename(entity.getFilename())
                                .fileType(entity.getFileType())
                                .fileUrl(new URL(entity.getFileUrl()))
                                .uploadedAt(entity.getUploadedAt())
                                .build();
        } catch (MalformedURLException exception) {
            throw new InfrastructureException(ErrorCode.FAILED_MAPPING);
        }
    }
}
