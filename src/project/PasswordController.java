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
public class PasswordController implements Initializable{

    @FXML
    private PasswordField password;

    Dialog<String> dialog = new Dialog<>();

    private ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void changePassword() {
        if (password.getText() == null) {
            dialog("Incorrect Information", "Enter A Password.");
        } else {
            AuthorizeController.adminpw = password.getText();
            dialog("Confirmation", "Password was changed!");
        }
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
