package com.marsraver.neverland.sound;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sound/play")
@AllArgsConstructor
public class SoundController {

    private MP3Player player;

    @GetMapping
    public ResponseEntity<Void> play(@RequestParam Sound sound) {
        player.play(sound);
        return ResponseEntity.ok(null);
    }

}
