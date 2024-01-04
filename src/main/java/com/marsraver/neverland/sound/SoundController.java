package com.marsraver.neverland.sound;

import com.marsraver.neverland.openai.Voice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sound")
@AllArgsConstructor
public class SoundController {

    private SoundService soundService;

    @GetMapping("play")
    public ResponseEntity<Void> play(@RequestParam Sound sound) {
        soundService.play(sound);
        return ResponseEntity.ok(null);
    }

    @PostMapping("say")
    public ResponseEntity<Void> say(@RequestBody SpeakRequest speakRequest) {
        soundService.say(speakRequest.text, Voice.valueOf(speakRequest.voice));
        return ResponseEntity.ok(null);
    }

    public record SpeakRequest(String text, String voice) {
    }

}
