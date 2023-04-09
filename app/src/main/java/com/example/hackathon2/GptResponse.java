package com.example.hackathon2;

public class GptResponse {
    private Choices[] choices;

    public Choices[] getChoices() {
        return choices;
    }

    public static class Choices {
        private String text;

        public String getText() {
            return text;
        }
    }
}
