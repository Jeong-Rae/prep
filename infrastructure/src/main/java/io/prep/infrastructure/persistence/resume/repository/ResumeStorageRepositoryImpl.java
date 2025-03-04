package io.prep.infrastructure.persistence.resume.repository;

import io.prep.core.resume.domain.ResumeStorage;
import io.prep.core.resume.repository.ResumeStorageRepository;
import io.prep.infrastructure.persistence.resume.entity.ResumeStorageEntity;
import io.prep.infrastructure.persistence.resume.mapper.ResumeStorageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeStorageRepositoryImpl implements ResumeStorageRepository {

    private final ResumeStorageJPARepository resumeStorageJPARepository;

    @Override
    public ResumeStorage save(ResumeStorage resumeStorage) {
        ResumeStorageEntity entity = ResumeStorageMapper.toEntity(resumeStorage);

        ResumeStorageEntity savedEntity = resumeStorageJPARepository.save(entity);

        return io.prep.infrastructure.persistence.resume.mapper.ResumeStorageMapper.toDomain(
                savedEntity);
    }
}
