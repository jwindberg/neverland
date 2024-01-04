package com.marsraver.neverland.openai;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marsraver.neverland.utilities.FileUtils;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Slf4j
@Service
public class TextToSpeech {
    private static final String TTS_URL = "https://api.openai.com/v1/audio/speech";
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");


    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final HttpClient client = HttpClient.newHttpClient();

    public byte[] generateMp3(TTSRequest ttsRequest) {
        String postBody = gson.toJson(ttsRequest);
        log.info("postBody = {}", postBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TTS_URL))
                .header("Authorization", "Bearer %s".formatted(OPENAI_API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(postBody))
                .build();
        try {
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            byte[] body = response.body();
            String fileName = FileUtils.writeSoundBytesToFile(body);
            log.info("Saved {} to {}", fileName, FileUtils.AUDIO_RESOURCES_PATH);
            response.headers().map().forEach((k, v) -> log.info("Header: {} = {}", k, v));
            return body;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void playMp3UsingJLayer(String fileName) {
        InputStream buffer = new BufferedInputStream(
                Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream("audio/%s".formatted(fileName))));
        try {
            Player player = new Player(buffer);
            player.play();
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAndPlay(String text, Voice voice) {
        TTSRequest ttsRequest = new TTSRequest(Model.TTS_1,
                text.replaceAll("\\s+", " ").trim(), voice);
        byte[] bytes = generateMp3(ttsRequest);
        var bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
        try {
            new Player(bufferedInputStream).play();
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

}