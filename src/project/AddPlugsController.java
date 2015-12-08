package project;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Controller for the AddPlugs.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 4, 2015
 */
public class AddPlugsController implements Initializable, NextScreen {
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
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        num = MainController.getNumOfPlugins();
        time();
    }

    private void time() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            timeLbl.setText(time.toString());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     *  Finds the jar javafx file of a new plugin.
     */
    @FXML
    public void findPlugin() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        file = chooser.showOpenDialog(MainScreen.getStage());
        txtArea.setText(file.getPath());
    }

    /**
     * Adds the plugin.
     */
    @FXML
    public void addPlugin() {
        num++;
        String resourceDir = "/home/touchmeister/resources";
        String number = "" + num;
        File destDir = new File(resourceDir, number);
        Boolean success = destDir.mkdir();
        try {
            if (success) {
                JarFile jFile = new JarFile(file);
                Enumeration<JarEntry> en = jFile.entries();
                while (en.hasMoreElements()) {
                    JarEntry entry = en.nextElement();
                    File f = new File(destDir.getPath(), entry.getName());
                    if (entry.isDirectory()) {
                        f.mkdir();
                    } else {
                        InputStream input = jFile.getInputStream(entry);
                        FileOutputStream output = new FileOutputStream(f);
                        while (input.available() > 0) {
                            output.write(input.read());
                        }
                        output.close();
                        input.close();
                    }
                }
                File place = new File("/home/touchmeister/resources", number);
                boolean yes = file.renameTo(new File(place.getPath(), file.getName()));
                //System.out.println(yes);
                dialog("Confirmation", "Plugin was added");
            } else {
                txtArea.clear();
                dialog("Incorrect", "Issue with creating the directories.");
            }
        } catch (IOException ioe) {
            txtArea.clear();
            dialog("Incorrect", "Jar File can not be read.");
        }
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

    public int getNum() {
        return  num;
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goToSettings() {
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }
}
