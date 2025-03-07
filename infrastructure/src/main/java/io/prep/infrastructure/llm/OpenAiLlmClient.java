package io.prep.infrastructure.llm;

import com.openai.client.OpenAIClient;
import com.openai.models.Thread;
import com.openai.models.*;
import com.openai.services.blocking.FileService;
import com.openai.services.blocking.beta.ThreadService;
import com.openai.services.blocking.beta.threads.RunService;
import io.prep.infrastructure.exception.ErrorCode;
import io.prep.infrastructure.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OpenAiLlmClient {
    private final static Logger logger = LoggerFactory.getLogger(OpenAiLlmClient.class);

    private final OpenAIClient openAIClient;

    public String uploadFile(MultipartFile file) {
        try {
            FileService fileService = openAIClient.files();

            FileObject fileObject = fileService.create(FileCreateParams.builder()
                                                                       .file(file.getBytes())
                                                                       .purpose(FilePurpose.ASSISTANTS)
                                                                       .build());

            fileObject.validate();

            return fileObject.id();
        } catch (IOException exception) {
            throw new InfrastructureException(ErrorCode.FAILED_CALL_LLM_API, exception);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw new InfrastructureException(ErrorCode.FAILED_CALL_LLM_API, exception);
        }
    }

    public String createThread(String userMessage, String fileId) {
        try {


            ThreadService threadService = openAIClient.beta().threads();

            BetaThreadCreateParams.Message.Attachment attachment = BetaThreadCreateParams.Message.Attachment.builder()
                                                                                                            .fileId(fileId)
                                                                                                            .addToolFileSearch()
                                                                                                            .build();
            BetaThreadCreateParams.Message message = BetaThreadCreateParams.Message.builder()
                                                                                   .content(userMessage)
                                                                                   .role(BetaThreadCreateParams.Message.Role.USER)
                                                                                   .addAttachment(attachment)
                                                                                   .build();


            Thread thread = threadService.create(BetaThreadCreateParams.builder().addMessage(message).build());
            logger.debug("Thread created: {}", thread);

            thread.validate();


            return thread.id();
        } catch (Exception exception) {
            throw new InfrastructureException(ErrorCode.FAILED_CALL_LLM_API, exception);
        }
    }

    public void run(String assistantId, String threadId) {
        try {
            RunService runService = openAIClient.beta().threads().runs();

            Run run = runService.create(
                    BetaThreadRunCreateParams.builder()
                                             .threadId(threadId)
                                             .addInclude(RunStepInclude.STEP_DETAILS_TOOL_CALLS_FILE_SEARCH_RESULTS_CONTENT)
                                             .assistantId(assistantId)
                                             .model(ChatModel.O3_MINI)
                                             .reasoningEffort(BetaThreadRunCreateParams.ReasoningEffort.LOW)
                                             .temperature(1.0)
                                             .topP(1.0)
                                             .truncationStrategy( // 1개 메시지만 가능한 유지
                                                                  BetaThreadRunCreateParams.TruncationStrategy.builder()
                                                                                                              .type(BetaThreadRunCreateParams.TruncationStrategy.Type.AUTO)
                                                                                                              .lastMessages(
                                                                                                                      1L)
                                                                                                              .build()
                                             )
                                             .build()
            );

            run.validate();
            logger.info("Run created: {}", run);
        } catch (Exception exception) {
            throw new InfrastructureException(ErrorCode.FAILED_CALL_LLM_API, exception);
        }
    }


}
