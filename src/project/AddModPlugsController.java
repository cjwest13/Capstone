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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 10/4/2015.
 */
public class AddModPlugsController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void addPlugs() {
        goToNextScreen("/fxml/AddPlugs.fxml");
    }

    @FXML
    public void modPlugs() {
        goToNextScreen("/fxml/ModPlugs.fxml");
    }

    @FXML
    public void goToSettings() {
        goToNextScreen("/fxml/Settings.fxml");
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
