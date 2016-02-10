package API;

import java.io.File;

/**
 * API for the accessing the methods associated with basic App Data.
 * @author  Clifton West
 * @version February 1, 2016
 */
public abstract class AppData {
    /** String that contains the name of the app */
    private String appName = "";

    /** String that contains the description of the app */
    private String description = "";

    /** String that contains the icon of the app */
    private File icon = null;

    /** String that contains the preview of the app */
    private File[] preview = null;

    /**
     * Setter for the appName variable.
     * @param appName String for the app's Name.
     */
    public void setAppName(String appName) {
       this.appName = appName;
    }

    /**
     * Getter for the appName variable.
     * @return String representing the app Name.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Setter for the description variable.
     * @param description String representing the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the description variable.
     * @return String representing the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the icon variable.
     * @param icon String representing the icon.
     */
    public void setIcon(File icon) {
        this.icon = icon;
    }

    /**
     * Getter for the icon variable.
     * @return File representing the icon.
     */
    public File getIcon() {
        return icon;
    }

    /**
     * Setter for the preview variable.
     * @param preview Array of Files for the preview.
     */
    public void setPreview(File[] preview) {
        this.preview = preview;
    }

    /**
     * Getter for the preview variable.
     * @return File[] represent the preview pictures.
     */
    public File[] getPreview() {
        return preview;
    }
}
