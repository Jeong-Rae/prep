package io.prep.infrastructure.persistence.resume.repository;

import io.prep.infrastructure.persistence.resume.entity.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResumeJPARepository extends JpaRepository<ResumeEntity, UUID> {

}
