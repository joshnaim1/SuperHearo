package com.example.hackathon2;

public class GptRequest {
    private String prompt;
    private int max_tokens;

    public GptRequest(String prompt, int max_tokens) {
        this.prompt = prompt;
        this.max_tokens = max_tokens;
    }
}
