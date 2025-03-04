package io.prep.infrastructure.persistence.resume.repository;

import io.prep.infrastructure.persistence.resume.entity.ResumeStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResumeStorageJPARepository extends JpaRepository<ResumeStorageEntity, UUID> {

}
