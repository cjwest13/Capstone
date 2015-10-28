package sample;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cjwest on 10/27/15.
 */
public class ScheduleController implements Initializable {

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
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), loadScreen);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            Scene scene = new Scene(loadScreen);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
