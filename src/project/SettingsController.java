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
 * Created by cj on 10/3/2015.
 */
public class SettingsController implements Initializable {

    @FXML
    private Button backbtn;


    //private ScreensController myController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    public void goToMain() {
        goToNextScreen("/fxml/Main.fxml");
        //myController.setScreen(MainScreen.screen1);
    }
    @FXML
    public void goToOS() {
        Platform.exit();
    }

    @FXML
    public void goToChangePw() {
        goToNextScreen("/fxml/Password.fxml");
    }

    @FXML
    public void goToAddModPlugs() {
    goToNextScreen("/fxml/AddModPlugs.fxml");

    }

    @FXML
    public  void goToGreeting() {
        goToNextScreen("/fxml/Greeting.fxml");
    }

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
            //stage.setFullScreen(true);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
