package apcs;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundThread extends Thread // only tested with .wav files.
{

    File toPlay;
    
    boolean loop = false;
    
    
    public SoundThread(){}
    
    public SoundThread(boolean toLoop)
    {
        super();
        this.loop = toLoop;
    }
    
    public void run() 
    {
        try
        {
            do 
            {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(toPlay);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000); // makes sure sound finishes before termination of program
            }
            while (loop == true);
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void playSound(String filePath)
    { // creates a new soundthread for each sound played, so only one object needs to be created in calling thread
        new SoundThread().playSoundHelper(filePath);
    }
    
    public void playSoundLooped(String filePath)
    { // same as playSound but with loop
        new SoundThread(true).playSoundHelper(filePath);
    }

    private void playSoundHelper(String filePath) 
    { // actually plays the sound file
        toPlay = new File(filePath);
        start();
    }
}