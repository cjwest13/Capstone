package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import utilities.NextScreen;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for AddModPlugs.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class AddModPlugsController implements Initializable, NextScreen {

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
        time();
    }

    /**
     * Animation to show the time.
     */
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
     * Function assigned to a fxml button that goes to the AddPlugs.fxml screen.
     */
    @FXML
    public void addPlugs() {
        NextScreen.super.goToNextScreen("/fxml/AddPlugs.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the ModPlugs.fxml screen.
     */
    @FXML
    public void modPlugs() {
        NextScreen.super.goToNextScreen("/fxml/ModPlugs.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goToSettings() {
        NextScreen.super.goToNextScreen("/fxml/Settings.fxml");
    }
}