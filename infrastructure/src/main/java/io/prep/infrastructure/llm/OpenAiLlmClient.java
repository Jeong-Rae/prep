package io.prep.infrastructure.llm;

import com.openai.client.OpenAIClient;
import com.openai.core.http.StreamResponse;
import com.openai.models.Thread;
import com.openai.models.*;
import com.openai.services.blocking.beta.ThreadService;
import com.openai.services.blocking.beta.threads.RunService;
import io.prep.infrastructure.exception.ErrorCode;
import io.prep.infrastructure.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OpenAiLlmClient {
    private final static Logger logger = LoggerFactory.getLogger(OpenAiLlmClient.class);
    private final static String sampleFile = "D:\\prep\\infrastructure\\src\\main\\java\\io\\prep\\infrastructure\\llm\\resume.pdf";
    private final OpenAIClient openAIClient;

    public String uploadFile(MultipartFile file) {
        try {
            FileCreateParams params = FileCreateParams.builder()
                                                      .file(Paths.get(sampleFile))
                                                      .purpose(FilePurpose.ASSISTANTS)
                                                      .build();

            FileObject fileObject = openAIClient.files().create(params);

            return fileObject.id();
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw new InfrastructureException(ErrorCode.FAILED_UPLOAD_FILE_TO_LLM, exception);
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
            logger.info("Thread created: {}", thread);

            return thread.id();
        } catch (Exception exception) {
            throw new InfrastructureException(ErrorCode.FAILED_CREATE_THREAD, exception);
        }
    }

    public String runAssistant(String assistantId, String threadId) {
        try {
            RunService runService = openAIClient.beta().threads().runs();

            StreamResponse<AssistantStreamEvent> streamResponse = runService.createStreaming(
                    BetaThreadRunCreateParams.builder()
                                             .threadId(threadId)
                                             .assistantId(assistantId)
                                             .build()
            );
            try (streamResponse) {
                streamResponse.stream().forEach(event -> {
                    if (event.isThreadRunCompleted()) {
                        extractValue(event);
                    } else {
                        if (event.threadMessageDelta().isPresent()) {
                            Optional<List<MessageContentDelta>> delta = event.threadMessageDelta()
                                                                             .get()
                                                                             .data()
                                                                             .delta()
                                                                             .content();
                            if (delta.isPresent()) {
                                delta.get().forEach(contentDelta -> {
                                    if (contentDelta != null) {
                                        if (contentDelta.isText()) {
                                            TextDeltaBlock text = contentDelta.asText();
                                            System.out.println(text.text().get().value());
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }

            return "";
        } catch (Exception exception) {
            throw new InfrastructureException(ErrorCode.FAILED_RUN_ASSISTANT, exception);
        }
    }

    private void extractValue(AssistantStreamEvent event) {
        Message message = event.threadMessageCompleted().orElseThrow(() -> {
            throw new InfrastructureException(ErrorCode.FAILED_EXTRACT_VALUE);
        }).data();

        message.content().forEach(content -> {
            if (content != null) {
                TextContentBlock text = content.asText();
                String value = text.text().value();
                System.out.println(value);
            }
        });
    }

}
