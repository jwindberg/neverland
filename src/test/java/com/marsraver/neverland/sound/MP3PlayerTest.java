package com.marsraver.neverland.sound;

import org.junit.jupiter.api.Test;

class MP3PlayerTest {

  private static final String RESOURCE = "/sounds/sonar-ping.mp3";

  MP3Player player = new MP3Player();

  @Test
  void play() {
    player.play(Sound.SONAR_SWEEP);
  }
}