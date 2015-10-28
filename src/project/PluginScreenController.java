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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the PluginScreen.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 3, 2015
 */
public class PluginScreenController implements Initializable {

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
     * Function assigned to a fxml grid that loads the plugin's main screen.
     * @param fxml path to an fxml file.
     */
    public void goToPlugin(String fxml) {
       goToNextScreen(fxml);
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
            stage.setFullScreen(false);
            System.out.println(stage.isFullScreen());
            stage.setFullScreen(true);
            stage.setScene(scene);

            stage.show();


        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }

}
