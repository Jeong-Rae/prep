package io.prep.core.llm;

import java.io.File;

public interface llmClient {
    String invoke(String systemPrompt, String message);

    String invokeWithFile(String systemPrompt, File file);
}
