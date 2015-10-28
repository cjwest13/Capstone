package project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Controller for the AddPlugs.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 4, 2015
 */
public class AddPlugsController implements Initializable{
    /** TestArea representing the file text area in the fxml */
    @FXML
    private TextField txtField;
    /** Dialog popup box */
    Dialog<String> dialog;
    /** Close Button for the Dialog box */
    private ButtonType close;
    /** The file selected */
    File file;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

    }

    /**
     *  Finds the jar javafx file of a new plugin.
     */
    @FXML
    public void findPlugin() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        file = chooser.showOpenDialog(MainScreen.getStage());
        txtField.setText(file.toString());
    }

    /**
     * Adds the plugin to the application.
     */
    @FXML
    public void addPlugin() {
        String destDir = "./resources";
        try {
            JarFile jfile = new JarFile(file);
            Enumeration<JarEntry> en = jfile.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                System.out.println(entry.getName());
                File f = new File(destDir + File.separator + entry.getName());
                System.out.println(f.toString());
                if (entry.isDirectory()) {
                    f.mkdir();
                    //System.out.println("mehhhh");

                } else {
                    //System.out.println("yeeee");
                    InputStream input = jfile.getInputStream(entry);
                    //System.out.println("ohhhh");
                    FileOutputStream output = new FileOutputStream(f);
                    //System.out.println("nooo");
                    while (input.available() > 0) {
                        output.write(input.read());
                    }
                    output.close();
                    input.close();
                }

            }
           // for (Enumeration<JarEntry>en = jfile.entries(); en.hasMoreElements();) {
            //    JarEntry entry = en.nextElement();
            //    System.out.println(entry.getName());
            //}
            //Do stuff to add the plugin.
        } catch (IOException ioe) {
            txtField.clear();
            dialog("Incorrect", "Jar File can not be read.");
        }
        goToSettings();
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
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = MainScreen.getStage();
            //stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
