package project;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by cj on 10/5/2015.
 */
public class GreetingController implements Initializable {
    @FXML
    private TextField greettxt;

    Dialog<String> dialog = new Dialog<>();

    private ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    @FXML
    public void greeting() {
        MainController.greeting = greettxt.getText();
        dialog("Confirmation", "Greeting was changed!");
    }

    private void dialog(String title, String message) {
        dialog.getDialogPane().getButtonTypes().add(close);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    @FXML
    public void goBack() {
        Parent loadScreen;
        try {
            loadScreen = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
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
