package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import utilities.NextScreen;
import API.*;

import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the AddPlugs.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class Proof extends AppData implements Initializable, NextScreen, Sound, SystemData {


    /** Dialog popup box */
    Dialog<String> dialog;

    /** Close Button for the Dialog box */
    private ButtonType close;

    /** The file selected */
    File file;

    /** Plugin Directory number */
    private static int num = 0;

    private Clip clip;

    private long position;

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
        position = 0;
        clip = null;
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        num = MainController.getNumOfPlugins();
        time();
    }

    /**
     * Animation to show the time.
     */
    private void time() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            timeLbl.setText(getDateAndTime());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void play() {
        clip = playOnce("/home/cjwest/Documents/welcome.wav");
    }

    @FXML
    public void loop() {
        clip = playLoop("/home/cjwest/Documents/welcome.wav");
    }

    @FXML
    public void resume() {
        resumeSound(clip, position);
    }

    @FXML
    public void stop() {
        position = pauseSound(clip);
    }

    @FXML
    public void end() {
        position = pauseSound(clip);
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void description1() {
        setDescription("omg becky look at that uhhhhh");
        dialog("description", getDescription());
    }

    @FXML
    public void appName() {
        setAppName("sir mix alot");
        dialog("Name", getAppName());
    }

    @FXML
    public void time24() {
        dialog("Name", get12HourTime(false, true));
    }

    @FXML
    public void time12() {
        dialog("Name", get24HourTime(true, false));
    }

    @FXML
    public void date() {
        dialog("Name", getAppDate());
    }

    @FXML
    public void icon() {
        setIcon(new File("/home/cjwest/Documents/Robot_icon.jpg"));
        dialog("Name", getIcon().getAbsolutePath());
    }

    @FXML
    public void preview() {
        File file = new File("/home/cjwest/Documents/preview");
        File[] files = file.listFiles();
        setPreview(files);
        File[] we = getPreview();
        dialog("Name", we[0].getAbsolutePath() +"\n" + we[1].getAbsolutePath() +"\n" + we[2].getAbsolutePath());
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
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }
}