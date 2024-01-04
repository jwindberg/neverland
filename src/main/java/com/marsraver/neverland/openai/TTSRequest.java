package com.marsraver.neverland.openai;


import com.google.gson.annotations.SerializedName;

public record TTSRequest(
        Model model,
        String input,
        Voice voice,
        @SerializedName("response_format")
        ResponseFormat responseFormat,
        double speed
) {
    public TTSRequest {
        // Input validation
        if (!validateSpeed(speed)) {
            throw new IllegalArgumentException("Speed must be between 0.25 and 4.0");
        }
    }

    public TTSRequest(Model model, String input, Voice voice) {
        this(model, input, voice, ResponseFormat.MP3, 1.0);
    }

    private boolean validateSpeed(double speed) {
        return speed >= 0.25 && speed <= 4.0;
    }
}
