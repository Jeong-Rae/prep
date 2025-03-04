package io.prep.core.resume.repository;

import io.prep.core.resume.domain.ResumeStorage;

public interface ResumeStorageRepository {
    ResumeStorage save(ResumeStorage resumeStorage);
}
