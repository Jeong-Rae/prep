package io.prep.infrastructure.configuration;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
    @Value("${openai.secretkey}")
    private String secretKey;


    @Bean
    OpenAIClient openAIClient() {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                                                .apiKey(secretKey)
                                                .build();
        return client;
    }
}
