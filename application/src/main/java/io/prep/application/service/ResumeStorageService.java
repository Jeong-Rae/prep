package io.prep.application.service;

import io.prep.application.exception.ApplicationException;
import io.prep.application.exception.ErrorCode;
import io.prep.core.exception.CoreException;
import io.prep.core.resume.FileType;
import io.prep.core.resume.domain.ResumeStorage;
import io.prep.core.resume.repository.ResumeStorageRepository;
import io.prep.core.util.FilenameUtils;
import io.prep.infrastructure.exception.InfrastructureException;
import io.prep.infrastructure.filestorage.FileStorage;
import io.prep.infrastructure.llm.OpenAiLlmClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ResumeStorageService {

    private final static Logger logger = Logger.getLogger(ResumeStorageService.class.getName());

    private final FileStorage fileStorage;
    private final ResumeStorageRepository resumeStorageRepository;
    private final OpenAiLlmClient llmClient;

    @Transactional
    public URL uploadAndSaveResume(MultipartFile file) {
        try {
            URL fileUrl = new URL(fileStorage.upload(file));
            String filename = file.getOriginalFilename();

            ResumeStorage resumeStorage = ResumeStorage.builder()
                                                       .filename(filename)
                                                       .fileType(FileType.fromExtension(
                                                               FilenameUtils.getExtension(filename)))
                                                       .fileUrl(fileUrl)
                                                       .uploadedAt(LocalDateTime.now())
                                                       .build();

            resumeStorageRepository.save(resumeStorage);

            String id = llmClient.uploadFile(file);
            logger.info(id);

            return fileUrl;
        } catch (CoreException | InfrastructureException exception) {
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, exception);
        } catch (IllegalArgumentException exception) {
            throw new ApplicationException(ErrorCode.INVALID_ARGUMENT, exception);
        } catch (Exception exception) {
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, exception);
        }
    }
}
