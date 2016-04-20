package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import utilities.NextScreen;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for AddModPlugs.fxml file.
 * @author  Clifton West
 * @version October 4, 2015
 */
public class AddModPlugsController implements Observer, Initializable, NextScreen {

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
     * Function assigned to a fxml button that goes to the AddPlugs.fxml screen.
     */
    @FXML
    public void addPlugs() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/AddPlugs.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the ModPlugs.fxml screen.
     */
    @FXML
    public void modPlugs() {
        MainScreen.removeObserver(this);
        goToNextScreen("/fxml/ModPlugs.fxml");
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