package com.marsraver.neverland.webhook;

import com.marsraver.neverland.sound.Sound;
import com.marsraver.neverland.webhook.WebHookSoundService.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("webhook")
public class WebHookController {

  private WebHookSoundService webHookSoundService;

  @PostMapping
  public ResponseEntity<Member> register(@RequestBody Registration registration) {
    return ResponseEntity.ok(
        webHookSoundService.register(registration.hostName, registration.rootUrl));
  }

  @DeleteMapping("{hostName}")
  public ResponseEntity<Member> delete(@PathVariable String hostName) {
    return ResponseEntity.ok(
        webHookSoundService.unRegister(hostName));
  }

  @GetMapping
  public ResponseEntity<Void> play(Sound sound) {
    webHookSoundService.playSound(sound);
    return ResponseEntity.ok(null);
  }

  @Data
  public static class Registration {

    private String hostName;
    private String rootUrl;
  }

}
