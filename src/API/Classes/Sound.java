package API.Classes;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Sound Class that contains Sound Management Methods.
 * @author Clifton West
 * @version April 17, 2016.
 */
public class Sound implements controller.Interface.SoundInterface {

    /** Arraylist for the all of the clips */
    private static ArrayList<Clip> sound;

    /**
     * Constructor for the Sound Class.
     */
    public Sound() {
        sound = new ArrayList();
    }
    /**
     * Plays the audio file from the beginning giving the String of the
     * file location once.
     * @param location  String of the file wanted to be played.
     * @return Clip     The clip that can be used for audio management purposes.
     */
    @Override
    public Clip playOnce(String location) {
        Clip clip = null;
        try {
            File file = new File(location);
            clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip.open(stream);
            clip.loop(0);
        } catch (UnsupportedAudioFileException afe) {
            System.out.println("This Audio File is not supported.");
        } catch (IOException ioe) {
            System.out.println("Audio File could not be played.");
        } catch (LineUnavailableException lue) {
            System.out.println("Me no play");
        } catch (NullPointerException npe) {
            System.out.println("File could not be found.");
        }
        sound.add(clip);
        return clip;
    }

    /**
     * Plays the audio file from the beginning giving the String of the
     * file location continuously.
     * @param location  String of the file wanted to be played.
     * @return Clip     The clip that can be used for audio management purposes.
     */
    @Override
    public Clip playLoop(String location) {
        Clip clip = null;
        try {
            File file = new File(location);
            clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException afe) {
            System.out.println("This Audio File is not supported.");
        } catch (IOException ioe) {
            System.out.println("Audio File could not be played.");
        } catch (LineUnavailableException lue) {
            System.out.println("Me no play");
        } catch (NullPointerException npe) {
            System.out.println("File could not be found.");
        }
        sound.add(clip);
        return clip;
    }

    /**
     * Pauses the audio file associated with the provided clip.
     * @param clip  The clip associated with the audio file.
     * @return long The time where the clip was paused.
     */
    @Override
    public long pauseSound(Clip clip) {
        long cliptime = clip.getMicrosecondPosition();
        clip.stop();
        return cliptime;
    }

    /**
     * Resumes the audio file at the specific position.
     * @param clip      The clip associated with the audio file.
     * @param position  The time where the clip was paused.
     */
    @Override
    public void resumeSound(Clip clip, long position) {
        clip.setMicrosecondPosition(position);
        clip.start();
    }

    /**
     * Ends the audio file associated with the provided clip.
     * @param clip  The clip associated with the audio file.
     */
    @Override
    public void endSound(Clip clip) {
        clip.stop();
    }
    /**
     * Stops all Sounds.
     */
    public void endAllSounds() {
        for (Clip clip: sound) {
            clip.stop();
        }
        sound.clear();
    }

}
