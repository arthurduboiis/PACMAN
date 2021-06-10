package BACK;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio{
    Clip clip;

    /**
     * initialise Audio
     * @param source String
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Audio(String source) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                new File(source));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
    }

    /**
     * joue
     */
    public void jouer(){
        clip.start();
    }

    /**
     * pause
     */
    public void pause(){
        clip.stop();
    }

    /**
     * boucle son
     */
    public void loop(){
        clip.loop(50);
    }


}