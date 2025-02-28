package io.prep.infrastructure.persistence.resume.repository;

import io.prep.core.resume.domain.Resume;
import io.prep.core.resume.repository.ResumeRepository;
import io.prep.infrastructure.persistence.resume.entity.ResumeEntity;
import io.prep.infrastructure.persistence.resume.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeJPARepository resumeJPARepository;

    @Override
    public Resume save(Resume resume) {
        ResumeEntity entity = ResumeMapper.toEntity(resume);

        ResumeEntity savedEntity = resumeJPARepository.save(entity);

        return ResumeMapper.toDomain(savedEntity);
    }
}
