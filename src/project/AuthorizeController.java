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
public class AuthorizeController implements Initializable{
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private String adminname = "admin";

    public static String adminpw;

    Dialog<String> dialog = new Dialog<>();

    private ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changePassword(adminpw);
    }
    private void changePassword(String text) {
        if (adminpw == null) {
            adminpw = "touch";
        } else {
            adminpw = text;
        }
    }
    @FXML
    public void goToMain() {
        goToNextScreen("/fxml/Main.fxml");
        //myController.setScreen(MainScreen.screen1);
    }

    @FXML
    public void getInfo() {
        if (!username.getText().equals(adminname)) {
            dialog("Incorrect Information", "The Username is Incorrect.");
        } else if (!password.getText().equals(adminpw)) {
            dialog("Incorrect Information", "The Password is Incorrect.");
        } else {
            goToSettings();
        }
    }

    private void goToSettings() {
        goToNextScreen("/fxml/Settings.fxml");
    }

    private void dialog(String title, String message) {
        dialog.getDialogPane().getButtonTypes().add(close);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setContentText(message);
        dialog.showAndWait();
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
