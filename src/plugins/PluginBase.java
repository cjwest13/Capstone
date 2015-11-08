package plugins;

/**
 * This class describes the fields and methods that will be available to the implemented plugins when they are
 * programmed.
 *
 * @author John Burrell, Clifton West
 * @version 1.0
 */
public interface PluginBase {

    /** This method is called when the plugin is created, and performes desired operation pre-build */
    public void create();

    /** This method is called when the plugin returns to the home screen or some other pausing event occurs */
    public void pause();

    /** This method is called when the plugin is returned to the screen after being paused */
    public void resume();

    /** This method is called when the plugin is closed */
    public void destroy();

    /** This method is called, in mainly a helper way, to save the settings of the plugin */
    public void saveSettings();

    /** This method loads the settings that were saved by the saveSettings() method */
    public void loadSettings();

    /** This method gets the javafx system keyboard if available */
    public void getKeyboard();

    /** Gets the stage of the Applicaton */
    public void getAppMainScreen();
}
