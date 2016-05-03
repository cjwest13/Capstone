package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import utilities.NextScreen;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Controller for the AddPlugs.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class AddPlugsController implements Observer, Initializable, NextScreen {

    /** TestField representing the file text area in the fxml */
    @FXML
    private TextArea txtArea;

    /** Dialog popup box */
    Dialog<String> dialog;

    /** Close Button for the Dialog box */
    private ButtonType close;

    /** The file selected */
    File file;

    /** Plugin Directory number */
    private static int num = 0;

    /** Label representing the label containing the time in the fxml */
    @FXML
    private Label timeLbl;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainScreen.addObserver(this);
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        num = MainController.getNumOfPlugins();
    }

    /**
     *  Finds the jar javafx file of a new plugin.
     */
    @FXML
    public void findPlugin() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        file = chooser.showOpenDialog(MainScreen.getStage());
        if (file != null && file.exists()) {
            txtArea.setText(file.getPath());
        }
    }

    /**
     * Adds the plugin.
     */
    @FXML
    public void addPlugin() {
        num++;
        //String resourceDir = "/home/touchmeister/resources";
        String resourceDir = "/home/cjwest/resources";
        String number = "" + num;
        File destDir = new File(resourceDir, number);
        Boolean success = destDir.mkdir();
        try {
            if (success) {
                //addPath(destDir);
                JarFile jFile = new JarFile(file);
                Enumeration<JarEntry> en = jFile.entries();
                while (en.hasMoreElements()) {
                    JarEntry entry = en.nextElement();
                    File f = new File(destDir.getPath(), entry.getName());
                    if (!f.exists()) {
                        f.getParentFile().mkdirs();
                    }
                    if (entry.isDirectory()) {
                        continue;
                    }
                    InputStream input = jFile.getInputStream(entry);
                    FileOutputStream output = new FileOutputStream(f);
                    while (input.available() > 0) {
                        output.write(input.read());
                    }
                    input.close();
                    output.close();
                }
            }
            boolean rename = file.renameTo(new File(destDir.getPath(), file.getName()));
            if (rename) {
                txtArea.clear();
                dialog("Confirmation", "Plugin was added");
            } else {
                txtArea.clear();
                dialog("Error", "Jar file could not be moved");
            }
        } catch (Exception e) {
            txtArea.clear();
            dialog("Incorrect", "Jar File can not be read.");
        }
    }

    /**
     * Adding file to the classpath.
     * @param f File
     * @throws Exception
     */
    public static void addPath(File f) throws Exception {
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{u.toURL()});
        System.out.println("WHII");
    }

    /**
     * Private method to create the dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     */
    private void dialog(String title, String message) {
        dialog.getDialogPane().getButtonTypes().add(close);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goToSettings() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/Settings.fxml");
    }

    /**
     * Update the time to the time label.
     * @param date Date object that is passed.
     */
    @Override
    public void update(Date date) {
        timeLbl.setText(date.toString());
    }
}