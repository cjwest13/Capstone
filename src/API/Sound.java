package API;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * API for accessing the sound capabilities of the terminal.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface Sound {

    default void uploadSound(File sound, String filename) {

    }

    /**
     * Plays the audio file from the beginning giving the String of the
     * file location.
     * @param location  String of the file wanted to be played.
     * @return Clip     The clip that can be used for audio management purposes.
     */
    default Clip playSound(String location) {
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
        return clip;
    }

    /**
     * Pauses the audio file associated with the provided clip.
     * @param clip  The clip associated with the audio file.
     * @return long The time where the clip was paused.
     */
    default long pauseSound(Clip clip) {
        long cliptime = clip.getMicrosecondPosition();
        clip.stop();
        return cliptime;
    }

    /**
     * Resumes the audio file at the specific position.
     * @param clip      The clip associated with the audio file.
     * @param position  The time where the clip was paused.
     */
    default void resumeSound(Clip clip, long position) {
        clip.setMicrosecondPosition(position);
        clip.start();
    }

    /**
     * Ends the audio file associated with the provided clip.
     * @param clip  The clip associated with the audio file.
     */
    default void endSound(Clip clip) {
        clip.stop();
    }

}
