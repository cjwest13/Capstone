package project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for AddModPlugs.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 4, 2015
 */
public class AddModPlugsController implements Initializable, NextScreen {

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
