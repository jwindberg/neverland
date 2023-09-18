package com.marsraver.neverland.sound;

import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.stereotype.Component;

@Component
public class MP3Player {

  public void play(Sound sound) {
    play(this.getClass().getResourceAsStream(sound.file));
  }


  public void play(String classPathResource) {
    play(this.getClass().getResourceAsStream(classPathResource));
  }

  public void play(InputStream inputStream) {
    try {
      Player player = new Player(inputStream);
      player.play();
    } catch (JavaLayerException e) {
      throw new RuntimeException(e);
    }
  }

}
