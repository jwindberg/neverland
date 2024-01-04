package com.marsraver.neverland.openai;

import org.springframework.beans.factory.annotation.Value;

public class OpenAiClient {
    private String apiKey;

    public OpenAiClient(@Value("${spring.ai.openai.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }
}
