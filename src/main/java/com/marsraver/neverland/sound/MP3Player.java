package com.marsraver.neverland.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MP3Player {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void play(Sound sound) {
        play(this.getClass().getResourceAsStream(sound.file));
    }

    public void play(String classPathResource) {
        play(this.getClass().getResourceAsStream(classPathResource));
    }

    public void play(InputStream inputStream) {
        executorService.submit(new SoundCall(inputStream));
    }

    @AllArgsConstructor
    private class SoundCall implements Runnable {
        private InputStream inputStream;

        @Override
        public void run() {
            try {
                Player player = new Player(inputStream);
                player.play();
            } catch (JavaLayerException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
