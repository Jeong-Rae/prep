package io.prep.infrastructure.persistence.resume.entity;

import io.prep.core.resume.FileType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "resume")
public class ResumeEntity {
    @Id
    @Column(name = "resume_id", columnDefinition = "BINARY(16)", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    /**
     * URL 타입을 String 으로 변환해서 넣어줘야함
     * ORM 의존분리
     */
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Builder
    public ResumeEntity(final UUID id,
                        final String fileName,
                        final FileType fileType,
                        final LocalDateTime uploadedAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
    }
}
