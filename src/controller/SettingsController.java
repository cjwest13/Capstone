package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import utilities.NextScreen;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for Password.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class SettingsController implements Observer, Initializable, NextScreen {

    /** Button representing the back Button in the fxml */
    @FXML
    private Button backbtn;

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
    }

    /**
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/Main.fxml");
    }

    /**
     * Function assigned to a fxml button closes the application.
     */
    @FXML
    public void goToOS() {
        MainScreen.removeObserver(this);
        Platform.exit();
        System.exit(0);
    }

    /**
     * Function assigned to a fxml button that goes to the Password.fxml screen.
     */
    @FXML
    public void goToChangePw() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/Password.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the AddModPlugs.fxml screen.
     */
    @FXML
    public void goToAddModPlugs() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/AddModPlugs.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the Greeting.fxml screen.
     */
    @FXML
    public  void goToGreeting() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/Greeting.fxml");
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