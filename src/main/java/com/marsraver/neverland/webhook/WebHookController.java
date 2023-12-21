package com.marsraver.neverland.webhook;

import com.marsraver.neverland.sound.Sound;
import com.marsraver.neverland.webhook.WebHookSoundService.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("webhook")
public class WebHookController {

    private WebHookSoundService webHookSoundService;

    @PostMapping
    public ResponseEntity<Member> register(@RequestBody Registration registration) {
        return ResponseEntity.ok(
                webHookSoundService.register(registration.hostName, "http://" + registration.hostName + ":8080/sound/play", registration.getSound()));
    }

    @DeleteMapping("{hostName}")
    public ResponseEntity<Member> delete(@PathVariable String hostName) {
        return ResponseEntity.ok(
                webHookSoundService.unRegister(hostName));
    }

    @GetMapping
    public ResponseEntity<Collection<Member>> getMembers() {
        return ResponseEntity.ok(
                webHookSoundService.getMembers());
    }

    @GetMapping("play")
    public ResponseEntity<Void> play() {
        webHookSoundService.playSound();
        return ResponseEntity.ok(null);
    }

    @GetMapping("playOneAtAtTime")
    public ResponseEntity<Void> playOneAtAtTime() {
        webHookSoundService.playOneAtAtTime();
        return ResponseEntity.ok(null);
    }

    @Data
    public static class Registration {
        private String hostName;
        private Sound sound = Sound.SONAR_PING;
    }

}
