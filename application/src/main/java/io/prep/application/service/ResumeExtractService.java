package io.prep.application.service;

import io.prep.infrastructure.llm.OpenAiLlmClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ResumeExtractService {
    private final static String RESUME_EXTRACTOR_ASSISTANT_ID = "asst_ZbA7sqGJjPimzLNy5QYszN87";
    private final static String EXTRACT_INSTRUCTION = "Please extract the resume";

    private final Logger logger = LoggerFactory.getLogger(ResumeExtractService.class);

    private final OpenAiLlmClient llmClient;

    public void extractResumeFromFile(MultipartFile multipartFile) {
        try {
            String uploadedResumeId = llmClient.uploadFile(multipartFile);
            logger.info("file id: {}", uploadedResumeId);
            String threadId = llmClient.createThread(EXTRACT_INSTRUCTION, uploadedResumeId);
            logger.info("thread id: {}", threadId);
            String runId = llmClient.runAssistant(RESUME_EXTRACTOR_ASSISTANT_ID, threadId);
            logger.info("Assistant invoked run id: {}", runId);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
        }
    }
}
