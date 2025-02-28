package io.prep.infrastructure.persistence.resume.mapper;

import io.prep.core.resume.domain.Resume;
import io.prep.infrastructure.exception.ErrorCode;
import io.prep.infrastructure.exception.InfrastructureException;
import io.prep.infrastructure.persistence.resume.entity.ResumeEntity;

import java.net.MalformedURLException;
import java.net.URL;

public class ResumeMapper {
    public static ResumeEntity toEntity(final Resume domain) {
        return new ResumeEntity(
                domain.getId(),
                domain.getFilename(),
                domain.getFileType(),
                domain.getFileUrl(),
                domain.getUploadedAt()
        );
    }

    public static Resume toDomain(final ResumeEntity entity) {
        try {
            return Resume.builder()
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
