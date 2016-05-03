package API.Interface;

import javax.sound.sampled.Clip;

/**
 * API for accessing the sound capabilities of the terminal.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface SoundInterface {

    /**
     * Plays the audio file from the beginning giving the String of the
     * file location once.
     * @param location  String of the file wanted to be played.
     * @return Clip     The clip that can be used for audio management purposes.
     */
    Clip playOnce(String location);

    /**
     * Plays the audio file from the beginning giving the String of the
     * file location continuously.
     * @param location  String of the file wanted to be played.
     * @return Clip     The clip that can be used for audio management purposes.
     */
    Clip playLoop(String location);

    /**
     * Pauses the audio file associated with the provided clip.
     * @param clip  The clip associated with the audio file.
     * @return long The time where the clip was paused.
     */
    long pauseSound(Clip clip);

    /**
     * Resumes the audio file at the specific position.
     * @param clip      The clip associated with the audio file.
     * @param position  The time where the clip was paused.
     */
    void resumeSound(Clip clip, long position);

    /**
     * Ends the audio file associated with the provided clip.
     * @param clip  The clip associated with the audio file.
     */
     void endSound(Clip clip);
}
