package io.prep.infrastructure.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    String upload(MultipartFile file);
}
