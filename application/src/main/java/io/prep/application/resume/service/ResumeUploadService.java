package io.prep.application.resume.service;

import io.prep.application.exception.ApplicationException;
import io.prep.application.exception.ErrorCode;
import io.prep.core.exception.CoreException;
import io.prep.infrastructure.exception.InfrastructureException;
import io.prep.infrastructure.filestorage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class ResumeUploadService {

    private final FileStorage fileStorage;

    public URL uploadAndSaveResume(MultipartFile file) {
        try {
            URL fileUrl = new URL(fileStorage.upload(file));

            return fileUrl;
        } catch (CoreException | InfrastructureException exception) {
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
