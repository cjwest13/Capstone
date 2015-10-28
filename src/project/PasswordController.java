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
 * Controller class for Password.fxml file.
 * @author  Clifton West, John Burrell
 * @version October 4, 2015
 */
public class PasswordController extends AuthorizeController implements Initializable{

    /** PasswordField representing the password password field in the fxml */
    @FXML
    private PasswordField password;
    /** Dialog popup box */
    private Dialog<String> dialog;
    /** Close Button for the Dialog box */
    private ButtonType close;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog = new Dialog<>();
        close = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
    }

    /**
     * Changes the Password for the admin.
     */
    @FXML
    public void changePassword() {
        if (password.getText() == null) {
            dialog("Incorrect Information", "Enter A Password.");
        } else {
            changePassword(password.getText());
            dialog("Confirmation", "Password was changed!");
        }
     }

    /**
     * Private method to create the dialog popup box.
     * @param title     Title of the dialog box.
     * @param message   Message inside of the dialog box.
     */
    private void dialog(String title, String message) {
        dialog.getDialogPane().getButtonTypes().add(close);
        dialog.setTitle(title);
        dialog.setHeight(200);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Function assigned to a fxml button that goes to the Settings.fxml screen.
     */
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
            //stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }

    }
}
