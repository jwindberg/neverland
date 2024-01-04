package com.marsraver.neverland.sound;

import com.marsraver.neverland.openai.Model;
import com.marsraver.neverland.openai.TTSRequest;
import com.marsraver.neverland.openai.TextToSpeech;
import com.marsraver.neverland.openai.Voice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class SoundService {
    static record SoundKey(String text, Voice voice) {
    }

    private Map<SoundKey, byte[]> storage = new HashMap<>();

    private MP3Player mp3Player;

    private TextToSpeech textToSpeech;

    public void play(Sound sound) {
        mp3Player.play(sound);
    }

    private byte[] retrieve(SoundKey soundKey) {
        TTSRequest ttRequest = new TTSRequest(Model.TTS_1,
                soundKey.text.replaceAll("\\s+", " ").trim(), soundKey.voice);
        return textToSpeech.generateMp3(ttRequest);
    }

    public void say(String text, Voice voice) {
        SoundKey soundKey = new SoundKey(text, voice);
        byte[] bytes = storage.computeIfAbsent(soundKey, this::retrieve);
        mp3Player.play(new ByteArrayInputStream(bytes));
    }
}
