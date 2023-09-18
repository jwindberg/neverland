package com.marsraver.neverland.sound;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Sound {

  SONAR_PING("/sounds/sonar-ping.mp3"), SONAR_SWEEP("/sounds/sonar-sweep-beep.mp3");

  String file;
}
