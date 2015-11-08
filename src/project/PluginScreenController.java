package project;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the PluginScreen.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class PluginScreenController implements Initializable, NextScreen {

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
     * Function assigned to a fxml button that goes to the Main.fxml screen.
     */
    @FXML
    public void goToMain() {
        NextScreen.super.goToNextScreen("/fxml/Main.fxml");
    }

    /**
     * Function assigned to a fxml grid that loads the plugin's main screen.
     * @param fxml path to an fxml file.
     */
    public void goToPlugin(String fxml) {
       //NextScreen.;
    }
}
