package project;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
 * Controller class for Password.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class SettingsController implements Initializable {
    /** Button representing the back Button in the fxml */
    @FXML
    private Button backbtn;

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
        goToNextScreen("/fxml/Main.fxml");
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
        goToNextScreen("/fxml/Password.fxml");
    }

    /**
     * Function assigned to a fxml button that goes to the AddModPlugs.fxml screen.
     */
    @FXML
    public void goToAddModPlugs() {
    goToNextScreen("/fxml/AddModPlugs.fxml");

    }

    /**
     * Function assigned to a fxml button that goes to the Greeting.fxml screen.
     */
    @FXML
    public  void goToGreeting() {
        goToNextScreen("/fxml/Greeting.fxml");
    }

    /**
     * Goes to the screen according to the fxml file that is passed.
     * @param fxml path to an fxml file.
     */
    private void goToNextScreen(String fxml) {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource(fxml));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = MainScreen.getStage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
