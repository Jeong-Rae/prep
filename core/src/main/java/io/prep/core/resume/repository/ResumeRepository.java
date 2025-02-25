package io.prep.core.resume.repository;

import io.prep.core.resume.domain.Resume;

public interface ResumeRepository {
    Resume save(Resume resume);
}
