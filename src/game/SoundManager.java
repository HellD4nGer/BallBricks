package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private Clip backgroundClip;
    private boolean isMuted = false;
    private final String BG_MUSIC_FILE = "sound.wav";

    public SoundManager() {
        try {
            File audioFile = new File(BG_MUSIC_FILE);
            if (audioFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioStream);
            } else {
                System.err.println("Audio file not found at: " + audioFile.getAbsolutePath());
                System.err.println("Make sure 'sound.wav' is in the main project folder.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading background music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundClip != null && !isMuted) {
            if (backgroundClip.isRunning()) {
                backgroundClip.stop();
            }
            backgroundClip.setFramePosition(0);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    public void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            stopBackgroundMusic();
            System.out.println("Sound Muted.");
        } else {
            playBackgroundMusic();
            System.out.println("Sound Unmuted. Playing music.");
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
}