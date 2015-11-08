package project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.NextScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cjwest on 10/27/15.
 */
public class ScheduleController implements Initializable, NextScreen {

    @FXML
    private WebView webView;
    private WebEngine webEngine;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        webEngine.load("http://agora.cs.wcu.edu/~ascott/schedule.shtml");
    }


    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
    @FXML
    public void goBack() {
        goToNextScreen("/fxml/main.fxml");

    }
}
