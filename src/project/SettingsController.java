package project;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for Password.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class SettingsController implements Initializable, NextScreen {
    /** Button representing the back Button in the fxml */
    @FXML
    private Button backbtn;

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
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        NextScreen.super.goToNextScreen("/fxml/Main.fxml");
    }

    /**
     * Function assigned to a fxml button closes the application.
     */
    @FXML
    public void goToOS() {
        Platform.exit();
    }

    /**
     * Function assigned to a fxml button that goes to the Password.fxml screen.
     */
    @FXML
    public void goToChangePw() {
        NextScreen.super.goToNextScreen("/fxml/Password.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the AddModPlugs.fxml screen.
     */
    @FXML
    public void goToAddModPlugs() {
        NextScreen.super.goToNextScreen("/fxml/AddModPlugs.fxml");

    }

    /**
     * Function assigned to a fxml button that goes to the Greeting.fxml screen.
     */
    @FXML
    public  void goToGreeting() {
        NextScreen.super.goToNextScreen("/fxml/Greeting.fxml");
    }
}