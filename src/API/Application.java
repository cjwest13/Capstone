package API;

import java.io.File;
import java.net.URL;

/**
 * This class describes the fields and methods that will be available to the implemented API when they are
 * programmed.
 *
 * @author  Clifton West
 * @version 1.0
 */
public interface Application {

    Boolean appDidLanch();

    Boolean appDidClose();

    Boolean appDidPause();

    void closeApp();

    void pauseApp();

    String getData(URL address, String params);

    String putData(URL address, String params);


    void setAppName(String appName);

    String getAppName();

    void setDescription(String description);

    void setIcon(File icon);

    File getIcon();

    void setPreview(File[] preview);

    File[] getPreview();

    void keyPressed(int keyID);

    void tapDown(int x, int y);

    void tapUp(int x, int y);

    void slide(int x, int x2, int y, int y2);

    void tapDragStopped(int[] x, int[] y);

    void mutlitaps();

    void saveState();

    void loadState();

    void dateAndTime();

    void getDate();

    void getAppTime();

    void guiScrenn1();

    void guiScreen2();

    void guiComponent1();

    void guiComponent2();

    void getKeyboard();


}
