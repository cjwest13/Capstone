package project;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for ModPlugs.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class ModPlugsController implements Initializable {

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
     * Changes the following plugin.
     */
    @FXML
    public void editPlugin() {

    }

    /**
     * Deletes the following plugin.
     */
    @FXML
    public void deletePlugin() {

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
